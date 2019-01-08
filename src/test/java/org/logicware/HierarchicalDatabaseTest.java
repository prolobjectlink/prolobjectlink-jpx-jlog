package org.logicware;

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
import org.logicware.db.ObjectConverter;
import org.logicware.db.PersistentContainer;
import org.logicware.db.Predicate;
import org.logicware.db.ProcedureQuery;
import org.logicware.db.engine.AbstractHierarchicalDatabase;
import org.logicware.db.prolog.PrologHierarchicalDatabase;
import org.logicware.db.prolog.PrologObjectConverter;
import org.logicware.db.prolog.PrologStoragePool;
import org.logicware.domain.geometry.Point;
import org.logicware.domain.geometry.Polygon;
import org.logicware.domain.geometry.Segment;
import org.logicware.prolog.PrologEngine;
import org.logicware.prolog.PrologTerm;

public class HierarchicalDatabaseTest extends BaseTest {

	@Test
	public void testContainsString() {

		hdb.getTransaction().begin();
		hdb.delete(Point.class);
		hdb.delete(Segment.class);
		hdb.delete(Polygon.class);
		hdb.getTransaction().commit();

		assertFalse(hdb.contains(a));
		assertFalse(hdb.contains(b));
		assertFalse(hdb.contains(c));
		assertFalse(hdb.contains(d));

		hdb.insert(a, b, c, d);
		hdb.insert(ab, bc, ca, cd, da);
		hdb.insert(triangle, tetragon);
		hdb.getTransaction().commit();

		assertTrue(hdb.contains(a));
		assertTrue(hdb.contains(b));
		assertTrue(hdb.contains(c));
		assertTrue(hdb.contains(d));

		assertTrue(hdb.contains("'" + Point.class.getName() + "'(Idp, X, Y)"));
		assertTrue(hdb.contains("'" + Point.class.getName() + "'( a, 3,14 )"));
		assertTrue(hdb.contains("'" + Point.class.getName() + "'( b, 3,14 )"));
		assertTrue(hdb.contains("'" + Point.class.getName() + "'( c, 3,14 )"));

		assertTrue(hdb.contains("'" + Segment.class.getName() + "'(Ids, Point0, Point1)"));
		assertTrue(hdb.contains("'" + Segment.class.getName() + "'( ab, '" + Point.class.getName()
				+ "'( a, 3,14 ), '" + Point.class.getName() + "'( b, 3,14 ) )"));
		assertTrue(hdb.contains("'" + Segment.class.getName() + "'( bc, '" + Point.class.getName()
				+ "'( b, 3,14 ), '" + Point.class.getName() + "'( c, 3,14 ) )"));
		assertTrue(hdb.contains("'" + Segment.class.getName() + "'( ca, '" + Point.class.getName()
				+ "'( c, 3,14 ), '" + Point.class.getName() + "'( a, 3,14 ) )"));

		assertTrue(hdb.contains("'" + Polygon.class.getName() + "'( triangle, Segment0, Segment1, Segment2 )"));
		assertTrue(hdb.contains("'" + Polygon.class.getName() + "'( triangle, '" + Segment.class.getName() + "'( ab, '"
				+ Point.class.getName() + "'( a, 3,14 ), '" + Point.class.getName() + "'( b, 3,14 ) ), '"
				+ Segment.class.getName() + "'( bc, '" + Point.class.getName() + "'( b, 3,14 ), '"
				+ Point.class.getName() + "'( c, 3,14 ) ), '" + Segment.class.getName() + "'( ca, '"
				+ Point.class.getName() + "'( c, 3,14 ), '" + Point.class.getName() + "'( a, 3,14 ) ) )"));

		assertTrue(hdb.contains(
				"'" + Segment.class.getName() + "'(Ids, Point0, Point1), '" + Point.class.getName() + "'(Idp, X, Y)"));

		hdb.getTransaction().close();

	}

	@Test
	public void testContainsO() {

		hdb.getTransaction().begin();
		hdb.delete(Point.class);
		hdb.delete(Segment.class);
		hdb.delete(Polygon.class);
		hdb.getTransaction().commit();

		assertFalse(hdb.contains(a));
		assertFalse(hdb.contains(b));
		assertFalse(hdb.contains(c));
		assertFalse(hdb.contains(d));

		hdb.insert(a, b, c, d);
		hdb.insert(ab, bc, ca, cd, da);
		hdb.insert(triangle, tetragon);
		hdb.getTransaction().commit();

		assertTrue(hdb.contains(a));
		assertTrue(hdb.contains(b));
		assertTrue(hdb.contains(c));
		assertTrue(hdb.contains(d));
		assertTrue(hdb.contains(ab));
		assertTrue(hdb.contains(bc));
		assertTrue(hdb.contains(ca));
		assertTrue(hdb.contains(triangle));

		hdb.getTransaction().close();

	}

	@Test
	public void testContainsClassOfO() {

		hdb.getTransaction().begin();
		hdb.delete(Point.class);
		hdb.delete(Segment.class);
		hdb.delete(Polygon.class);
		hdb.getTransaction().commit();

		assertFalse(hdb.contains(a));
		assertFalse(hdb.contains(b));
		assertFalse(hdb.contains(c));
		assertFalse(hdb.contains(d));

		hdb.insert(a, b, c, d);
		hdb.insert(ab, bc, ca, cd, da);
		hdb.insert(triangle, tetragon);
		hdb.getTransaction().commit();

		assertTrue(hdb.contains(a));
		assertTrue(hdb.contains(b));
		assertTrue(hdb.contains(c));
		assertTrue(hdb.contains(d));

		assertTrue(hdb.contains(Point.class));
		assertTrue(hdb.contains(Segment.class));
		assertTrue(hdb.contains(Polygon.class));

		hdb.getTransaction().close();

	}

	@Test
	public void testContainsPredicateOfO() {

		hdb.getTransaction().begin();
		hdb.delete(Point.class);
		hdb.delete(Segment.class);
		hdb.delete(Polygon.class);
		hdb.getTransaction().commit();

		assertFalse(hdb.contains(a));
		assertFalse(hdb.contains(b));
		assertFalse(hdb.contains(c));
		assertFalse(hdb.contains(d));

		hdb.insert(a, b, c, d);
		hdb.insert(ab, bc, ca, cd, da);
		hdb.insert(triangle, tetragon);
		hdb.getTransaction().commit();

		assertTrue(hdb.contains(a));
		assertTrue(hdb.contains(b));
		assertTrue(hdb.contains(c));
		assertTrue(hdb.contains(d));

		assertTrue(hdb.contains(new Predicate<Point>() {

			private static final long serialVersionUID = -2571750464384955652L;

			public boolean evaluate(Point point) {
				return point.getIdp().equals("a");
			}

		}));

		assertTrue(hdb.contains(new Predicate<Segment>() {

			private static final long serialVersionUID = 4513211052052556385L;

			public boolean evaluate(Segment s) {
				return s.getIds().equals("bc");
			}

		}));

		assertTrue(hdb.contains(new Predicate<Polygon>() {

			private static final long serialVersionUID = -5287584658912508897L;

			public boolean evaluate(Polygon s) {
				return s.getId().equals("triangle");
			}

		}));

		hdb.getTransaction().close();

	}

	@Test
	public void testContainsStringInt() {

		hdb.getTransaction().begin();
		hdb.delete(Point.class);
		hdb.delete(Segment.class);
		hdb.delete(Polygon.class);
		hdb.getTransaction().commit();

		assertFalse(hdb.contains(a));
		assertFalse(hdb.contains(b));
		assertFalse(hdb.contains(c));
		assertFalse(hdb.contains(d));

		hdb.insert(a, b, c, d);
		hdb.insert(ab, bc, ca, cd, da);
		hdb.insert(triangle, tetragon);
		hdb.getTransaction().commit();

		assertTrue(hdb.contains("'" + Point.class.getName() + "'", 3));
		assertTrue(hdb.contains("'" + Segment.class.getName() + "'", 3));
		assertTrue(hdb.contains("'" + Polygon.class.getName() + "'", 4));

		hdb.getTransaction().close();

	}

	@Test
	public void testOpen() {

		assertTrue(hdb.isOpen());
		hdb.close();
		assertFalse(hdb.isOpen());
		hdb.open();
		assertTrue(hdb.isOpen());

	}

	@Test
	public void testInsert() {

		hdb.getTransaction().begin();
		hdb.delete(Point.class);
		hdb.delete(Segment.class);
		hdb.delete(Polygon.class);
		hdb.getTransaction().commit();

		assertFalse(hdb.contains(a));
		assertFalse(hdb.contains(b));
		assertFalse(hdb.contains(c));
		assertFalse(hdb.contains(d));

		hdb.insert(a, b, c, d);
		hdb.insert(ab, bc, ca, cd, da);
		hdb.insert(triangle, tetragon);
		hdb.getTransaction().commit();

		assertTrue(hdb.contains(a));
		assertTrue(hdb.contains(b));
		assertTrue(hdb.contains(c));
		assertTrue(hdb.contains(d));
		assertTrue(hdb.contains(ab));
		assertTrue(hdb.contains(bc));
		assertTrue(hdb.contains(ca));
		assertTrue(hdb.contains(triangle));

		hdb.getTransaction().close();

	}

	@Test
	public void testUpdate() {

		hdb.begin();
		hdb.insert(a);
		hdb.insert(b);
		hdb.insert(c);
		hdb.insert(ab);
		hdb.insert(bc);
		hdb.insert(ca);
		hdb.insert(triangle);
		hdb.commit();

		// create new update objects

		Point newA = new Point("a", 6,28);
		Point newB = new Point("b", 6,28);
		Point newC = new Point("c", 6,28);

		Segment newAB = new Segment("ab", newA, newB);
		Segment newBC = new Segment("bc", newB, newC);
		Segment newCA = new Segment("ca", newC, newA);

		Polygon newTriangle = new Polygon("triangle", newAB, newBC, newCA);

		assertTrue(hdb.contains(a));
		assertTrue(hdb.contains(b));
		assertTrue(hdb.contains(c));
		assertTrue(hdb.contains(ab));
		assertTrue(hdb.contains(bc));
		assertTrue(hdb.contains(ca));
		assertTrue(hdb.contains(triangle));

		assertFalse(hdb.contains(newA));
		assertFalse(hdb.contains(newB));
		assertFalse(hdb.contains(newC));
		assertFalse(hdb.contains(newAB));
		assertFalse(hdb.contains(newBC));
		assertFalse(hdb.contains(newCA));
		assertFalse(hdb.contains(newTriangle));

		// updating

		hdb.update(a, newA);
		hdb.update(b, newB);
		hdb.update(c, newC);
		hdb.update(ab, newAB);
		hdb.update(bc, newBC);
		hdb.update(ca, newCA);
		hdb.update(triangle, newTriangle);
		hdb.commit();

		assertFalse(hdb.contains(a));
		assertFalse(hdb.contains(b));
		assertFalse(hdb.contains(c));
		assertFalse(hdb.contains(ab));
		assertFalse(hdb.contains(bc));
		assertFalse(hdb.contains(ca));
		assertFalse(hdb.contains(triangle));

		assertTrue(hdb.contains(newA));
		assertTrue(hdb.contains(newB));
		assertTrue(hdb.contains(newC));
		assertTrue(hdb.contains(newAB));
		assertTrue(hdb.contains(newBC));
		assertTrue(hdb.contains(newCA));
		assertTrue(hdb.contains(newTriangle));

		// reverting update changes

		hdb.update(newA, a);
		hdb.update(newB, b);
		hdb.update(newC, c);
		hdb.update(newAB, ab);
		hdb.update(newBC, bc);
		hdb.update(newCA, ca);
		hdb.update(newTriangle, triangle);
		hdb.commit();

		assertTrue(hdb.contains(a));
		assertTrue(hdb.contains(b));
		assertTrue(hdb.contains(c));
		assertTrue(hdb.contains(ab));
		assertTrue(hdb.contains(bc));
		assertTrue(hdb.contains(ca));
		assertTrue(hdb.contains(triangle));

		assertFalse(hdb.contains(newA));
		assertFalse(hdb.contains(newB));
		assertFalse(hdb.contains(newC));
		assertFalse(hdb.contains(newAB));
		assertFalse(hdb.contains(newBC));
		assertFalse(hdb.contains(newCA));
		assertFalse(hdb.contains(newTriangle));

	}

	@Test
	public void testDeleteClassOfQ() {

		hdb.getTransaction().begin();
		hdb.delete(Point.class);
		hdb.delete(Segment.class);
		hdb.delete(Polygon.class);
		hdb.getTransaction().commit();

		assertFalse(hdb.contains(Point.class));
		assertFalse(hdb.contains(Segment.class));
		assertFalse(hdb.contains(Polygon.class));

		hdb.getTransaction().close();

	}

	@Test
	public void testDeleteOArray() {

		hdb.getTransaction().begin();
		hdb.insert(a, b, c, d);
		hdb.insert(ab, bc, ca, cd, da);
		hdb.insert(triangle, tetragon);
		hdb.getTransaction().commit();

		assertTrue(hdb.contains(a));
		assertTrue(hdb.contains(b));
		assertTrue(hdb.contains(c));
		assertTrue(hdb.contains(d));

		assertEquals(a, hdb.createQuery(a).getSolution());
		assertEquals(b, hdb.createQuery(b).getSolution());
		assertEquals(c, hdb.createQuery(c).getSolution());
		assertEquals(d, hdb.createQuery(d).getSolution());

		hdb.delete(a, b, c, d);
		hdb.delete(ab, bc, ca, cd, da);
		hdb.delete(triangle, tetragon);
		hdb.getTransaction().commit();

		assertFalse(hdb.contains(a));
		assertFalse(hdb.contains(b));
		assertFalse(hdb.contains(c));
		assertFalse(hdb.contains(d));

		hdb.getTransaction().close();

	}

	@Test
	public void testGetTransaction() {
		assertNotNull(hdb.getTransaction());
		hdb.getTransaction().begin();
		assertTrue(hdb.getTransaction().isActive());
		hdb.getTransaction().commit();
		assertTrue(hdb.getTransaction().isActive());
		hdb.getTransaction().close();
		assertFalse(hdb.getTransaction().isActive());
	}

	@Test
	public void testCreateQueryString() {

		hdb.getTransaction().begin();
		hdb.delete(Point.class);
		hdb.delete(Segment.class);
		hdb.delete(Polygon.class);
		hdb.getTransaction().commit();

		assertFalse(hdb.contains(a));
		assertFalse(hdb.contains(b));
		assertFalse(hdb.contains(c));
		assertFalse(hdb.contains(d));

		hdb.insert(a, b, c, d);
		hdb.insert(ab, bc, ca, cd, da);
		hdb.insert(triangle, tetragon);
		hdb.getTransaction().commit();

		assertTrue(hdb.contains(a));
		assertTrue(hdb.contains(b));
		assertTrue(hdb.contains(c));
		assertTrue(hdb.contains(d));

		List<Object> objects = new ArrayList<Object>();

		objects = hdb.createQuery("'" + Point.class.getName() + "'(Idp, X, Y)").getSolutions();
		assertEquals(Arrays.asList(a, b, c, d), createList(objects));

		// point query restricted to specifics x and y points
		objects = hdb.createQuery("'" + Point.class.getName() + "'(Idp, X, Y), X =:= 3, Y =:= 14").getSolutions();
		assertEquals(Arrays.asList(a, b, c, d), createList(objects));

		objects = hdb.createQuery("'" + Segment.class.getName() + "'(Ids, Point0, Point1)").getSolutions();
		assertEquals(Arrays.asList(ab, bc, ca, cd, da), createList(objects));

		// segment query restricted to specifics points initial and finals
		objects = hdb
				.createQuery(
						"'" + Segment.class.getName() + "'(Ids, Point0, Point1), Point0 == '" + Point.class.getName()
								+ "'(a, 3,14), Point1 == '" + Point.class.getName() + "'(b, 3,14)")
				.getSolutions();
		assertEquals(Arrays.asList(ab), createList(objects));

		// predicate projection
		List<Point> points = new ArrayList<Point>();
		List<Segment> segments = new ArrayList<Segment>();
		objects = hdb.createQuery(
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

		hdb.getTransaction().close();

	}

	@Test
	public void testCreateQueryO() {

		hdb.getTransaction().begin();
		hdb.delete(Point.class);
		hdb.delete(Segment.class);
		hdb.delete(Polygon.class);
		hdb.getTransaction().commit();

		assertFalse(hdb.contains(a));
		assertFalse(hdb.contains(b));
		assertFalse(hdb.contains(c));
		assertFalse(hdb.contains(d));

		hdb.insert(a, b, c, d);
		hdb.insert(ab, bc, ca, cd, da);
		hdb.insert(triangle, tetragon);
		hdb.getTransaction().commit();

		assertTrue(hdb.contains(a));
		assertTrue(hdb.contains(b));
		assertTrue(hdb.contains(c));
		assertTrue(hdb.contains(d));

		assertEquals(a, hdb.createQuery(a).getSolution());
		assertEquals(b, hdb.createQuery(b).getSolution());
		assertEquals(c, hdb.createQuery(c).getSolution());
		assertEquals(d, hdb.createQuery(d).getSolution());
		assertEquals(ab, hdb.createQuery(ab).getSolution());
		assertEquals(bc, hdb.createQuery(bc).getSolution());
		assertEquals(ca, hdb.createQuery(ca).getSolution());
		assertEquals(cd, hdb.createQuery(cd).getSolution());
		assertEquals(da, hdb.createQuery(da).getSolution());

		assertEquals(triangle, hdb.createQuery(triangle).getSolution());

		assertEquals(Arrays.asList(a, b, c, d), hdb.createQuery(new Point(3,14)).getSolutions());
		assertEquals(Arrays.asList(ab, bc, ca, cd, da), hdb.createQuery(new Segment()).getSolutions());
		assertEquals(Arrays.asList(triangle), hdb.createQuery(new Polygon()).getSolutions());

		hdb.getTransaction().close();

	}

	@Test
	public void testCreateQueryClassOfO() {

		hdb.getTransaction().begin();
		hdb.delete(Point.class);
		hdb.delete(Segment.class);
		hdb.delete(Polygon.class);
		hdb.getTransaction().commit();

		assertFalse(hdb.contains(a));
		assertFalse(hdb.contains(b));
		assertFalse(hdb.contains(c));
		assertFalse(hdb.contains(d));

		hdb.insert(a, b, c, d);
		hdb.insert(ab, bc, ca, cd, da);
		hdb.insert(triangle, tetragon);
		hdb.getTransaction().commit();

		assertTrue(hdb.contains(a));
		assertTrue(hdb.contains(b));
		assertTrue(hdb.contains(c));
		assertTrue(hdb.contains(d));

		assertEquals(Arrays.asList(a, b, c, d), hdb.createQuery(Point.class).getSolutions());
		assertEquals(Arrays.asList(ab, bc, ca, cd, da), hdb.createQuery(Segment.class).getSolutions());
		assertEquals(Arrays.asList(triangle), hdb.createQuery(Polygon.class).getSolutions());

		hdb.getTransaction().close();

	}

	@Test
	public void testCreateQueryPredicateOfO() {

		hdb.getTransaction().begin();
		hdb.delete(Point.class);
		hdb.delete(Segment.class);
		hdb.delete(Polygon.class);
		hdb.getTransaction().commit();

		assertFalse(hdb.contains(a));
		assertFalse(hdb.contains(b));
		assertFalse(hdb.contains(c));
		assertFalse(hdb.contains(d));

		hdb.insert(a, b, c, d);
		hdb.insert(ab, bc, ca, cd, da);
		hdb.insert(triangle, tetragon);
		hdb.getTransaction().commit();

		assertTrue(hdb.contains(a));
		assertTrue(hdb.contains(b));
		assertTrue(hdb.contains(c));
		assertTrue(hdb.contains(d));

		Point point = hdb.createQuery(new Predicate<Point>() {

			private static final long serialVersionUID = 5537472073072610294L;

			public boolean evaluate(Point point) {
				return point.getIdp().equals("a");
			}

		}).getSolution();

		assertEquals(a, point);

		Segment segment = hdb.createQuery(new Predicate<Segment>() {

			private static final long serialVersionUID = 2582694696337258202L;

			public boolean evaluate(Segment s) {
				return s.getIds().equals("bc");
			}

		}).getSolution();

		assertEquals(bc, segment);

		Polygon polygon = hdb.createQuery(new Predicate<Polygon>() {

			private static final long serialVersionUID = -8685504885457305629L;

			public boolean evaluate(Polygon s) {
				return s.getId().equals("triangle");
			}

		}).getSolution();

		assertEquals(triangle, polygon);

		hdb.getTransaction().close();

	}

	@Test
	public void testCreateProcedureQuery() {

		hdb.getTransaction().begin();
		hdb.delete(Point.class);
		hdb.delete(Segment.class);
		hdb.delete(Polygon.class);
		hdb.getTransaction().commit();

		assertFalse(hdb.contains(a));
		assertFalse(hdb.contains(b));
		assertFalse(hdb.contains(c));
		assertFalse(hdb.contains(d));

		hdb.insert(a, b, c, d);
		hdb.insert(ab, bc, ca, cd, da);
		hdb.insert(triangle, tetragon);
		hdb.getTransaction().commit();

		assertTrue(hdb.contains(a));
		assertTrue(hdb.contains(b));
		assertTrue(hdb.contains(c));
		assertTrue(hdb.contains(d));

		ProcedureQuery query = hdb.createProcedureQuery("'" + Point.class.getName() + "'", "Idp", "X", "Y");
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

		hdb.getTransaction().close();

	}

	@Test
	public void testContainerOf() {

		assertEquals(PrologStoragePool.class, hdb.containerOf(Point.class).getClass());
		assertEquals(PrologStoragePool.class, hdb.containerOf(Segment.class).getClass());
		assertEquals(PrologStoragePool.class, hdb.containerOf(Polygon.class).getClass());

	}

	@Test
	public void testLocationOf() {

		assertEquals("dat" + SEPARATOR + "hierarchical" + SEPARATOR + hdb.getName() + SEPARATOR + "database" + SEPARATOR
				+ Point.class.getPackage().getName().replace('.', SEPARATOR), hdb.locationOf(Point.class));
		assertEquals("dat" + SEPARATOR + "hierarchical" + SEPARATOR + hdb.getName() + SEPARATOR + "database" + SEPARATOR
				+ Segment.class.getPackage().getName().replace('.', SEPARATOR), hdb.locationOf(Segment.class));
		assertEquals("dat" + SEPARATOR + "hierarchical" + SEPARATOR + hdb.getName() + SEPARATOR + "database" + SEPARATOR
				+ Polygon.class.getPackage().getName().replace('.', SEPARATOR), hdb.locationOf(Polygon.class));

	}

	@Test
	public void testGetSchema() {
		assertNotNull(hdb.getSchema());
	}

	@Test
	public void testGetName() {
		assertEquals("test", hdb.getName());
	}

	@Test
	public void testFlush() {

		hdb.getTransaction().begin();
		hdb.delete(Point.class);
		hdb.delete(Segment.class);
		hdb.delete(Polygon.class);
		hdb.getTransaction().commit();

		assertFalse(hdb.contains(a));
		assertFalse(hdb.contains(b));
		assertFalse(hdb.contains(c));
		assertFalse(hdb.contains(d));
		assertFalse(hdb.contains(ab));
		assertFalse(hdb.contains(bc));
		assertFalse(hdb.contains(ca));
		assertFalse(hdb.contains(triangle));

		hdb.insert(a, b, c, d);
		hdb.insert(ab, bc, ca, cd, da);
		hdb.insert(triangle, tetragon);

		hdb.flush();

		assertTrue(hdb.contains(a));
		assertTrue(hdb.contains(b));
		assertTrue(hdb.contains(c));
		assertTrue(hdb.contains(d));
		assertTrue(hdb.contains(ab));
		assertTrue(hdb.contains(bc));
		assertTrue(hdb.contains(ca));
		assertTrue(hdb.contains(triangle));

		hdb.getTransaction().close();

	}

	@Test
	public void testClose() {

		assertTrue(hdb.isOpen());
		hdb.close();
		assertFalse(hdb.isOpen());

	}

	@Test
	public void testClear() {

//		hdb.begin();
		hdb.getTransaction().begin();
		hdb.delete(Point.class);
		hdb.delete(Segment.class);
		hdb.delete(Polygon.class);
		hdb.getTransaction().commit();
//		hdb.commit();

		assertFalse(hdb.contains(a));
		assertFalse(hdb.contains(b));
		assertFalse(hdb.contains(c));
		assertFalse(hdb.contains(d));

		hdb.insert(a);
		hdb.insert(b);
		hdb.insert(c);
		hdb.insert(d);
		hdb.getTransaction().commit();

		assertTrue(hdb.contains(a));
		assertTrue(hdb.contains(b));
		assertTrue(hdb.contains(c));
		assertTrue(hdb.contains(d));

		hdb.clear();
		hdb.getTransaction().commit();

		assertFalse(hdb.contains(a));
		assertFalse(hdb.contains(b));
		assertFalse(hdb.contains(c));
		assertFalse(hdb.contains(d));

	}

	@Test
	public void testGetContainerFactory() {
		assertNotNull(hdb.getContainerFactory());
	}

	@Test
	public void testBackupAndRestore() {

		hdb.begin();
		hdb.insert(a);
		hdb.insert(b);
		hdb.insert(c);
		hdb.insert(d);
		hdb.insert(ab);
		hdb.insert(bc);
		hdb.insert(ca);
		hdb.insert(cd);
		hdb.insert(da);
		hdb.insert(triangle);
		hdb.commit();

		// make backup
		hdb.backup(BACKUP_DIRECTORY, BACKUP_ZIP_FILE_NAME_4);

		// Physic existence test
		File file = new File(BACKUP_ZIP_FILE_PATH_4);
		assertTrue(file.exists());
		assertTrue(file.length() > 0);

		// restore the created backup
		hdb.restore("", BACKUP_ZIP_FILE_PATH_4);

		// Logical program saved
		PrologEngine engine = provider.newEngine();
		String name = Point.class.getSimpleName();
		PersistentContainer pc = hdb.containerOf(Point.class);
		String location = pc.locationOf(Point.class) + SEPARATOR + name + '.' + 0;
		engine.consult(location);
		assertFalse(engine.isProgramEmpty());
		assertEquals(4, engine.getProgramSize());
		engine.dispose();

		name = Segment.class.getSimpleName();
		pc = hdb.containerOf(Segment.class);
		location = pc.locationOf(Segment.class) + SEPARATOR + name + '.' + 0;
		engine.consult(location);
		assertFalse(engine.isProgramEmpty());
		assertEquals(5, engine.getProgramSize());
		engine.dispose();

		name = Polygon.class.getSimpleName();
		pc = hdb.containerOf(Polygon.class);
		location = pc.locationOf(Polygon.class) + SEPARATOR + name + '.' + 0;
		engine.consult(location);
		assertFalse(engine.isProgramEmpty());
		assertEquals(2, engine.getProgramSize());
		engine.dispose();

	}

	@Test
	public void testGetLocation() {
		assertEquals("dat" + SEPARATOR + "hierarchical" + SEPARATOR + hdb.getName() + SEPARATOR + "database",
				hdb.getLocation());
	}

	@Test
	public void testBegin() {
		assertFalse(hdb.getTransaction().isActive());
		hdb.getTransaction().begin();
		assertTrue(hdb.getTransaction().isActive());
		hdb.getTransaction().close();
		assertFalse(hdb.getTransaction().isActive());
	}

	@Test
	public void testCommit() {

//		hdb.begin();
		hdb.getTransaction().begin();
		hdb.delete(Point.class);
		hdb.delete(Segment.class);
		hdb.delete(Polygon.class);
		hdb.getTransaction().commit();
//		hdb.commit();

		assertFalse(hdb.contains(a));
		assertFalse(hdb.contains(b));
		assertFalse(hdb.contains(c));
		assertFalse(hdb.contains(d));
		assertFalse(hdb.contains(ab));
		assertFalse(hdb.contains(bc));
		assertFalse(hdb.contains(ca));
		assertFalse(hdb.contains(triangle));

		hdb.insert(a, b, c, d);
		hdb.insert(ab, bc, ca, cd, da);
		hdb.insert(triangle, tetragon);
		hdb.getTransaction().commit();
//		hdb.commit();

		assertTrue(hdb.contains(a));
		assertTrue(hdb.contains(b));
		assertTrue(hdb.contains(c));
		assertTrue(hdb.contains(d));
		assertTrue(hdb.contains(ab));
		assertTrue(hdb.contains(bc));
		assertTrue(hdb.contains(ca));
		assertTrue(hdb.contains(triangle));

		hdb.getTransaction().close();

	}

	@Test
	public void testRollback() {

		Point p = new Point("p");

//		hdb.begin();
		hdb.getTransaction().begin();
		hdb.delete(Point.class);
		hdb.delete(Segment.class);
		hdb.delete(Polygon.class);
		hdb.getTransaction().commit();
//		hdb.commit();

		assertFalse(hdb.contains(a));
		assertFalse(hdb.contains(b));
		assertFalse(hdb.contains(c));
		assertFalse(hdb.contains(d));
		assertFalse(hdb.contains(ab));
		assertFalse(hdb.contains(bc));
		assertFalse(hdb.contains(ca));
		assertFalse(hdb.contains(triangle));

		hdb.insert(p);

		assertTrue(hdb.contains(p));

		hdb.rollback();

		assertFalse(hdb.contains(p));

	}

	@Test
	public void testIsOpen() {

		assertTrue(hdb.isOpen());

		hdb.close();

		assertFalse(hdb.isOpen());

	}

	@Test
	public void testGetEngine() {
		assertNotNull(hdb.getEngine());
	}

	@Test
	public void testGetProvider() {
		assertNotNull(hdb.getProvider());
	}

	@Test
	public void testGetConverter() {
		assertEquals(new PrologObjectConverter(provider), hdb.getConverter());
		assertNotNull(hdb.getConverter());
	}

	@Test
	public void testClassOfO() {

		assertEquals(Point.class, hdb.classOf(a));
		assertEquals(Segment.class, hdb.classOf(ab));
		assertEquals(Polygon.class, hdb.classOf(triangle));

	}

	@Test
	public void testClassOfStringInt() {

		assertEquals(Point.class, hdb.classOf(Point.class.getName(), 3));
		assertEquals(Segment.class, hdb.classOf(Segment.class.getName(), 3));
		assertEquals(Polygon.class, hdb.classOf(Polygon.class.getName(), 4));

	}

	@Test
	public void testClassOfPredicateOfO() {

		assertEquals(Point.class, hdb.classOf(new Predicate<Point>() {

			private static final long serialVersionUID = -4018100887741066860L;

			public boolean evaluate(Point p) {
				return a.equals(p);
			}

		}));

		assertEquals(Segment.class, hdb.classOf(new Predicate<Segment>() {

			private static final long serialVersionUID = 4166804501726650718L;

			public boolean evaluate(Segment s) {
				return ab.equals(s);
			}

		}));

		assertEquals(Polygon.class, hdb.classOf(new Predicate<Polygon>() {

			private static final long serialVersionUID = -6708308685793944730L;

			public boolean evaluate(Polygon p) {
				return triangle.equals(p);
			}

		}));

	}

	@Test
	public void testClassesOfString() {
		assertArrayEquals(new Class[] { Segment.class, Point.class }, hdb
				.classesOf("'" + Segment.class.getName() + "'(Ids, Point0, Point1), Point0 == '" + Point.class.getName()
						+ "'(a, 3,14), Point1 == '" + Point.class.getName() + "'(b, 3,14)")
				.toArray());
	}

	@Test
	public void testClassesOfPrologTermArray() {
		ObjectConverter<PrologTerm> converter = new PrologObjectConverter(provider);
		assertArrayEquals(new Class[] { Point.class, Segment.class, Polygon.class },
				hdb.classesOf(
						new PrologTerm[] { converter.toTerm(a), converter.toTerm(ab), converter.toTerm(triangle) })
						.toArray());
	}

	@Test
	public void testUnwrapClassOfK() {
		assertEquals(PrologHierarchicalDatabase.class.getName(),
				hdb.unwrap(PrologHierarchicalDatabase.class).getClass().getName());
	}

	@Test
	public void testUnwrapObjectClassOfK() {
		assertEquals(PrologHierarchicalDatabase.class.getName(),
				hdb.unwrap(hdb, PrologHierarchicalDatabase.class).getClass().getName());
	}

	@Test
	public void testIsWrappedByClassOfQ() {
		assertTrue(hdb.isWrappedFor(AbstractHierarchicalDatabase.class));
		assertTrue(hdb.isWrappedFor(PrologHierarchicalDatabase.class));
	}

	@Test
	public void testIsWrappedByObjectClassOfQ() {
		assertTrue(hdb.isWrappedFor(hdb, AbstractHierarchicalDatabase.class));
		assertTrue(hdb.isWrappedFor(hdb, PrologHierarchicalDatabase.class));
	}

}
