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

import org.codehaus.preon.buffer.ByteOrder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO: class description
 *
 * @author Copyright &#169; 2021 Chesapeake Technology International Corp.
 * @since
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface VarInt
{
    /**
     * The type of endianness: either {@link ByteOrder#LittleEndian} or {@link ByteOrder#BigEndian}.
     *
     * @return The type of endianness. Defaults to {@link ByteOrder#LittleEndian}.
     */
//    ByteOrder byteOrder() default ByteOrder.LittleEndian;

    /**
     * The base to encode in.
     *
     * @return The type of endianness. Defaults to 128.
     */
    int base() default 128;
    boolean unsinged() default false;
}
