package org.codehaus.preon.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Allows the field order to be specified.
 *
 * @author Copyright &#169; 2021 Chesapeake Technology International Corp.
 * @since
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldOrder
{
    int index();
}
