package org.codehaus.preon.codec;

import org.codehaus.preon.DecodingException;
import org.codehaus.preon.buffer.BitBuffer;
import org.codehaus.preon.buffer.ByteOrder;
import org.codehaus.preon.channel.BitChannel;

import java.io.IOException;
import java.math.BigInteger;

public enum NumericUnsignedType implements IIntegerType
{
    UByte(IntegerType.Byte)
            {
                @Override
                public Object decodeLeb128(BitBuffer buffer) throws DecodingException
                {
                    return decodeAnyLeb128(buffer, MAX_BYTE_LEB128_BYTES).byteValue();
                }
            },
    UShort(IntegerType.Short)
            {
                @Override
                public Object decodeLeb128(BitBuffer buffer) throws DecodingException
                {
                    return decodeAnyLeb128(buffer, MAX_SHORT_LEB128_BYTES).shortValue();
                }
            },
    UInteger(IntegerType.Integer)
            {
                @Override
                public Object decodeLeb128(BitBuffer buffer) throws DecodingException
                {
                    return decodeAnyLeb128(buffer, MAX_INT_LEB128_BYTES).intValue();
                }
            },
    ULong(IntegerType.Long)
            {
                @Override
                public Object decodeLeb128(BitBuffer buffer) throws DecodingException
                {
                    return decodeAnyLeb128(buffer, MAX_LONG_LEB128_BYTES);
                }
            },
    ;

    private final IntegerType delegate;

    private NumericUnsignedType(IntegerType delegate)
    {
        this.delegate = delegate;
    }

    public int getDefaultSize()
    {
        if (delegate.getDefaultSize() % 2 == 0)
        {
            return delegate.getDefaultSize() / 2;
        }
        throw new IllegalArgumentException("Size of " + delegate.getDefaultSize() + " can not be divided by two.");
    }

    public Object decode(BitBuffer buffer, int size, ByteOrder endian)
    {
        return delegate.decode(buffer, size, endian);
    }

    public void encode(BitChannel channel, int size, ByteOrder endian, Object value) throws IOException
    {
        //calculate the maximum value that can be stored with given size
        //maximum size that can be stored: 32bits
        //TODO special treatment for BigInteger
        //TODO use guavas ints
        long longVal = ((Number) value).longValue();
        if (longVal < 0)
        {
            throw new IllegalArgumentException("Value " + longVal + " is negative, can not be encoded in unsiged.");
        }
        final BigInteger maxVal = BigInteger.valueOf(2).pow(size);
        if (maxVal.compareTo(BigInteger.valueOf(longVal)) == -1)
        {
            throw new IllegalArgumentException("Value " + longVal + " is too big to be encoded in " + size + " bits.");
        }
        delegate.encode(channel, size, endian, value);
    }

    public Class<?> getType()
    {
        return delegate.getType();
    }

    public Class<?> getPrimitiveType()
    {
        return delegate.getPrimitiveType();
    }

    public Class<?>[] getNumericTypes()
    {
        return delegate.getNumericTypes();
    }

    @Override
    public int getLeb128Size(Number number)
    {
        long value = (long) number;
        long remaining = value >> 7;
        int count = 0;

        while (remaining != 0)
        {
            remaining >>= 7;
            count++;
        }

        return count + 1;
    }

    @Override
    public void encodeLeb128(BitChannel channel, Object value) throws IOException
    {
        //TODO Allow for BigInteger?
        long longVal = ((Number) value).longValue();
        byte[] bytes = new byte[getLeb128Size(longVal)];
        int byteCount = 0;
        if (longVal < 0)
        {
            throw new IllegalArgumentException("Value " + longVal + " is negative, can not be encoded as unsigned.");
        }
        long remaining = longVal >>> 7;

        while (remaining != 0)
        {
            bytes[byteCount] = ((byte) ((longVal & 0x7f) | 0x80));
            byteCount++;
            longVal = remaining;
            remaining >>>= 7;
        }
        // When there's nothing left in remaining, longVal contains the last byte
        bytes[byteCount] = (byte) longVal;
        channel.write(bytes, 0, bytes.length);
    }

    private static Long decodeAnyLeb128(BitBuffer buffer, int size) throws DecodingException {
        long result = 0;
        long cur;
        int bytesRead = 0;

        do
        {
            cur = buffer.readAsByte(8) & 0xff;
            result |= (cur & 0x7f) << (bytesRead * 7);
            bytesRead++;
        } while (((cur & 0x80) == 0x80) && bytesRead < size);

        if ((cur & 0x80) == 0x80)
        {
            throw new DecodingException("Invalid LEB128 sequence. More than " + bytesRead + " bytes may be too large.");
        }

        return result;
    }

}
