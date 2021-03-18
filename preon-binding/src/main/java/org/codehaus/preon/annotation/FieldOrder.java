//========================================================================
//
//                       U N C L A S S I F I E D
//
//========================================================================
//  Copyright (c) 2021 Chesapeake Technology International Corp.
//  ALL RIGHTS RESERVED
//  This material may be reproduced by or for the U.S. Government
//  pursuant to the copyright license under the clause at
//  DFARS 252.227-7013 (OCT 1988).
//=======================================================================

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
