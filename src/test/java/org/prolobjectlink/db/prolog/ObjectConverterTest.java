/*-
 * #%L
 * >prolobjectlink-jpx-jlog
 * %%
 * Copyright (C) 2012 - 2019 Prolobjectlink Project
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
package org.prolobjectlink.db.prolog;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.junit.Test;
import org.prolobjectlink.BaseTest;
import org.prolobjectlink.db.ObjectConverter;
import org.prolobjectlink.domain.geometry.Point;
import org.prolobjectlink.domain.geometry.Polygon;
import org.prolobjectlink.domain.geometry.Segment;
import org.prolobjectlink.domain.geometry.Tetragon;
import org.prolobjectlink.prolog.Prolog;
import org.prolobjectlink.prolog.PrologProvider;
import org.prolobjectlink.prolog.PrologTerm;
import org.prolobjectlink.prolog.jlog.JLog;

public class ObjectConverterTest extends BaseTest {

	protected static final Class<?> engine = JLog.class;
	protected static final PrologProvider provider = Prolog.getProvider(engine);
	protected static final ObjectConverter<PrologTerm> converter = new PrologObjectConverter(provider);

	@Test
	public final void testToObject() {

		// Booleans
		assertEquals(true, converter.toObject(provider.prologTrue()));
		assertEquals(false, converter.toObject(provider.prologFalse()));

		// String
		assertEquals(new String("prolobjectlink"), converter.toObject(provider.newAtom("prolobjectlink")));
		assertEquals(new String("Prolobjectlink"), converter.toObject(provider.newAtom("Prolobjectlink")));

		// Float
		assertEquals(new Float(3.14159), converter.toObject(provider.newFloat(3.14159)));

		// Double
		assertEquals(new Double(3.1415926535897932384626433832795),
				converter.toObject(provider.newDouble(3.1415926535897932384626433832795)));

		// Integer
		assertEquals(new Integer(100), converter.toObject(provider.newInteger(100)));

		// Long
		assertEquals(new Long(100), converter.toObject(provider.newLong(100)));

		// Object[]
		assertArrayEquals(new Object[0], (Object[]) converter.toObject(provider.prologEmpty()));
		assertArrayEquals(new Object[0], (Object[]) converter.toObject(provider.newList()));
		assertArrayEquals(new Object[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }, (Object[]) converter.toObject(
				provider.newList(new PrologTerm[] { zero, one, two, three, four, five, six, seven, eight, nine })));

		// Date
		assertEquals(new Date(1000),
				converter.toObject(provider.parseTerm("'" + PrologDate.class.getName() + "'(1000)")));

		// Collections
		assertEquals(new ArrayList<Integer>(), converter.toObject(provider
				.parseTerm("'" + PrologArrayList.class.getName() + "'(0,[nil,nil,nil,nil,nil,nil,nil,nil,nil,nil])")));
		assertEquals(new HashMap<Integer, Integer>(), converter.toObject(provider
				.parseTerm("'" + PrologHashMap.class.getName() + "'(0,[nil,nil,nil,nil,nil,nil,nil,nil,nil,nil])")));
		assertEquals(new HashSet<Integer>(), converter.toObject(provider
				.parseTerm("'" + PrologHashSet.class.getName() + "'(0,[nil,nil,nil,nil,nil,nil,nil,nil,nil,nil])")));
		assertEquals(new TreeMap<Integer, Integer>(),
				converter.toObject(provider.parseTerm("'" + PrologTreeMap.class.getName() + "'(nil,nil,nil,nil)")));
		assertEquals(new TreeSet<Integer>(),
				converter.toObject(provider.parseTerm("'" + PrologTreeSet.class.getName() + "'(nil,nil,nil)")));

		// Prolog Structure
		assertEquals(new Point("a", 3, 14), converter.toObject(provider.newStructure("'" + Point.class.getName() + "'",
				provider.newAtom("a"), provider.newInteger(3), provider.newInteger(14))));

		// Prolog Structure Composition
		assertEquals(new Segment("ab", new Point("a", 3, 14), new Point("b", 3, 14)),
				converter.toObject(provider.newStructure("'" + Segment.class.getName() + "'", provider.newAtom("ab"),
						provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("a"),
								provider.newInteger(3), provider.newInteger(14)),
						provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("b"),
								provider.newInteger(3), provider.newInteger(14)))));

		// Prolog Structure Composition and Inheritance
		assertEquals(
				new Polygon("triangle", new Segment("ab", new Point("a", 3, 14), new Point("b", 3, 14)),
						new Segment("bc", new Point("b", 3, 14), new Point("c", 3, 14)),
						new Segment("ca", new Point("c", 3, 14), new Point("a", 3, 14))),

				converter.toObject(
						provider.newStructure("'" + Polygon.class.getName() + "'", provider.newAtom("triangle"),

								provider.newStructure("'" + Segment.class.getName() + "'", provider.newAtom("ab"),
										provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("a"),
												provider.newInteger(3), provider.newInteger(14)),
										provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("b"),
												provider.newInteger(3), provider.newInteger(14))),

								provider.newStructure("'" + Segment.class.getName() + "'", provider.newAtom("bc"),
										provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("b"),
												provider.newInteger(3), provider.newInteger(14)),
										provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("c"),
												provider.newInteger(3), provider.newInteger(14))),

								provider.newStructure("'" + Segment.class.getName() + "'", provider.newAtom("ca"),
										provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("c"),
												provider.newInteger(3), provider.newInteger(14)),
										provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("a"),
												provider.newInteger(3), provider.newInteger(14)))

						)));

		assertEquals(
				new Tetragon("tetragon", new Segment("ab", new Point("a", 3, 14), new Point("b", 3, 14)),
						new Segment("bc", new Point("b", 3, 14), new Point("c", 3, 14)),
						new Segment("cd", new Point("c", 3, 14), new Point("d", 3, 14)),
						new Segment("da", new Point("d", 3, 14), new Point("a", 3, 14))),

				converter.toObject(
						provider.newStructure("'" + Tetragon.class.getName() + "'", provider.newAtom("tetragon"),

								provider.newStructure("'" + Segment.class.getName() + "'", provider.newAtom("ab"),
										provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("a"),
												provider.newInteger(3), provider.newInteger(14)),
										provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("b"),
												provider.newInteger(3), provider.newInteger(14))),

								provider.newStructure("'" + Segment.class.getName() + "'", provider.newAtom("bc"),
										provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("b"),
												provider.newInteger(3), provider.newInteger(14)),
										provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("c"),
												provider.newInteger(3), provider.newInteger(14))),

								provider.newStructure("'" + Segment.class.getName() + "'", provider.newAtom("cd"),
										provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("c"),
												provider.newInteger(3), provider.newInteger(14)),
										provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("d"),
												provider.newInteger(3), provider.newInteger(14))),

								provider.newStructure("'" + Segment.class.getName() + "'", provider.newAtom("da"),
										provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("d"),
												provider.newInteger(3), provider.newInteger(14)),
										provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("a"),
												provider.newInteger(3), provider.newInteger(14)))

						)));

	}

	@Test
	public final void testToObjectsArray() {
		assertArrayEquals(new Object[0], converter.toObjectsArray(new PrologTerm[0]));
		assertArrayEquals(new Object[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }, converter
				.toObjectsArray(new PrologTerm[] { zero, one, two, three, four, five, six, seven, eight, nine }));
	}

	@Test
	public final void testToTerm() {

		// Prolog Booleans
		assertEquals(provider.prologTrue(), converter.toTerm(new Boolean(true)));
		assertEquals(provider.prologFalse(), converter.toTerm(new Boolean(false)));

		// Prolog Atom
		assertEquals(provider.newAtom("prolobjectlink"), converter.toTerm(new String("prolobjectlink")));
		assertEquals(provider.newAtom("Prolobjectlink"), converter.toTerm(new String("Prolobjectlink")));

		// Prolog Float
		assertEquals(provider.newFloat(3.14159), converter.toTerm(new Float(3.14159)));

		// Prolog Double
		assertEquals(provider.newDouble(3.1415926535897932384626433832795),
				converter.toTerm(new Double(3.1415926535897932384626433832795)));

		// Prolog Integer
		assertEquals(provider.newInteger(100), converter.toTerm(new Integer(100)));

		// Prolog Long
		assertEquals(provider.newLong(100), converter.toTerm(new Long(100)));

		// Prolog List
		assertEquals(provider.prologEmpty(), converter.toTerm(new Object[0]));
		assertEquals(provider.newList(), converter.toTerm(new Object[0]));
		assertEquals(provider.newList(new PrologTerm[] { zero, one, two, three, four, five, six, seven, eight, nine }),
				converter.toTerm(new Object[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }));

		// Prolog Structure
		assertEquals(provider.newStructure("'" + Point.class.getName() + "'", provider.newVariable("Idp", 0),
				provider.newVariable("X", 1), provider.newVariable("Y", 2)), converter.toTerm(Point.class));

		assertEquals(provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("a"),
				provider.newInteger(3), provider.newInteger(14)), converter.toTerm(new Point("a", 3, 14)));

		// Prolog Structure Composition
		assertEquals(
				provider.newStructure("'" + Segment.class.getName() + "'", provider.newAtom("ab"),
						provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("a"),
								provider.newInteger(3), provider.newInteger(14)),
						provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("b"),
								provider.newInteger(3), provider.newInteger(14))),
				converter.toTerm(new Segment("ab", new Point("a", 3, 14), new Point("b", 3, 14))));

		// Prolog Structure Composition and Inheritance
		assertEquals(

				provider.newStructure("'" + Polygon.class.getName() + "'", provider.newAtom("triangle"),

						provider.newStructure("'" + Segment.class.getName() + "'", provider.newAtom("ab"),
								provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("a"),
										provider.newInteger(3), provider.newInteger(14)),
								provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("b"),
										provider.newInteger(3), provider.newInteger(14))),

						provider.newStructure("'" + Segment.class.getName() + "'", provider.newAtom("bc"),
								provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("b"),
										provider.newInteger(3), provider.newInteger(14)),
								provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("c"),
										provider.newInteger(3), provider.newInteger(14))),

						provider.newStructure("'" + Segment.class.getName() + "'", provider.newAtom("ca"),
								provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("c"),
										provider.newInteger(3), provider.newInteger(14)),
								provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("a"),
										provider.newInteger(3), provider.newInteger(14)))

				)

				,

				converter
						.toTerm(new Polygon("triangle", new Segment("ab", new Point("a", 3, 14), new Point("b", 3, 14)),
								new Segment("bc", new Point("b", 3, 14), new Point("c", 3, 14)),
								new Segment("ca", new Point("c", 3, 14), new Point("a", 3, 14)))));

		assertEquals(

				provider.newStructure("'" + Tetragon.class.getName() + "'", provider.newAtom("tetragon"),

						provider.newStructure("'" + Segment.class.getName() + "'", provider.newAtom("ab"),
								provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("a"),
										provider.newInteger(3), provider.newInteger(14)),
								provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("b"),
										provider.newInteger(3), provider.newInteger(14))),

						provider.newStructure("'" + Segment.class.getName() + "'", provider.newAtom("bc"),
								provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("b"),
										provider.newInteger(3), provider.newInteger(14)),
								provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("c"),
										provider.newInteger(3), provider.newInteger(14))),

						provider.newStructure("'" + Segment.class.getName() + "'", provider.newAtom("cd"),
								provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("c"),
										provider.newInteger(3), provider.newInteger(14)),
								provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("d"),
										provider.newInteger(3), provider.newInteger(14))),

						provider.newStructure("'" + Segment.class.getName() + "'", provider.newAtom("da"),
								provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("d"),
										provider.newInteger(3), provider.newInteger(14)),
								provider.newStructure("'" + Point.class.getName() + "'", provider.newAtom("a"),
										provider.newInteger(3), provider.newInteger(14)))

				)

				,

				converter.toTerm(
						new Tetragon("tetragon", new Segment("ab", new Point("a", 3, 14), new Point("b", 3, 14)),
								new Segment("bc", new Point("b", 3, 14), new Point("c", 3, 14)),
								new Segment("cd", new Point("c", 3, 14), new Point("d", 3, 14)),
								new Segment("da", new Point("d", 3, 14), new Point("a", 3, 14)))));

		// Prolog Date Structure
		assertEquals(provider.parseStructure("'" + PrologDate.class.getName() + "'(1000)"),
				converter.toTerm(new Date(1000)));

		// Prolog Collection Structures
		assertEquals(provider.parseStructure("'" + PrologArrayList.class.getName() + "'(0,[])"),
				converter.toTerm(new ArrayList<Integer>()));
		assertEquals(provider.parseStructure("'" + PrologHashMap.class.getName() + "'(0,[])"),
				converter.toTerm(new HashMap<Integer, Integer>()));
		assertEquals(provider.parseStructure("'" + PrologHashSet.class.getName() + "'(0,[])"),
				converter.toTerm(new HashSet<Integer>()));
		assertEquals(provider.parseStructure("'" + PrologTreeMap.class.getName() + "'(nil,nil,nil,nil)"),
				converter.toTerm(new TreeMap<Integer, Integer>()));
		assertEquals(provider.parseStructure("'" + PrologTreeSet.class.getName() + "'(nil,nil,nil)"),
				converter.toTerm(new TreeSet<Integer>()));

	}

	@Test
	public final void testToTermsArray() {
		assertArrayEquals(new PrologTerm[0], converter.toTermsArray(new Object[0]));
		assertArrayEquals(new PrologTerm[] { zero, one, two, three, four, five, six, seven, eight, nine },
				converter.toTermsArray(new Object[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }));
	}

}
