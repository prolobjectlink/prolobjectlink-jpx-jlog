/*
 * #%L
 * prolobjectlink-jpd
 * %%
 * Copyright (C) 2012 - 2017 WorkLogic Project
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
package org.worklogic.db.prolog;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.worklogic.db.prolog.PrologTreeSet;

public class TreeSetTest extends CollectionTest {

	private PrologTreeSet<Integer> set;

	@Before
	public void setUp() throws Exception {
		set = new PrologTreeSet<Integer>(Arrays.asList(5, 7, 3, 9, 1, 11, -1));
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public final void testHashCode() {
		assertEquals(set.hashCode(), new PrologTreeSet<Integer>(Arrays.asList(5, 7, 3, 9, 1, 11, -1)).hashCode());
	}

	@Test
	public final void testSize() {
		assertEquals(7, set.size());
	}

	@Test
	public final void testIterator() {

		PrologTreeSet<Integer> treeSet = new PrologTreeSet<Integer>();
		Iterator<Integer> i = treeSet.iterator();
		assertFalse(i.hasNext());

		i = set.iterator();
		assertTrue(i.hasNext());
		assertEquals(_one, i.next());
		assertTrue(i.hasNext());
		assertEquals(one, i.next());
		assertTrue(i.hasNext());
		assertEquals(three, i.next());
		assertTrue(i.hasNext());
		assertEquals(five, i.next());
		assertTrue(i.hasNext());
		assertEquals(seven, i.next());
		assertTrue(i.hasNext());
		assertEquals(nine, i.next());
		assertTrue(i.hasNext());
		assertEquals(eleven, i.next());
		assertFalse(i.hasNext());

	}

	@Test
	public final void testContains() {

		assertTrue(set.contains(_one));
		assertFalse(set.contains(zero));
		assertTrue(set.contains(one));
		assertFalse(set.contains(two));
		assertTrue(set.contains(three));
		assertFalse(set.contains(four));
		assertTrue(set.contains(five));
		assertFalse(set.contains(six));
		assertTrue(set.contains(seven));
		assertFalse(set.contains(eight));
		assertTrue(set.contains(nine));
		assertFalse(set.contains(ten));
		assertTrue(set.contains(eleven));

	}

	@Test
	public final void testEqualsObject() {
		assertEquals(set, new PrologTreeSet<Integer>(Arrays.asList(5, 7, 3, 9, 1, 11, -1)));
	}

	@Test
	public final void testToString() {
		assertEquals("[-1, 1, 3, 5, 7, 9, 11]", set.toString());
	}

	@Test
	public final void testAdd() {

		PrologTreeSet<Integer> treeSet = new PrologTreeSet<Integer>();

		assertTrue(treeSet.add(5));
		assertTrue(treeSet.add(7));
		assertTrue(treeSet.add(3));
		assertTrue(treeSet.add(9));
		assertTrue(treeSet.add(1));
		assertTrue(treeSet.add(11));
		assertTrue(treeSet.add(-1));

		// already contained
		assertFalse(treeSet.add(5));
		assertFalse(treeSet.add(7));
		assertFalse(treeSet.add(3));
		assertFalse(treeSet.add(9));
		assertFalse(treeSet.add(1));
		assertFalse(treeSet.add(11));
		assertFalse(treeSet.add(-1));

	}

	@Test
	public final void testRemove() {

		assertTrue(set.remove(9));
		assertTrue(set.remove(-1));

		assertArrayEquals(new Integer[] { 1, 3, 5, 7, 11 }, set.toArray(new Integer[0]));

		assertFalse(set.remove(9));
		assertFalse(set.remove(-1));

		assertTrue(set.remove(1));
		assertTrue(set.remove(3));
		assertTrue(set.remove(5));
		assertTrue(set.remove(7));
		assertTrue(set.remove(11));

		assertTrue(set.isEmpty());
		assertArrayEquals(new Integer[0], set.toArray(new Integer[0]));

	}

	@Test
	public final void testClear() {
		set.clear();
		assertTrue(set.isEmpty());
	}

	@Test
	public final void testIsEmpty() {
		PrologTreeSet<Integer> treeSet = new PrologTreeSet<Integer>();
		assertTrue(treeSet.isEmpty());
		assertFalse(set.isEmpty());
	}

	@Test
	public final void testToArray() {
		assertArrayEquals(new Integer[] { _one, one, three, five, seven, nine, eleven }, set.toArray());
	}

	@Test
	public final void testToArrayTArray() {
		assertArrayEquals(new Integer[] { _one, one, three, five, seven, nine, eleven }, set.toArray(new Integer[0]));
	}

	@Test
	public final void testContainsAll() {

		assertTrue(set.containsAll(

				Arrays.asList(

						_one, one, three, five, seven, nine, eleven

				)

		));

		assertFalse(set.containsAll(

				Arrays.asList(

						zero, one, two, three, four, five, six, seven, eight, nine

				)

		));

	}

	@Test
	public final void testAddAll() {
		Collection<Integer> integers = Arrays.asList(5, 7, 3, 9, 1, 11, -1);
		PrologTreeSet<Integer> integerSet = new PrologTreeSet<Integer>();
		integerSet.addAll(integers);
		assertEquals(set, integerSet);
	}

	@Test
	public final void testRetainAll() {

		assertTrue(set.retainAll(Arrays.asList(-1, 9)));
		assertArrayEquals(new Integer[] { -1, 9 }, set.toArray(new Integer[0]));

	}

	@Test
	public final void testRemoveAll() {

		assertTrue(set.removeAll(Arrays.asList(5, 7, 3, 1, 11)));
		assertArrayEquals(new Integer[] { -1, 9 }, set.toArray(new Integer[0]));

	}

}
