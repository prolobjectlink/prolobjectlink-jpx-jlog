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

import java.util.Stack;

import org.logicware.jpi.AbstractTerm;
import org.logicware.jpi.NumberExpectedError;
import org.logicware.jpi.PrologNumber;
import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologTerm;

import ubc.cs.JLog.Foundation.jEquivalenceMapping;
import ubc.cs.JLog.Foundation.jType;
import ubc.cs.JLog.Terms.jInteger;
import ubc.cs.JLog.Terms.jReal;
import ubc.cs.JLog.Terms.jTerm;
import ubc.cs.JLog.Terms.jVariable;

public abstract class JLogTerm extends AbstractTerm implements PrologTerm {

    protected int vIndex;
    protected jTerm value;
    protected PrologTerm vValue;
    protected static int vIdexer = 0;

    protected final jEquivalenceMapping equivalence = new jEquivalenceMapping();

    protected JLogTerm(int type, PrologProvider provider) {
	super(type, provider);
    }

    protected JLogTerm(int type, PrologProvider provider, jTerm value) {
	super(type, provider);
	this.value = value;
    }

    protected JLogTerm(int type, PrologProvider provider, int vIndex) {
	this(type, provider, new jVariable());
	this.vIndex = vIndex;
    }

    protected JLogTerm(int type, PrologProvider provider, String name, int vIndex) {
	this(type, provider, new jVariable(name));
	this.vIndex = vIndex;
    }

    protected final void checkNumberType(PrologTerm term) {
	if (!term.isNumber()) {
	    throw new NumberExpectedError(term);
	}
    }

    public final boolean isAtom() {
	return this instanceof JLogAtom || isEmptyList();
    }

    public final boolean isNumber() {
	return isDouble() || isFloat() || isInteger() || isLong();
    }

    public final boolean isFloat() {
	return this instanceof JLogFloat;
    }

    public final boolean isDouble() {
	return this instanceof JLogDouble;
    }

    public final boolean isInteger() {
	return this instanceof JLogInteger;
    }

    public final boolean isLong() {
	return this instanceof JLogLong;
    }

    public final boolean isVariable() {
	return this instanceof JLogVariable;
    }

    public final boolean isList() {
	return (this instanceof JLogList)

		||

	(this instanceof JLogEmpty);
    }

    public final boolean isStructure() {
	return this instanceof JLogStructure;
    }

    public final boolean isNil() {
	return this instanceof JLogNil;
    }

    public final boolean isEmptyList() {
	return this instanceof JLogEmpty;
    }

    public final boolean isEvaluable() {
	return this instanceof JLogExpression;
    }

    public boolean isAtomic() {
	switch (value.type) {
	case jType.TYPE_ATOM:
	    return true;
	case jType.TYPE_INTEGER:
	    return true;
	case jType.TYPE_REAL:
	    return true;
	case jType.TYPE_VARIABLE:
	    return true;
	}
	return false;
    }

    public boolean isCompound() {
	switch (value.type) {
	case jType.TYPE_PREDICATE:
	    return true;
	case jType.TYPE_LIST:
	    return true;
	}
	return false;
    }

    public abstract String getIndicator();

    public abstract boolean hasIndicator(String functor, int arity);

    public abstract int getArity();

    public abstract String getFunctor();

    public abstract PrologTerm[] getArguments();

    public final boolean unify(PrologTerm term) {

	// jTerm thisTerm = value;
	// jTerm otherTerm = fromTerm(term, jTerm.class);
	// jUnifiedVector v = new jUnifiedVector();
	// boolean match = thisTerm.unify(otherTerm, v);
	// v.restoreVariables();
	// return match;

	Stack<PrologTerm> stack = new Stack<PrologTerm>();
	boolean match = unify(term, stack);
	for (PrologTerm prologTerm : stack) {
	    unwrap(prologTerm, JLogTerm.class).unbind();
	}
	stack.clear();
	return match;

    }

    protected final boolean unify(PrologTerm term, Stack<PrologTerm> stack) {
	return unify(unwrap(term, JLogTerm.class), stack);
    }

    protected final boolean unify(JLogTerm otherTerm, Stack<PrologTerm> stack) {

	JLogTerm thisTerm = this;

	if (thisTerm.isVariableBound()) {
	    return thisTerm.vValue.unwrap(JLogTerm.class).unify(otherTerm, stack);
	}

	else if (otherTerm.isVariableBound()) {
	    return otherTerm.vValue.unwrap(JLogTerm.class).unify(thisTerm, stack);
	}

	// current term is a free variable
	else if (thisTerm.isVariableNotBound()) {
	    // if (!thisTerm.occurs(otherTerm)) {
	    thisTerm.bind(otherTerm);
	    stack.push(thisTerm);
	    return true;
	    // }
	}

	// the other term is a free variable
	else if (otherTerm.isVariableNotBound()) {
	    // if (!otherTerm.occurs(thisTerm)) {
	    otherTerm.bind(thisTerm);
	    stack.push(otherTerm);
	    return true;
	    // }
	}

	// if at least term is a number then check equivalence
	else if (thisTerm.isNumber() || otherTerm.isNumber()) {
	    if ((thisTerm.isInteger() || thisTerm.isLong()) && (otherTerm.isInteger() || otherTerm.isLong())) {
		int thisInt = fromTerm(thisTerm, jInteger.class).getIntegerValue();
		int otherInt = fromTerm(otherTerm, jInteger.class).getIntegerValue();
		return thisInt == otherInt;
	    }
	    return thisTerm.equals(otherTerm);
	}

	else {

	    int thisArity = thisTerm.getArity();
	    int otherArity = otherTerm.getArity();
	    String thisFunctor = thisTerm.getFunctor();
	    String otherFunctor = otherTerm.getFunctor();
	    if (thisFunctor.equals(otherFunctor) && thisArity == otherArity) {
		PrologTerm[] thisArguments = thisTerm.getArguments();
		PrologTerm[] otherArguments = otherTerm.getArguments();
		for (int i = 0; i < thisArity; i++) {
		    if (thisArguments[i] != null && otherArguments[i] != null) {
			JLogTerm thisJLogTerm = unwrap(thisArguments[i], JLogTerm.class);
			JLogTerm otherJLogTerm = unwrap(otherArguments[i], JLogTerm.class);
			if (!thisJLogTerm.unify(otherJLogTerm, stack)) {
			    return false;
			}
		    }
		}
		return true;
	    }
	}
	return false;

    }

    /**
     * Check if Variable and bound. A variable bound is synonym of not free
     * variable because this variable have instance value.
     * 
     * @return true if Variable and bound.
     */
    protected final boolean isVariableBound() {
	return isVariable() && vValue != null;
    }

    /**
     * Check if current variable is not bound. A variable not bound is synonym
     * of free variable because this variable don't have instance value.
     * 
     * @return true if Variable and not bound.
     */
    protected final boolean isVariableNotBound() {
	return isVariable() && vValue == null;
    }

    /** Binds a variable to a term */
    protected final void bind(JLogTerm term) {
	if (this != term) {
	    vValue = term;
	}
    }

    /** Unbinds a term reseting it to a variable */
    protected final void unbind() {
	vValue = null;
    }

    /**
     * If the current term is variable check that your occurrence in other term
     * passed as parameter. If the other term is compound and at less one
     * argument match with the current variable term then return true indicating
     * that the current variable term occurs in compound term. If the other term
     * is compound and at less one argument is another compound term, then the
     * current term check your occurrence in this compound term in recursive
     * way.
     * 
     * @param otherTerm
     *            term to check if current term occurs inside him
     * @return true if current term occurs in other compound term, false in
     *         another case
     */
    protected final boolean occurs(PrologTerm otherTerm) {
	JLogTerm thisTerm = this;
	if (thisTerm.isVariable() && otherTerm.isCompound()) {
	    PrologTerm[] otherArguments = otherTerm.getArguments();
	    for (int i = 0; i < otherArguments.length; i++) {
		PrologTerm argument = otherArguments[i];
		if (argument != null) {
		    if (argument.isVariable()) {
			return thisTerm == argument;
		    } else if (argument.isCompound()) {
			if (thisTerm.occurs(argument)) {
			    return true;
			}
		    }
		}
	    }
	}
	return false;
    }

    public final int compareTo(PrologTerm term) {

	// jTerm thisTerm = value;
	// jTerm otherTerm = fromTerm(term, jTerm.class);
	// return thisTerm.compare(otherTerm, true);

	int termType = term.getType();

	if ((type >> 8) < (termType >> 8)) {
	    return -1;
	} else if ((type >> 8) > (termType >> 8)) {
	    return 1;
	}

	switch (type) {
	case ATOM_TYPE:

	    // alphabetic functor comparison
	    int result = value.getName().compareTo(term.getFunctor());
	    if (result < 0) {
		return -1;
	    } else if (result > 0) {
		return 1;
	    }
	    break;

	case FLOAT_TYPE: {

	    checkNumberType(term);
	    float thisValue = ((jReal) value).getRealValue();
	    float otherValue = ((PrologNumber) term).getFloatValue();

	    if (thisValue < otherValue) {
		return -1;
	    } else if (thisValue > otherValue) {
		return 1;
	    }

	}
	    break;

	case LONG_TYPE: {

	    checkNumberType(term);
	    long thisValue = ((jInteger) value).getIntegerValue();
	    long otherValue = ((PrologNumber) term).getLongValue();

	    if (thisValue < otherValue) {
		return -1;
	    } else if (thisValue > otherValue) {
		return 1;
	    }

	}
	    break;

	case DOUBLE_TYPE: {

	    checkNumberType(term);
	    double thisValue = ((jReal) value).getRealValue();
	    double otherValue = ((PrologNumber) term).getDoubleValue();

	    if (thisValue < otherValue) {
		return -1;
	    } else if (thisValue > otherValue) {
		return 1;
	    }

	}
	    break;

	case INTEGER_TYPE: {

	    checkNumberType(term);
	    int thisValue = ((jInteger) value).getIntegerValue();
	    int otherValue = ((PrologNumber) term).getIntValue();

	    if (thisValue < otherValue) {
		return -1;
	    } else if (thisValue > otherValue) {
		return 1;
	    }

	}
	    break;

	case LIST_TYPE:
	case EMPTY_TYPE:
	case STRUCTURE_TYPE:
	case EXPRESSION_TYPE:

	    PrologTerm thisCompound = this;
	    PrologTerm otherCompound = term;

	    // comparison by arity
	    if (thisCompound.getArity() < otherCompound.getArity()) {
		return -1;
	    } else if (thisCompound.getArity() > otherCompound.getArity()) {
		return 1;
	    }

	    // alphabetic functor comparison
	    result = thisCompound.getFunctor().compareTo(otherCompound.getFunctor());
	    if (result < 0) {
		return -1;
	    } else if (result > 0) {
		return 1;
	    }

	    // arguments comparison
	    PrologTerm[] thisArguments = thisCompound.getArguments();
	    PrologTerm[] otherArguments = otherCompound.getArguments();

	    for (int i = 0; i < thisArguments.length; i++) {
		PrologTerm thisArgument = thisArguments[i];
		PrologTerm otherArgument = otherArguments[i];
		if (thisArgument != null && otherArgument != null) {
		    result = thisArgument.compareTo(otherArgument);
		    if (result != 0) {
			return result;
		    }
		}
	    }
	    break;

	case VARIABLE_TYPE:

	    JLogTerm thisVariable = unwrap(JLogTerm.class);
	    JLogTerm otherVariable = unwrap(term, JLogTerm.class);
	    if (thisVariable.vIndex < otherVariable.vIndex) {
		return -1;
	    } else if (thisVariable.vIndex > otherVariable.vIndex) {
		return 1;
	    }
	    break;

	}

	return 0;
    }

    @Override
    public final int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + type;
	result = prime * result + ((value == null) ? 0 : value.hashCode());
	return result;
    }

    @Override
    public final boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	JLogTerm other = (JLogTerm) obj;
	if (type != other.type)
	    return false;
	if (value == null) {
	    if (other.value != null)
		return false;
	} else if (!value.equivalence(other.value, equivalence))
	    return false;

	// else if (!value.equals(other.value))
	// return false;

	return true;
    }

    @Override
    public final String toString() {
	return value.toString(true);
    }

    @Override
    public abstract PrologTerm clone();

}
