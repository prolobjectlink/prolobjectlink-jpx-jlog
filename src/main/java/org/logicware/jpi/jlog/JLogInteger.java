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
import org.logicware.jpi.PrologDouble;
import org.logicware.jpi.PrologFloat;
import org.logicware.jpi.PrologInteger;
import org.logicware.jpi.PrologLong;
import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologTerm;

import ubc.cs.JLog.Terms.jInteger;

public class JLogInteger extends JLogTerm implements PrologInteger {

	public JLogInteger(PrologProvider provider) {
		super(INTEGER_TYPE, provider, new jInteger(0));
	}

	public JLogInteger(PrologProvider provider, Number value) {
		super(INTEGER_TYPE, provider, new jInteger(value.intValue()));
	}

	public PrologInteger getPrologInteger() {
		return new JLogInteger(provider, getIntValue());
	}

	public PrologFloat getPrologFloat() {
		return new JLogFloat(provider, getFloatValue());
	}

	public PrologDouble getPrologDouble() {
		return new JLogDouble(provider, getDoubleValue());
	}

	public PrologLong getPrologLong() {
		return new JLogLong(provider, getLongValue());
	}

	public long getLongValue() {
		return (long) getIntValue();
	}

	public double getDoubleValue() {
		return (double) getIntValue();
	}

	public int getIntValue() {
		return ((jInteger) value).getIntegerValue();
	}

	public float getFloatValue() {
		return (float) getIntValue();
	}

	public PrologTerm[] getArguments() {
		return new JLogInteger[0];
	}

	public int getArity() {
		throw new ArityError(this);
	}

	public String getFunctor() {
		throw new FunctorError(this);
	}

	public String getIndicator() {
		throw new IndicatorError(this);
	}

	public boolean hasIndicator(String functor, int arity) {
		throw new IndicatorError(this);
	}

}
