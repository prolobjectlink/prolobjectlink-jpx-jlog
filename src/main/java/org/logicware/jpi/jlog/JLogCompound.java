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

import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologTerm;

import ubc.cs.JLog.Foundation.jEquivalenceMapping;
import ubc.cs.JLog.Terms.jCompoundTerm;
import ubc.cs.JLog.Terms.jList;
import ubc.cs.JLog.Terms.jListPair;
import ubc.cs.JLog.Terms.jNullList;
import ubc.cs.JLog.Terms.jPredicateTerms;
import ubc.cs.JLog.Terms.jTerm;

public abstract class JLogCompound extends JLogTerm {

	protected JLogCompound(int type, PrologProvider provider) {
		super(type, provider);
	}

	protected JLogCompound(int type, PrologProvider provider, jTerm value) {
		super(type, provider, value);
	}

	protected static final String SIMPLE_ATOM_REGEX = ".|[a-z][A-Za-z0-9_]*";

	protected final jPredicateTerms emptyBody = new jPredicateTerms();
	protected final jEquivalenceMapping equiMap = new jEquivalenceMapping();

	protected final jList adaptList(PrologTerm[] arguments) {
		jList pList = jNullList.NULL_LIST;
		for (int i = arguments.length - 1; i >= 0; --i) {
			pList = new jListPair(fromTerm(arguments[i], jTerm.class), pList);
		}
		return pList;
	}

	protected final jCompoundTerm adaptCompound(PrologTerm[] arguments) {
		jCompoundTerm compound = new jCompoundTerm(arguments.length);
		for (PrologTerm iPrologTerm : arguments) {
			compound.addTerm(fromTerm(iPrologTerm, jTerm.class));
		}
		return compound;
	}

}
