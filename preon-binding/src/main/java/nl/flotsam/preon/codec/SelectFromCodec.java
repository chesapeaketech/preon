/*
 * Copyright (C) 2008 Wilfred Springer
 * 
 * This file is part of Preon.
 * 
 * Preon is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2, or (at your option) any later version.
 * 
 * Preon is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * Preon; see the file COPYING. If not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Linking this library statically or dynamically with other modules is making a
 * combined work based on this library. Thus, the terms and conditions of the
 * GNU General Public License cover the whole combination.
 * 
 * As a special exception, the copyright holders of this library give you
 * permission to link this library with independent modules to produce an
 * executable, regardless of the license terms of these independent modules, and
 * to copy and distribute the resulting executable under terms of your choice,
 * provided that you also meet, for each linked independent module, the terms
 * and conditions of the license of that module. An independent module is a
 * module which is not derived from or based on this library. If you modify this
 * library, you may extend this exception to your version of the library, but
 * you are not obligated to do so. If you do not wish to do so, delete this
 * exception statement from your version.
 */

package nl.flotsam.preon.codec;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;

import nl.flotsam.limbo.BindingException;
import nl.flotsam.limbo.Document;
import nl.flotsam.limbo.Expression;
import nl.flotsam.limbo.Expressions;
import nl.flotsam.limbo.Reference;
import nl.flotsam.limbo.ReferenceContext;
import nl.flotsam.preon.Builder;
import nl.flotsam.preon.Codec;
import nl.flotsam.preon.CodecDescriptor;
import nl.flotsam.preon.CodecFactory;
import nl.flotsam.preon.DecodingException;
import nl.flotsam.preon.Resolver;
import nl.flotsam.preon.ResolverContext;
import nl.flotsam.preon.annotation.Choices;
import nl.flotsam.preon.buffer.BitBuffer;
import nl.flotsam.preon.buffer.ByteOrder;

public class SelectFromCodec<T> implements Codec<T> {

    private final static String PREFIX_NAME = "prefix";

    private int size;

    private ByteOrder byteOrder;

    private List<Expression<Boolean, Resolver>> conditions;

    private List<Codec<?>> codecs;

    private Class<?>[] types;

    private Class<?> type;

    private Codec<?> defaultCodec;

    public SelectFromCodec(Class<?> type, Choices choices, ResolverContext context,
            CodecFactory factory, AnnotatedElement metadata) {
        this.size = choices.prefixSize();
        this.types = new Class<?>[choices.alternatives().length];
        this.byteOrder = choices.byteOrder();
        conditions = new ArrayList<Expression<Boolean, Resolver>>();
        codecs = new ArrayList<Codec<?>>();
        if (choices.defaultType() != Void.class) {
            factory.create(null, choices.defaultType(), context);
        }
        context = new SelectFromContext(context, size);
        for (int i = 0; i < choices.alternatives().length; i++) {
            types[i] = choices.alternatives()[i].type();
            conditions.add(Expressions
                    .createBoolean(context, choices.alternatives()[i].condition()));
            codecs.add(factory.create(null, choices.alternatives()[i].type(), context));
        }
    }

    public T decode(BitBuffer buffer, Resolver resolver, Builder builder) throws DecodingException {
        if (size <= 0) {
            for (int i = 0; i < conditions.size(); i++) {
                if (conditions.get(i).eval(resolver)) {
                    return (T) codecs.get(i).decode(buffer, resolver, builder);
                }
            }
        } else {
            int size = buffer.readAsInt(this.size, byteOrder);
            Resolver prefixResolver = new PrefixAwareResolver(resolver, size);
            for (int i = 0; i < conditions.size(); i++) {
                if (conditions.get(i).eval(prefixResolver)) {
                    return (T) codecs.get(i).decode(buffer, resolver, builder);
                }
            }
        }
        if (defaultCodec != null) {
            return (T) defaultCodec.decode(buffer, resolver, builder);
        } else {
            return null;
        }
    }

    public CodecDescriptor getCodecDescriptor() {
        // TODO Auto-generated method stub
        return null;
    }

    public int getSize(Resolver resolver) {
        return -1;
    }

    public Expression<Integer, Resolver> getSize() {
        return null;
    }

    public Class<?> getType() {
        return type;
    }

    public Class<?>[] getTypes() {
        return types;
    }

    private static class SelectFromContext implements ResolverContext {

        private ResolverContext wrapped;

        private int size;

        public SelectFromContext(ResolverContext wrapped, int size) {
            this.wrapped = wrapped;
            this.size = size;
        }

        public Reference<Resolver> selectAttribute(String name) throws BindingException {
            if (PREFIX_NAME.equals(name)) {
                return new PrefixReference(this, size);
            } else {
                return wrapped.selectAttribute(name);
            }
        }

        public Reference<Resolver> selectItem(String index) throws BindingException {
            return wrapped.selectItem(index);
        }

        public Reference<Resolver> selectItem(Expression<Integer, Resolver> index)
                throws BindingException {
            return wrapped.selectItem(index);
        }

        public void document(Document target) {
            wrapped.document(target);
        }

    }

    private static class PrefixReference implements Reference<Resolver> {

        private ReferenceContext<Resolver> referenceContext;

        private int size;

        public PrefixReference(ReferenceContext<Resolver> context, int size) {
            this.referenceContext = context;
            this.size = size;
        }

        public ReferenceContext<Resolver> getReferenceContext() {
            return referenceContext;
        }

        public Class<?> getType() {
            return Integer.class;
        }

        public boolean isAssignableTo(Class<?> type) {
            return type.isAssignableFrom(Integer.class);
        }

        public Object resolve(Resolver context) {
            return context.get(PREFIX_NAME);
        }

        public Reference<Resolver> selectAttribute(String name) throws BindingException {
            throw new BindingException("No attributes defined for Integer.");
        }

        public Reference<Resolver> selectItem(String index) throws BindingException {
            throw new BindingException("No attributes defined for Integer.");
        }

        public Reference<Resolver> selectItem(Expression<Integer, Resolver> index)
                throws BindingException {
            throw new BindingException("No attributes defined for Integer.");
        }

        public void document(Document target) {
            target.text("the first " + size + " bits of ");
            referenceContext.document(target);
        }

    }

    private static class PrefixAwareResolver implements Resolver {

        private Resolver resolver;

        private int prefixValue;

        public PrefixAwareResolver(Resolver resolver, int prefixValue) {
            this.resolver = resolver;
            this.prefixValue = prefixValue;
        }

        public Object get(String name) throws BindingException {
            if (PREFIX_NAME.equals(name)) {
                return prefixValue;
            } else {
                return resolver.get(name);
            }
        }

        public Resolver getOuter() {
            return resolver.getOuter();
        }

    }

}
