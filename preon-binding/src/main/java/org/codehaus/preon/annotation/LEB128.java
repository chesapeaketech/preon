package org.codehaus.preon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate field is expected to use LEB128 encoding format. May be used in conjunction with
 * {@link BoundNumber} to indicate if Unsigned. BoundNumber annotation is unnecessary if value is signed.
 *
 * @author Copyright &#169; 2021 Chesapeake Technology International Corp.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LEB128
{
}
