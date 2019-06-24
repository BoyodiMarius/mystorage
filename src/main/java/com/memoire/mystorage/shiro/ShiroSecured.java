/*
 * Développée par TCHAKPEDEOU Paul, Ing. des Travaux Informatiques,
 * Diplomé de l'IAI - TOGO, Promotion 2015.
 * copyright 2016, Paul Abram Informatics.
 */
package com.memoire.mystorage.shiro;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.interceptor.InterceptorBinding;

/**
 *
 * @author PaulAbram
 */
@Inherited
@InterceptorBinding
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ShiroSecured {

}
