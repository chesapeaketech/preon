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
import org.codehaus.preon.codec.helper.Leb128SignedByteWrapper;
import org.codehaus.preon.codec.helper.Leb128SignedIntWrapper;
import org.codehaus.preon.codec.helper.Leb128SignedLongWrapper;
import org.codehaus.preon.codec.helper.Leb128SignedShortWrapper;
import org.codehaus.preon.codec.helper.Leb128UnsignedByteWrapper;
import org.codehaus.preon.codec.helper.Leb128UnsignedIntWrapper;
import org.codehaus.preon.codec.helper.Leb128UnsignedLongWrapper;
import org.codehaus.preon.codec.helper.Leb128UnsignedShortWrapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

/**
 * Testing for LEB128 encoding/decoding.
 *
 * @author Copyright &#169; 2021 Chesapeake Technology International Corp.
 */
public class Leb128CodecTest
{
    byte[] biggerThanMaxLongBytes   = { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                                        (byte) 0xFF, (byte) 0x01 };
    byte[] unsignedLongEncodedBytes = { (byte) 0x80, (byte) 0xF2, (byte) 0x8B, (byte) 0xA8, (byte) 0x09 };
    long   unsignedLong             = 2_500_000_000L;
    byte[] signedLongEncodedBytes   = { (byte) 0x80, (byte) 0x8E, (byte) 0xF4, (byte) 0xD7, (byte) 0x76 };
    long   signedLong               = -2_500_000_000L;

    byte[] unsignedIntEncodedBytes = { (byte) 0xB8, (byte) 0x91, (byte) 0x02 };
    int    unsignedInt             = 35_000;
    byte[] signedIntEncodedBytes   = { (byte) 0xC8, (byte) 0xEE, (byte) 0x7D };
    int    signedInt               = -35_000;

    byte[] unsignedShortEncodedBytes = { (byte) 0x96, (byte) 0x01 };
    short  unsignedShort             = 150;
    byte[] signedShortEncodedBytes   = { (byte) 0xEA, (byte) 0x7E };
    short  signedShort               = -150;

    byte[] unsignedByteEncodedBytes = { (byte) 0x78 };
    byte   unsignedByte             = 120;
    byte[] signedByteEncodedBytes   = { (byte) 0x88, (byte) 0x7F };
    byte   signedByte               = -120;

    /**
     * Validates that an unsigned long is properly encoded in LEB128.
     */
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
        }
        catch (IOException e)
        {
            System.out.println("Something bad happened.");
        }
    }

    /**
     * Validates that an unsigned long is properly decoded from LEB128.
     */
    @Test
    public void testUnsignedLongDecoding()
    {
        final Codec<Leb128UnsignedLongWrapper> codec = Codecs.create(Leb128UnsignedLongWrapper.class);
        final Leb128UnsignedLongWrapper wrapper;
        try
        {
            wrapper = Codecs.decode(codec, unsignedLongEncodedBytes);
        }
        catch (DecodingException e)
        {
            fail("Could not decode the Long bytes.");
            return;
        }
        assertEquals(unsignedLong, (long) wrapper.value);
    }

    /**
     * Validates that a signed long is properly encoded in LEB128.
     */
    @Test
    public void testSignedLongEncoding()
    {
        Leb128SignedLongWrapper test = new Leb128SignedLongWrapper(signedLong);
        Codec<Leb128SignedLongWrapper> codec = Codecs.create(Leb128SignedLongWrapper.class);
        try
        {
            byte[] bytes = Codecs.encode(test, codec);
            assertEquals(signedLongEncodedBytes.length, bytes.length);
            for (int i = 0; i < signedLongEncodedBytes.length; i++)
            {
                assertEquals(signedLongEncodedBytes[i], bytes[i]);
            }
        }
        catch (IOException e)
        {
            System.out.println("Something bad happened.");
        }
    }

    /**
     * Validates that a signed long is properly decoded from LEB128.
     */
    @Test
    public void testSignedLongDecoding()
    {
        final Codec<Leb128SignedLongWrapper> codec = Codecs.create(Leb128SignedLongWrapper.class);
        final Leb128SignedLongWrapper wrapper;
        try
        {
            wrapper = Codecs.decode(codec, signedLongEncodedBytes);
        }
        catch (DecodingException e)
        {
            fail("Could not decode the Long bytes.");
            return;
        }
        assertEquals(signedLong, (long) wrapper.value);
    }

    /**
     * Validates that an error is thrown when a value that is longer than expected is attempted to be decoded unsigned into a long.
     */
    @Test
    public void testUnsignedLongDecodingError()
    {
        final Codec<Leb128UnsignedLongWrapper> codec = Codecs.create(Leb128UnsignedLongWrapper.class);
        assertThrows(DecodingException.class, () -> Codecs.decode(codec, biggerThanMaxLongBytes));
    }

    /**
     * Validates that an error is thrown when a value that is longer than expected is attempted to be decoded signed into a long.
     */
    @Test
    public void testSignedLongDecodingError()
    {
        final Codec<Leb128SignedLongWrapper> codec = Codecs.create(Leb128SignedLongWrapper.class);
        assertThrows(DecodingException.class, () -> Codecs.decode(codec, biggerThanMaxLongBytes));
    }

    /**
     * Validates that an unsigned int is properly encoded in LEB128.
     */
    @Test
    public void testUnsignedIntEncoding()
    {
        Leb128UnsignedIntWrapper test = new Leb128UnsignedIntWrapper(unsignedInt);
        Codec<Leb128UnsignedIntWrapper> codec = Codecs.create(Leb128UnsignedIntWrapper.class);
        try
        {
            byte[] bytes = Codecs.encode(test, codec);
            assertEquals(unsignedIntEncodedBytes.length, bytes.length);
            for (int i = 0; i < unsignedIntEncodedBytes.length; i++)
            {
                assertEquals(unsignedIntEncodedBytes[i], bytes[i]);
            }
        }
        catch (IOException e)
        {
            System.out.println("Something bad happened.");
        }
    }

    /**
     * Validates that an unsigned int is properly decoded from LEB128.
     */
    @Test
    public void testUnsignedIntDecoding()
    {
        final Codec<Leb128UnsignedIntWrapper> codec = Codecs.create(Leb128UnsignedIntWrapper.class);
        final Leb128UnsignedIntWrapper wrapper;
        try
        {
            wrapper = Codecs.decode(codec, unsignedIntEncodedBytes);
        }
        catch (DecodingException e)
        {
            fail("Could not decode the Integer bytes.");
            return;
        }
        assertEquals(unsignedInt, (int) wrapper.value);
    }

    /**
     * Validates that a signed int is properly encoded in LEB128.
     */
    @Test
    public void testSignedIntEncoding()
    {
        Leb128SignedIntWrapper test = new Leb128SignedIntWrapper(signedInt);
        Codec<Leb128SignedIntWrapper> codec = Codecs.create(Leb128SignedIntWrapper.class);
        try
        {
            byte[] bytes = Codecs.encode(test, codec);
            assertEquals(signedIntEncodedBytes.length, bytes.length);
            for (int i = 0; i < signedIntEncodedBytes.length; i++)
            {
                assertEquals(signedIntEncodedBytes[i], bytes[i]);
            }
        }
        catch (IOException e)
        {
            System.out.println("Something bad happened.");
        }
    }

    /**
     * Validates that a signed int is properly decoded from LEB128.
     */
    @Test
    public void testSignedIntDecoding()
    {
        final Codec<Leb128SignedIntWrapper> codec = Codecs.create(Leb128SignedIntWrapper.class);
        final Leb128SignedIntWrapper wrapper;
        try
        {
            wrapper = Codecs.decode(codec, signedIntEncodedBytes);
        }
        catch (DecodingException e)
        {
            fail("Could not decode the Integer bytes.");
            return;
        }
        assertEquals(signedInt, (int) wrapper.value);
    }

    /**
     * Validates that an error is thrown when a value that is longer than expected is attempted to be decoded unsigned into an int.
     */
    @Test
    public void testUnsignedIntDecodingError()
    {
        final Codec<Leb128UnsignedIntWrapper> codec = Codecs.create(Leb128UnsignedIntWrapper.class);
        assertThrows(DecodingException.class, () -> Codecs.decode(codec, biggerThanMaxLongBytes));
    }

    /**
     * Validates that an error is thrown when a value that is longer than expected is attempted to be decoded signed into an int.
     */
    @Test
    public void testSignedIntDecodingError()
    {
        final Codec<Leb128SignedIntWrapper> codec = Codecs.create(Leb128SignedIntWrapper.class);
        assertThrows(DecodingException.class, () -> Codecs.decode(codec, biggerThanMaxLongBytes));
    }

    /**
     * Validates that an unsigned short is properly encoded in LEB128.
     */
    @Test
    public void testUnsignedShortEncoding()
    {
        Leb128UnsignedShortWrapper test = new Leb128UnsignedShortWrapper(unsignedShort);
        Codec<Leb128UnsignedShortWrapper> codec = Codecs.create(Leb128UnsignedShortWrapper.class);
        try
        {
            byte[] bytes = Codecs.encode(test, codec);
            assertEquals(unsignedShortEncodedBytes.length, bytes.length);
            for (int i = 0; i < unsignedShortEncodedBytes.length; i++)
            {
                assertEquals(unsignedShortEncodedBytes[i], bytes[i]);
            }
        }
        catch (IOException e)
        {
            System.out.println("Something bad happened.");
        }
    }

    /**
     * Validates that an unsigned short is properly decoded from LEB128.
     */
    @Test
    public void testUnsignedShortDecoding()
    {
        final Codec<Leb128UnsignedShortWrapper> codec = Codecs.create(Leb128UnsignedShortWrapper.class);
        final Leb128UnsignedShortWrapper wrapper;
        try
        {
            wrapper = Codecs.decode(codec, unsignedShortEncodedBytes);
        }
        catch (DecodingException e)
        {
            fail("Could not decode the Short bytes.");
            return;
        }
        assertEquals(unsignedShort, (short) wrapper.value);
    }

    /**
     * Validates that a signed short is properly encoded in LEB128.
     */
    @Test
    public void testSignedShortEncoding()
    {
        Leb128SignedShortWrapper test = new Leb128SignedShortWrapper(signedShort);
        Codec<Leb128SignedShortWrapper> codec = Codecs.create(Leb128SignedShortWrapper.class);
        try
        {
            byte[] bytes = Codecs.encode(test, codec);
            assertEquals(signedShortEncodedBytes.length, bytes.length);
            for (int i = 0; i < signedShortEncodedBytes.length; i++)
            {
                assertEquals(signedShortEncodedBytes[i], bytes[i]);
            }
        }
        catch (IOException e)
        {
            System.out.println("Something bad happened.");
        }
    }

    /**
     * Validates that a signed short is properly decoded from LEB128.
     */
    @Test
    public void testSignedShortDecoding()
    {
        final Codec<Leb128SignedShortWrapper> codec = Codecs.create(Leb128SignedShortWrapper.class);
        final Leb128SignedShortWrapper wrapper;
        try
        {
            wrapper = Codecs.decode(codec, signedShortEncodedBytes);
        }
        catch (DecodingException e)
        {
            fail("Could not decode the Short bytes.");
            return;
        }
        assertEquals(signedShort, (short) wrapper.value);
    }

    /**
     * Validates that an error is thrown when a value that is longer than expected is attempted to be decoded unsigned into a short.
     */
    @Test
    public void testUnsignedShortDecodingError()
    {
        final Codec<Leb128UnsignedShortWrapper> codec = Codecs.create(Leb128UnsignedShortWrapper.class);
        assertThrows(DecodingException.class, () -> Codecs.decode(codec, biggerThanMaxLongBytes));
    }

    /**
     * Validates that an error is thrown when a value that is longer than expected is attempted to be decoded signed into a short.
     */
    @Test
    public void testSignedShortDecodingError()
    {
        final Codec<Leb128SignedShortWrapper> codec = Codecs.create(Leb128SignedShortWrapper.class);
        assertThrows(DecodingException.class, () -> Codecs.decode(codec, biggerThanMaxLongBytes));
    }

    /**
     * Validates that an unsigned byte is properly encoded in LEB128.
     */
    @Test
    public void testUnsignedByteEncoding()
    {
        Leb128UnsignedByteWrapper test = new Leb128UnsignedByteWrapper(unsignedByte);
        Codec<Leb128UnsignedByteWrapper> codec = Codecs.create(Leb128UnsignedByteWrapper.class);
        try
        {
            byte[] bytes = Codecs.encode(test, codec);
            assertEquals(unsignedByteEncodedBytes.length, bytes.length);
            for (int i = 0; i < unsignedByteEncodedBytes.length; i++)
            {
                assertEquals(unsignedByteEncodedBytes[i], bytes[i]);
            }
        }
        catch (IOException e)
        {
            System.out.println("Something bad happened.");
        }
    }

    /**
     * Validates that an unsigned byte is properly decoded from LEB128.
     */
    @Test
    public void testUnsignedByteDecoding()
    {
        final Codec<Leb128UnsignedByteWrapper> codec = Codecs.create(Leb128UnsignedByteWrapper.class);
        final Leb128UnsignedByteWrapper wrapper;
        try
        {
            wrapper = Codecs.decode(codec, unsignedByteEncodedBytes);
        }
        catch (DecodingException e)
        {
            fail("Could not decode the Byte bytes.");
            return;
        }
        assertEquals(unsignedByte, (byte) wrapper.value);
    }

    /**
     * Validates that a signed byte is properly encoded in LEB128.
     */
    @Test
    public void testSignedByteEncoding()
    {
        Leb128SignedByteWrapper test = new Leb128SignedByteWrapper(signedByte);
        Codec<Leb128SignedByteWrapper> codec = Codecs.create(Leb128SignedByteWrapper.class);
        try
        {
            byte[] bytes = Codecs.encode(test, codec);
            assertEquals(signedByteEncodedBytes.length, bytes.length);
            for (int i = 0; i < signedByteEncodedBytes.length; i++)
            {
                assertEquals(signedByteEncodedBytes[i], bytes[i]);
            }
        }
        catch (IOException e)
        {
            System.out.println("Something bad happened.");
        }
    }

    /**
     * Validates that a signed byte is properly decoded from LEB128.
     */
    @Test
    public void testSignedByteDecoding()
    {
        final Codec<Leb128SignedByteWrapper> codec = Codecs.create(Leb128SignedByteWrapper.class);
        final Leb128SignedByteWrapper wrapper;
        try
        {
            wrapper = Codecs.decode(codec, signedByteEncodedBytes);
        }
        catch (DecodingException e)
        {
            fail("Could not decode the Byte bytes.");
            return;
        }
        assertEquals(signedByte, (byte) wrapper.value);
    }

    /**
     * Validates that an error is thrown when a value that is longer than expected is attempted to be decoded unsigned into a byte.
     */
    @Test
    public void testUnsignedByteDecodingError()
    {
        final Codec<Leb128UnsignedByteWrapper> codec = Codecs.create(Leb128UnsignedByteWrapper.class);
        assertThrows(DecodingException.class, () -> Codecs.decode(codec, biggerThanMaxLongBytes));
    }

    /**
     * Validates that an error is thrown when a value that is longer than expected is attempted to be decoded signed into a byte.
     */
    @Test
    public void testSignedByteDecodingError()
    {
        final Codec<Leb128SignedByteWrapper> codec = Codecs.create(Leb128SignedByteWrapper.class);
        assertThrows(DecodingException.class, () -> Codecs.decode(codec, biggerThanMaxLongBytes));
    }
}
