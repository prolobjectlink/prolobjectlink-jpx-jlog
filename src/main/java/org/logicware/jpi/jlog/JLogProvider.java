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

import java.io.IOException;

import org.logicware.jpi.AbstractProvider;
import org.logicware.jpi.PrologAtom;
import org.logicware.jpi.PrologConverter;
import org.logicware.jpi.PrologDouble;
import org.logicware.jpi.PrologEngine;
import org.logicware.jpi.PrologExpression;
import org.logicware.jpi.PrologFloat;
import org.logicware.jpi.PrologInteger;
import org.logicware.jpi.PrologList;
import org.logicware.jpi.PrologLong;
import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologStructure;
import org.logicware.jpi.PrologTerm;
import org.logicware.jpi.PrologVariable;

import ubc.cs.JLog.Foundation.jKnowledgeBase;
import ubc.cs.JLog.Foundation.jPrologFileServices;
import ubc.cs.JLog.Foundation.jPrologServices;
import ubc.cs.JLog.Parser.pOperatorRegistry;
import ubc.cs.JLog.Parser.pParseStream;
import ubc.cs.JLog.Parser.pPredicateRegistry;
import ubc.cs.JLog.Terms.jPredicateTerms;
import ubc.cs.JLog.Terms.jTerm;

public final class JLogProvider extends AbstractProvider implements PrologProvider {

    private final jKnowledgeBase kb = new jKnowledgeBase();
    private final pOperatorRegistry or = new pOperatorRegistry();
    private final pPredicateRegistry pr = new pPredicateRegistry();
    private final jPrologServices engine = new jPrologServices(kb, pr, or);

    protected static final String DOT = ".";
    protected static final String BUILTINS = "builtins";

    public JLogProvider() {
	this(new JLogConverter());
    }

    public JLogProvider(PrologConverter<jTerm> converter) {
	super(converter);
	engine.setFileServices(new jPrologFileServices());
	try {
	    engine.loadLibrary(BUILTINS);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public boolean isCompliant() {
	return false;
    }

    public boolean preserveQuotes() {
	return false;
    }

    public PrologTerm prologNil() {
	return new JLogNil(this);
    }

    public PrologTerm prologCut() {
	return new JLogCut(this);
    }

    public PrologTerm prologFail() {
	return new JLogFail(this);
    }

    public PrologTerm prologTrue() {
	return new JLogTrue(this);
    }

    public PrologTerm prologFalse() {
	return new JLogFalse(this);
    }

    public PrologTerm prologEmpty() {
	return new JLogEmpty(this);
    }

    public PrologTerm parsePrologTerm(String str) {
	String s = str.charAt(str.length() - 1) == '.' ? str : str + DOT;
	return toTerm(new pParseStream(s, kb, pr, or).parseTerm(), PrologTerm.class);
    }

    public PrologTerm[] parsePrologTerms(String str) {
	String s = str.charAt(str.length() - 1) == '.' ? str : str + DOT;
	jPredicateTerms terms = new pParseStream(s, kb, pr, or).parseQuery();
	PrologTerm[] prologTerms = new PrologTerm[terms.size()];
	for (int i = 0; i < prologTerms.length; i++) {
	    prologTerms[i] = toTerm(terms.elementAt(i), PrologTerm.class);
	}
	return prologTerms;
    }

    public PrologEngine newEngine() {
	return new JLogEngine(this);
    }

    public PrologAtom newAtom(String functor) {
	return new JLogAtom(this, functor);
    }

    @Deprecated
    public PrologFloat newFloat(Number value) {
	return new JLogFloat(this, value);
    }

    public PrologDouble newDouble(Number value) {
	return new JLogDouble(this, value);
    }

    public PrologInteger newInteger(Number value) {
	return new JLogInteger(this, value);
    }

    public PrologLong newLong(Number value) {
	return new JLogLong(this, value);
    }

    public PrologVariable newVariable() {
	return new JLogVariable(this);
    }

    public PrologVariable newVariable(String name) {
	return new JLogVariable(this, name);
    }

    public PrologVariable newVariable(int position) {
	return newVariable();
    }

    public PrologVariable newVariable(String name, int position) {
	return newVariable(name);
    }

    public PrologList newList() {
	return new JLogEmpty(this);
    }

    public PrologList newList(PrologTerm[] arguments) {
	if (arguments != null && arguments.length > 0) {
	    return new JLogList(this, arguments);
	}
	return new JLogEmpty(this);
    }

    public PrologList newList(PrologTerm head, PrologTerm tail) {
	return new JLogList(this, head, tail);
    }

    public PrologList newList(PrologTerm[] arguments, PrologTerm tail) {
	return new JLogList(this, arguments, tail);
    }

    public PrologStructure newStructure(String functor, PrologTerm... arguments) {
	return new JLogStructure(this, functor, arguments);
    }

    @Deprecated
    public PrologExpression newExpression(PrologTerm left, String operator, PrologTerm right) {
	return new JLogExpression(this, left, operator, right);
    }

    @Override
    public String toString() {
	return "JLogProvider [converter=" + converter + "]";
    }

}
