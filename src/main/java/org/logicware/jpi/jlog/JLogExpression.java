/*
 * #%L
 * prolobjectlink-jlog
 * %%
 * Copyright (C) 2012 - 2017 Logicware Project
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.logicware.jpi.jlog;

import org.logicware.jpi.PrologExpression;
import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologTerm;

import ubc.cs.JLog.Terms.jCompoundTerm;
import ubc.cs.JLog.Terms.jPredicate;
import ubc.cs.JLog.Terms.jTerm;

public final class JLogExpression extends JLogCompound implements PrologExpression {

    @Deprecated
    JLogExpression(PrologProvider provider, PrologTerm left, String operator, PrologTerm right) {
	super(EXPRESSION_TYPE, provider);
	PrologTerm[] operands = { left, right };
	value = new jPredicate(operator, adaptCompound(operands));
    }

    @Deprecated
    JLogExpression(PrologProvider provider, jTerm left, String functor, jTerm right) {
	super(EXPRESSION_TYPE, provider);
	jCompoundTerm compound = new jCompoundTerm(2);
	compound.addTerm(left);
	compound.addTerm(right);
	value = new jPredicate(functor, compound);
    }

    public String getOperator() {
	return getFunctor();
    }

    public PrologTerm getLeft() {
	return toTerm(((jPredicate) value).getArguments().elementAt(0), PrologTerm.class);
    }

    public PrologTerm getRight() {
	return toTerm(((jPredicate) value).getArguments().elementAt(1), PrologTerm.class);
    }

    @Override
    public PrologTerm[] getArguments() {
	jPredicate structure = (jPredicate) value;
	int arity = structure.getArity();
	PrologTerm[] arguments = new PrologTerm[arity];
	jCompoundTerm compound = structure.getArguments();
	for (int i = 0; i < arity; i++) {
	    arguments[i] = toTerm(compound.elementAt(i), PrologTerm.class);
	}
	return arguments;
    }

    @Override
    public int getArity() {
	jPredicate structure = (jPredicate) value;
	return structure.getArity();
    }

    @Override
    public String getFunctor() {
	jPredicate structure = (jPredicate) value;
	return structure.getName();
    }

    @Override
    public String getIndicator() {
	return getFunctor() + "/" + getArity();
    }

    @Override
    public boolean hasIndicator(String functor, int arity) {
	return getFunctor().equals(functor) && getArity() == arity;
    }

    @Override
    public PrologTerm clone() {
	PrologTerm l = getLeft();
	String o = getOperator();
	PrologTerm r = getRight();
	return new JLogExpression(provider, l, o, r);
    }

}
