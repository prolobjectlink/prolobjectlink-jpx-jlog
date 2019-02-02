package org.prolobjectlink.db.prolog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;
import org.prolobjectlink.BaseTest;
import org.prolobjectlink.db.prolog.PrologArrayList;
import org.prolobjectlink.db.prolog.PrologDate;
import org.prolobjectlink.db.prolog.PrologHashMap;
import org.prolobjectlink.db.prolog.PrologHashSet;
import org.prolobjectlink.db.prolog.PrologTreeMap;
import org.prolobjectlink.db.prolog.PrologTreeSet;
import org.prolobjectlink.domain.classes.MonsEnum;

public class PersistenceTest extends BaseTest {

	protected Integer zero = new Integer(0);
	protected Integer one = new Integer(1);
	protected Integer two = new Integer(2);
	protected Integer three = new Integer(3);
	protected Integer four = new Integer(4);
	protected Integer five = new Integer(5);
	protected Integer six = new Integer(6);
	protected Integer seven = new Integer(7);
	protected Integer eight = new Integer(8);
	protected Integer nine = new Integer(9);
	protected Integer ten = new Integer(10);

	@Test
	public final void testDate() {

		// date time

		storage.getTransaction().begin();
		storage.insert(new PrologDate(0));
		assertTrue(storage.contains(new PrologDate(0)));
		assertEquals(new PrologDate(0), storage.find(new PrologDate(0)));
		storage.delete(new PrologDate(0));
		assertFalse(storage.contains(new PrologDate(0)));
		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

	@Test
	public final void testUUID() {

		// java util uuid

		UUID uuid = UUID.randomUUID();

		storage.getTransaction().begin();
		storage.insert(uuid);
		assertTrue(storage.contains(uuid));
		assertEquals(uuid, storage.find(uuid));
		storage.delete(uuid);
		assertFalse(storage.contains(uuid));
		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

	@Test
	public final void testEnum() {

		// java util enum

		storage.getTransaction().begin();
		storage.insert(MonsEnum.JAN);
		assertTrue(storage.contains(MonsEnum.JAN));
		assertEquals(MonsEnum.JAN, storage.find(MonsEnum.JAN));
		storage.delete(MonsEnum.JAN);
		assertFalse(storage.contains(MonsEnum.JAN));
		storage.insert(MonsEnum.FEB);
		assertTrue(storage.contains(MonsEnum.FEB));
		assertEquals(MonsEnum.FEB, storage.find(MonsEnum.FEB));
		storage.delete(MonsEnum.FEB);
		assertFalse(storage.contains(MonsEnum.FEB));
		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

	@Test
	public final void testEntry() {

		// entry

		storage.getTransaction().begin();
		storage.insert(new PrologHashMap<Integer, String>().new HashEntry<String, String>("key", "value"));

		assertTrue(
				storage.contains(new PrologHashMap<Integer, String>().new HashEntry<String, String>("key", "value")));
		assertEquals(new PrologHashMap<Integer, String>().new HashEntry<String, String>("key", "value"),
				storage.find(new PrologHashMap<Integer, String>().new HashEntry<String, String>("key", "value")));
		storage.delete(new PrologHashMap<Integer, String>().new HashEntry<String, String>("key", "value"));
		assertFalse(
				storage.contains(new PrologHashMap<Integer, String>().new HashEntry<String, String>("key", "value")));
		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

	@Test
	public final void testArrayList() {

		// arrays
		PrologArrayList<Integer> integerList = new PrologArrayList<Integer>(
				Arrays.asList(zero, one, two, three, four, five, six, seven, eight, nine));

		storage.getTransaction().begin();
		storage.insert(integerList);
		assertTrue(storage.contains(integerList));
		assertEquals(integerList, storage.find(integerList));
		storage.delete(integerList);
		assertFalse(storage.contains(integerList));
		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

	@Test
	public final void testTreeMap() {

		storage.getTransaction().begin();
		storage.insert(new PrologTreeMap<Integer, Integer>());
		assertTrue(storage.contains(new PrologTreeMap<Integer, Integer>()));
		assertEquals(new PrologTreeMap<Integer, Integer>(), storage.find(new PrologTreeMap<Integer, Integer>()));
		storage.delete(new PrologTreeMap<Integer, Integer>());
		assertFalse(storage.contains(new PrologTreeMap<Integer, Integer>()));
		storage.getTransaction().commit();
		storage.getTransaction().close();

		PrologTreeMap<Integer, String> treeMap = new PrologTreeMap<Integer, String>();
		treeMap.put(7, "seven");
		treeMap.put(7, "seven");
		treeMap.put(5, "five");
		treeMap.put(3, "three");
		treeMap.put(2, "two");
		treeMap.put(1, "one");

		// for (int i = 0; i < 1000; i++) {
		// treeMap.put(i, "" + i + "");
		// // System.out.println(treeMap);
		// }
		//
		// System.out.println(treeMap);

		storage.getTransaction().begin();
		storage.insert(treeMap);
		assertTrue(storage.contains(treeMap));
		assertEquals(treeMap, storage.find(treeMap));
		storage.delete(treeMap);
		assertFalse(storage.contains(treeMap));
		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

	@Test
	public final void testTreeSet() {

		storage.getTransaction().begin();
		storage.insert(new PrologTreeSet<Integer>());
		assertTrue(storage.contains(new PrologTreeSet<Integer>()));
		assertEquals(new PrologTreeSet<Integer>(), storage.find(new PrologTreeSet<Integer>()));
		storage.delete(new PrologTreeSet<Integer>());
		assertFalse(storage.contains(new PrologTreeSet<Integer>()));
		storage.getTransaction().commit();
		storage.getTransaction().close();

		PrologTreeSet<Integer> treeSet = new PrologTreeSet<Integer>();
		treeSet.add(7);
		treeSet.add(7);
		treeSet.add(5);
		treeSet.add(3);
		treeSet.add(2);
		treeSet.add(1);

		// for (int i = 0; i < 1000; i++) {
		// treeMap.put(i, "" + i + "");
		// // System.out.println(treeMap);
		// }
		//
		// System.out.println(treeMap);

		storage.getTransaction().begin();
		storage.insert(treeSet);
		assertTrue(storage.contains(treeSet));
		assertEquals(treeSet, storage.find(treeSet));
		storage.delete(treeSet);
		assertFalse(storage.contains(treeSet));
		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

	@Test
	public final void testHashMap() {

		storage.getTransaction().begin();
		storage.insert(new PrologHashMap<Integer, Integer>());
		assertTrue(storage.contains(new PrologHashMap<Integer, Integer>()));
		assertEquals(new PrologHashMap<Integer, Integer>(), storage.find(new PrologHashMap<Integer, Integer>()));
		storage.delete(new PrologHashMap<Integer, Integer>());
		assertFalse(storage.contains(new PrologHashMap<Integer, Integer>()));
		storage.getTransaction().commit();
		storage.getTransaction().close();

		PrologHashMap<Integer, String> hashMap = new PrologHashMap<Integer, String>();
		hashMap.put(7, "seven");
		hashMap.put(7, "seven");
		hashMap.put(5, "five");
		hashMap.put(3, "three");
		hashMap.put(2, "two");
		hashMap.put(1, "one");

		storage.getTransaction().begin();
		storage.insert(hashMap);
		assertTrue(storage.contains(hashMap));
		assertEquals(hashMap, storage.find(hashMap));
		storage.delete(hashMap);
		assertFalse(storage.contains(hashMap));
		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

	@Test
	public final void testHashSet() {

		storage.getTransaction().begin();
		storage.insert(new PrologHashSet<Integer>());
		assertTrue(storage.contains(new PrologHashSet<Integer>()));
		assertEquals(new PrologHashSet<Integer>(), storage.find(new PrologHashSet<Integer>()));
		storage.delete(new PrologHashSet<Integer>());
		assertFalse(storage.contains(new PrologHashSet<Integer>()));
		storage.getTransaction().commit();
		storage.getTransaction().close();

		PrologHashSet<Integer> hashSet = new PrologHashSet<Integer>();
		hashSet.add(7);
		hashSet.add(7);
		hashSet.add(5);
		hashSet.add(3);
		hashSet.add(2);
		hashSet.add(1);

		storage.getTransaction().begin();
		storage.insert(hashSet);
		assertTrue(storage.contains(hashSet));
		assertEquals(hashSet, storage.find(hashSet));
		storage.delete(hashSet);
		assertFalse(storage.contains(hashSet));
		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

}
