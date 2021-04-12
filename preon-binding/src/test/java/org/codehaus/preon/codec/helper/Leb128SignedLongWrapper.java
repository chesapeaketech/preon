package org.codehaus.preon.codec.helper;

import org.codehaus.preon.annotation.LEB128;

/**
 * Helper class for verifying decoding/encoding
 *
 * @author Copyright &#169; 2021 Chesapeake Technology International Corp.
 */
public class Leb128SignedLongWrapper
{
    @LEB128
    public Long value;

    public Leb128SignedLongWrapper()
    {
    }

    public Leb128SignedLongWrapper(Long value)
    {
        this.value = value;
    }
}
