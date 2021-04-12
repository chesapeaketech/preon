package org.codehaus.preon.codec.helper;

import org.codehaus.preon.annotation.BoundNumber;
import org.codehaus.preon.annotation.LEB128;

/**
 * Helper class for verifying decoding/encoding
 *
 * @author Copyright &#169; 2021 Chesapeake Technology International Corp.
 */
public class Leb128UnsignedShortWrapper
{
    @BoundNumber(unsigned = true)
    @LEB128
    public Short value;

    public Leb128UnsignedShortWrapper()
    {
    }

    public Leb128UnsignedShortWrapper(Short value)
    {
        this.value = value;
    }
}
