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

import java.util.ArrayDeque;
import java.util.Deque;

import ubc.cs.JLog.Terms.jAtom;
import ubc.cs.JLog.Terms.jCompoundTerm;
import ubc.cs.JLog.Terms.jInteger;
import ubc.cs.JLog.Terms.jPredicate;
import ubc.cs.JLog.Terms.jReal;
import ubc.cs.JLog.Terms.jTerm;
import ubc.cs.JLog.Terms.jVariable;

/**
 * Util class for {@link JLogTerm}
 * 
 * @author Jose Zalacain
 * @since 1.0
 *
 */
final class JLogTermUtil {

	private JLogTermUtil() {
	}

	static final boolean unify(jTerm thisTerm, jTerm otherTerm) {
		return unify(thisTerm, otherTerm, new ArrayDeque<jVariable>());
	}

	static final boolean unify(jTerm thisTerm, jTerm otherTerm, Deque<jVariable> stack) {

		// if left term is variable
		if (thisTerm instanceof jVariable) {
			jVariable thisVariable = (jVariable) thisTerm;

			// if left term is free variable
			if (!thisVariable.isBound()) {
				thisVariable.setBinding(otherTerm);
				stack.push(thisVariable);
				return true;
			}

			// if left term is variable bound
			return unify(thisVariable.getTerm(), otherTerm, stack);
		}

		// if right term is variable
		else if (otherTerm instanceof jVariable) {
			jVariable otherVariable = (jVariable) otherTerm;

			// if right term is free variable
			if (!otherVariable.isBound()) {
				otherVariable.setBinding(thisTerm);
				stack.push(otherVariable);
				return true;
			}

			// if right term is variable bound
			return unify(otherVariable.getTerm(), thisTerm, stack);
		}

		// if at least term is a integer then check equivalence
		else if ((thisTerm instanceof jInteger) || (otherTerm instanceof jInteger)) {
			return thisTerm.equals(otherTerm);
		}

		// if at least term is a real then check equivalence
		else if ((thisTerm instanceof jReal) || (otherTerm instanceof jReal)) {
			return thisTerm.equals(otherTerm);
		}

		else if ((thisTerm instanceof jCompoundTerm) && (otherTerm instanceof jCompoundTerm)) {
			jCompoundTerm thisAtom = (jCompoundTerm) thisTerm;
			jCompoundTerm otherAtom = (jCompoundTerm) otherTerm;
			int thisArity = thisAtom.size();
			int otherArity = otherAtom.size();
			String thisFunctor = thisAtom.getName();
			String otherFunctor = otherAtom.getName();
			if (thisFunctor.equals(otherFunctor) && thisArity == otherArity) {
				for (int i = 0; i < thisArity; i++) {
					if (!unify(thisAtom.elementAt(i), otherAtom.elementAt(i), stack)) {
						return false;
					}
				}
				return true;
			}
			return false;
		}

		else if ((thisTerm instanceof jAtom) && (otherTerm instanceof jAtom)) {
			return thisTerm.getName().equals(otherTerm.getName());
		}

		// if both terms are predicate
		else if ((thisTerm instanceof jPredicate) && (otherTerm instanceof jPredicate)) {

			jPredicate thisPredicate = (jPredicate) thisTerm;
			jPredicate otherPredicate = (jPredicate) otherTerm;

			int thisArity = thisPredicate.getArity();
			int otherArity = otherPredicate.getArity();
			String thisFunctor = thisPredicate.getName();
			String otherFunctor = otherPredicate.getName();
			if (thisFunctor.equals(otherFunctor) && thisArity == otherArity) {
				jCompoundTerm thisArguments = thisPredicate.getArguments();
				jCompoundTerm otherArguments = otherPredicate.getArguments();
				for (int i = 0; i < thisArity; i++) {
					if (!unify(thisArguments, otherArguments, stack)) {
						return false;
					}
				}
				return true;
			}
		}

		return false;
	}

}
