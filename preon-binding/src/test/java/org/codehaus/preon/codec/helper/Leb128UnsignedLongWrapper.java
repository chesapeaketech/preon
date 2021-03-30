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
    @BoundNumber(unsinged = true)
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
