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

import static org.logicware.jpi.jlog.JLogProvider.FUNCTORS;
import static ubc.cs.JLog.Foundation.iType.TYPE_PREDICATE;
import static ubc.cs.JLog.Parser.pOperatorEntry.FX;
import static ubc.cs.JLog.Parser.pOperatorEntry.FY;
import static ubc.cs.JLog.Parser.pOperatorEntry.XF;
import static ubc.cs.JLog.Parser.pOperatorEntry.XFX;
import static ubc.cs.JLog.Parser.pOperatorEntry.XFY;
import static ubc.cs.JLog.Parser.pOperatorEntry.YF;
import static ubc.cs.JLog.Parser.pOperatorEntry.YFX;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

import org.logicware.jpi.AbstractEngine;
import org.logicware.jpi.Licenses;
import org.logicware.jpi.OperatorEntry;
import org.logicware.jpi.PredicateIndicator;
import org.logicware.jpi.PrologClause;
import org.logicware.jpi.PrologEngine;
import org.logicware.jpi.PrologIndicator;
import org.logicware.jpi.PrologOperator;
import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologQuery;
import org.logicware.jpi.PrologTerm;
import org.logicware.jpi.StructureExpectedError;

import ubc.cs.JLog.Foundation.iNameArityStub;
import ubc.cs.JLog.Foundation.jKnowledgeBase;
import ubc.cs.JLog.Foundation.jPrologFileServices;
import ubc.cs.JLog.Foundation.jPrologServices;
import ubc.cs.JLog.Foundation.jRule;
import ubc.cs.JLog.Foundation.jRuleDefinitions;
import ubc.cs.JLog.Foundation.jUnifiedVector;
import ubc.cs.JLog.Parser.pGenericPredicateEntry;
import ubc.cs.JLog.Parser.pOperatorEntry;
import ubc.cs.JLog.Parser.pOperatorRegistry;
import ubc.cs.JLog.Parser.pParseStream;
import ubc.cs.JLog.Parser.pPredicateOperatorEntry;
import ubc.cs.JLog.Parser.pPredicateRegistry;
import ubc.cs.JLog.Terms.iNameArity;
import ubc.cs.JLog.Terms.jBuiltinRule;
import ubc.cs.JLog.Terms.jCons;
import ubc.cs.JLog.Terms.jIf;
import ubc.cs.JLog.Terms.jPredicate;
import ubc.cs.JLog.Terms.jPredicateTerms;
import ubc.cs.JLog.Terms.jTerm;

public final class JLogEngine extends AbstractEngine implements PrologEngine {

	final jPrologServices engine;
	private final jKnowledgeBase kb;
	private final pOperatorRegistry or;
	private final pPredicateRegistry pr;

	protected static final String DOT = ".";
	protected static final String NECK = ":-";
	protected static final String COMMA = ",";
	protected static final String BUILTINS = "builtins";

	static final String SIMPLE_ATOM_REGEX = "\\.|\\?|#|[a-z][A-Za-z0-9_]*";

	protected final jPredicateTerms emptyBody = new jPredicateTerms();

	protected JLogEngine(PrologProvider provider) {
		super(provider);
		kb = new jKnowledgeBase();
		or = new pOperatorRegistry();
		pr = new pPredicateRegistry();
		engine = new jPrologServices(kb, pr, or);
		engine.setFileServices(new jPrologFileServices());
		try {
			engine.loadLibrary(BUILTINS);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private jRule toRule(String str, jPrologServices engine) {
		jKnowledgeBase ikb = engine.getKnowledgeBase();
		pOperatorRegistry ior = engine.getOperatorRegistry();
		pPredicateRegistry ipr = engine.getPredicateRegistry();
		String clause = str.charAt(str.length() - 1) == '.' ? str : str + DOT;
		jTerm term = new pParseStream(clause, ikb, ipr, ior).parseTerm();
		if (term.type == jTerm.TYPE_PREDICATE) { // fact
			jPredicate predicate = (jPredicate) term;
			return new jRule(predicate, emptyBody);
		} else if (term.type == jTerm.TYPE_IF) { // rule
			jIf rule = (jIf) term;
			jPredicate h = (jPredicate) rule.getLHS();
			jTerm ruleBody = rule.getRHS();
			switch (ruleBody.type) {

			// only and just only predicate
			case jTerm.TYPE_PREDICATE:
				jPredicate predicateBody = (jPredicate) ruleBody;
				jPredicateTerms b = new jPredicateTerms();
				b.addTerm(predicateBody);
				return new jRule(h, b);

			//
			case jTerm.TYPE_CONS:
				b = new jPredicateTerms();
				while (ruleBody instanceof jCons) {
					b.addTerm(((jCons) ruleBody).getLHS());
					ruleBody = ((jCons) ruleBody).getRHS().getTerm();
				}
				b.addTerm(ruleBody);
				return new jRule(h, b);

			//
			default:
				throw new StructureExpectedError(term);
			}

		}

		// no rule
		throw new StructureExpectedError(term);

	}

	private jRule toRule(PrologTerm head, PrologTerm... body) {
		jTerm termHead = fromTerm(head, jTerm.class);
		if (termHead.type == jTerm.TYPE_PREDICATE) {
			jPredicate predicateHead = (jPredicate) termHead;
			jPredicateTerms predicateBody = new jPredicateTerms();
			for (PrologTerm iPrologTerm : body) {
				predicateBody.addTerm(fromTerm(iPrologTerm, jTerm.class));
			}
			return new jRule(predicateHead, predicateBody);
		}
		throw new StructureExpectedError(head);
	}

	public void include(String file) {
		try {
			FileReader fileReader = new FileReader(file);
			new pParseStream(fileReader, kb, pr, or).parseSource();
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
						String functor = ruleHead.getName();
						if (!functor.matches(SIMPLE_ATOM_REGEX)) {
							StringBuilder buffer = new StringBuilder();
							buffer.append('\'');
							buffer.append(functor);
							buffer.append('\'');
							String quoted = "" + buffer + "";
							FUNCTORS.put(functor, quoted);

						}

						// rule body
						jPredicateTerms ruleBody = jRule.getBase();
						Enumeration<?> k = ruleBody.enumTerms();
						if (k.hasMoreElements()) {
							while (k.hasMoreElements()) {
								jTerm term = (jTerm) k.nextElement();
								if (term.type == TYPE_PREDICATE) {
									jPredicate bodyPart = (jPredicate) term;
									functor = bodyPart.getName();
									if (!functor.matches(SIMPLE_ATOM_REGEX)) {
										StringBuilder buffer = new StringBuilder();
										buffer.append('\'');
										buffer.append(functor);
										buffer.append('\'');
										String quoted = "" + buffer + "";
										FUNCTORS.put(functor, quoted);
									}
								}
							}
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void consult(String path) {
		try {
			kb.clearRules();
			FileReader fileReader = new FileReader(path);
			new pParseStream(fileReader, kb, pr, or).parseSource();
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
						String functor = ruleHead.getName();
						if (!functor.matches(SIMPLE_ATOM_REGEX)) {
							StringBuilder buffer = new StringBuilder();
							buffer.append('\'');
							buffer.append(functor);
							buffer.append('\'');
							String quoted = "" + buffer + "";
							FUNCTORS.put(functor, quoted);

						}

						// rule body
						jPredicateTerms ruleBody = jRule.getBase();
						Enumeration<?> k = ruleBody.enumTerms();
						if (k.hasMoreElements()) {
							while (k.hasMoreElements()) {
								jTerm term = (jTerm) k.nextElement();
								if (term.type == TYPE_PREDICATE) {
									jPredicate bodyPart = (jPredicate) term;
									functor = bodyPart.getName();
									if (!functor.matches(SIMPLE_ATOM_REGEX)) {
										StringBuilder buffer = new StringBuilder();
										buffer.append('\'');
										buffer.append(functor);
										buffer.append('\'');
										String quoted = "" + buffer + "";
										FUNCTORS.put(functor, quoted);
									}
								}
							}
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void persist(String path) {
		PrintWriter writer = null;
		try {
			StringBuilder buffer = new StringBuilder();
			writer = new PrintWriter(new FileWriter(path));
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
						buffer.append(ruleHead.toString(true));

						// rule body
						jPredicateTerms ruleBody = jRule.getBase();
						Enumeration<?> k = ruleBody.enumTerms();
						if (k.hasMoreElements()) {
							buffer.append(NECK);
							while (k.hasMoreElements()) {
								jTerm term = (jTerm) k.nextElement();
								buffer.append(term.toString(true));
								if (k.hasMoreElements()) {
									buffer.append(COMMA);
								}
							}
						}

						// rule end
						buffer.append(DOT);
						String str = "" + buffer + "";

						//
						for (Entry<String, String> entry : FUNCTORS.entrySet()) {

							// retrieve cached functors
							String key = entry.getKey();
							String value = entry.getValue();

							// first and unique term pattern
							String firstRegex = "(" + key + "";
							if (str.contains(firstRegex)) {
								str = str.replaceAll(key, value);
							}

							// non-first term pattern
							String nonFirstRegex = "," + key + "";
							if (str.contains(nonFirstRegex)) {
								str = str.replaceAll(key, value);
							}

						}

						writer.println(str);
						buffer = new StringBuilder();

					}
				}
			}
			writer.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	public void abolish(String functor, int arity) {
		iNameArityStub na = new iNameArityStub(functor, arity);
		jRuleDefinitions definitions = kb.getRuleDefinitionsMatch(na);
		if (definitions != null) {
			definitions.clearRules();
		}
	}

	public void asserta(String stringClause) {
		asserta(toRule(stringClause, engine));
	}

	public void asserta(PrologTerm head, PrologTerm... body) {
		asserta(toRule(head, body));
	}

	private void asserta(jRule rule) {
		if (!clause(rule)) {
			kb.addRuleFirst(rule);
		}
	}

	public void assertz(String stringClause) {
		assertz(toRule(stringClause, engine));
	}

	public void assertz(PrologTerm head, PrologTerm... body) {
		assertz(toRule(head, body));
	}

	private void assertz(jRule rule) {
		if (!clause(rule)) {
			kb.addRuleLast(rule);
		}
	}

	public boolean clause(String stringClause) {
		return clause(toRule(stringClause, engine));
	}

	public boolean clause(PrologTerm head, PrologTerm... body) {
		return clause(toRule(head, body));
	}

	private boolean clause(jRule rule) {
		jPredicate head = rule.getHead();
		jPredicateTerms body = rule.getBase();
		for (Enumeration<?> e = kb.enumDefinitions(); e.hasMoreElements();) {
			Object object = e.nextElement();
			if (object instanceof jRuleDefinitions) {
				jRuleDefinitions rds = (jRuleDefinitions) object;
				for (Enumeration<?> r = rds.enumRules(); r.hasMoreElements();) {
					Object object2 = r.nextElement();
					if (!(object2 instanceof jBuiltinRule)) {
						jRule jRule = (jRule) object2;
						jPredicate rh = jRule.getHead();
						jPredicateTerms rb = jRule.getBase();
						jUnifiedVector v = new jUnifiedVector();
						if (rh.unify(head, v) && rb.unify(body, v)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public void retract(String stringClause) {
		retract(toRule(stringClause, engine));
	}

	public void retract(PrologTerm head, PrologTerm... body) {
		retract(toRule(head, body));
	}

	private void retract(jRule rule) {
		String name = rule.getName();
		int arity = rule.getArity();
		jPredicate head = rule.getHead();
		jPredicateTerms body = rule.getBase();
		iNameArity na = new iNameArityStub(name, arity);
		jRuleDefinitions rds = kb.getRuleDefinitionsMatch(na);
		if (rds != null && rds.size() > 0) {
			Enumeration<?> e = rds.enumRules();
			while (e.hasMoreElements()) {
				Object object = e.nextElement();
				if (object instanceof jRule) {
					jRule jRule = (jRule) object;
					jPredicate ruleHead = jRule.getHead();
					jPredicateTerms ruleBody = jRule.getBase();
					jUnifiedVector v = new jUnifiedVector();
					if (ruleHead.unify(head, v) && ruleBody.unify(body, v)) {
						rds.removeRule(jRule);
					}
				}
			}
		}
	}

	public PrologQuery query(String stringQuery) {
		return new JLogQuery(this, stringQuery);
	}

	public PrologQuery query(PrologTerm... terms) {
		return new JLogQuery(this, terms);
	}

	public void operator(int priority, String specifier, String operator) {
		int type = -1;
		if (specifier.equals("fx")) {
			type = FX;
		} else if (specifier.equals("fy")) {
			type = FY;
		} else if (specifier.equals("xfx")) {
			type = XFX;
		} else if (specifier.equals("xfy")) {
			type = XFY;
		} else if (specifier.equals("yfx")) {
			type = YFX;
		} else if (specifier.equals("xf")) {
			type = XF;
		} else if (specifier.equals("yf")) {
			type = YF;
		}
		pOperatorEntry op = new pPredicateOperatorEntry(operator, type, priority);
		engine.getOperatorRegistry().addOperator(op);
	}

	public boolean currentPredicate(String functor, int arity) {
		PrologIndicator pi = new PredicateIndicator(functor, arity);
		return currentPredicates().contains(pi);
	}

	public boolean currentOperator(int priority, String specifier, String operator) {
		int type = -1;
		if (specifier.equals("fx")) {
			type = FX;
		} else if (specifier.equals("fy")) {
			type = FY;
		} else if (specifier.equals("xfx")) {
			type = XFX;
		} else if (specifier.equals("xfy")) {
			type = XFY;
		} else if (specifier.equals("yfx")) {
			type = YFX;
		} else if (specifier.equals("xf")) {
			type = XF;
		} else if (specifier.equals("yf")) {
			type = YF;
		}
		pOperatorEntry op = engine.getOperatorRegistry().getOperator(operator, true);
		op = op == null ? engine.getOperatorRegistry().getOperator(operator, false) : op;
		return op != null && op.getPriority() == priority && op.getType() == type;

	}

	public Set<PrologIndicator> currentPredicates() {

		// built-ins on libraries
		Set<PrologIndicator> builtins = new HashSet<PrologIndicator>();
		Enumeration<?> e = engine.getPredicateRegistry().enumPredicates();
		while (e.hasMoreElements()) {
			Object object = e.nextElement();
			if (object instanceof pGenericPredicateEntry) {
				pGenericPredicateEntry entry = (pGenericPredicateEntry) object;
				String functor = entry.getName();
				int arity = entry.getArity();
				PredicateIndicator pi = new PredicateIndicator(functor, arity);
				builtins.add(pi);
			}
		}

		// user defined predicates
		e = kb.enumDefinitions();
		while (e.hasMoreElements()) {
			jRuleDefinitions definitions = (jRuleDefinitions) e.nextElement();
			Enumeration<?> rules = definitions.enumRules();
			while (rules.hasMoreElements()) {
				Object object2 = rules.nextElement();
				if (!(object2 instanceof jBuiltinRule)) {
					jRule jRule = (jRule) object2;
					jPredicate ruleHead = jRule.getHead();
					String functor = ruleHead.getName();
					int arity = ruleHead.getArity();
					PredicateIndicator pi = new PredicateIndicator(functor, arity);
					builtins.add(pi);
				}
			}
		}

		return builtins;
	}

	public Set<PrologOperator> currentOperators() {
		HashSet<PrologOperator> operators = new HashSet<PrologOperator>();
		Enumeration<?> e = engine.getOperatorRegistry().enumOperators();
		while (e.hasMoreElements()) {
			Object object = e.nextElement();
			if (object instanceof pOperatorEntry) {
				pOperatorEntry entry = (pOperatorEntry) object;
				String specifier = "";
				String operator = entry.getName();
				int priority = entry.getPriority();
				switch (entry.getType()) {
				case FX:
					specifier = "fx";
					break;
				case FY:
					specifier = "fy";
					break;
				case XFX:
					specifier = "xfx";
					break;
				case XFY:
					specifier = "xfy";
					break;
				case YFX:
					specifier = "yfx";
					break;
				case XF:
					specifier = "xf";
					break;
				default:
					specifier = "yf";
					break;
				}
				OperatorEntry op = new OperatorEntry(priority, specifier, operator);
				operators.add(op);
			}
		}
		return operators;
	}

	public Iterator<PrologClause> iterator() {
		Collection<PrologClause> cls = new LinkedList<PrologClause>();
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
					PrologTerm head = toTerm(ruleHead, PrologTerm.class);

					// rule body
					jPredicateTerms ruleBody = jRule.getBase();
					PrologTerm body = toTerm(ruleBody, PrologTerm.class);

					// rule end
					if (!(body instanceof JLogTrue)) {
						cls.add(new JLogClause(head, body, false, false, false));
					} else {
						cls.add(new JLogClause(head, false, false, false));
					}

				}
			}
		}
		return new PrologProgramIterator(cls);
	}

	public int getProgramSize() {
		int programSize = 0;
		Enumeration<?> de = kb.enumDefinitions();
		while (de.hasMoreElements()) {
			jRuleDefinitions rules = (jRuleDefinitions) de.nextElement();
			Enumeration<?> re = rules.enumRules();
			while (re.hasMoreElements()) {
				Object rule = re.nextElement();
				if (!(rule instanceof jBuiltinRule)) {
					programSize++;
				}
			}
		}
		return programSize;
	}

	public String getLicense() {
		return Licenses.GPL_V2;
	}

	public String getVersion() {
		String credits = jPrologServices.getRequiredCreditInfo();
		StringTokenizer tokenizer = new StringTokenizer(credits);
		/* String name = */tokenizer.nextToken();
		return tokenizer.nextToken();
	}

	public String getName() {
		String credits = jPrologServices.getRequiredCreditInfo();
		StringTokenizer tokenizer = new StringTokenizer(credits);
		return tokenizer.nextToken();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((engine == null) ? 0 : engine.hashCode());
		result = prime * result + ((kb == null) ? 0 : kb.hashCode());
		result = prime * result + ((or == null) ? 0 : or.hashCode());
		result = prime * result + ((pr == null) ? 0 : pr.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JLogEngine other = (JLogEngine) obj;
		if (engine == null) {
			if (other.engine != null)
				return false;
		} else if (!engine.equals(other.engine))
			return false;
		if (kb == null) {
			if (other.kb != null)
				return false;
		} else if (!kb.equals(other.kb))
			return false;
		if (or == null) {
			if (other.or != null)
				return false;
		} else if (!or.equals(other.or))
			return false;
		if (pr == null) {
			if (other.pr != null)
				return false;
		} else if (!pr.equals(other.pr))
			return false;
		return true;
	}

	public void dispose() {
		engine.release();
		kb.clearRules();
	}

}
