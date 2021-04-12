package org.codehaus.preon.codec.helper;

import org.codehaus.preon.annotation.LEB128;

/**
 * Helper class for verifying decoding/encoding
 *
 * @author Copyright &#169; 2021 Chesapeake Technology International Corp.
 */
public class Leb128SignedByteWrapper
{
    @LEB128
    public Byte value;

    public Leb128SignedByteWrapper()
    {
    }

    public Leb128SignedByteWrapper(Byte value)
    {
        this.value = value;
    }
}
