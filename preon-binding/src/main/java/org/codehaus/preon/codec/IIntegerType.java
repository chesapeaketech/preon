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

import org.codehaus.preon.DecodingException;
import org.codehaus.preon.buffer.BitBuffer;
import org.codehaus.preon.channel.BitChannel;

import java.io.IOException;

/**
 * Operations only applicable to whole number (integer) numeric types.
 *
 * @author Copyright &#169; 2021 Chesapeake Technology International Corp.
 */
public interface IIntegerType extends INumericType
{
    // (Number of bytes in the type * number of bits in a byte + (number of non-continuation bits in LEB128 - 1 to make it round up))
    //  / number of non-continuation bits in LEB128
    int MAX_LONG_LEB128_BYTES = (Long.BYTES * 8 + 6) / 7;
    int MAX_INT_LEB128_BYTES = (Integer.BYTES * 8 + 6) / 7;
    int MAX_SHORT_LEB128_BYTES = (Short.BYTES * 8 + 6) / 7;
    int MAX_BYTE_LEB128_BYTES = (Byte.BYTES * 8 + 6) / 7;

    int getLeb128Size(Number number);

    Object decodeLeb128(BitBuffer bitBuffer) throws DecodingException;

    void encodeLeb128(BitChannel channel, Object value) throws IOException;
}
