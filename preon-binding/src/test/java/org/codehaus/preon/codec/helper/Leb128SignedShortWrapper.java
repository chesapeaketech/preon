package org.codehaus.preon.codec.helper;

import org.codehaus.preon.annotation.LEB128;

/**
 * Helper class for verifying decoding/encoding
 *
 * @author Copyright &#169; 2021 Chesapeake Technology International Corp.
 */
public class Leb128SignedShortWrapper
{
    @LEB128
    public Short value;

    public Leb128SignedShortWrapper()
    {
    }

    public Leb128SignedShortWrapper(Short value)
    {
        this.value = value;
    }
}
