package org.codehaus.preon.codec;

import org.codehaus.preon.buffer.BitBuffer;
import org.codehaus.preon.buffer.ByteOrder;
import org.codehaus.preon.channel.BitChannel;

import java.io.IOException;
import java.math.BigInteger;

public enum NumericUnsignedType implements IIntegerType
{
    UByte(IntegerType.Byte),
    UShort(IntegerType.Short),
    UInteger(IntegerType.Integer),
    ULong(IntegerType.Long),
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
}
