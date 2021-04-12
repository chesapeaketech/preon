package org.codehaus.preon.codec.helper;

import org.codehaus.preon.annotation.BoundNumber;
import org.codehaus.preon.annotation.LEB128;

/**
 * Helper class for verifying decoding/encoding
 *
 * @author Copyright &#169; 2021 Chesapeake Technology International Corp.
 */
public class Leb128UnsignedLongWrapper
{
    @BoundNumber(unsigned = true)
    @LEB128
    public Long value;

    public Leb128UnsignedLongWrapper()
    {
    }

    public Leb128UnsignedLongWrapper(Long value)
    {
        this.value = value;
    }
}
