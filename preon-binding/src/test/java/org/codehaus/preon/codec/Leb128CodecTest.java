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

import org.codehaus.preon.Codec;
import org.codehaus.preon.Codecs;
import org.codehaus.preon.DecodingException;
import org.codehaus.preon.codec.helper.Leb128SignedLongWrapper;
import org.codehaus.preon.codec.helper.Leb128UnsignedLongWrapper;
import org.codehaus.preon.codec.helper.Leb128UnsignedShortWrapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Testing for LEB128 encoding/decoding.
 *
 * @author Copyright &#169; 2021 Chesapeake Technology International Corp.
 * //TODO: Add validation for other types.
 */
public class Leb128CodecTest
{
    byte[] unsignedLongEncodedBytes = {(byte) 0x80, (byte) 0xF2, (byte) 0x8B, (byte) 0xA8, (byte) 0x09};
    long unsignedLong = 2_500_000_000L;
    byte[] signedLongEncodedBytes = {(byte) 0x80, (byte) 0x8E, (byte) 0xF4, (byte) 0xD7, (byte) 0x76};
    long signedLong = -2_500_000_000L;

    @Test
    public void testUnsignedLongEncoding()
    {
        Leb128UnsignedLongWrapper test = new Leb128UnsignedLongWrapper(unsignedLong);
        Codec<Leb128UnsignedLongWrapper> codec = Codecs.create(Leb128UnsignedLongWrapper.class);
        try
        {
            byte[] bytes = Codecs.encode(test, codec);
            assertEquals(unsignedLongEncodedBytes.length, bytes.length);
            for (int i = 0; i < unsignedLongEncodedBytes.length; i++)
            {
                assertEquals(unsignedLongEncodedBytes[i], bytes[i]);
            }
        } catch (IOException e)
        {
            System.out.println("Something bad happened.");
        }
    }

    @Test
    public void testUnsignedLongDecoding()
    {
        final Codec<Leb128UnsignedLongWrapper> codec = Codecs.create(Leb128UnsignedLongWrapper.class);
        final Leb128UnsignedLongWrapper longWrapper;
        try
        {
            longWrapper = Codecs.decode(codec, unsignedLongEncodedBytes);
        } catch (DecodingException e)
        {
            fail("Could not decode the diag revealer header bytes");
            return;
        }
        assertEquals(unsignedLong, (long) longWrapper.value);
    }

    @Test
    public void testSignedLongEncoding()
    {
        Leb128SignedLongWrapper test = new Leb128SignedLongWrapper(signedLong);
        Codec<Leb128SignedLongWrapper> codec = Codecs.create(Leb128SignedLongWrapper.class);
        try
        {
            byte[] bytes = Codecs.encode(test, codec);
            assertEquals(signedLongEncodedBytes.length, signedLongEncodedBytes.length);
            for (int i = 0; i < signedLongEncodedBytes.length; i++)
            {
                assertEquals(signedLongEncodedBytes[i], bytes[i]);
            }
        } catch (IOException e)
        {
            System.out.println("Something bad happened.");
        }
    }

    @Test
    public void testSignedLongDecoding()
    {
        final Codec<Leb128SignedLongWrapper> codec = Codecs.create(Leb128SignedLongWrapper.class);
        final Leb128SignedLongWrapper longWrapper;
        try
        {
            longWrapper = Codecs.decode(codec, signedLongEncodedBytes);
        } catch (DecodingException e)
        {
            fail("Could not decode the diag revealer header bytes");
            return;
        }
        assertEquals(signedLong, (long) longWrapper.value);
    }

    @Test
    public void testDecodingError()
    {
        final Codec<Leb128UnsignedShortWrapper> codec = Codecs.create(Leb128UnsignedShortWrapper.class);
        assertThrows(DecodingException.class, () -> Codecs.decode(codec, unsignedLongEncodedBytes));
    }
}
