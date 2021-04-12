package org.codehaus.preon.codec.helper;

import org.codehaus.preon.annotation.BoundNumber;
import org.codehaus.preon.annotation.LEB128;

/**
 * Helper class for verifying decoding/encoding
 *
 * @author Copyright &#169; 2021 Chesapeake Technology International Corp.
 */
public class Leb128UnsignedByteWrapper
{
    @BoundNumber(unsigned = true)
    @LEB128
    public Byte value;

    public Leb128UnsignedByteWrapper()
    {
    }

    public Leb128UnsignedByteWrapper(Byte value)
    {
        this.value = value;
    }
}
