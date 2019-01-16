package org.worklogic;

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
import org.logicware.prolog.PrologClause;
import org.logicware.prolog.PrologEngine;
import org.worklogic.db.ObjectConverterFactory;
import org.worklogic.db.Predicate;
import org.worklogic.db.ProcedureQuery;
import org.worklogic.db.etc.Settings;
import org.worklogic.db.prolog.PrologObjectConverter;
import org.worklogic.domain.geometry.Point;
import org.worklogic.domain.geometry.Polygon;
import org.worklogic.domain.geometry.Segment;
import org.worklogic.domain.geometry.Tetragon;

public class StoragePoolTest extends BaseTest {

	@Test
	public void testGetPoolName() {
		assertEquals(POOL_NAME, storagePool.getPoolName());
	}

	@Test
	public void testGetCapacity() {
		assertEquals(1000, storagePool.getCapacity());
	}

	@Test
	public void testGetPoolSize() {

		assertEquals(0, storagePool.getPoolSize());

		storagePool.getTransaction().begin();

		storagePool.insert(a);
		storagePool.insert(b);
		storagePool.insert(c);
		storagePool.insert(d);
		storagePool.insert(ab);
		storagePool.insert(bc);
		storagePool.insert(ca);
		storagePool.insert(cd);
		storagePool.insert(da);
		storagePool.insert(triangle);

		storagePool.getTransaction().commit();
		storagePool.getTransaction().close();

		assertEquals(1, storagePool.getPoolSize());

	}

	@Test
	public void testIsEmpty() {

		assertTrue(storagePool.isEmpty());

		storagePool.getTransaction().begin();

		storagePool.insert(a);
		storagePool.insert(b);
		storagePool.insert(c);
		storagePool.insert(d);
		storagePool.insert(ab);
		storagePool.insert(bc);
		storagePool.insert(ca);
		storagePool.insert(cd);
		storagePool.insert(da);
		storagePool.insert(triangle);

		storagePool.getTransaction().commit();
		storagePool.getTransaction().close();

		assertFalse(storagePool.isEmpty());

	}

	@Test
	public void testFlush() {

		storagePool.getTransaction().begin();

		storagePool.insert(a);
		storagePool.insert(b);
		storagePool.insert(c);
		storagePool.insert(d);
		storagePool.insert(ab);
		storagePool.insert(bc);
		storagePool.insert(ca);
		storagePool.insert(cd);
		storagePool.insert(da);
		storagePool.insert(triangle);

		storagePool.flush();

		// Physic existence test
		File file = new File(POOL_ROOT);
		File[] files = file.listFiles(storagePool.getFilter());
		for (File poolFile : files) {
			assertTrue(poolFile.exists());
			assertTrue(poolFile.length() > 0);
		}

		assertTrue(storagePool.contains(a));
		assertTrue(storagePool.contains(b));
		assertTrue(storagePool.contains(c));
		assertTrue(storagePool.contains(d));
		assertTrue(storagePool.contains(ab));
		assertTrue(storagePool.contains(bc));
		assertTrue(storagePool.contains(ca));
		assertTrue(storagePool.contains(cd));
		assertTrue(storagePool.contains(da));
		assertTrue(storagePool.contains(triangle));

		storagePool.getTransaction().close();

	}

	@Test
	public void testGetContainerFactory() {
		assertEquals(new Settings(driver).getContainerFactory(), storagePool.getContainerFactory());
	}

	@Test
	public void testCreateAndRestoreBackup() {

		storagePool.getTransaction().begin();

		storagePool.delete(Point.class);
		storagePool.delete(Segment.class);
		storagePool.delete(Polygon.class);

		assertFalse(storagePool.contains(a));
		assertFalse(storagePool.contains(b));
		assertFalse(storagePool.contains(c));
		assertFalse(storagePool.contains(ab));
		assertFalse(storagePool.contains(bc));
		assertFalse(storagePool.contains(ca));
		assertFalse(storagePool.contains(cd));
		assertFalse(storagePool.contains(da));
		assertFalse(storagePool.contains(triangle));

		storagePool.insert(a);
		storagePool.insert(b);
		storagePool.insert(c);
		storagePool.insert(d);
		storagePool.insert(ab);
		storagePool.insert(bc);
		storagePool.insert(ca);
		storagePool.insert(cd);
		storagePool.insert(da);
		storagePool.insert(triangle);

		storagePool.getTransaction().commit();

		assertTrue(storagePool.contains(a));
		assertTrue(storagePool.contains(b));
		assertTrue(storagePool.contains(c));
		assertTrue(storagePool.contains(ab));
		assertTrue(storagePool.contains(bc));
		assertTrue(storagePool.contains(ca));
		assertTrue(storagePool.contains(cd));
		assertTrue(storagePool.contains(da));
		assertTrue(storagePool.contains(triangle));

		// make backup
		storagePool.backup(BACKUP_DIRECTORY, BACKUP_ZIP_FILE_NAME_3);

		// Physic existence test
		File file = new File(BACKUP_ZIP_FILE_PATH_3);
		assertTrue(file.exists());
		assertTrue(file.length() > 0);

		// restore the created backup
		storagePool.restore("", BACKUP_ZIP_FILE_PATH_3);

		file = new File(POOL_ROOT);
		File[] files = file.listFiles(storagePool.getFilter());
		for (File poolFile : files) {

			// Physic existence test
			assertTrue(poolFile.exists());
			assertTrue(poolFile.length() > 0);

			// Logical program saved
			PrologEngine engine = provider.newEngine();
			engine.consult(poolFile.getAbsolutePath());
			assertEquals(10, engine.getProgramSize());
			assertFalse(engine.isProgramEmpty());
			engine.dispose();

		}

		storagePool.getTransaction().close();

	}

	@Test
	public void testOpenAndClose() {

		assertFalse(storagePool.isOpen());
		storagePool.open();
		assertTrue(storagePool.isOpen());
		storagePool.close();
		assertFalse(storagePool.isOpen());

	}

	@Test
	public void testInsert() {

		storagePool.getTransaction().begin();

		storagePool.delete(a);
		storagePool.delete(b);
		storagePool.delete(c);
		storagePool.delete(d);
		storagePool.delete(ab);
		storagePool.delete(bc);
		storagePool.delete(ca);
		storagePool.delete(cd);
		storagePool.delete(da);
		storagePool.delete(triangle);

		storagePool.getTransaction().commit();

		assertFalse(storagePool.contains(a));
		assertFalse(storagePool.contains(b));
		assertFalse(storagePool.contains(c));
		assertFalse(storagePool.contains(ab));
		assertFalse(storagePool.contains(bc));
		assertFalse(storagePool.contains(ca));
		assertFalse(storagePool.contains(triangle));

		storagePool.insert(a);
		storagePool.insert(b);
		storagePool.insert(c);
		storagePool.insert(d);
		storagePool.insert(ab);
		storagePool.insert(bc);
		storagePool.insert(ca);
		storagePool.insert(cd);
		storagePool.insert(da);
		storagePool.insert(triangle);

		storagePool.getTransaction().commit();

		assertTrue(storagePool.contains(a));
		assertTrue(storagePool.contains(b));
		assertTrue(storagePool.contains(c));
		assertTrue(storagePool.contains(d));
		assertTrue(storagePool.contains(ab));
		assertTrue(storagePool.contains(bc));
		assertTrue(storagePool.contains(ca));
		assertTrue(storagePool.contains(cd));
		assertTrue(storagePool.contains(da));
		assertTrue(storagePool.contains(triangle));

		assertEquals(a, storagePool.createQuery(a).getSolution());
		assertEquals(b, storagePool.createQuery(b).getSolution());
		assertEquals(c, storagePool.createQuery(c).getSolution());
		assertEquals(d, storagePool.createQuery(d).getSolution());

		storagePool.getTransaction().close();

	}

	@Test
	public void testUpdate() {

		storagePool.getTransaction().begin();

		storagePool.insert(a);
		storagePool.insert(b);
		storagePool.insert(c);
		storagePool.insert(d);
		storagePool.insert(ab);
		storagePool.insert(bc);
		storagePool.insert(ca);
		storagePool.insert(cd);
		storagePool.insert(da);
		storagePool.insert(triangle);

		storagePool.getTransaction().commit();

		// create new update objects

		Point newA = new Point("a", 6, 28);
		Point newB = new Point("b", 6, 28);
		Point newC = new Point("c", 6, 28);
		Point newD = new Point("d", 6, 28);

		Segment newAB = new Segment("ab", newA, newB);
		Segment newBC = new Segment("bc", newB, newC);
		Segment newCA = new Segment("ca", newC, newA);
		Segment newCD = new Segment("cd", newC, newD);
		Segment newDA = new Segment("da", newD, newA);

		Polygon newTriangle = new Polygon("triangle", newAB, newBC, newCA);

		assertTrue(storagePool.contains(a));
		assertTrue(storagePool.contains(b));
		assertTrue(storagePool.contains(c));
		assertTrue(storagePool.contains(d));
		assertTrue(storagePool.contains(ab));
		assertTrue(storagePool.contains(bc));
		assertTrue(storagePool.contains(ca));
		assertTrue(storagePool.contains(cd));
		assertTrue(storagePool.contains(da));

		assertFalse(storagePool.contains(newA));
		assertFalse(storagePool.contains(newB));
		assertFalse(storagePool.contains(newC));
		assertFalse(storagePool.contains(newD));
		assertFalse(storagePool.contains(newAB));
		assertFalse(storagePool.contains(newBC));
		assertFalse(storagePool.contains(newCA));
		assertFalse(storagePool.contains(newCD));
		assertFalse(storagePool.contains(newDA));
		assertFalse(storagePool.contains(newTriangle));

		// updating

		storagePool.update(a, newA);
		storagePool.update(b, newB);
		storagePool.update(c, newC);
		storagePool.update(d, newD);
		storagePool.update(ab, newAB);
		storagePool.update(bc, newBC);
		storagePool.update(ca, newCA);
		storagePool.update(cd, newCD);
		storagePool.update(da, newDA);
		storagePool.update(triangle, newTriangle);

		assertFalse(storagePool.contains(a));
		assertFalse(storagePool.contains(b));
		assertFalse(storagePool.contains(c));
		assertFalse(storagePool.contains(d));
		assertFalse(storagePool.contains(ab));
		assertFalse(storagePool.contains(bc));
		assertFalse(storagePool.contains(ca));
		assertFalse(storagePool.contains(cd));
		assertFalse(storagePool.contains(da));
		assertFalse(storagePool.contains(triangle));

		assertTrue(storagePool.contains(newA));
		assertTrue(storagePool.contains(newB));
		assertTrue(storagePool.contains(newC));
		assertTrue(storagePool.contains(newD));
		assertTrue(storagePool.contains(newAB));
		assertTrue(storagePool.contains(newBC));
		assertTrue(storagePool.contains(newCA));
		assertTrue(storagePool.contains(newCD));
		assertTrue(storagePool.contains(newDA));
		assertTrue(storagePool.contains(newTriangle));

		// reverting update changes

		storagePool.update(newA, a);
		storagePool.update(newB, b);
		storagePool.update(newC, c);
		storagePool.update(newD, d);
		storagePool.update(newAB, ab);
		storagePool.update(newBC, bc);
		storagePool.update(newCA, ca);
		storagePool.update(newCD, cd);
		storagePool.update(newDA, da);
		storagePool.update(newTriangle, triangle);

		storagePool.getTransaction().commit();

		assertTrue(storagePool.contains(a));
		assertTrue(storagePool.contains(b));
		assertTrue(storagePool.contains(c));
		assertTrue(storagePool.contains(d));
		assertTrue(storagePool.contains(ab));
		assertTrue(storagePool.contains(bc));
		assertTrue(storagePool.contains(ca));
		assertTrue(storagePool.contains(cd));
		assertTrue(storagePool.contains(da));
		assertTrue(storagePool.contains(triangle));

		assertFalse(storagePool.contains(newA));
		assertFalse(storagePool.contains(newB));
		assertFalse(storagePool.contains(newC));
		assertFalse(storagePool.contains(newD));
		assertFalse(storagePool.contains(newAB));
		assertFalse(storagePool.contains(newBC));
		assertFalse(storagePool.contains(newCA));
		assertFalse(storagePool.contains(newCD));
		assertFalse(storagePool.contains(newDA));
		assertFalse(storagePool.contains(newTriangle));

		storagePool.getTransaction().close();

	}

	@Test
	public void testDeleteClassOfQ() {

		storagePool.getTransaction().begin();

		storagePool.delete(Point.class);
		storagePool.delete(Segment.class);
		storagePool.delete(Polygon.class);

		storagePool.getTransaction().commit();

		assertFalse(storagePool.contains(Point.class));
		assertFalse(storagePool.contains(Segment.class));
		assertFalse(storagePool.contains(Polygon.class));

		storagePool.insert(a);
		storagePool.insert(b);
		storagePool.insert(c);
		storagePool.insert(d);
		storagePool.insert(ab);
		storagePool.insert(bc);
		storagePool.insert(ca);
		storagePool.insert(ca);
		storagePool.insert(da);
		storagePool.insert(triangle);

		storagePool.getTransaction().commit();

		assertTrue(storagePool.contains(Point.class));
		assertTrue(storagePool.contains(Segment.class));
		assertTrue(storagePool.contains(Polygon.class));

		storagePool.getTransaction().close();

	}

	@Test
	public void testDeleteOArray() {

		storagePool.getTransaction().begin();

		storagePool.delete(a);
		storagePool.delete(b);
		storagePool.delete(c);
		storagePool.delete(d);
		storagePool.delete(ab);
		storagePool.delete(bc);
		storagePool.delete(ca);
		storagePool.delete(cd);
		storagePool.delete(da);
		storagePool.delete(triangle);

		storagePool.getTransaction().commit();

		assertFalse(storagePool.contains(a));
		assertFalse(storagePool.contains(b));
		assertFalse(storagePool.contains(c));
		assertFalse(storagePool.contains(ab));
		assertFalse(storagePool.contains(bc));
		assertFalse(storagePool.contains(ca));
		assertFalse(storagePool.contains(triangle));

		storagePool.insert(a);
		storagePool.insert(b);
		storagePool.insert(c);
		storagePool.insert(d);
		storagePool.insert(ab);
		storagePool.insert(bc);
		storagePool.insert(ca);
		storagePool.insert(cd);
		storagePool.insert(da);
		storagePool.insert(triangle);

		storagePool.getTransaction().commit();

		assertTrue(storagePool.contains(a));
		assertTrue(storagePool.contains(b));
		assertTrue(storagePool.contains(c));
		assertTrue(storagePool.contains(d));
		assertTrue(storagePool.contains(ab));
		assertTrue(storagePool.contains(bc));
		assertTrue(storagePool.contains(ca));
		assertTrue(storagePool.contains(cd));
		assertTrue(storagePool.contains(da));
		assertTrue(storagePool.contains(triangle));

		storagePool.getTransaction().close();

	}

	@Test
	public void testGetTransaction() {

		assertFalse(storagePool.getTransaction().isActive());
		storagePool.getTransaction().begin();
		assertTrue(storagePool.getTransaction().isActive());
		storagePool.getTransaction().commit();
		assertTrue(storagePool.getTransaction().isActive());
		storagePool.getTransaction().close();
		assertFalse(storagePool.getTransaction().isActive());
		storagePool.getTransaction().begin();
		assertTrue(storagePool.getTransaction().isActive());
		storagePool.getTransaction().rollback();
		assertTrue(storagePool.getTransaction().isActive());
		storagePool.getTransaction().close();
		assertFalse(storagePool.getTransaction().isActive());

	}

	@Test
	public void testCreateQueryString() {

		storagePool.getTransaction().begin();

		storagePool.delete(Point.class);
		storagePool.delete(Segment.class);
		storagePool.delete(Polygon.class);

		storagePool.getTransaction().commit();

		assertFalse(storagePool.contains(a));
		assertFalse(storagePool.contains(b));
		assertFalse(storagePool.contains(c));
		assertFalse(storagePool.contains(ab));
		assertFalse(storagePool.contains(bc));
		assertFalse(storagePool.contains(ca));
		assertFalse(storagePool.contains(cd));
		assertFalse(storagePool.contains(da));
		assertFalse(storagePool.contains(triangle));

		storagePool.insert(a);
		storagePool.insert(b);
		storagePool.insert(c);
		storagePool.insert(d);
		storagePool.insert(ab);
		storagePool.insert(bc);
		storagePool.insert(ca);
		storagePool.insert(cd);
		storagePool.insert(da);
		storagePool.insert(triangle);

		storagePool.getTransaction().commit();

		assertTrue(storagePool.contains(a));
		assertTrue(storagePool.contains(b));
		assertTrue(storagePool.contains(c));
		assertTrue(storagePool.contains(ab));
		assertTrue(storagePool.contains(bc));
		assertTrue(storagePool.contains(ca));
		assertTrue(storagePool.contains(cd));
		assertTrue(storagePool.contains(da));
		assertTrue(storagePool.contains(triangle));

		List<Object> objects = new ArrayList<Object>();

		objects = storagePool.createQuery("'" + Point.class.getName() + "'(Idp, X, Y)").getSolutions();
		assertEquals(Arrays.asList(a, b, c, d), createList(objects));

		// point query restricted to specifics x and y points
		objects = storagePool.createQuery("'" + Point.class.getName() + "'(Idp, X, Y), X =:= 3, Y =:= 14")
				.getSolutions();
		assertEquals(Arrays.asList(a, b, c, d), createList(objects));

		objects = storagePool.createQuery("'" + Segment.class.getName() + "'(Ids, Point0, Point1)").getSolutions();
		assertEquals(Arrays.asList(ab, bc, ca, cd, da), createList(objects));

		// segment query restricted to specifics points initial and finals
		objects = storagePool
				.createQuery("'" + Segment.class.getName() + "'(Ids, Point0, Point1), Point0 == '"
						+ Point.class.getName() + "'(a, 3,14), Point1 == '" + Point.class.getName() + "'(b, 3,14)")
				.getSolutions();
		assertEquals(Arrays.asList(ab), createList(objects));

		// predicate projection
		List<Point> points = new ArrayList<Point>();
		List<Segment> segments = new ArrayList<Segment>();
		objects = storagePool.createQuery(
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

		storagePool.getTransaction().close();

	}

	@Test
	public void testCreateQueryO() {

		storagePool.getTransaction().begin();

		storagePool.delete(Point.class);
		storagePool.delete(Segment.class);
		storagePool.delete(Polygon.class);

		storagePool.getTransaction().commit();

		assertFalse(storagePool.contains(a));
		assertFalse(storagePool.contains(b));
		assertFalse(storagePool.contains(c));
		assertFalse(storagePool.contains(ab));
		assertFalse(storagePool.contains(bc));
		assertFalse(storagePool.contains(ca));
		assertFalse(storagePool.contains(cd));
		assertFalse(storagePool.contains(da));
		assertFalse(storagePool.contains(triangle));

		storagePool.insert(a);
		storagePool.insert(b);
		storagePool.insert(c);
		storagePool.insert(d);
		storagePool.insert(ab);
		storagePool.insert(bc);
		storagePool.insert(ca);
		storagePool.insert(cd);
		storagePool.insert(da);
		storagePool.insert(triangle);

		storagePool.getTransaction().commit();

		assertTrue(storagePool.contains(a));
		assertTrue(storagePool.contains(b));
		assertTrue(storagePool.contains(c));
		assertTrue(storagePool.contains(ab));
		assertTrue(storagePool.contains(bc));
		assertTrue(storagePool.contains(ca));
		assertTrue(storagePool.contains(cd));
		assertTrue(storagePool.contains(da));
		assertTrue(storagePool.contains(triangle));

		assertEquals(a, storagePool.createQuery(a).getSolution());
		assertEquals(b, storagePool.createQuery(b).getSolution());
		assertEquals(c, storagePool.createQuery(c).getSolution());
		assertEquals(d, storagePool.createQuery(d).getSolution());
		assertEquals(ab, storagePool.createQuery(ab).getSolution());
		assertEquals(bc, storagePool.createQuery(bc).getSolution());
		assertEquals(ca, storagePool.createQuery(ca).getSolution());
		assertEquals(cd, storagePool.createQuery(cd).getSolution());
		assertEquals(da, storagePool.createQuery(da).getSolution());

		assertEquals(triangle, storagePool.createQuery(triangle).getSolution());

		assertEquals(Arrays.asList(a, b, c, d), storagePool.createQuery(new Point(3, 14)).getSolutions());
		assertEquals(Arrays.asList(ab, bc, ca, cd, da), storagePool.createQuery(new Segment()).getSolutions());
		assertEquals(Arrays.asList(triangle), storagePool.createQuery(new Polygon()).getSolutions());

		storagePool.getTransaction().close();

	}

	@Test
	public void testCreateQueryClassOfO() {

		storagePool.getTransaction().begin();

		storagePool.delete(Point.class);
		storagePool.delete(Segment.class);
		storagePool.delete(Polygon.class);

		storagePool.getTransaction().commit();

		assertFalse(storagePool.contains(a));
		assertFalse(storagePool.contains(b));
		assertFalse(storagePool.contains(c));
		assertFalse(storagePool.contains(ab));
		assertFalse(storagePool.contains(bc));
		assertFalse(storagePool.contains(ca));
		assertFalse(storagePool.contains(cd));
		assertFalse(storagePool.contains(da));
		assertFalse(storagePool.contains(triangle));

		storagePool.insert(a);
		storagePool.insert(b);
		storagePool.insert(c);
		storagePool.insert(d);
		storagePool.insert(ab);
		storagePool.insert(bc);
		storagePool.insert(ca);
		storagePool.insert(cd);
		storagePool.insert(da);
		storagePool.insert(triangle);

		storagePool.getTransaction().commit();

		assertTrue(storagePool.contains(a));
		assertTrue(storagePool.contains(b));
		assertTrue(storagePool.contains(c));
		assertTrue(storagePool.contains(ab));
		assertTrue(storagePool.contains(bc));
		assertTrue(storagePool.contains(ca));
		assertTrue(storagePool.contains(cd));
		assertTrue(storagePool.contains(da));
		assertTrue(storagePool.contains(triangle));

		assertEquals(Arrays.asList(a, b, c, d), storagePool.createQuery(Point.class).getSolutions());
		assertEquals(Arrays.asList(ab, bc, ca, cd, da), storagePool.createQuery(Segment.class).getSolutions());
		assertEquals(Arrays.asList(triangle), storagePool.createQuery(Polygon.class).getSolutions());

		storagePool.getTransaction().close();

	}

	@Test
	public void testCreateQueryPredicateOfO() {

		storagePool.getTransaction().begin();

		Point point = storagePool.createQuery(new Predicate<Point>() {

			public boolean evaluate(Point point) {
				return point.getIdp().equals("a");
			}

		}).getSolution();

		assertEquals(a, point);

		Segment segment = storagePool.createQuery(new Predicate<Segment>() {

			public boolean evaluate(Segment s) {
				return s.getIds().equals("bc");
			}

		}).getSolution();

		assertEquals(bc, segment);

		Polygon polygon = storagePool.createQuery(new Predicate<Polygon>() {

			public boolean evaluate(Polygon s) {
				return s.getId().equals("triangle");
			}

		}).getSolution();

		assertEquals(triangle, polygon);

		storagePool.getTransaction().close();

	}

	@Test
	public void testCreateProcedureQuery() {

		storagePool.getTransaction().begin();

		ProcedureQuery query = storagePool.createProcedureQuery("'" + Point.class.getName() + "'", "Idp", "X", "Y");
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

		storagePool.getTransaction().close();

	}

	@Test
	public void testContainerOf() {
		assertEquals(storagePool, storagePool.containerOf(Point.class));
		assertEquals(storagePool, storagePool.containerOf(Segment.class));
		assertEquals(storagePool, storagePool.containerOf(Polygon.class));
		assertEquals(storagePool, storagePool.containerOf(Tetragon.class));
	}

	@Test
	public void testLocationOf() {
		assertEquals(POOL_ROOT + SEPARATOR + POOL_NAME, storagePool.locationOf(Point.class));
		assertEquals(POOL_ROOT + SEPARATOR + POOL_NAME, storagePool.locationOf(Segment.class));
		assertEquals(POOL_ROOT + SEPARATOR + POOL_NAME, storagePool.locationOf(Polygon.class));
		assertEquals(POOL_ROOT + SEPARATOR + POOL_NAME, storagePool.locationOf(Tetragon.class));
	}

	@Test
	public void testGetLocation() {
		assertEquals(POOL_ROOT + SEPARATOR + POOL_NAME, storagePool.getLocation());
	}

	@Test
	public void testFindString() {

		storagePool.getTransaction().begin();

		storagePool.delete(Point.class);
		storagePool.delete(Segment.class);
		storagePool.delete(Polygon.class);

		storagePool.getTransaction().commit();

		assertFalse(storagePool.contains(a));
		assertFalse(storagePool.contains(b));
		assertFalse(storagePool.contains(c));
		assertFalse(storagePool.contains(ab));
		assertFalse(storagePool.contains(bc));
		assertFalse(storagePool.contains(ca));
		assertFalse(storagePool.contains(cd));
		assertFalse(storagePool.contains(da));
		assertFalse(storagePool.contains(triangle));

		storagePool.insert(a);
		storagePool.insert(b);
		storagePool.insert(c);
		storagePool.insert(d);
		storagePool.insert(ab);
		storagePool.insert(bc);
		storagePool.insert(ca);
		storagePool.insert(cd);
		storagePool.insert(da);
		storagePool.insert(triangle);

		storagePool.getTransaction().commit();

		assertTrue(storagePool.contains(a));
		assertTrue(storagePool.contains(b));
		assertTrue(storagePool.contains(c));
		assertTrue(storagePool.contains(ab));
		assertTrue(storagePool.contains(bc));
		assertTrue(storagePool.contains(ca));
		assertTrue(storagePool.contains(cd));
		assertTrue(storagePool.contains(da));
		assertTrue(storagePool.contains(triangle));

		assertArrayEquals(new Object[] { a },
				(Object[]) storagePool.find("'" + Point.class.getName() + "'(Idp, X, Y)"));

		// point query restricted to specifics x and y points
		assertArrayEquals(new Object[] { a },
				(Object[]) storagePool.find("'" + Point.class.getName() + "'(Idp, X, Y), X =:= 3, Y =:= 14"));

		assertArrayEquals(new Object[] { ab },
				(Object[]) storagePool.find("'" + Segment.class.getName() + "'(Ids, Point0, Point1)"));

		// segment query restricted to specifics points initial and finals
		Object solution = storagePool.find("'" + Segment.class.getName() + "'(Ids, Point0, Point1), Point0 == '"
				+ Point.class.getName() + "'(a, 3,14), Point1 == '" + Point.class.getName() + "'(b, 3,14)");
		storagePool.close();

		if (solution instanceof Object[]) {
			Object[] objects = (Object[]) solution;
			if (objects.length > 0) {
				Object object = objects[0];
				if (object instanceof Segment) {
					Segment segment = (Segment) object;
					assertEquals(ab, segment);
				}
			}
		}

		storagePool.getTransaction().close();

	}

	@Test
	public void testFindStringObjectArray() {

		storagePool.getTransaction().begin();

		storagePool.delete(Point.class);
		storagePool.delete(Segment.class);
		storagePool.delete(Polygon.class);

		storagePool.getTransaction().commit();

		assertFalse(storagePool.contains(a));
		assertFalse(storagePool.contains(b));
		assertFalse(storagePool.contains(c));
		assertFalse(storagePool.contains(ab));
		assertFalse(storagePool.contains(bc));
		assertFalse(storagePool.contains(ca));
		assertFalse(storagePool.contains(cd));
		assertFalse(storagePool.contains(da));
		assertFalse(storagePool.contains(triangle));

		storagePool.insert(a);
		storagePool.insert(b);
		storagePool.insert(c);
		storagePool.insert(d);
		storagePool.insert(ab);
		storagePool.insert(bc);
		storagePool.insert(ca);
		storagePool.insert(cd);
		storagePool.insert(da);
		storagePool.insert(triangle);

		storagePool.getTransaction().commit();

		assertTrue(storagePool.contains(a));
		assertTrue(storagePool.contains(b));
		assertTrue(storagePool.contains(c));
		assertTrue(storagePool.contains(ab));
		assertTrue(storagePool.contains(bc));
		assertTrue(storagePool.contains(ca));
		assertTrue(storagePool.contains(cd));
		assertTrue(storagePool.contains(da));
		assertTrue(storagePool.contains(triangle));

		storagePool.getTransaction().commit();

		assertEquals(a, storagePool.find("'" + Point.class.getName() + "'", null, 3, 14));
		assertEquals(ab, storagePool.find("'" + Segment.class.getName() + "'", null, null, null));
		assertEquals(triangle, storagePool.find("'" + Polygon.class.getName() + "'", null, null, null, null));

		storagePool.getTransaction().close();

	}

	@Test
	public void testFindO() {

		storagePool.getTransaction().begin();

		storagePool.delete(Point.class);
		storagePool.delete(Segment.class);
		storagePool.delete(Polygon.class);

		storagePool.getTransaction().commit();

		assertFalse(storagePool.contains(a));
		assertFalse(storagePool.contains(b));
		assertFalse(storagePool.contains(c));
		assertFalse(storagePool.contains(ab));
		assertFalse(storagePool.contains(bc));
		assertFalse(storagePool.contains(ca));
		assertFalse(storagePool.contains(cd));
		assertFalse(storagePool.contains(da));
		assertFalse(storagePool.contains(triangle));

		storagePool.insert(a);
		storagePool.insert(b);
		storagePool.insert(c);
		storagePool.insert(d);
		storagePool.insert(ab);
		storagePool.insert(bc);
		storagePool.insert(ca);
		storagePool.insert(cd);
		storagePool.insert(da);
		storagePool.insert(triangle);

		storagePool.getTransaction().commit();

		assertTrue(storagePool.contains(a));
		assertTrue(storagePool.contains(b));
		assertTrue(storagePool.contains(c));
		assertTrue(storagePool.contains(ab));
		assertTrue(storagePool.contains(bc));
		assertTrue(storagePool.contains(ca));
		assertTrue(storagePool.contains(cd));
		assertTrue(storagePool.contains(da));
		assertTrue(storagePool.contains(triangle));

		assertEquals(a, storagePool.find(a));
		assertEquals(b, storagePool.find(b));
		assertEquals(c, storagePool.find(c));
		assertEquals(d, storagePool.find(d));
		assertEquals(ab, storagePool.find(ab));
		assertEquals(bc, storagePool.find(bc));
		assertEquals(ca, storagePool.find(ca));
		assertEquals(triangle, storagePool.find(triangle));

		assertEquals(a, storagePool.find(new Point(3, 14)));
		assertEquals(ab, storagePool.find(new Segment()));
		assertEquals(triangle, storagePool.find(new Polygon()));

		storagePool.getTransaction().close();

	}

	@Test
	public void testFindClassOfO() {

		storagePool.getTransaction().begin();

		storagePool.delete(Point.class);
		storagePool.delete(Segment.class);
		storagePool.delete(Polygon.class);

		storagePool.getTransaction().commit();

		assertFalse(storagePool.contains(a));
		assertFalse(storagePool.contains(b));
		assertFalse(storagePool.contains(c));
		assertFalse(storagePool.contains(ab));
		assertFalse(storagePool.contains(bc));
		assertFalse(storagePool.contains(ca));
		assertFalse(storagePool.contains(cd));
		assertFalse(storagePool.contains(da));
		assertFalse(storagePool.contains(triangle));

		storagePool.insert(a);
		storagePool.insert(b);
		storagePool.insert(c);
		storagePool.insert(d);
		storagePool.insert(ab);
		storagePool.insert(bc);
		storagePool.insert(ca);
		storagePool.insert(cd);
		storagePool.insert(da);
		storagePool.insert(triangle);

		storagePool.getTransaction().commit();

		assertTrue(storagePool.contains(a));
		assertTrue(storagePool.contains(b));
		assertTrue(storagePool.contains(c));
		assertTrue(storagePool.contains(ab));
		assertTrue(storagePool.contains(bc));
		assertTrue(storagePool.contains(ca));
		assertTrue(storagePool.contains(cd));
		assertTrue(storagePool.contains(da));
		assertTrue(storagePool.contains(triangle));

		assertEquals(a, storagePool.find(Point.class));
		assertEquals(ab, storagePool.find(Segment.class));
		assertEquals(triangle, storagePool.find(Polygon.class));

		storagePool.getTransaction().close();

	}

	@Test
	public void testFindPredicateOfO() {

		storagePool.getTransaction().begin();

		storagePool.insert(a);
		storagePool.insert(b);
		storagePool.insert(c);
		storagePool.insert(d);
		storagePool.insert(ab);
		storagePool.insert(bc);
		storagePool.insert(ca);
		storagePool.insert(triangle);

		storagePool.getTransaction().commit();

		Point point = storagePool.find(new Predicate<Point>() {

			public boolean evaluate(Point point) {
				return point.getIdp().equals("a");
			}

		});

		assertEquals(a, point);

		Segment segment = storagePool.find(new Predicate<Segment>() {

			public boolean evaluate(Segment s) {
				return s.getIds().equals("bc");
			}

		});

		assertEquals(bc, segment);

		Polygon polygon = storagePool.find(new Predicate<Polygon>() {

			public boolean evaluate(Polygon s) {
				return s.getId().equals("triangle");
			}

		});

		assertEquals(triangle, polygon);

		storagePool.getTransaction().close();

	}

	@Test
	public void testFindAllString() {

		storagePool.getTransaction().begin();

		storagePool.delete(Point.class);
		storagePool.delete(Segment.class);
		storagePool.delete(Polygon.class);

		storagePool.getTransaction().commit();

		assertFalse(storagePool.contains(a));
		assertFalse(storagePool.contains(b));
		assertFalse(storagePool.contains(c));
		assertFalse(storagePool.contains(ab));
		assertFalse(storagePool.contains(bc));
		assertFalse(storagePool.contains(ca));
		assertFalse(storagePool.contains(cd));
		assertFalse(storagePool.contains(da));
		assertFalse(storagePool.contains(triangle));

		storagePool.insert(a);
		storagePool.insert(b);
		storagePool.insert(c);
		storagePool.insert(d);
		storagePool.insert(ab);
		storagePool.insert(bc);
		storagePool.insert(ca);
		storagePool.insert(cd);
		storagePool.insert(da);
		storagePool.insert(triangle);

		storagePool.getTransaction().commit();

		assertTrue(storagePool.contains(a));
		assertTrue(storagePool.contains(b));
		assertTrue(storagePool.contains(c));
		assertTrue(storagePool.contains(ab));
		assertTrue(storagePool.contains(bc));
		assertTrue(storagePool.contains(ca));
		assertTrue(storagePool.contains(cd));
		assertTrue(storagePool.contains(da));
		assertTrue(storagePool.contains(triangle));

		List<Object> solutions = new ArrayList<Object>();

		solutions = storagePool.findAll("'" + Point.class.getName() + "'(Idp, X, Y)");
		assertEquals(Arrays.asList(a, b, c, d), createList(solutions));

		// point query restricted to specifics x and y points
		solutions = storagePool.findAll("'" + Point.class.getName() + "'(Idp, X, Y), X =:= 3, Y =:= 14");
		assertEquals(Arrays.asList(a, b, c, d), createList(solutions));

		solutions = storagePool.findAll("'" + Segment.class.getName() + "'(Ids, Point0, Point1)");
		assertEquals(Arrays.asList(ab, bc, ca, cd, da), createList(solutions));

		// segment query restricted to specifics points initial and finals
		solutions = storagePool.findAll("'" + Segment.class.getName() + "'(Ids, Point0, Point1), Point0 == '"
				+ Point.class.getName() + "'(a, 3,14), Point1 == '" + Point.class.getName() + "'(b, 3,14)");
		assertEquals(Arrays.asList(ab), createList(solutions));

		// predicate projection
		solutions = storagePool.findAll(
				"'" + Point.class.getName() + "'(Idp, X, Y), '" + Segment.class.getName() + "'(Ids, Point0, Point1)");

		List<Point> points = new ArrayList<Point>();
		List<Segment> segments = new ArrayList<Segment>();
		for (Object solution : solutions) {
			if (solution instanceof Object[]) {
				Object[] objects = (Object[]) solution;
				if (objects.length > 1) {
					Object object0 = objects[0];
					Object object1 = objects[1];
					if (object0 instanceof Point) {
						Point point = (Point) object0;
						points.add(point);
					}
					if (object1 instanceof Segment) {
						Segment segment = (Segment) object1;
						segments.add(segment);
					}
				}
			}
		}

		// all combination created by the Projection or Cartesian Product
		assertEquals(Arrays.asList(a, a, a, a, a, b, b, b, b, b, c, c, c, c, c, d, d, d, d, d), points);
		assertEquals(Arrays.asList(ab, bc, ca, cd, da, ab, bc, ca, cd, da, ab, bc, ca, cd, da, ab, bc, ca, cd, da),
				segments);

		storagePool.getTransaction().close();

	}

	@Test
	public void testFindAllStringObjectArray() {

		storagePool.getTransaction().begin();

		storagePool.delete(Point.class);
		storagePool.delete(Segment.class);
		storagePool.delete(Polygon.class);

		storagePool.getTransaction().commit();

		assertFalse(storagePool.contains(a));
		assertFalse(storagePool.contains(b));
		assertFalse(storagePool.contains(c));
		assertFalse(storagePool.contains(ab));
		assertFalse(storagePool.contains(bc));
		assertFalse(storagePool.contains(ca));
		assertFalse(storagePool.contains(cd));
		assertFalse(storagePool.contains(da));
		assertFalse(storagePool.contains(triangle));

		storagePool.insert(a);
		storagePool.insert(b);
		storagePool.insert(c);
		storagePool.insert(d);
		storagePool.insert(ab);
		storagePool.insert(bc);
		storagePool.insert(ca);
		storagePool.insert(cd);
		storagePool.insert(da);
		storagePool.insert(triangle);

		storagePool.getTransaction().commit();

		assertTrue(storagePool.contains(a));
		assertTrue(storagePool.contains(b));
		assertTrue(storagePool.contains(c));
		assertTrue(storagePool.contains(ab));
		assertTrue(storagePool.contains(bc));
		assertTrue(storagePool.contains(ca));
		assertTrue(storagePool.contains(cd));
		assertTrue(storagePool.contains(da));
		assertTrue(storagePool.contains(triangle));

		assertEquals(Arrays.asList(a, b, c, d), storagePool.findAll("'" + Point.class.getName() + "'", null, 3, 14));
		assertEquals(Arrays.asList(ab, bc, ca, cd, da),
				storagePool.findAll("'" + Segment.class.getName() + "'", null, null, null));
		assertEquals(Arrays.asList(triangle),
				storagePool.findAll("'" + Polygon.class.getName() + "'", null, null, null, null));

		storagePool.getTransaction().close();

	}

	@Test
	public void testFindAllO() {

		storagePool.getTransaction().begin();

		storagePool.delete(Point.class);
		storagePool.delete(Segment.class);
		storagePool.delete(Polygon.class);

		storagePool.getTransaction().commit();

		assertFalse(storagePool.contains(a));
		assertFalse(storagePool.contains(b));
		assertFalse(storagePool.contains(c));
		assertFalse(storagePool.contains(ab));
		assertFalse(storagePool.contains(bc));
		assertFalse(storagePool.contains(ca));
		assertFalse(storagePool.contains(cd));
		assertFalse(storagePool.contains(da));
		assertFalse(storagePool.contains(triangle));

		storagePool.insert(a);
		storagePool.insert(b);
		storagePool.insert(c);
		storagePool.insert(d);
		storagePool.insert(ab);
		storagePool.insert(bc);
		storagePool.insert(ca);
		storagePool.insert(cd);
		storagePool.insert(da);
		storagePool.insert(triangle);

		storagePool.getTransaction().commit();

		assertTrue(storagePool.contains(a));
		assertTrue(storagePool.contains(b));
		assertTrue(storagePool.contains(c));
		assertTrue(storagePool.contains(ab));
		assertTrue(storagePool.contains(bc));
		assertTrue(storagePool.contains(ca));
		assertTrue(storagePool.contains(cd));
		assertTrue(storagePool.contains(da));
		assertTrue(storagePool.contains(triangle));

		assertEquals(Arrays.asList(a, b, c, d), storagePool.findAll(new Point(3, 14)));
		assertEquals(Arrays.asList(ab, bc, ca, cd, da), storagePool.findAll(new Segment()));
		assertEquals(Arrays.asList(triangle), storagePool.findAll(new Polygon()));

		storagePool.getTransaction().close();

	}

	@Test
	public void testFindAllClassOfO() {

		storagePool.getTransaction().begin();

		storagePool.delete(Point.class);
		storagePool.delete(Segment.class);
		storagePool.delete(Polygon.class);

		storagePool.getTransaction().commit();

		assertFalse(storagePool.contains(a));
		assertFalse(storagePool.contains(b));
		assertFalse(storagePool.contains(c));
		assertFalse(storagePool.contains(ab));
		assertFalse(storagePool.contains(bc));
		assertFalse(storagePool.contains(ca));
		assertFalse(storagePool.contains(cd));
		assertFalse(storagePool.contains(da));
		assertFalse(storagePool.contains(triangle));

		storagePool.insert(a);
		storagePool.insert(b);
		storagePool.insert(c);
		storagePool.insert(d);
		storagePool.insert(ab);
		storagePool.insert(bc);
		storagePool.insert(ca);
		storagePool.insert(cd);
		storagePool.insert(da);
		storagePool.insert(triangle);

		storagePool.getTransaction().commit();

		assertTrue(storagePool.contains(a));
		assertTrue(storagePool.contains(b));
		assertTrue(storagePool.contains(c));
		assertTrue(storagePool.contains(ab));
		assertTrue(storagePool.contains(bc));
		assertTrue(storagePool.contains(ca));
		assertTrue(storagePool.contains(cd));
		assertTrue(storagePool.contains(da));
		assertTrue(storagePool.contains(triangle));

		assertEquals(Arrays.asList(a, b, c, d), storagePool.findAll(Point.class));
		assertEquals(Arrays.asList(ab, bc, ca, cd, da), storagePool.findAll(Segment.class));
		assertEquals(Arrays.asList(triangle), storagePool.findAll(Polygon.class));

		storagePool.getTransaction().close();

	}

	@Test
	public void testFindAllPredicateOfO() {

		storagePool.getTransaction().begin();

		storagePool.delete(Point.class);
		storagePool.delete(Segment.class);
		storagePool.delete(Polygon.class);

		storagePool.getTransaction().commit();

		assertFalse(storagePool.contains(a));
		assertFalse(storagePool.contains(b));
		assertFalse(storagePool.contains(c));
		assertFalse(storagePool.contains(ab));
		assertFalse(storagePool.contains(bc));
		assertFalse(storagePool.contains(ca));
		assertFalse(storagePool.contains(cd));
		assertFalse(storagePool.contains(da));
		assertFalse(storagePool.contains(triangle));

		storagePool.insert(a);
		storagePool.insert(b);
		storagePool.insert(c);
		storagePool.insert(d);
		storagePool.insert(ab);
		storagePool.insert(bc);
		storagePool.insert(ca);
		storagePool.insert(cd);
		storagePool.insert(da);
		storagePool.insert(triangle);

		storagePool.getTransaction().commit();

		assertTrue(storagePool.contains(a));
		assertTrue(storagePool.contains(b));
		assertTrue(storagePool.contains(c));
		assertTrue(storagePool.contains(ab));
		assertTrue(storagePool.contains(bc));
		assertTrue(storagePool.contains(ca));
		assertTrue(storagePool.contains(cd));
		assertTrue(storagePool.contains(da));
		assertTrue(storagePool.contains(triangle));

		List<Point> points = storagePool.findAll(new Predicate<Point>() {

			public boolean evaluate(Point point) {
				return point.getIdp().equals("a");
			}
		});

		assertEquals(Arrays.asList(a), points);

		List<Segment> segments = storagePool.findAll(new Predicate<Segment>() {

			public boolean evaluate(Segment s) {
				return s.getIds().equals("bc");
			}
		});

		assertEquals(Arrays.asList(bc), segments);

		List<Polygon> polygons = storagePool.findAll(new Predicate<Polygon>() {

			public boolean evaluate(Polygon s) {
				return s.getId().equals("triangle");
			}
		});

		assertEquals(Arrays.asList(triangle), polygons);

		storagePool.getTransaction().close();

	}

	@Test
	public void testClear() {

		storagePool.getTransaction().begin();

		assertTrue(storagePool.contains(a));
		assertTrue(storagePool.contains(b));
		assertTrue(storagePool.contains(c));
		assertTrue(storagePool.contains(ab));
		assertTrue(storagePool.contains(bc));
		assertTrue(storagePool.contains(ca));
		assertTrue(storagePool.contains(cd));
		assertTrue(storagePool.contains(da));
		assertTrue(storagePool.contains(triangle));

		storagePool.clear();

		assertFalse(storagePool.contains(a));
		assertFalse(storagePool.contains(b));
		assertFalse(storagePool.contains(c));
		assertFalse(storagePool.contains(ab));
		assertFalse(storagePool.contains(bc));
		assertFalse(storagePool.contains(ca));
		assertFalse(storagePool.contains(cd));
		assertFalse(storagePool.contains(da));
		assertFalse(storagePool.contains(triangle));

		storagePool.getTransaction().close();

	}

	@Test
	public void testContainsString() {

		storagePool.getTransaction().begin();

		storagePool.delete(Point.class);
		storagePool.delete(Segment.class);
		storagePool.delete(Polygon.class);

		storagePool.getTransaction().commit();

		assertFalse(storagePool.contains(a));
		assertFalse(storagePool.contains(b));
		assertFalse(storagePool.contains(c));
		assertFalse(storagePool.contains(ab));
		assertFalse(storagePool.contains(bc));
		assertFalse(storagePool.contains(ca));
		assertFalse(storagePool.contains(cd));
		assertFalse(storagePool.contains(da));
		assertFalse(storagePool.contains(triangle));

		storagePool.insert(a);
		storagePool.insert(b);
		storagePool.insert(c);
		storagePool.insert(d);
		storagePool.insert(ab);
		storagePool.insert(bc);
		storagePool.insert(ca);
		storagePool.insert(cd);
		storagePool.insert(da);
		storagePool.insert(triangle);

		storagePool.getTransaction().commit();

		assertTrue(storagePool.contains(a));
		assertTrue(storagePool.contains(b));
		assertTrue(storagePool.contains(c));
		assertTrue(storagePool.contains(ab));
		assertTrue(storagePool.contains(bc));
		assertTrue(storagePool.contains(ca));
		assertTrue(storagePool.contains(cd));
		assertTrue(storagePool.contains(da));
		assertTrue(storagePool.contains(triangle));

		assertTrue(storagePool.contains("'" + Point.class.getName() + "'(Idp, X, Y)"));
		assertTrue(storagePool.contains("'" + Point.class.getName() + "'( a, 3,14 )"));
		assertTrue(storagePool.contains("'" + Point.class.getName() + "'( b, 3,14 )"));
		assertTrue(storagePool.contains("'" + Point.class.getName() + "'( c, 3,14 )"));

		assertTrue(storagePool.contains("'" + Segment.class.getName() + "'(Ids, Point0, Point1)"));
		assertTrue(storagePool.contains("'" + Segment.class.getName() + "'( ab, '" + Point.class.getName()
				+ "'( a, 3,14 ), '" + Point.class.getName() + "'( b, 3,14 ) )"));
		assertTrue(storagePool.contains("'" + Segment.class.getName() + "'( bc, '" + Point.class.getName()
				+ "'( b, 3,14 ), '" + Point.class.getName() + "'( c, 3,14 ) )"));
		assertTrue(storagePool.contains("'" + Segment.class.getName() + "'( ca, '" + Point.class.getName()
				+ "'( c, 3,14 ), '" + Point.class.getName() + "'( a, 3,14 ) )"));

		assertTrue(storagePool.contains("'" + Polygon.class.getName() + "'( triangle, Segment0, Segment1, Segment2 )"));
		assertTrue(storagePool.contains("'" + Polygon.class.getName() + "'( triangle, '" + Segment.class.getName()
				+ "'( ab, '" + Point.class.getName() + "'( a, 3,14 ), '" + Point.class.getName() + "'( b, 3,14 ) ), '"
				+ Segment.class.getName() + "'( bc, '" + Point.class.getName() + "'( b, 3,14 ), '"
				+ Point.class.getName() + "'( c, 3,14 ) ), '" + Segment.class.getName() + "'( ca, '"
				+ Point.class.getName() + "'( c, 3,14 ), '" + Point.class.getName() + "'( a, 3,14 ) ) )"));

		assertTrue(storagePool.contains(
				"'" + Segment.class.getName() + "'(Ids, Point0, Point1), '" + Point.class.getName() + "'(Idp, X, Y)"));

		storagePool.getTransaction().close();

	}

	@Test
	public void testContainsStringInt() {

		storagePool.getTransaction().begin();

		storagePool.delete(Point.class);
		storagePool.delete(Segment.class);
		storagePool.delete(Polygon.class);

		storagePool.getTransaction().commit();

		assertFalse(storagePool.contains(a));
		assertFalse(storagePool.contains(b));
		assertFalse(storagePool.contains(c));
		assertFalse(storagePool.contains(d));

		storagePool.insert(a, b, c, d);
		storagePool.insert(ab, bc, ca, cd, da);
		storagePool.insert(triangle, tetragon);

		storagePool.getTransaction().commit();

		assertTrue(storagePool.contains(a));
		assertTrue(storagePool.contains(b));
		assertTrue(storagePool.contains(c));
		assertTrue(storagePool.contains(d));

		PrologEngine engine = storagePool.getEngine();
		for (PrologClause prologClause : engine) {
			System.out.println(prologClause);
		}

		assertTrue(storagePool.contains(Point.class.getName(), 3));
		assertTrue(storagePool.contains(Segment.class.getName(), 3));
		assertTrue(storagePool.contains(Polygon.class.getName(), 4));

		storagePool.getTransaction().close();

	}

	@Test
	public void testContainsO() {

		storagePool.getTransaction().begin();

		storagePool.delete(Point.class);
		storagePool.delete(Segment.class);
		storagePool.delete(Polygon.class);

		storagePool.getTransaction().commit();

		assertFalse(storagePool.contains(a));
		assertFalse(storagePool.contains(b));
		assertFalse(storagePool.contains(c));
		assertFalse(storagePool.contains(ab));
		assertFalse(storagePool.contains(bc));
		assertFalse(storagePool.contains(ca));
		assertFalse(storagePool.contains(cd));
		assertFalse(storagePool.contains(da));
		assertFalse(storagePool.contains(triangle));

		storagePool.insert(a);
		storagePool.insert(b);
		storagePool.insert(c);
		storagePool.insert(d);
		storagePool.insert(ab);
		storagePool.insert(bc);
		storagePool.insert(ca);
		storagePool.insert(cd);
		storagePool.insert(da);
		storagePool.insert(triangle);

		storagePool.getTransaction().commit();

		assertTrue(storagePool.contains(a));
		assertTrue(storagePool.contains(b));
		assertTrue(storagePool.contains(c));
		assertTrue(storagePool.contains(ab));
		assertTrue(storagePool.contains(bc));
		assertTrue(storagePool.contains(ca));
		assertTrue(storagePool.contains(cd));
		assertTrue(storagePool.contains(da));
		assertTrue(storagePool.contains(triangle));

		assertTrue(storagePool.contains(a));
		assertTrue(storagePool.contains(b));
		assertTrue(storagePool.contains(c));
		assertTrue(storagePool.contains(ab));
		assertTrue(storagePool.contains(bc));
		assertTrue(storagePool.contains(ca));
		assertTrue(storagePool.contains(triangle));

		storagePool.getTransaction().close();

	}

	@Test
	public void testContainsClassOfO() {

		storagePool.getTransaction().begin();

		storagePool.delete(Point.class);
		storagePool.delete(Segment.class);
		storagePool.delete(Polygon.class);

		storagePool.getTransaction().commit();

		assertFalse(storagePool.contains(a));
		assertFalse(storagePool.contains(b));
		assertFalse(storagePool.contains(c));
		assertFalse(storagePool.contains(ab));
		assertFalse(storagePool.contains(bc));
		assertFalse(storagePool.contains(ca));
		assertFalse(storagePool.contains(cd));
		assertFalse(storagePool.contains(da));
		assertFalse(storagePool.contains(triangle));

		storagePool.insert(a);
		storagePool.insert(b);
		storagePool.insert(c);
		storagePool.insert(d);
		storagePool.insert(ab);
		storagePool.insert(bc);
		storagePool.insert(ca);
		storagePool.insert(cd);
		storagePool.insert(da);
		storagePool.insert(triangle);

		storagePool.getTransaction().commit();

		assertTrue(storagePool.contains(a));
		assertTrue(storagePool.contains(b));
		assertTrue(storagePool.contains(c));
		assertTrue(storagePool.contains(ab));
		assertTrue(storagePool.contains(bc));
		assertTrue(storagePool.contains(ca));
		assertTrue(storagePool.contains(cd));
		assertTrue(storagePool.contains(da));
		assertTrue(storagePool.contains(triangle));

		assertTrue(storagePool.contains(Point.class));
		assertTrue(storagePool.contains(Segment.class));
		assertTrue(storagePool.contains(Polygon.class));

		storagePool.getTransaction().close();

	}

	@Test
	public void testContainsPredicateOfO() {

		storagePool.getTransaction().begin();

		storagePool.delete(Point.class);
		storagePool.delete(Segment.class);
		storagePool.delete(Polygon.class);

		storagePool.getTransaction().commit();

		assertFalse(storagePool.contains(new Predicate<Point>() {

			public boolean evaluate(Point point) {
				return point.getIdp().equals("a");
			}

		}));

		assertFalse(storagePool.contains(new Predicate<Point>() {

			public boolean evaluate(Point point) {
				return point.getIdp().equals("b");
			}

		}));

		assertFalse(storagePool.contains(new Predicate<Point>() {

			public boolean evaluate(Point point) {
				return point.getIdp().equals("c");
			}

		}));

		assertFalse(storagePool.contains(new Predicate<Point>() {

			public boolean evaluate(Point point) {
				return point.getIdp().equals("d");
			}

		}));

		assertFalse(storagePool.contains(new Predicate<Segment>() {

			public boolean evaluate(Segment o) {
				return o.getIds().equals("ab");
			}

		}));

		assertFalse(storagePool.contains(new Predicate<Segment>() {

			public boolean evaluate(Segment o) {
				return o.getIds().equals("bc");
			}

		}));

		assertFalse(storagePool.contains(new Predicate<Segment>() {

			public boolean evaluate(Segment o) {
				return o.getIds().equals("ca");
			}

		}));

		assertFalse(storagePool.contains(new Predicate<Segment>() {

			public boolean evaluate(Segment o) {
				return o.getIds().equals("cd");
			}

		}));

		assertFalse(storagePool.contains(new Predicate<Segment>() {

			public boolean evaluate(Segment o) {
				return o.getIds().equals("da");
			}

		}));

		assertFalse(storagePool.contains(new Predicate<Polygon>() {

			public boolean evaluate(Polygon o) {
				return o.getId().equals("triangle");
			}

		}));

		storagePool.insert(a);
		storagePool.insert(b);
		storagePool.insert(c);
		storagePool.insert(d);
		storagePool.insert(ab);
		storagePool.insert(bc);
		storagePool.insert(ca);
		storagePool.insert(cd);
		storagePool.insert(da);
		storagePool.insert(triangle);

		storagePool.getTransaction().commit();

		assertTrue(storagePool.contains(new Predicate<Point>() {

			public boolean evaluate(Point point) {
				return point.getIdp().equals("a");
			}

		}));

		assertTrue(storagePool.contains(new Predicate<Point>() {

			public boolean evaluate(Point point) {
				return point.getIdp().equals("b");
			}

		}));

		assertTrue(storagePool.contains(new Predicate<Point>() {

			public boolean evaluate(Point point) {
				return point.getIdp().equals("c");
			}

		}));

		assertTrue(storagePool.contains(new Predicate<Point>() {

			public boolean evaluate(Point point) {
				return point.getIdp().equals("d");
			}

		}));

		assertTrue(storagePool.contains(new Predicate<Segment>() {

			public boolean evaluate(Segment o) {
				return o.getIds().equals("ab");
			}

		}));

		assertTrue(storagePool.contains(new Predicate<Segment>() {

			public boolean evaluate(Segment o) {
				return o.getIds().equals("bc");
			}

		}));

		assertTrue(storagePool.contains(new Predicate<Segment>() {

			public boolean evaluate(Segment o) {
				return o.getIds().equals("ca");
			}

		}));

		assertTrue(storagePool.contains(new Predicate<Segment>() {

			public boolean evaluate(Segment o) {
				return o.getIds().equals("cd");
			}

		}));

		assertTrue(storagePool.contains(new Predicate<Segment>() {

			public boolean evaluate(Segment o) {
				return o.getIds().equals("da");
			}

		}));

		assertTrue(storagePool.contains(new Predicate<Polygon>() {

			public boolean evaluate(Polygon o) {
				return o.getId().equals("triangle");
			}

		}));

		storagePool.getTransaction().close();

	}

	@Test
	public void testGetConverter() {
		assertEquals(ObjectConverterFactory.createConverter(PrologObjectConverter.class, provider),
				storagePool.getConverter());
	}

	@Test
	public void testGetProperties() {
		assertNotNull(storagePool.getProperties());
	}

	@Test
	public void testGetProvider() {
		assertEquals(provider, storagePool.getProvider());
	}

	@Test
	public void testGetEngine() {
		assertEquals(provider.newEngine(), storagePool.getEngine());
	}

	@Test
	public final void testClasses() {

		storagePool.getTransaction().begin();

		storagePool.insert(a);
		storagePool.insert(b);
		storagePool.insert(c);
		storagePool.insert(d);
		storagePool.insert(ab);
		storagePool.insert(bc);
		storagePool.insert(ca);
		storagePool.insert(cd);
		storagePool.insert(da);
		storagePool.insert(triangle);

		storagePool.getTransaction().commit();

		assertEquals(4, storagePool.classes().size());

		storagePool.getTransaction().close();

	}

}
