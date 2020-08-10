package com.memoire.mystorage.web.security.shiro;

import com.memoire.mystorage.api.dao.security.ProfilRoleDaoBeanLocal;
import com.memoire.mystorage.api.dao.security.ProfilUtilisateurDaoBeanLocal;
import com.memoire.mystorage.api.dao.security.UtilisateurDaoBeanLocal;
import com.memoire.mystorage.api.entities.security.Profil;
import com.memoire.mystorage.api.entities.security.ProfilRole;
import com.memoire.mystorage.api.entities.security.ProfilUtilisateur;
import com.memoire.mystorage.api.entities.security.Utilisateur;
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

    protected static List<ProfilUtilisateur> profilUtilisateurs;
    protected static List<ProfilRole> profilRoles;
    protected static ProfilUtilisateurDaoBeanLocal pudbl;
    protected static UtilisateurDaoBeanLocal udbl;
    protected static ProfilRoleDaoBeanLocal prdbl;
    protected static ProfilUtilisateur profilUtilisateur;
    protected static Utilisateur utilisateur;
    protected static Profil profil;

    public EntityRealm() throws NamingException {
        CredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher("SHA-256");
        this.setCredentialsMatcher(credentialsMatcher);
        InitialContext context = new InitialContext();
        //La classe session bean de l'utilisateur(précise la classe du sesion bean)
        EntityRealm.pudbl = (ProfilUtilisateurDaoBeanLocal) context.lookup("java:global/MyStorage/ProfilUtilisateurDaoBean");
        EntityRealm.udbl = (UtilisateurDaoBeanLocal) context.lookup("java:global/MyStorage/UtilisateurDaoBean");
        //La classe session bean des roles par profil(précise la classe du sesion bean)
        EntityRealm.prdbl = (ProfilRoleDaoBeanLocal) context.lookup("java:global/MyStorage/ProfilRoleDaoBean");

    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {

        final UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

        utilisateur = udbl.getOneBy("login", token.getUsername());

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo();
        try {
            if (utilisateur != null) {

                simpleAuthenticationInfo = new SimpleAuthenticationInfo(utilisateur, utilisateur.getPasswd(), getName());

            } else {
                simpleAuthenticationInfo = null;
                throw new UnknownAccountException("Utilisateur inconnu");
            }
        } catch (UnknownAccountException e) {
        }
        return simpleAuthenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        utilisateur = (Utilisateur) principals.fromRealm(this.getName()).iterator().next();
        final SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        if (utilisateur != null) {
            profilUtilisateurs = pudbl.getBy("utilisateurProfil", "etat", utilisateur, true);
            if (!profilUtilisateurs.isEmpty()) {
                for (ProfilUtilisateur pu : profilUtilisateurs) {
                    profilRoles = prdbl.getBy("profil", "etat", pu.getProfil(), true);
                    final List<String> roles = new ArrayList<>();
                    for (ProfilRole proRole : profilRoles) {
                        roles.add(proRole.getRole().getNom());
                    }
                    info.addRoles(roles);
                }

            }
            return info;
        } else {
            return null;
        }
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }
}
