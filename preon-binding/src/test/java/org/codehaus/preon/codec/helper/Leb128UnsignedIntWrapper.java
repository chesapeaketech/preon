package org.codehaus.preon.codec.helper;

import org.codehaus.preon.annotation.BoundNumber;
import org.codehaus.preon.annotation.LEB128;

/**
 * Helper class for verifying decoding/encoding
 *
 * @author Copyright &#169; 2021 Chesapeake Technology International Corp.
 */
public class Leb128UnsignedIntWrapper
{
    @BoundNumber(unsigned = true)
    @LEB128
    public Integer value;

    public Leb128UnsignedIntWrapper()
    {
    }

    public Leb128UnsignedIntWrapper(Integer value)
    {
        this.value = value;
    }
}
