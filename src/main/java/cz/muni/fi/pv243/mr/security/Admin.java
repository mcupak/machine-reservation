package cz.muni.fi.pv243.mr.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jboss.seam.security.annotations.SecurityBindingType;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@SecurityBindingType
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface  Admin {
}
