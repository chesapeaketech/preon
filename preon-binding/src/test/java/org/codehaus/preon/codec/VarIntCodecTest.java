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
import org.codehaus.preon.annotation.BoundList;
import org.codehaus.preon.annotation.VarInt;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * TODO: class description
 *
 * @author Copyright &#169; 2021 Chesapeake Technology International Corp.
 * @since
 */
public class VarIntCodecTest
{
    byte[] longNumberExpectedBytes = {(byte)0x80, (byte)0xE5, (byte)0x9A, (byte)0x77};
    byte[] shortNumberExpectedBytes = {(byte)0x01};

    @Test
    public void testLongEncoding() {
        LongWrapper test = new LongWrapper(250000000L);
        Codec<LongWrapper> codec = Codecs.create(LongWrapper.class);
        try {
            byte[] bytes = Codecs.encode(test, codec);
            for (int i =0; i < longNumberExpectedBytes.length; i++) {
                assertEquals(longNumberExpectedBytes[i], bytes[i]);
            }
        } catch(IOException e) {
            System.out.println("Something bad happened.");
        }
    }

    @Test
    public void testLongListEncoding() {
        TestClass test = new TestClass();
        test.longValue.add(new LongWrapper(250000000L));
        Codec<TestClass> codec = Codecs.create(TestClass.class);
        try {
            byte[] bytes = Codecs.encode(test, codec);
            for (int i =0; i < longNumberExpectedBytes.length; i++) {
                assertEquals(longNumberExpectedBytes[i], bytes[i]);
            }
        } catch(IOException e) {
            System.out.println("Something bad happened.");
        }
    }

    public class TestClass{
        @BoundList(type=LongWrapper.class)
        List<LongWrapper> longValue = new ArrayList<>();
    }
    public class LongWrapper
    {
        @VarInt
        Long value;

        public LongWrapper(Long value)
        {
            this.value = value;
        }
    }
}
