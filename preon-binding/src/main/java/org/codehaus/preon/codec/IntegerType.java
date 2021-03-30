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

import org.codehaus.preon.DecodingException;
import org.codehaus.preon.buffer.BitBuffer;
import org.codehaus.preon.buffer.ByteOrder;
import org.codehaus.preon.channel.BitChannel;

import java.io.IOException;

/**
 * Integer types of numerics, since some things can be done with integer types that you wouldn't want to do with a
 * floating point type.
 *
 * @author Copyright &#169; 2021 Chesapeake Technology International Corp.
 */
public enum IntegerType implements IIntegerType
{
    Integer
     {
         @Override
         public Object decodeLeb128(BitBuffer buffer) throws DecodingException
         {
             int result = 0;
             int cur;
             int count = 0;
             int signBits = -1;

             do
             {
                 cur = buffer.readAsByte(8) & 0xff;
                 result |= (cur & 0x7f) << (count * 7);
                 signBits <<= 7;
                 count++;
             }
             while (((cur & 0x80) == 0x80) && count < MAX_INT_LEB128_BYTES);

             if ((cur & 0x80) == 0x80)
             {
                 throw new DecodingException("Invalid LEB128 sequence for an int.");
             }

             // Sign extend if appropriate
             if (((signBits >> 1) & result) != 0)
             {
                 result |= signBits;
             }

             return result;
         }

         public int getDefaultSize()
         {
             return 32;
         }

         public Integer decode(BitBuffer buffer, int size, ByteOrder endian)
         {
             return buffer.readAsInt(size, endian);
         }

         public void encode(BitChannel channel, int size, ByteOrder endian, Object value) throws IOException
         {
             channel.write(size, (Integer) value, endian);
         }

         public Class<?> getType()
         {
             return Integer.class;
         }

         @Override
         public Class<?> getPrimitiveType()
         {
             return java.lang.Integer.TYPE;
         }

         @Override
         public Class<?>[] getNumericTypes()
         {
             return new Class<?>[] { getType() };
         }
     },

    Long
     {
         @Override
         public Object decodeLeb128(BitBuffer buffer) throws DecodingException
         {
             long result = 0;
             long cur;
             int count = 0;
             long signBits = -1L;

             do
             {
                 cur = buffer.readAsByte(8) & 0xff;
                 result |= (cur & 0x7f) << (count * 7);
                 signBits <<= 7;
                 count++;
             }
             while (((cur & 0x80) == 0x80) && count < MAX_LONG_LEB128_BYTES);

             if ((cur & 0x80) == 0x80)
             {
                 throw new DecodingException("Invalid LEB128 sequence for a long.");
             }

             // Sign extend if appropriate
             if (((signBits >> 1) & result) != 0)
             {
                 result |= signBits;
             }

             return result;
         }

         public int getDefaultSize()
         {
             return 64;
         }

         public Long decode(BitBuffer buffer, int size, ByteOrder endian)
         {
             return buffer.readAsLong(size, endian);
         }

         public void encode(BitChannel channel, int size, ByteOrder endian, Object value) throws IOException
         {
             channel.write(size, (Long) value, endian);
         }

         public Class<?> getType()
         {
             return Long.class;
         }

         @Override
         public Class<?> getPrimitiveType()
         {
             return java.lang.Long.TYPE;
         }

         @Override
         public Class<?>[] getNumericTypes()
         {
             return new Class<?>[] { getType() };
         }
     },

    Short
     {
         @Override
         public Object decodeLeb128(BitBuffer buffer) throws DecodingException
         {
             short result = 0;
             short cur;
             int count = 0;
             short signBits = -1;

             do
             {
                 cur = (short) (buffer.readAsByte(8) & 0xff);
                 result |= (cur & 0x7f) << (count * 7);
                 signBits <<= 7;
                 count++;
             }
             while (((cur & 0x80) == 0x80) && count < MAX_SHORT_LEB128_BYTES);

             if ((cur & 0x80) == 0x80)
             {
                 throw new DecodingException("Invalid LEB128 sequence for a short.");
             }

             // Sign extend if appropriate
             if (((signBits >> 1) & result) != 0)
             {
                 result |= signBits;
             }

             return result;
         }

         public int getDefaultSize()
         {
             return 16;
         }

         public Short decode(BitBuffer buffer, int size, ByteOrder endian)
         {
             return buffer.readAsShort(size, endian);
         }

         public void encode(BitChannel channel, int size, ByteOrder endian, Object value) throws IOException
         {
             channel.write(size, (Short) value, endian);
         }

         public Class<?> getType()
         {
             return Short.class;
         }

         @Override
         public Class<?> getPrimitiveType()
         {
             return java.lang.Short.TYPE;
         }

         @Override
         public Class<?>[] getNumericTypes()
         {
             return new Class<?>[] { getType() };
         }
     },

    Byte
     {
         @Override
         public Object decodeLeb128(BitBuffer buffer) throws DecodingException
         {
             byte result = 0;
             byte cur;
             int count = 0;
             byte signBits = -1;

             do
             {
                 cur = (byte) (buffer.readAsByte(8) & 0xff);
                 result |= (cur & 0x7f) << (count * 7);
                 signBits <<= 7;
                 count++;
             }
             while (((cur & 0x80) == 0x80) && count < MAX_BYTE_LEB128_BYTES);

             if ((cur & 0x80) == 0x80)
             {
                 throw new DecodingException("Invalid LEB128 sequence for a byte.");
             }

             // Sign extend if appropriate
             if (((signBits >> 1) & result) != 0)
             {
                 result |= signBits;
             }

             return result;
         }

         public int getDefaultSize()
         {
             return 8;
         }

         public Byte decode(BitBuffer buffer, int size, ByteOrder endian)
         {
             return buffer.readAsByte(size, endian);
         }

         public void encode(BitChannel channel, int size, ByteOrder endian, Object value) throws IOException
         {
             channel.write(size, (Byte) value);
         }

         public Class<?> getType()
         {
             return Byte.class;
         }

         @Override
         public Class<?> getPrimitiveType()
         {
             return java.lang.Byte.TYPE;
         }

         @Override
         public Class<?>[] getNumericTypes()
         {
             return new Class<?>[] { getType() };
         }
     };

    @Override
    public int getLeb128Size(Number number)
    {
        long value = (long) number;
        long remaining = value >> 7;
        int count = 0;
        boolean hasMore = true;
        int end = ((value & java.lang.Long.MIN_VALUE) == 0) ? 0 : -1;

        while (hasMore)
        {
            hasMore = (remaining != end)
                      || ((remaining & 1) != ((value >> 6) & 1));

            value = remaining;
            remaining >>= 7;
            count++;
        }

        return count;
    }

    @Override
    public void encodeLeb128(BitChannel channel, Object value) throws IOException
    {
        // TODO: If we want to add in handling for BigInteger, should probably use it here, too
        long longValue = ((Number) value).longValue();
        long remaining = longValue >> 7;
        boolean hasMore = true;
        int count = 0;
        long end = ((longValue & java.lang.Long.MIN_VALUE) == 0) ? 0L : -1L;
        byte[] bytes = new byte[getLeb128Size(longValue)];
        while (hasMore)
        {
            hasMore = (remaining != end)
                      || ((remaining & 1) != ((longValue >> 6) & 1));

            bytes[count] = (byte) ((longValue & 0x7f) | (hasMore ? 0x80 : 0));
            count++;
            longValue = remaining;
            remaining >>= 7;
        }
        channel.write(bytes, 0, bytes.length);
    }
}
