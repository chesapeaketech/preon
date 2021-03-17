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

package org.codehaus.preon.codec;

import org.codehaus.preon.channel.BitChannel;
import org.codehaus.preon.codec.INumericType;

import java.io.IOException;

/**
 * TODO: class description
 *
 * @author Copyright &#169; 2021 Chesapeake Technology International Corp.
 * @since
 */
public interface IIntegerType extends INumericType
{
    int getVarIntSize(Number number);
    Object decodeVarInt();
    void encodeVarInt(BitChannel channel, Object value) throws IOException;
}
