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

import java.util.ArrayList;
import java.util.Enumeration;

import org.logicware.jpi.AbstractConverter;
import org.logicware.jpi.PrologAtom;
import org.logicware.jpi.PrologConverter;
import org.logicware.jpi.PrologDouble;
import org.logicware.jpi.PrologExpression;
import org.logicware.jpi.PrologFloat;
import org.logicware.jpi.PrologInteger;
import org.logicware.jpi.PrologList;
import org.logicware.jpi.PrologLong;
import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologStructure;
import org.logicware.jpi.PrologTerm;
import org.logicware.jpi.PrologVariable;
import org.logicware.jpi.UnknownTermError;

import ubc.cs.JLog.Foundation.jEquivalenceMapping;
import ubc.cs.JLog.Terms.jAtom;
import ubc.cs.JLog.Terms.jBinaryBuiltinPredicate;
import ubc.cs.JLog.Terms.jBuiltinPredicate;
import ubc.cs.JLog.Terms.jCompoundTerm;
import ubc.cs.JLog.Terms.jFail;
import ubc.cs.JLog.Terms.jInteger;
import ubc.cs.JLog.Terms.jList;
import ubc.cs.JLog.Terms.jListPair;
import ubc.cs.JLog.Terms.jNullList;
import ubc.cs.JLog.Terms.jPredicate;
import ubc.cs.JLog.Terms.jPredicateTerms;
import ubc.cs.JLog.Terms.jReal;
import ubc.cs.JLog.Terms.jTerm;
import ubc.cs.JLog.Terms.jTrue;
import ubc.cs.JLog.Terms.jVariable;

public class JLogConverter extends AbstractConverter<jTerm> implements PrologConverter<jTerm> {

    protected static final String DOT = ".";
    protected static final String NECK = ":-";
    protected static final String COMMA = ",";
    protected static final String BUILTINS = "builtins";
    protected static final String SIMPLE_ATOM_REGEX = "\\.|[a-z][A-Za-z0-9_]*";

    protected final jPredicateTerms emptyBody = new jPredicateTerms();
    protected final jEquivalenceMapping equivalence = new jEquivalenceMapping();

    protected jList adaptList(PrologTerm[] arguments) {
	jList pList = jNullList.NULL_LIST;
	for (int i = arguments.length - 1; i >= 0; --i) {
	    pList = new jListPair(fromTerm(arguments[i]), pList);
	}
	return pList;
    }

    protected jCompoundTerm adaptCompound(PrologTerm[] arguments) {
	jCompoundTerm compound = new jCompoundTerm(arguments.length);
	for (PrologTerm iPrologTerm : arguments) {
	    compound.addTerm(fromTerm(iPrologTerm));
	}
	return compound;
    }

    @Override
    public PrologTerm toTerm(jTerm prologTerm) {
	switch (prologTerm.type) {
	case jTerm.TYPE_NULLLIST:
	    return new JLogEmpty(provider);
	case jTerm.TYPE_ATOM:
	    String value = prologTerm.getName();
	    if (value.equals(JLogNil.NIL_STR)) {
		return new JLogNil(provider);
	    } else if (value.equals(JLogFalse.FALSE_STR)) {
		return new JLogFalse(provider);
	    } else if (!value.matches(SIMPLE_ATOM_REGEX)) {
		return new JLogAtom(provider, "'" + value + "'");
	    }
	    return new JLogAtom(provider, value);
	case jTerm.TYPE_INTEGER:
	    return new JLogInteger(provider, ((jInteger) prologTerm).getIntegerValue());
	case jTerm.TYPE_REAL:
	    return new JLogDouble(provider, ((jReal) prologTerm).getRealValue());
	case jTerm.TYPE_BUILTINPREDICATE:
	    jBuiltinPredicate builtin = (jBuiltinPredicate) prologTerm;
	    if (builtin.equivalence(jTrue.TRUE, equivalence)) {
		return new JLogTrue(provider);
	    } else if (builtin.equivalence(jFail.FAIL, equivalence)) {
		return new JLogFail(provider);
	    } else if (builtin.equivalence(JLogCut.JCUT, equivalence)) {
		return new JLogCut(provider);
	    }
	case jTerm.TYPE_VARIABLE:
	    String name = ((jVariable) prologTerm).getName();
	    PrologVariable variable = sharedVariables.get(name);
	    if (variable == null) {
		variable = new JLogVariable(provider, name);
		sharedVariables.put(variable.getName(), variable);
	    }
	    return variable;
	case jTerm.TYPE_LIST:
	    jTerm[] array = new jTerm[0];
	    jList jlist = (jList) prologTerm;
	    ArrayList<jTerm> arguments = new ArrayList<jTerm>();
	    Enumeration<jTerm> e = new JLogEnumeration(jlist);
	    while (e.hasMoreElements()) {
		arguments.add(e.nextElement());
	    }
	    return new JLogList(provider, arguments.toArray(array));
	case jTerm.TYPE_COMPARE:
	case jTerm.TYPE_OPERATOR:
	case jTerm.TYPE_ARITHMETIC:
	case jTerm.TYPE_UNARYOPERATOR:
	case jTerm.TYPE_NUMERICCOMPARE:
	case jTerm.TYPE_UNARYARITHMETIC: {
	    jBinaryBuiltinPredicate binary = (jBinaryBuiltinPredicate) prologTerm;
	    return new JLogExpression(provider, binary.getLHS(), binary.getName(), binary.getRHS());
	}
	case jTerm.TYPE_PREDICATE: {
	    jPredicate predicate = (jPredicate) prologTerm;
	    jCompoundTerm compound = predicate.getArguments();
	    String functor = predicate.getName();
	    return new JLogStructure(provider, functor, compound);
	}
	case jTerm.TYPE_PREDICATETERMS: {
	    jPredicateTerms terms = (jPredicateTerms) prologTerm;
	    Enumeration<?> k = terms.enumTerms();
	    if (k.hasMoreElements()) {
		PrologTerm body = null;
		while (k.hasMoreElements()) {
		    jTerm term = (jTerm) k.nextElement();
		    if (body != null) {
			body = new JLogStructure(provider, ",", body, toTerm(term));
		    } else {
			body = toTerm(term);
		    }
		}
		return body;
	    }
	    return new JLogTrue(provider);
	}
	}
	throw new UnknownTermError(prologTerm);
    }

    @Override
    public jTerm fromTerm(PrologTerm term) {
	switch (term.getType()) {
	case PrologTerm.NIL_TYPE:
	    return new jAtom("nil");
	case PrologTerm.CUT_TYPE:
	    return new jAtom("!");
	case PrologTerm.FAIL_TYPE:
	    return jFail.FAIL;
	case PrologTerm.TRUE_TYPE:
	    return jTrue.TRUE;
	case PrologTerm.FALSE_TYPE:
	    return new jAtom("false");
	case PrologTerm.EMPTY_TYPE:
	    return jNullList.NULL_LIST;
	case PrologTerm.ATOM_TYPE:
	    String value = ((PrologAtom) term).getStringValue();
	    return new jAtom(value);
	case PrologTerm.FLOAT_TYPE:
	    return new jReal(((PrologFloat) term).getFloatValue());
	case PrologTerm.INTEGER_TYPE:
	    return new jInteger(((PrologInteger) term).getIntValue());
	case PrologTerm.DOUBLE_TYPE:
	    return new jReal(((PrologDouble) term).getFloatValue());
	case PrologTerm.LONG_TYPE:
	    return new jInteger(((PrologLong) term).getIntValue());
	case PrologTerm.VARIABLE_TYPE:
	    String name = ((PrologVariable) term).getName();
	    jTerm variable = sharedPrologVariables.get(name);
	    if (variable == null) {
		variable = new jVariable(name);
		sharedPrologVariables.put(name, variable);
	    }
	    return variable;
	case PrologTerm.LIST_TYPE:
	    PrologTerm[] arguments = ((PrologList) term).getArguments();
	    return adaptList(arguments);
	case PrologTerm.STRUCTURE_TYPE:
	    String functor = term.getFunctor();
	    arguments = ((PrologStructure) term).getArguments();
	    return new jPredicate(functor, adaptCompound(arguments));
	case PrologTerm.EXPRESSION_TYPE:
	    PrologExpression exp = (PrologExpression) term;
	    return new jPredicate(exp.getOperator(), adaptCompound(exp.getArguments()));
	}
	throw new UnknownTermError(term);
    }

    @Override
    public jTerm[] fromTermArray(PrologTerm[] terms) {
	jTerm[] prologTerms = new jTerm[terms.length];
	for (int i = 0; i < terms.length; i++) {
	    prologTerms[i] = fromTerm(terms[i]);
	}
	return prologTerms;
    }

    @Override
    public jTerm fromTerm(PrologTerm head, PrologTerm[] body) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public PrologProvider createProvider() {
	return new JLogProvider(this);
    }

}
