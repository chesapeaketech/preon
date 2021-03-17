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

import org.codehaus.preon.buffer.BitBuffer;
import org.codehaus.preon.buffer.ByteOrder;
import org.codehaus.preon.channel.BitChannel;
import org.codehaus.preon.codec.IIntegerType;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * TODO: class description
 *
 * @author Copyright &#169; 2021 Chesapeake Technology International Corp.
 * @since
 */
public enum IntegerType implements IIntegerType
{
    Integer
            {
                @Override
                public int getVarIntSize(Number number)
                {
                    return 0;
                }

                @Override
                public Object decodeVarInt()
                {
                    return null;
                }

                @Override
                public void encodeVarInt(BitChannel channel, Object value)
                {

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
                    return new Class<?>[]{getType()};
                }
            },

    Long
            {
                @Override
                public int getVarIntSize(Number number)
                {
                    // TODO: This could be much cleverer.
                    java.lang.Long value = (java.lang.Long) number;
                    long remaining = value >> 7;
                    int count = 0;
                    boolean hasMore = true;
                    int end = ((value & java.lang.Long.MIN_VALUE) == 0) ? 0 : -1;

                    while (hasMore) {
                        hasMore = (remaining != end)
                                || ((remaining & 1) != ((value >> 6) & 1));

                        value = remaining;
                        remaining >>= 7;
                        count++;
                    }

                    return count;
                }

                @Override
                public Object decodeVarInt()
                {
                    return null;
                }

                @Override
                public void encodeVarInt(BitChannel channel, Object value) throws IOException
                {
                    java.lang.Long longValue = (java.lang.Long) value;
                    Long remaining = longValue >> 7;
                    boolean hasMore = true;
                    Long end = ((longValue & java.lang.Long.MIN_VALUE) == 0) ? 0L : -1L;
                    ByteBuffer buffer = ByteBuffer.allocate(getVarIntSize(longValue));
                    buffer.order(java.nio.ByteOrder.LITTLE_ENDIAN);
                    while (hasMore) {
                        hasMore = (remaining != end)
                                || ((remaining & 1) != ((longValue >> 6) & 1));

                        buffer.put((byte) ((longValue & 0x7f) | (hasMore ? 0x80 : 0)));
                        longValue = remaining;
                        remaining >>= 7;
                    }
                    channel.write(buffer);
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
                    return new Class<?>[]{getType()};
                }
            },

    Short
            {
                @Override
                public int getVarIntSize(Number number)
                {
                    return 0;
                }

                @Override
                public Object decodeVarInt()
                {
                    return null;
                }

                @Override
                public void encodeVarInt(BitChannel channel, Object value)
                {

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
                    return new Class<?>[]{getType()};
                }
            },

    Byte
            {
                @Override
                public int getVarIntSize(Number number)
                {
                    return 0;
                }

                @Override
                public Object decodeVarInt()
                {
                    return null;
                }

                @Override
                public void encodeVarInt(BitChannel channel, Object value)
                {

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
                    return new Class<?>[]{getType()};
                }
            };
}
