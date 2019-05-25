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
package org.prolobjectlink;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.prolobjectlink.db.ObjectConverter;
import org.prolobjectlink.db.PersistentContainer;
import org.prolobjectlink.db.Predicate;
import org.prolobjectlink.db.ProcedureQuery;
import org.prolobjectlink.db.prolog.PrologObjectConverter;
import org.prolobjectlink.db.prolog.PrologStorageManager;
import org.prolobjectlink.db.prolog.PrologStoragePool;
import org.prolobjectlink.db.storage.AbstractStorageManager;
import org.prolobjectlink.domain.geometry.Point;
import org.prolobjectlink.domain.geometry.Polygon;
import org.prolobjectlink.domain.geometry.Segment;
import org.prolobjectlink.prolog.PrologEngine;
import org.prolobjectlink.prolog.PrologTerm;

public class StorageManagerTest extends BaseTest {

	@Test
	public final void testClear() {

		storageManager.getTransaction().begin();

		storageManager.delete(Point.class);
		storageManager.delete(Segment.class);
		storageManager.delete(Polygon.class);

		storageManager.getTransaction().commit();

		assertFalse(storageManager.contains(a));
		assertFalse(storageManager.contains(b));
		assertFalse(storageManager.contains(c));
		assertFalse(storageManager.contains(d));

		storageManager.insert(a);
		storageManager.insert(b);
		storageManager.insert(c);
		storageManager.insert(d);

		storageManager.getTransaction().commit();

		assertTrue(storageManager.contains(a));
		assertTrue(storageManager.contains(b));
		assertTrue(storageManager.contains(c));
		assertTrue(storageManager.contains(d));

		storageManager.clear();

		storageManager.getTransaction().commit();

		assertFalse(storageManager.contains(a));
		assertFalse(storageManager.contains(b));
		assertFalse(storageManager.contains(c));
		assertFalse(storageManager.contains(d));

		storageManager.getTransaction().close();

	}

	@Test
	public final void testClose() {

		storageManager.getTransaction().begin();

		storageManager.insert(a);
		storageManager.insert(b);
		storageManager.insert(c);
		storageManager.insert(ab);
		storageManager.insert(bc);
		storageManager.insert(ca);
		storageManager.insert(triangle);

		assertTrue(storageManager.isOpen());

		storageManager.close();

		assertFalse(storageManager.isOpen());

		// assertFalse(objectManager.contains(a));
		// assertFalse(objectManager.contains(b));
		// assertFalse(objectManager.contains(c));
		// assertFalse(objectManager.contains(d));

		storageManager.getTransaction().commit();
		storageManager.getTransaction().close();

	}

	@Test
	public final void testInsertOArray() {

		storageManager.getTransaction().begin();

		storageManager.delete(Point.class);
		storageManager.delete(Segment.class);
		storageManager.delete(Polygon.class);

		assertFalse(storageManager.contains(a));
		assertFalse(storageManager.contains(b));
		assertFalse(storageManager.contains(c));
		assertFalse(storageManager.contains(d));

		storageManager.insert(a, b, c, d);
		storageManager.insert(ab, bc, ca, cd, da);
		storageManager.insert(triangle, tetragon);

		assertTrue(storageManager.contains(a));
		assertTrue(storageManager.contains(b));
		assertTrue(storageManager.contains(c));
		assertTrue(storageManager.contains(d));

		assertEquals(a, storageManager.createQuery(a).getSolution());
		assertEquals(b, storageManager.createQuery(b).getSolution());
		assertEquals(c, storageManager.createQuery(c).getSolution());
		assertEquals(d, storageManager.createQuery(d).getSolution());

		storageManager.getTransaction().commit();
		storageManager.getTransaction().close();

	}

	@Test
	public final void testUpdate() {

		storageManager.getTransaction().begin();

		storageManager.insert(a);
		storageManager.insert(b);
		storageManager.insert(c);
		storageManager.insert(ab);
		storageManager.insert(bc);
		storageManager.insert(ca);
		storageManager.insert(triangle);

		// create new update objects

		Point newA = new Point("a", 6, 28);
		Point newB = new Point("b", 6, 28);
		Point newC = new Point("c", 6, 28);

		Segment newAB = new Segment("ab", newA, newB);
		Segment newBC = new Segment("bc", newB, newC);
		Segment newCA = new Segment("ca", newC, newA);

		Polygon newTriangle = new Polygon("triangle", newAB, newBC, newCA);

		assertTrue(storageManager.contains(a));
		assertTrue(storageManager.contains(b));
		assertTrue(storageManager.contains(c));
		assertTrue(storageManager.contains(ab));
		assertTrue(storageManager.contains(bc));
		assertTrue(storageManager.contains(ca));
		assertTrue(storageManager.contains(triangle));

		assertFalse(storageManager.contains(newA));
		assertFalse(storageManager.contains(newB));
		assertFalse(storageManager.contains(newC));
		assertFalse(storageManager.contains(newAB));
		assertFalse(storageManager.contains(newBC));
		assertFalse(storageManager.contains(newCA));
		assertFalse(storageManager.contains(newTriangle));

		// updating

		storageManager.update(a, newA);
		storageManager.update(b, newB);
		storageManager.update(c, newC);
		storageManager.update(ab, newAB);
		storageManager.update(bc, newBC);
		storageManager.update(ca, newCA);
		storageManager.update(triangle, newTriangle);

		assertFalse(storageManager.contains(a));
		assertFalse(storageManager.contains(b));
		assertFalse(storageManager.contains(c));
		assertFalse(storageManager.contains(ab));
		assertFalse(storageManager.contains(bc));
		assertFalse(storageManager.contains(ca));
		assertFalse(storageManager.contains(triangle));

		assertTrue(storageManager.contains(newA));
		assertTrue(storageManager.contains(newB));
		assertTrue(storageManager.contains(newC));
		assertTrue(storageManager.contains(newAB));
		assertTrue(storageManager.contains(newBC));
		assertTrue(storageManager.contains(newCA));
		assertTrue(storageManager.contains(newTriangle));

		// reverting update changes

		storageManager.update(newA, a);
		storageManager.update(newB, b);
		storageManager.update(newC, c);
		storageManager.update(newAB, ab);
		storageManager.update(newBC, bc);
		storageManager.update(newCA, ca);
		storageManager.update(newTriangle, triangle);

		assertTrue(storageManager.contains(a));
		assertTrue(storageManager.contains(b));
		assertTrue(storageManager.contains(c));
		assertTrue(storageManager.contains(ab));
		assertTrue(storageManager.contains(bc));
		assertTrue(storageManager.contains(ca));
		assertTrue(storageManager.contains(triangle));

		assertFalse(storageManager.contains(newA));
		assertFalse(storageManager.contains(newB));
		assertFalse(storageManager.contains(newC));
		assertFalse(storageManager.contains(newAB));
		assertFalse(storageManager.contains(newBC));
		assertFalse(storageManager.contains(newCA));
		assertFalse(storageManager.contains(newTriangle));

		storageManager.getTransaction().commit();
		storageManager.getTransaction().close();

	}

	@Test
	public final void testDeleteClassOfO() {

		storageManager.getTransaction().begin();

		storageManager.delete(Point.class);
		storageManager.delete(Segment.class);
		storageManager.delete(Polygon.class);

		assertFalse(storageManager.contains(Point.class));
		assertFalse(storageManager.contains(Segment.class));
		assertFalse(storageManager.contains(Polygon.class));

		storageManager.getTransaction().commit();
		storageManager.getTransaction().close();

	}

	@Test
	public final void testDeleteOArray() {

		storageManager.getTransaction().begin();

		storageManager.insert(a, b, c, d);
		storageManager.insert(ab, bc, ca, cd, da);
		storageManager.insert(triangle, tetragon);

		assertTrue(storageManager.contains(a));
		assertTrue(storageManager.contains(b));
		assertTrue(storageManager.contains(c));
		assertTrue(storageManager.contains(d));

		assertEquals(a, storageManager.createQuery(a).getSolution());
		assertEquals(b, storageManager.createQuery(b).getSolution());
		assertEquals(c, storageManager.createQuery(c).getSolution());
		assertEquals(d, storageManager.createQuery(d).getSolution());

		storageManager.delete(a, b, c, d);
		storageManager.delete(ab, bc, ca, cd, da);
		storageManager.delete(triangle, tetragon);

		assertFalse(storageManager.contains(a));
		assertFalse(storageManager.contains(b));
		assertFalse(storageManager.contains(c));
		assertFalse(storageManager.contains(d));

		storageManager.getTransaction().commit();
		storageManager.getTransaction().close();

	}

	@Test
	public void testGetTransaction() {
		assertNotNull(storageManager.getTransaction());
		storageManager.getTransaction().begin();
		assertTrue(storageManager.getTransaction().isActive());
		storageManager.getTransaction().commit();
		assertTrue(storageManager.getTransaction().isActive());
		storageManager.getTransaction().close();
		assertFalse(storageManager.getTransaction().isActive());
	}

	@Test
	public final void testContainsString() {

		storageManager.getTransaction().begin();

		storageManager.delete(Point.class);
		storageManager.delete(Segment.class);
		storageManager.delete(Polygon.class);

		assertFalse(storageManager.contains(a));
		assertFalse(storageManager.contains(b));
		assertFalse(storageManager.contains(c));
		assertFalse(storageManager.contains(d));

		storageManager.insert(a, b, c, d);
		storageManager.insert(ab, bc, ca, cd, da);
		storageManager.insert(triangle, tetragon);

		assertTrue(storageManager.contains(a));
		assertTrue(storageManager.contains(b));
		assertTrue(storageManager.contains(c));
		assertTrue(storageManager.contains(d));

		assertTrue(storageManager.contains("'" + Point.class.getName() + "'(Idp, X, Y)"));
		assertTrue(storageManager.contains("'" + Point.class.getName() + "'( a, 3,14 )"));
		assertTrue(storageManager.contains("'" + Point.class.getName() + "'( b, 3,14 )"));
		assertTrue(storageManager.contains("'" + Point.class.getName() + "'( c, 3,14 )"));

		assertTrue(storageManager.contains("'" + Segment.class.getName() + "'(Ids, Point0, Point1)"));
		assertTrue(storageManager.contains("'" + Segment.class.getName() + "'( ab, '" + Point.class.getName()
				+ "'( a, 3,14 ), '" + Point.class.getName() + "'( b, 3,14 ) )"));
		assertTrue(storageManager.contains("'" + Segment.class.getName() + "'( bc, '" + Point.class.getName()
				+ "'( b, 3,14 ), '" + Point.class.getName() + "'( c, 3,14 ) )"));
		assertTrue(storageManager.contains("'" + Segment.class.getName() + "'( ca, '" + Point.class.getName()
				+ "'( c, 3,14 ), '" + Point.class.getName() + "'( a, 3,14 ) )"));

		assertTrue(
				storageManager.contains("'" + Polygon.class.getName() + "'( triangle, Segment0, Segment1, Segment2 )"));
		assertTrue(storageManager.contains("'" + Polygon.class.getName() + "'( triangle, '" + Segment.class.getName()
				+ "'( ab, '" + Point.class.getName() + "'( a, 3,14 ), '" + Point.class.getName() + "'( b, 3,14 ) ), '"
				+ Segment.class.getName() + "'( bc, '" + Point.class.getName() + "'( b, 3,14 ), '"
				+ Point.class.getName() + "'( c, 3,14 ) ), '" + Segment.class.getName() + "'( ca, '"
				+ Point.class.getName() + "'( c, 3,14 ), '" + Point.class.getName() + "'( a, 3,14 ) ) )"));

		assertTrue(storageManager.contains(
				"'" + Segment.class.getName() + "'(Ids, Point0, Point1), '" + Point.class.getName() + "'(Idp, X, Y)"));

		storageManager.getTransaction().commit();
		storageManager.getTransaction().close();

	}

	@Test
	public final void testContainsObject() {

		storageManager.getTransaction().begin();

		storageManager.delete(Point.class);
		storageManager.delete(Segment.class);
		storageManager.delete(Polygon.class);

		assertFalse(storageManager.contains(a));
		assertFalse(storageManager.contains(b));
		assertFalse(storageManager.contains(c));
		assertFalse(storageManager.contains(d));

		storageManager.insert(a, b, c, d);
		storageManager.insert(ab, bc, ca, cd, da);
		storageManager.insert(triangle, tetragon);

		assertTrue(storageManager.contains(a));
		assertTrue(storageManager.contains(b));
		assertTrue(storageManager.contains(c));
		assertTrue(storageManager.contains(d));
		assertTrue(storageManager.contains(ab));
		assertTrue(storageManager.contains(bc));
		assertTrue(storageManager.contains(ca));
		assertTrue(storageManager.contains(triangle));

		storageManager.getTransaction().commit();
		storageManager.getTransaction().close();

	}

	@Test
	public final void testContainsClassOfQ() {

		storageManager.getTransaction().begin();

		storageManager.delete(Point.class);
		storageManager.delete(Segment.class);
		storageManager.delete(Polygon.class);

		assertFalse(storageManager.contains(a));
		assertFalse(storageManager.contains(b));
		assertFalse(storageManager.contains(c));
		assertFalse(storageManager.contains(d));

		storageManager.insert(a, b, c, d);
		storageManager.insert(ab, bc, ca, cd, da);
		storageManager.insert(triangle, tetragon);

		assertTrue(storageManager.contains(a));
		assertTrue(storageManager.contains(b));
		assertTrue(storageManager.contains(c));
		assertTrue(storageManager.contains(d));

		assertTrue(storageManager.contains(Point.class));
		assertTrue(storageManager.contains(Segment.class));
		assertTrue(storageManager.contains(Polygon.class));

		storageManager.getTransaction().commit();
		storageManager.getTransaction().close();

	}

	@Test
	public final void testContainsPredicateOfO() {

		storageManager.getTransaction().begin();

		storageManager.delete(Point.class);
		storageManager.delete(Segment.class);
		storageManager.delete(Polygon.class);

		assertFalse(storageManager.contains(a));
		assertFalse(storageManager.contains(b));
		assertFalse(storageManager.contains(c));
		assertFalse(storageManager.contains(d));

		storageManager.insert(a, b, c, d);
		storageManager.insert(ab, bc, ca, cd, da);
		storageManager.insert(triangle, tetragon);

		assertTrue(storageManager.contains(a));
		assertTrue(storageManager.contains(b));
		assertTrue(storageManager.contains(c));
		assertTrue(storageManager.contains(d));

		assertTrue(storageManager.contains(new Predicate<Point>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1091450628443460071L;

			public boolean evaluate(Point point) {
				return point.getIdp().equals("a");
			}

		}));

		assertTrue(storageManager.contains(new Predicate<Segment>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -6385025460925004612L;

			public boolean evaluate(Segment s) {
				return s.getIds().equals("bc");
			}

		}));

		assertTrue(storageManager.contains(new Predicate<Polygon>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 8931404328750365135L;

			public boolean evaluate(Polygon s) {
				return s.getId().equals("triangle");
			}

		}));

		storageManager.getTransaction().commit();
		storageManager.getTransaction().close();

	}

	@Test
	public final void testContainsStringInt() {

		storageManager.getTransaction().begin();

		storageManager.delete(Point.class);
		storageManager.delete(Segment.class);
		storageManager.delete(Polygon.class);

		storageManager.getTransaction().commit();

		assertFalse(storageManager.contains(a));
		assertFalse(storageManager.contains(b));
		assertFalse(storageManager.contains(c));
		assertFalse(storageManager.contains(d));

		storageManager.insert(a, b, c, d);
		storageManager.insert(ab, bc, ca, cd, da);
		storageManager.insert(triangle, tetragon);

		storageManager.getTransaction().commit();

		assertTrue(storageManager.contains(a));
		assertTrue(storageManager.contains(b));
		assertTrue(storageManager.contains(c));
		assertTrue(storageManager.contains(d));

		assertTrue(storageManager.contains("'" + Point.class.getName() + "'", 3));
		assertTrue(storageManager.contains("'" + Segment.class.getName() + "'", 3));
		assertTrue(storageManager.contains("'" + Polygon.class.getName() + "'", 4));

		storageManager.getTransaction().close();

	}

	@Test
	public final void testCreateQueryString() {

		storageManager.getTransaction().begin();

		storageManager.delete(Point.class);
		storageManager.delete(Segment.class);
		storageManager.delete(Polygon.class);

		assertFalse(storageManager.contains(a));
		assertFalse(storageManager.contains(b));
		assertFalse(storageManager.contains(c));
		assertFalse(storageManager.contains(d));

		storageManager.insert(a, b, c, d);
		storageManager.insert(ab, bc, ca, cd, da);
		storageManager.insert(triangle, tetragon);

		assertTrue(storageManager.contains(a));
		assertTrue(storageManager.contains(b));
		assertTrue(storageManager.contains(c));
		assertTrue(storageManager.contains(d));

		List<Object> objects = new ArrayList<Object>();

		objects = storageManager.createQuery("'" + Point.class.getName() + "'(Idp, X, Y)").getSolutions();
		assertEquals(Arrays.asList(a, b, c, d), createList(objects));

		// point query restricted to specifics x and y points
		objects = storageManager.createQuery("'" + Point.class.getName() + "'(Idp, X, Y), X =:= 3, Y =:= 14")
				.getSolutions();
		assertEquals(Arrays.asList(a, b, c, d), createList(objects));

		objects = storageManager.createQuery("'" + Segment.class.getName() + "'(Ids, Point0, Point1)").getSolutions();
		assertEquals(Arrays.asList(ab, bc, ca, cd, da), createList(objects));

		// segment query restricted to specifics points initial and finals
		objects = storageManager
				.createQuery("'" + Segment.class.getName() + "'(Ids, Point0, Point1), Point0 == '"
						+ Point.class.getName() + "'(a, 3,14), Point1 == '" + Point.class.getName() + "'(b, 3,14)")
				.getSolutions();
		assertEquals(Arrays.asList(ab), createList(objects));

		// predicate projection
		List<Point> points = new ArrayList<Point>();
		List<Segment> segments = new ArrayList<Segment>();
		objects = storageManager.createQuery(
				"'" + Segment.class.getName() + "'(Ids, Point0, Point1), '" + Point.class.getName() + "'(Idp, X, Y)")
				.getSolutions();
		for (Object object : objects) {
			if (object instanceof Object[]) {
				Object[] array = (Object[]) object;
				if (array.length > 1) {
					Object object0 = array[0];
					Object object1 = array[1];
					if (object0 instanceof Segment) {
						Segment segment = (Segment) object0;
						segments.add(segment);
					}
					if (object1 instanceof Point) {
						Point point = (Point) object1;
						points.add(point);
					}
				}
			}
		}

		// all combination created by the Projection or Cartesian Product
		assertEquals(Arrays.asList(ab, ab, ab, ab, bc, bc, bc, bc, ca, ca, ca, ca, cd, cd, cd, cd, da, da, da, da),
				segments);
		assertEquals(Arrays.asList(a, b, c, d, a, b, c, d, a, b, c, d, a, b, c, d, a, b, c, d), points);

		storageManager.getTransaction().commit();
		storageManager.getTransaction().close();

	}

	@Test
	public final void testCreateQueryO() {

		storageManager.getTransaction().begin();

		storageManager.delete(Point.class);
		storageManager.delete(Segment.class);
		storageManager.delete(Polygon.class);

		assertFalse(storageManager.contains(a));
		assertFalse(storageManager.contains(b));
		assertFalse(storageManager.contains(c));
		assertFalse(storageManager.contains(d));

		storageManager.insert(a, b, c, d);
		storageManager.insert(ab, bc, ca, cd, da);
		storageManager.insert(triangle, tetragon);

		assertTrue(storageManager.contains(a));
		assertTrue(storageManager.contains(b));
		assertTrue(storageManager.contains(c));
		assertTrue(storageManager.contains(d));

		assertEquals(a, storageManager.createQuery(a).getSolution());
		assertEquals(b, storageManager.createQuery(b).getSolution());
		assertEquals(c, storageManager.createQuery(c).getSolution());
		assertEquals(d, storageManager.createQuery(d).getSolution());
		assertEquals(ab, storageManager.createQuery(ab).getSolution());
		assertEquals(bc, storageManager.createQuery(bc).getSolution());
		assertEquals(ca, storageManager.createQuery(ca).getSolution());
		assertEquals(cd, storageManager.createQuery(cd).getSolution());
		assertEquals(da, storageManager.createQuery(da).getSolution());

		assertEquals(triangle, storageManager.createQuery(triangle).getSolution());

		assertEquals(Arrays.asList(a, b, c, d), storageManager.createQuery(new Point(3, 14)).getSolutions());
		assertEquals(Arrays.asList(ab, bc, ca, cd, da), storageManager.createQuery(new Segment()).getSolutions());
		assertEquals(Arrays.asList(triangle), storageManager.createQuery(new Polygon()).getSolutions());

		storageManager.getTransaction().commit();
		storageManager.getTransaction().close();

	}

	@Test
	public final void testCreateQueryClassOfO() {

		storageManager.getTransaction().begin();

		storageManager.delete(Point.class);
		storageManager.delete(Segment.class);
		storageManager.delete(Polygon.class);

		assertFalse(storageManager.contains(a));
		assertFalse(storageManager.contains(b));
		assertFalse(storageManager.contains(c));
		assertFalse(storageManager.contains(d));

		storageManager.insert(a, b, c, d);
		storageManager.insert(ab, bc, ca, cd, da);
		storageManager.insert(triangle, tetragon);

		assertTrue(storageManager.contains(a));
		assertTrue(storageManager.contains(b));
		assertTrue(storageManager.contains(c));
		assertTrue(storageManager.contains(d));

		assertEquals(Arrays.asList(a, b, c, d), storageManager.createQuery(Point.class).getSolutions());
		assertEquals(Arrays.asList(ab, bc, ca, cd, da), storageManager.createQuery(Segment.class).getSolutions());
		assertEquals(Arrays.asList(triangle), storageManager.createQuery(Polygon.class).getSolutions());

		storageManager.getTransaction().commit();
		storageManager.getTransaction().close();

	}

	@Test
	public final void testCreateQueryPredicateOfO() {

		storageManager.getTransaction().begin();

		storageManager.delete(Point.class);
		storageManager.delete(Segment.class);
		storageManager.delete(Polygon.class);

		assertFalse(storageManager.contains(a));
		assertFalse(storageManager.contains(b));
		assertFalse(storageManager.contains(c));
		assertFalse(storageManager.contains(d));

		storageManager.insert(a, b, c, d);
		storageManager.insert(ab, bc, ca, cd, da);
		storageManager.insert(triangle, tetragon);

		assertTrue(storageManager.contains(a));
		assertTrue(storageManager.contains(b));
		assertTrue(storageManager.contains(c));
		assertTrue(storageManager.contains(d));

		Point point = storageManager.createQuery(new Predicate<Point>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7031438597240011857L;

			public boolean evaluate(Point point) {
				return point.getIdp().equals("a");
			}

		}).getSolution();

		assertEquals(a, point);

		Segment segment = storageManager.createQuery(new Predicate<Segment>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -8990328940620440509L;

			public boolean evaluate(Segment s) {
				return s.getIds().equals("bc");
			}

		}).getSolution();

		assertEquals(bc, segment);

		Polygon polygon = storageManager.createQuery(new Predicate<Polygon>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7822136021196518641L;

			public boolean evaluate(Polygon s) {
				return s.getId().equals("triangle");
			}

		}).getSolution();

		assertEquals(triangle, polygon);

		storageManager.getTransaction().commit();
		storageManager.getTransaction().close();

	}

	@Test
	public final void testCreateProcedureQuery() {

		storageManager.getTransaction().begin();

		storageManager.delete(Point.class);
		storageManager.delete(Segment.class);
		storageManager.delete(Polygon.class);

		assertFalse(storageManager.contains(a));
		assertFalse(storageManager.contains(b));
		assertFalse(storageManager.contains(c));
		assertFalse(storageManager.contains(d));

		storageManager.insert(a, b, c, d);
		storageManager.insert(ab, bc, ca, cd, da);
		storageManager.insert(triangle, tetragon);

		assertTrue(storageManager.contains(a));
		assertTrue(storageManager.contains(b));
		assertTrue(storageManager.contains(c));
		assertTrue(storageManager.contains(d));

		ProcedureQuery query = storageManager.createProcedureQuery("'" + Point.class.getName() + "'", "Idp", "X", "Y");
		query.setMaxSolution(2);
		query.setFirstSolution(2);
		query.execute();

		assertTrue(query.hasNext());
		Object object = query.next();
		assertTrue(object instanceof Object[]);
		Object[] objects = (Object[]) object;
		assertEquals("a", objects[0]);
		assertEquals(3, objects[1]);
		assertEquals(14, objects[2]);
		assertEquals("a", query.getArgumentValue(0));
		assertEquals(3, query.getArgumentValue(1));
		assertEquals(14, query.getArgumentValue(2));
		assertEquals("a", query.getArgumentValue("Idp"));
		assertEquals(3, query.getArgumentValue("X"));
		assertEquals(14, query.getArgumentValue("Y"));

		assertTrue(query.hasNext());
		object = query.next();
		assertTrue(object instanceof Object[]);
		objects = (Object[]) object;
		assertEquals("b", objects[0]);
		assertEquals(3, objects[1]);
		assertEquals(14, objects[2]);
		assertEquals("b", query.getArgumentValue(0));
		assertEquals(3, query.getArgumentValue(1));
		assertEquals(14, query.getArgumentValue(2));
		assertEquals("b", query.getArgumentValue("Idp"));
		assertEquals(3, query.getArgumentValue("X"));
		assertEquals(14, query.getArgumentValue("Y"));

		assertTrue(query.hasNext());
		object = query.next();
		assertTrue(object instanceof Object[]);
		objects = (Object[]) object;
		assertEquals("c", objects[0]);
		assertEquals(3, objects[1]);
		assertEquals(14, objects[2]);
		assertEquals("c", query.getArgumentValue(0));
		assertEquals(3, query.getArgumentValue(1));
		assertEquals(14, query.getArgumentValue(2));
		assertEquals("c", query.getArgumentValue("Idp"));
		assertEquals(3, query.getArgumentValue("X"));
		assertEquals(14, query.getArgumentValue("Y"));

		assertTrue(query.hasNext());
		object = query.next();
		assertTrue(object instanceof Object[]);
		objects = (Object[]) object;
		assertEquals("d", objects[0]);
		assertEquals(3, objects[1]);
		assertEquals(14, objects[2]);
		assertEquals("d", query.getArgumentValue(0));
		assertEquals(3, query.getArgumentValue(1));
		assertEquals(14, query.getArgumentValue(2));
		assertEquals("d", query.getArgumentValue("Idp"));
		assertEquals(3, query.getArgumentValue("X"));
		assertEquals(14, query.getArgumentValue("Y"));

		assertFalse(query.hasNext());

		storageManager.getTransaction().commit();
		storageManager.getTransaction().close();

	}

	@Test
	public void testContainerOf() {

		assertEquals(PrologStoragePool.class, storageManager.containerOf(Point.class).getClass());
		assertEquals(PrologStoragePool.class, storageManager.containerOf(Segment.class).getClass());
		assertEquals(PrologStoragePool.class, storageManager.containerOf(Polygon.class).getClass());

	}

	@Test
	public final void testLocationOf() {

		assertEquals(BASE_LOCATION, storageManager.locationOf(Point.class));
		assertEquals(BASE_LOCATION, storageManager.locationOf(Segment.class));
		assertEquals(BASE_LOCATION, storageManager.locationOf(Polygon.class));

	}

	@Test
	public final void testBackupAndRestore() {

		storageManager.getTransaction().begin();

		storageManager.insert(a);
		storageManager.insert(b);
		storageManager.insert(c);
		storageManager.insert(d);
		storageManager.insert(ab);
		storageManager.insert(bc);
		storageManager.insert(ca);
		storageManager.insert(cd);
		storageManager.insert(da);
		storageManager.insert(triangle);

		// make backup
		storageManager.backup(BACKUP_DIRECTORY, BACKUP_ZIP_FILE_NAME_1);

		// Physic existence test
		File file = new File(BACKUP_ZIP_FILE_PATH_1);
		assertTrue(file.exists());
		assertTrue(file.length() > 0);

		// restore the created backup
		storageManager.restore("", BACKUP_ZIP_FILE_PATH_1);

		// Logical program saved
		PrologEngine engine = provider.newEngine();
		String name = Point.class.getSimpleName();
		PersistentContainer pc = storageManager.containerOf(Point.class);
		String location = pc.locationOf(Point.class) + SEPARATOR + name + '.' + 0;
		engine.consult(location);
		assertFalse(engine.isProgramEmpty());
		assertEquals(4, engine.getProgramSize());
		engine.dispose();

		name = Segment.class.getSimpleName();
		pc = storageManager.containerOf(Segment.class);
		location = pc.locationOf(Segment.class) + SEPARATOR + name + '.' + 0;
		engine.consult(location);
		assertFalse(engine.isProgramEmpty());
		assertEquals(5, engine.getProgramSize());
		engine.dispose();

		name = Polygon.class.getSimpleName();
		pc = storageManager.containerOf(Polygon.class);
		location = pc.locationOf(Polygon.class) + SEPARATOR + name + '.' + 0;
		engine.consult(location);
		assertFalse(engine.isProgramEmpty());
		assertEquals(1, engine.getProgramSize());
		engine.dispose();

		storageManager.getTransaction().commit();
		storageManager.getTransaction().close();

	}

	@Test
	public void testClassOfO() {

		assertEquals(Point.class, storageManager.classOf(a));
		assertEquals(Segment.class, storageManager.classOf(ab));
		assertEquals(Polygon.class, storageManager.classOf(triangle));

	}

	@Test
	public void testClassOfStringInt() {

		assertEquals(Point.class, storageManager.classOf(Point.class.getName(), 3));
		assertEquals(Segment.class, storageManager.classOf(Segment.class.getName(), 3));
		assertEquals(Polygon.class, storageManager.classOf(Polygon.class.getName(), 4));

	}

	@Test
	public void testClassOfPredicateOfO() {

		assertEquals(Point.class, storageManager.classOf(new Predicate<Point>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 8771854686044560004L;

			public boolean evaluate(Point p) {
				return a.equals(p);
			}

		}));

		assertEquals(Segment.class, storageManager.classOf(new Predicate<Segment>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -4999782656360060383L;

			public boolean evaluate(Segment s) {
				return ab.equals(s);
			}

		}));

		assertEquals(Polygon.class, storageManager.classOf(new Predicate<Polygon>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1231241228768814572L;

			public boolean evaluate(Polygon p) {
				return triangle.equals(p);
			}

		}));

	}

	@Test
	public void testClassesOfString() {

		storageManager.getTransaction().begin();

		assertArrayEquals(new Class[] { Segment.class, Point.class },
				storageManager.classesOf("'" + Segment.class.getName() + "'(Ids, Point0, Point1), Point0 == '"
						+ Point.class.getName() + "'(a, 3,14), Point1 == '" + Point.class.getName() + "'(b, 3,14)")
						.toArray());

		storageManager.getTransaction().commit();
		storageManager.getTransaction().close();

	}

	@Test
	public void testClassesOfPrologTermArray() {
		ObjectConverter<PrologTerm> converter = new PrologObjectConverter(provider);
		assertArrayEquals(new Class[] { Point.class, Segment.class, Polygon.class },
				storageManager.classesOf(
						new PrologTerm[] { converter.toTerm(a), converter.toTerm(ab), converter.toTerm(triangle) })
						.toArray());

	}

	@Test
	public void testUnwrapClassOfK() {
		assertEquals(PrologStorageManager.class.getName(),
				storageManager.unwrap(PrologStorageManager.class).getClass().getName());
	}

	@Test
	public void testUnwrapObjectClassOfK() {
		assertEquals(PrologStorageManager.class.getName(),
				storageManager.unwrap(storageManager, PrologStorageManager.class).getClass().getName());
	}

	@Test
	public void testIsWrappedByClassOfQ() {
		assertTrue(storageManager.isWrappedFor(AbstractStorageManager.class));
		assertTrue(storageManager.isWrappedFor(PrologStorageManager.class));
	}

	@Test
	public void testIsWrappedByObjectClassOfQ() {
		assertTrue(storageManager.isWrappedFor(storageManager, AbstractStorageManager.class));
		assertTrue(storageManager.isWrappedFor(storageManager, PrologStorageManager.class));
	}

	@Test
	public void testGetEngine() {
		assertNotNull(storageManager.getEngine());
	}

	@Test
	public void testGetProvider() {
		assertNotNull(storageManager.getProvider());
	}

	@Test
	public final void testIsOpen() {

		storageManager.getTransaction().begin();

		storageManager.insert(a);
		storageManager.insert(b);
		storageManager.insert(c);
		storageManager.insert(ab);
		storageManager.insert(bc);
		storageManager.insert(ca);
		storageManager.insert(triangle);

		assertTrue(storageManager.isOpen());

		storageManager.close();

		assertFalse(storageManager.isOpen());

		storageManager.getTransaction().commit();
		storageManager.getTransaction().close();

	}

	@Test
	public void testBegin() {
		assertFalse(storageManager.getTransaction().isActive());
		storageManager.getTransaction().begin();
		assertTrue(storageManager.getTransaction().isActive());
		storageManager.getTransaction().close();
		assertFalse(storageManager.getTransaction().isActive());
	}

	@Test
	public void testCommit() {

		storageManager.begin();
		storageManager.delete(Point.class);
		storageManager.delete(Segment.class);
		storageManager.delete(Polygon.class);
		storageManager.commit();

		assertFalse(storageManager.contains(a));
		assertFalse(storageManager.contains(b));
		assertFalse(storageManager.contains(c));
		assertFalse(storageManager.contains(d));
		assertFalse(storageManager.contains(ab));
		assertFalse(storageManager.contains(bc));
		assertFalse(storageManager.contains(ca));
		assertFalse(storageManager.contains(triangle));

		storageManager.insert(a, b, c, d);
		storageManager.insert(ab, bc, ca, cd, da);
		storageManager.insert(triangle, tetragon);
		storageManager.commit();

		assertTrue(storageManager.contains(a));
		assertTrue(storageManager.contains(b));
		assertTrue(storageManager.contains(c));
		assertTrue(storageManager.contains(d));
		assertTrue(storageManager.contains(ab));
		assertTrue(storageManager.contains(bc));
		assertTrue(storageManager.contains(ca));
		assertTrue(storageManager.contains(triangle));

	}

	@Test
	public void testRollback() {

		Point p = new Point("p");

		storageManager.begin();
		storageManager.delete(Point.class);
		storageManager.delete(Segment.class);
		storageManager.delete(Polygon.class);
		storageManager.commit();

		assertFalse(storageManager.contains(a));
		assertFalse(storageManager.contains(b));
		assertFalse(storageManager.contains(c));
		assertFalse(storageManager.contains(d));
		assertFalse(storageManager.contains(ab));
		assertFalse(storageManager.contains(bc));
		assertFalse(storageManager.contains(ca));
		assertFalse(storageManager.contains(triangle));

		storageManager.insert(p);

		assertTrue(storageManager.contains(p));

		storageManager.rollback();

		assertFalse(storageManager.contains(p));

	}

	@Test
	public final void testClasses() {

		storageManager.getTransaction().begin();

		storageManager.insert(a);
		storageManager.insert(b);
		storageManager.insert(c);
		storageManager.insert(d);
		storageManager.insert(ab);
		storageManager.insert(bc);
		storageManager.insert(ca);
		storageManager.insert(cd);
		storageManager.insert(da);
		storageManager.insert(triangle);

		storageManager.getTransaction().commit();

		assertEquals(4, storageManager.classes().size());

		storageManager.getTransaction().close();

	}

}
