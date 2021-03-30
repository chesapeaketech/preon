package org.codehaus.preon.codec;

import org.codehaus.preon.buffer.BitBuffer;
import org.codehaus.preon.buffer.ByteOrder;
import org.codehaus.preon.channel.BitChannel;

import java.io.IOException;

public enum NumericType implements INumericType
{

    Float
     {
         public int getDefaultSize()
         {
             return 32;
         }

         public Float decode(BitBuffer buffer, int size, ByteOrder endian)
         {
             int value = buffer.readAsInt(size, endian);
             return java.lang.Float.intBitsToFloat(value);
         }

         //TODO handle io exceptions on encode
         public void encode(BitChannel channel, int size, ByteOrder endian, Object value) throws IOException
         {
             channel.write(size, (Float) value, endian);
         }

         public Class<?> getType()
         {
             return Float.class;
         }

         @Override
         public Class<?> getPrimitiveType()
         {
             return java.lang.Float.TYPE;
         }

         @Override
         public Class<?>[] getNumericTypes()
         {
             return new Class<?>[] { getType() };
         }
     },

    Double
     {
         public int getDefaultSize()
         {
             return 64;
         }

         public Double decode(BitBuffer buffer, int size, ByteOrder endian)
         {
             return java.lang.Double.longBitsToDouble(buffer.readAsLong(
              size, endian));
         }

         public void encode(BitChannel channel, int size, ByteOrder endian, Object value) throws IOException
         {
             channel.write(size, (Double) value, endian);
         }

         public Class<?> getType()
         {
             return Double.class;
         }

         @Override
         public Class<?> getPrimitiveType()
         {
             return java.lang.Double.TYPE;
         }

         @Override
         public Class<?>[] getNumericTypes()
         {
             return new Class<?>[] { getType() };
         }
     },
}
