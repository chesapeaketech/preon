package org.codehaus.preon.codec.helper;

import org.codehaus.preon.annotation.LEB128;

/**
 * Helper class for verifying decoding/encoding
 *
 * @author Copyright &#169; 2021 Chesapeake Technology International Corp.
 */
public class Leb128SignedIntWrapper
{
    @LEB128
    public Integer value;

    public Leb128SignedIntWrapper()
    {
    }

    public Leb128SignedIntWrapper(Integer value)
    {
        this.value = value;
    }
}
