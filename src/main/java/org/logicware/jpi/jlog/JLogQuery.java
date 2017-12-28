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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.logicware.jpi.AbstractQuery;
import org.logicware.jpi.PrologEngine;
import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologQuery;
import org.logicware.jpi.PrologTerm;

import ubc.cs.JLog.Foundation.jKnowledgeBase;
import ubc.cs.JLog.Foundation.jPrologAPI;
import ubc.cs.JLog.Foundation.jPrologServices;
import ubc.cs.JLog.Foundation.jRule;
import ubc.cs.JLog.Foundation.jRuleDefinitions;
import ubc.cs.JLog.Foundation.jVariableVector;
import ubc.cs.JLog.Parser.pOperatorRegistry;
import ubc.cs.JLog.Parser.pParseStream;
import ubc.cs.JLog.Parser.pPredicateRegistry;
import ubc.cs.JLog.Terms.jBuiltinRule;
import ubc.cs.JLog.Terms.jPredicate;
import ubc.cs.JLog.Terms.jPredicateTerms;
import ubc.cs.JLog.Terms.jTerm;
import ubc.cs.JLog.Terms.jVariable;

public final class JLogQuery extends AbstractQuery implements PrologQuery {

	final jPrologAPI jlogApi;
	private Map<?, ?> solution;
	private jVariableVector vector = new jVariableVector();

	protected static final String DOT = ".";
	protected static final String NECK = ":-";
	protected static final String COMMA = ",";
	protected static final String SIMPLE_ATOM_REGEX = ".|[a-z][A-Za-z0-9_]*";

	protected static final PrologProvider provider = new JLogProvider();

	JLogQuery(PrologEngine engine, String str) {
		super(engine);

		// saving variable order
		JLogEngine pe = engine.unwrap(JLogEngine.class);
		jKnowledgeBase kb = pe.engine.getKnowledgeBase();
		pOperatorRegistry or = pe.engine.getOperatorRegistry();
		pPredicateRegistry pr = pe.engine.getPredicateRegistry();
		String s = str.charAt(str.length() - 1) == '.' ? str : str + DOT;
		pParseStream parser = new pParseStream(s, kb, pr, or);
		jPredicateTerms terms = parser.parseQuery();
		terms.enumerateVariables(vector, true);

		// adapt program to string
		String source = adapt(pe.engine);
		jlogApi = new jPrologAPI(source);
		try {
			solution = jlogApi.query(s);
		} catch (Exception e) {
			solution = null;
		}

	}

	JLogQuery(PrologEngine engine, PrologTerm[] terms) {
		super(engine);

		String str = Arrays.toString(terms).substring(1);
		str = str.substring(0, str.length() - 1) + DOT;

		// saving variable order
		JLogEngine pe = engine.unwrap(JLogEngine.class);
		jKnowledgeBase kb = pe.engine.getKnowledgeBase();
		pOperatorRegistry or = pe.engine.getOperatorRegistry();
		pPredicateRegistry pr = pe.engine.getPredicateRegistry();
		pParseStream parser = new pParseStream(str, kb, pr, or);
		jPredicateTerms jpts = parser.parseQuery();
		jpts.enumerateVariables(vector, true);

		// adapt program to string
		String source = adapt(pe.engine);
		jlogApi = new jPrologAPI(source);
		try {
			solution = jlogApi.query(str);
		} catch (Exception e) {
			solution = null;
		}

	}

	boolean quoted(String functor) {
		if (!functor.isEmpty()) {
			char beginChar = functor.charAt(0);
			char endChar = functor.charAt(functor.length() - 1);
			return beginChar == '\'' && endChar == '\'';
		}
		return false;
	}

	String removeQuoted(String functor) {
		if (quoted(functor)) {
			String newFunctor = "";
			newFunctor += functor.substring(1, functor.length() - 1);
			return newFunctor;
		}
		return functor;
	}

	PrologTerm adapt(Object object) {

		// null pointer
		if (object == null) {
			return new JLogNil(provider);
		}

		// string data type
		else if (object instanceof String) {
			String string = (String) object;
			String unQuotedString = removeQuoted(string);
			if (unQuotedString.matches(SIMPLE_ATOM_REGEX)) {
				return new JLogAtom(provider, unQuotedString);
			}
			return new JLogAtom(provider, string);
		}

		// primitives and wrappers data types
		else if (object.getClass() == boolean.class || object instanceof Boolean) {
			return (Boolean) object ? new JLogTrue(provider) : new JLogFalse(provider);
		} else if (object.getClass() == int.class || object instanceof Integer) {
			return new JLogInteger(provider, (Integer) object);
		} else if (object.getClass() == float.class || object instanceof Float) {
			return new JLogFloat(provider, (Float) object);
		} else if (object.getClass() == long.class || object instanceof Long) {
			return new JLogLong(provider, (Long) object);
		} else if (object.getClass() == double.class || object instanceof Double) {
			return new JLogDouble(provider, (Double) object);
		}

		//
		else if (object instanceof Object[]) {
			Object[] objects = (Object[]) object;
			PrologTerm[] terms = new PrologTerm[objects.length];
			for (int i = 0; i < objects.length; i++) {
				terms[i] = adapt(objects[i]);
			}
			return new JLogList(provider, terms);
		} else if (object instanceof jTerm) {
			return adapt((jTerm) object);
		}
		return null;
	}

	protected String adapt(jPrologServices engine) {
		jKnowledgeBase kb = engine.getKnowledgeBase();
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		Enumeration<?> enumeration = kb.enumDefinitions();
		while (enumeration.hasMoreElements()) {
			jRuleDefinitions object = (jRuleDefinitions) enumeration.nextElement();
			Enumeration<?> r = object.enumRules();
			while (r.hasMoreElements()) {
				Object object2 = r.nextElement();
				if (!(object2 instanceof jBuiltinRule)) {
					jRule jRule = (jRule) object2;

					// rule head
					jPredicate ruleHead = jRule.getHead();
					writer.print(ruleHead);

					// rule body
					jPredicateTerms ruleBody = jRule.getBase();
					Enumeration<?> k = ruleBody.enumTerms();
					if (k.hasMoreElements()) {
						writer.print(NECK);
						while (k.hasMoreElements()) {
							jTerm term = (jTerm) k.nextElement();
							writer.print(term);
							if (k.hasMoreElements()) {
								writer.print(COMMA);
							}
						}
					}

					// rule end
					writer.println(DOT);
				}
			}
		}
		writer.flush();
		writer.close();

		return "" + stringWriter + "";
	}

	public boolean hasSolution() {
		return solution != null;
	}

	public boolean hasMoreSolutions() {
		return solution != null;
	}

	public PrologTerm[] oneSolution() {
		if (hasSolution()) {
			int index = 0;
			PrologTerm[] array = new PrologTerm[solution.size()];
			for (Enumeration<?> e = vector.enumerate(); e.hasMoreElements();) {
				Object object = e.nextElement();
				if (object instanceof jVariable) {
					String key = ((jVariable) object).getName();
					array[index++] = adapt(solution.get(key));
				}
			}
			return array;
		}
		return new PrologTerm[0];
	}

	public Map<String, PrologTerm> oneVariablesSolution() {
		if (hasSolution()) {
			Map<String, PrologTerm> map = new HashMap<String, PrologTerm>(solution.size());
			for (Enumeration<?> e = vector.enumerate(); e.hasMoreElements();) {
				Object object = e.nextElement();
				if (object instanceof jVariable) {
					String key = ((jVariable) object).getName();
					map.put(key, adapt(solution.get(key)));
				}
			}
			return map;
		}
		return new HashMap<String, PrologTerm>(0);
	}

	public PrologTerm[] nextSolution() {
		PrologTerm[] array = oneSolution();
		solution = jlogApi.retry();
		return array;
	}

	public Map<String, PrologTerm> nextVariablesSolution() {
		Map<String, PrologTerm> map = oneVariablesSolution();
		solution = jlogApi.retry();
		return map;
	}

	public PrologTerm[][] nSolutions(int n) {
		if (n > 0) {
			int m = 0;
			int index = 0;
			List<PrologTerm[]> all = new ArrayList<PrologTerm[]>();
			while (hasMoreSolutions() && index < n) {
				PrologTerm[] solutions = oneSolution();
				m = solutions.length > m ? solutions.length : m;
				all.add(solutions);
				index++;
				solution = jlogApi.retry();
			}

			PrologTerm[][] allSolutions = new PrologTerm[n][m];
			for (int i = 0; i < n; i++) {
				PrologTerm[] solutionArray = all.get(i);
				for (int j = 0; j < m; j++) {
					allSolutions[i][j] = solutionArray[j];
				}
			}
			return allSolutions;
		}
		return new PrologTerm[0][0];
	}

	@SuppressWarnings("unchecked")
	public Map<String, PrologTerm>[] nVariablesSolutions(int n) {
		if (n > 0) {
			int index = 0;
			Map<String, PrologTerm>[] solutionMaps = new HashMap[n];
			while (hasMoreSolutions() && index < n) {
				Map<String, PrologTerm> solutionMap = oneVariablesSolution();
				solutionMaps[index++] = solutionMap;
				solution = jlogApi.retry();
			}
			return solutionMaps;
		}
		return new HashMap[0];
	}

	public PrologTerm[][] allSolutions() {
		// n:solutionCount, m:solutionSize
		int n = 0;
		int m = 0;
		List<PrologTerm[]> all = new ArrayList<PrologTerm[]>();
		while (hasMoreSolutions()) {
			PrologTerm[] solutions = oneSolution();
			m = solutions.length > m ? solutions.length : m;
			n++;
			all.add(solutions);
			solution = jlogApi.retry();
		}

		PrologTerm[][] allSolutions = new PrologTerm[n][m];
		for (int i = 0; i < n; i++) {
			PrologTerm[] solutionArray = all.get(i);
			for (int j = 0; j < m; j++) {
				allSolutions[i][j] = solutionArray[j];
			}
		}
		return allSolutions;
	}

	@SuppressWarnings("unchecked")
	public Map<String, PrologTerm>[] allVariablesSolutions() {
		List<Map<String, PrologTerm>> allVariables = new ArrayList<Map<String, PrologTerm>>();
		while (hasMoreSolutions()) {
			Map<String, PrologTerm> variables = oneVariablesSolution();
			allVariables.add(variables);
			solution = jlogApi.retry();
		}

		int lenght = allVariables.size();
		Map<String, PrologTerm>[] allVariablesSolution = new HashMap[lenght];
		for (int i = 0; i < lenght; i++) {
			allVariablesSolution[i] = allVariables.get(i);
		}
		return allVariablesSolution;
	}

	public void dispose() {
		jlogApi.stop();
		if (solution != null) {
			solution.clear();
		}
	}

}
