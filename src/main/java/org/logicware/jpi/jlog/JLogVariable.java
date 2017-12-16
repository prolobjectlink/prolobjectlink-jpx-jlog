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

import org.logicware.jpi.ArityError;
import org.logicware.jpi.FunctorError;
import org.logicware.jpi.IndicatorError;
import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologTerm;
import org.logicware.jpi.PrologVariable;

import ubc.cs.JLog.Terms.jVariable;

public class JLogVariable extends JLogTerm implements PrologVariable {

    public JLogVariable(PrologProvider provider) {
	super(VARIABLE_TYPE, provider, vIdexer++);
    }

    public JLogVariable(PrologProvider provider, String name) {
	super(VARIABLE_TYPE, provider, name, vIdexer++);
    }

    public boolean isAnonymous() {
	return !((jVariable) value).isNamed();
    }

    public String getName() {
	return ((jVariable) value).getName();
    }

    public void setName(String name) {
	this.value = new jVariable(name);
    }

    @Override
    public PrologTerm[] getArguments() {
	return new JLogVariable[0];
    }

    @Override
    public int getArity() {
	throw new ArityError(this);
    }

    @Override
    public String getFunctor() {
	throw new FunctorError(this);
    }

    @Override
    public String getIndicator() {
	throw new IndicatorError(this);
    }

    @Override
    public boolean hasIndicator(String functor, int arity) {
	throw new IndicatorError(this);
    }

    public int getPosition() {
	throw new UnsupportedOperationException("getPosition()");
    }

    @Override
    public PrologTerm clone() {
	String n = getName();
	return new JLogVariable(provider, n);
    }

}
