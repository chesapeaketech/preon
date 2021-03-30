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

import nl.flotsam.pecia.Documenter;
import nl.flotsam.pecia.ParaContents;
import nl.flotsam.pecia.SimpleContents;
import org.codehaus.preon.Builder;
import org.codehaus.preon.Codec;
import org.codehaus.preon.CodecDescriptor;
import org.codehaus.preon.DecodingException;
import org.codehaus.preon.Resolver;
import org.codehaus.preon.buffer.BitBuffer;
import org.codehaus.preon.channel.BitChannel;
import org.codehaus.preon.descriptor.NullDocumenter;
import org.codehaus.preon.el.Expression;

import java.io.IOException;

/**
 * Codec for handling integer type values in LEB128 format.
 *
 * @author Copyright &#169; 2021 Chesapeake Technology International Corp.
 */
public class Leb128Codec implements Codec<Object>
{
    private IIntegerType type;

    public Leb128Codec(IIntegerType type)
    {
        this.type = type;
    }

    /**
     * Decodes a value from the {@link BitBuffer}.
     *
     * @param buffer   The {@link BitBuffer} containing the data from which a value will be decoded.
     * @param resolver The object capable of resolving variable references, when required.
     * @param builder  The object responsible for creating default instances of objects, when needed. (In reality, this
     *                 is most likely going to be important to {@link ObjectCodecFactory
     *                 ObjectCodecFactories} only, but in order to make sure the {@link Builder} arrives there, we need
     *                 to have the ability to pass it in.
     * @return The decoded value.
     * @throws DecodingException If the {@link Codec} fails to decode the value.
     */
    @Override
    public Object decode(BitBuffer buffer, Resolver resolver, Builder builder) throws DecodingException
    {
        return type.decodeLeb128(buffer);
    }

    /**
     * Encodes the object to the {@link BitChannel}.
     *
     * @param value    The object to encode.
     * @param channel  The channel to receive the encoded representation.
     * @param resolver The object providing access to the context.
     */
    @Override
    public void encode(Object value, BitChannel channel, Resolver resolver) throws IOException
    {
        type.encodeLeb128(channel, value);
    }

    /**
     * Returns an expression that is expected to return the number of bits occupied by objects created by this Codec, as
     * a function of the context to which variables will be resolved.
     * <p/>
     * <p> This method may return null, indicating that it is impossible to state anything at all on the expected number
     * of bits. Note that if this method <em>does</em> return an {@link Expression}, then it will require a {@link
     * Resolver} to resolve variables inside this expression, <em>unless {@link Expression#isParameterized()} returns
     * <code>false</code></em> . </p>
     *
     * @return null since the size can vary depending on the value.
     */
    @Override
    public Expression<Integer, Resolver> getSize()
    {
        return null;
    }

    /**
     * Returns an object that is capable of rendering a description of the data structure encoded/decoded by this
     * Codec.
     *
     * @return An object capable of describing the {@link Codec}.
     */
    @Override
    public CodecDescriptor getCodecDescriptor()
    {
        return new CodecDescriptor()
        {
            @Override
            public <C extends ParaContents<?>> Documenter<C> summary()
            {
                return new Documenter<C>()
                {
                    @Override
                    public void document(C target)
                    {
                        target.document(reference(Adjective.A, true)).text(". ");
                    }
                };
            }

            @Override
            public <C extends ParaContents<?>> Documenter<C> reference(Adjective adjective, boolean startWithCapital)
            {
                return new Documenter<C>()
                {
                    @Override
                    public void document(C target)
                    {
                        String unsignedDesc = "";

                        if (type.getClass().isAssignableFrom(NumericUnsignedType.class))
                        {
                            unsignedDesc = "unsigned, ";
                        }

                        target.text(adjective.asTextPreferA(startWithCapital)).text("variable length (").text(unsignedDesc)
                              .text(")").text(" integer value in LEB128 format");
                    }
                };
            }

            @Override
            public <C extends SimpleContents<?>> Documenter<C> details(String bufferReference)
            {
                return new NullDocumenter<>();
            }

            @Override
            public boolean requiresDedicatedSection()
            {
                return false;
            }

            @Override
            public String getTitle()
            {
                return null;
            }
        };
    }

    /**
     * Returns an array of types constructed potentially by this Codec.
     *
     * @return An array of types constructed potentially by this codec.
     */
    @Override
    public Class<?>[] getTypes()
    {
        return new Class[] { type.getType() };
    }

    /**
     * Returns the (common super) type of object constructed by this {@link Codec}.
     *
     * @return The (common super-) type of object constructed by this {@link Codec}.
     */
    @Override
    public Class<?> getType()
    {
        return type.getType();
    }
}
