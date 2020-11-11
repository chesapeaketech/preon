/**
 * Copyright (c) 2009-2016 Wilfred Springer
 * <p>
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * <p>
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package org.codehaus.preon.codec;

import junit.framework.TestCase;
import org.codehaus.preon.Codec;
import org.codehaus.preon.CodecFactory;
import org.codehaus.preon.ResolverContext;

import java.lang.reflect.AnnotatedElement;

import static org.easymock.EasyMock.*;

public class CompoundCodecFactoryTest extends TestCase
{

    private CodecFactory delegate1;

    private CodecFactory delegate2;

    private Codec<Integer> codec;

    private AnnotatedElement metadata;

    private ResolverContext context;

    private CompoundCodecFactory factory;

    public void setUp()
    {
        delegate1 = createMock(CodecFactory.class);
        delegate2 = createMock(CodecFactory.class);
        codec = createMock(Codec.class);
        context = createMock(ResolverContext.class);
        factory = new CompoundCodecFactory();
        factory.add(delegate1);
        factory.add(delegate2);
    }

    public void testNoMatch()
    {
        expect(delegate1.create(metadata, Integer.class, context))
                .andReturn(null);
        expect(delegate2.create(metadata, Integer.class, context))
                .andReturn(null);
        replay(delegate1, delegate2, context);
        assertNull(factory.create(metadata, Integer.class, context));
        verify(delegate1, delegate2, context);
    }

    public void testSecondMatch()
    {
        expect(delegate1.create(metadata, Integer.class, context))
                .andReturn(null);
        expect(delegate2.create(metadata, Integer.class, context))
                .andReturn(codec);
        replay(delegate1, delegate2, context);
        assertEquals(codec, factory.create(metadata, Integer.class, context));
        verify(delegate1, delegate2, context);
    }
}
