package com.memoire.mystorage.shiro;

import com.memoire.mystorage.dao.ProfilRoleDaoBeanLocal;
import com.memoire.mystorage.dao.UtilisateurDaoBeanlocal;
import com.memoire.mystorage.entities.Profil;
import com.memoire.mystorage.entities.ProfilRole;
import com.memoire.mystorage.entities.Utilisateur;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

public class EntityRealm extends AuthorizingRealm {

    protected UtilisateurDaoBeanlocal udb;
    protected ProfilRoleDaoBeanLocal prdbl;
    protected static Utilisateur utilisateur;
    protected static Profil profil;
    protected static List<ProfilRole> profilRoles;

    public EntityRealm() throws NamingException {
        System.out.println("enter entity realm");
        this.setName("entityRealm");
        CredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher("SHA-256");
        this.setCredentialsMatcher(credentialsMatcher);
        InitialContext context = new InitialContext();
        this.udb = (UtilisateurDaoBeanlocal) context.lookup("java:global/MyStorage/UtilisateurDaoBean");
        this.prdbl = (ProfilRoleDaoBeanLocal) context.lookup("java:global/MyStorage/ProfilRoleDaoBean");

        System.out.println("out entity realm");
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {

        final UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

        utilisateur = udb.getOneBy("login", token.getUsername());

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo();
        try {
            if (utilisateur != null) {

                simpleAuthenticationInfo = new SimpleAuthenticationInfo(utilisateur.getLogin(), utilisateur.getPass(), getName());

            } else {
                simpleAuthenticationInfo = null;
                throw new UnknownAccountException("Utilisateur inconnu");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return simpleAuthenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userId = (String) principals.fromRealm(this.getName()).iterator().next();
        utilisateur = udb.getOneBy("login", userId);
        if (utilisateur != null) {
            final SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            profilRoles = this.prdbl.getBy("profil", utilisateur.getProfil());

            final List<String> roles = new ArrayList<>();
            for (ProfilRole proRole : profilRoles) {
                roles.add(proRole.getRole().getNom());
            }
            info.addRoles(roles);

            return info;
        } else {
            return null;
        }
    }

    /*@Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        
        SimpleAuthorizationInfo info = null;

        try {
            String userId = (String) principals.fromRealm(this.getName()).iterator().next();
            personnel = pdaol.getOneBy("login", userId);
            if (personnel != null) {
               
                info = new SimpleAuthorizationInfo();
                String nomGroupeUtil = personnel.getCategoriePersonnel().getNom();
                info.addRole(nomGroupeUtil);
                
                final List<String> perm = new ArrayList<>();
                for (ProfilRole ca : personnel.getCategoriePersonnel().getCategoriePersonnelRoles()) {
                    perm.add(ca.getRole().getNom());
                }
                info.addStringPermissions(perm);

            }
        } catch (Exception e) {

        }
        return info;
    }*/
    public static Utilisateur getUser() {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated()) {
            return utilisateur;
        }
        return null;
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();

    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

}
