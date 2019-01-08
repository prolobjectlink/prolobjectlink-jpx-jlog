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
package org.logicware.db.prolog;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.junit.Test;
import org.logicware.db.prolog.PrologArrayList;

public class ArraySublistTest extends CollectionTest {

	private List<Integer> sublist = new PrologArrayList<Integer>(
			Arrays.asList(zero, one, two, three, four, five, six, seven, eight, nine)).subList(2, 8);

	@Test
	public final void testHashCode() {

		assertEquals(
				new PrologArrayList<Integer>(Arrays.asList(zero, one, two, three, four, five, six, seven, eight, nine))
						.subList(2, 8).hashCode(),
				sublist.hashCode());

	}

	@Test
	public final void testIsEmpty() {

		assertTrue(new PrologArrayList<Object>().subList(0, 0).isEmpty());
		assertFalse(sublist.isEmpty());

	}

	@Test
	public final void testToString() {
		assertEquals("[2, 3, 4, 5, 6, 7]", sublist.toString());
	}

	@Test
	public final void testEqualsObject() {
		assertEquals(
				new PrologArrayList<Integer>(Arrays.asList(zero, one, two, three, four, five, six, seven, eight, nine))
						.subList(2, 8),
				sublist);
	}

	@Test
	public final void testClear() {
		sublist.clear();
		// assertEquals(new PrologArrayList<Integer>().subList(0, 0), sublist);
		assertEquals(new PrologArrayList<Integer>().subList(0, 0).size(), sublist.size());
		assertEquals(0, sublist.size());
	}

	@Test
	public final void testSize() {
		assertEquals(0, new PrologArrayList<Object>().subList(0, 0).size());
		assertEquals(6, sublist.size());

	}

	@Test
	public final void testContains() {

		assertFalse(sublist.contains(zero));
		assertFalse(sublist.contains(one));
		assertTrue(sublist.contains(two));
		assertTrue(sublist.contains(three));
		assertTrue(sublist.contains(four));
		assertTrue(sublist.contains(five));
		assertTrue(sublist.contains(six));
		assertTrue(sublist.contains(seven));
		assertFalse(sublist.contains(eight));
		assertFalse(sublist.contains(nine));

		// sublist = new PrologArrayList<Integer>();
		// for (int i = 0; i < 10000; i++) {
		// list.add(i);
		// }
		//
		// for (int i = 0; i < 10000; i++) {
		// list.contains(i);
		// }

	}

	@Test
	public final void testContainsAll() {

		assertFalse(sublist.containsAll(

				Arrays.asList(

						zero, one, two, three, four, five, six, seven, eight, nine

				)

		));

		assertTrue(sublist.containsAll(

				Arrays.asList(

						two, three, four, five, six, seven

				)

		));

	}

	@Test
	public final void testRemove() {

		assertFalse(sublist.remove(zero));
		assertTrue(sublist.remove(two));
		assertTrue(sublist.remove(four));
		assertTrue(sublist.remove(six));
		assertFalse(sublist.remove(eight));

		assertArrayEquals(new Integer[] { three, five, seven }, sublist.toArray(new Integer[0]));

	}

	@Test
	public final void testRetainAll() {
		assertTrue(sublist.retainAll(Arrays.asList(three, five, seven)));
		assertArrayEquals(new Integer[] { three, five, seven }, sublist.toArray(new Integer[0]));

	}

	@Test
	public final void testRemoveAll() {

		assertTrue(sublist.removeAll(Arrays.asList(two, four, six)));
		assertArrayEquals(new Integer[] { three, five, seven }, sublist.toArray(new Integer[0]));

	}

	@Test
	public final void testIterator() {

		Iterator<Integer> iterator = sublist.iterator();

		int number = 2;
		for (; iterator.hasNext();) {
			Integer prologInteger = (Integer) iterator.next();
			assertEquals(new Integer(number++), prologInteger);
		}

	}

	@Test
	public final void testToArrayTArray() {
		assertArrayEquals(new Integer[] { two, three, four, five, six, seven }, sublist.toArray(new Integer[0]));
	}

	@Test
	public final void testToArray() {
		assertArrayEquals(new Integer[] { two, three, four, five, six, seven }, sublist.toArray());
	}

	@Test
	public final void testAddO() {
		// TODO
		PrologArrayList<Integer> integers = new PrologArrayList<Integer>();

		assertTrue(integers.add(one));
		assertArrayEquals(new Integer[] { one }, integers.toArray(new Integer[0]));

		assertTrue(integers.add(three));
		assertArrayEquals(new Integer[] { one, three }, integers.toArray(new Integer[0]));

		assertTrue(integers.add(five));
		assertArrayEquals(new Integer[] { one, three, five, }, integers.toArray(new Integer[0]));

		assertTrue(integers.add(seven));
		assertArrayEquals(new Integer[] { one, three, five, seven }, integers.toArray(new Integer[0]));

		assertTrue(integers.add(nine));
		assertArrayEquals(new Integer[] { one, three, five, seven, nine }, integers.toArray(new Integer[0]));

	}

	@Test
	public final void testAddAll() {

		List<Integer> integers = new PrologArrayList<Integer>().subList(0, 0);
		assertTrue(integers.addAll(sublist));
		// assertEquals(sublist, integers);
		assertEquals(6, integers.size());

	}

	@Test
	public final void testAddAllCollectionOfQextendsO() {

		List<Integer> integers = new PrologArrayList<Integer>().subList(0, 0);
		assertTrue(integers.addAll(0, Arrays.asList(two, three, four, five, six, seven)));
		assertEquals(6, integers.size());
		assertEquals(sublist.size(), integers.size());
		// assertEquals(sublist, integers);

	}

	@Test
	public final void testGet() {

		assertEquals(two, sublist.get(0));
		assertEquals(three, sublist.get(1));
		assertEquals(four, sublist.get(2));
		assertEquals(five, sublist.get(3));
		assertEquals(six, sublist.get(4));
		assertEquals(seven, sublist.get(5));

	}

	@Test
	public final void testSet() {

		List<Integer> integers = new PrologArrayList<Integer>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0)).subList(2,
				8);

		assertEquals(seven, integers.set(0, two));
		assertEquals(six, integers.set(1, three));
		assertEquals(five, integers.set(2, four));
		assertEquals(four, integers.set(3, five));
		assertEquals(three, integers.set(4, six));
		assertEquals(two, integers.set(5, seven));

		assertEquals(sublist.size(), integers.size());
		// assertEquals(sublist, integers);

	}

	@Test
	public final void testAddIntO() {

		List<Integer> integers = new PrologArrayList<Integer>().subList(0, 0);

		integers.add(0, two);
		integers.add(1, three);
		integers.add(2, four);
		integers.add(3, five);
		integers.add(4, six);
		integers.add(5, seven);

		assertEquals(sublist.size(), integers.size());
		// assertEquals(sublist, integers);

	}

	@Test
	public final void testAddAllIntCollectionOfQextendsO() {

		List<Integer> integers = new PrologArrayList<Integer>(Arrays.asList(two, three, four, five, six, seven))
				.subList(0, 0);
		assertTrue(integers.addAll(0, Arrays.asList(two, three, four, five, six, seven)));
		integers = new PrologArrayList<Integer>(Arrays.asList(two, three, six, seven)).subList(0, 4);
		assertTrue(integers.addAll(2, Arrays.asList(four, five)));
		assertEquals(sublist.size(), integers.size());
		// assertEquals(sublist, integers);

	}

	@Test
	public final void testRemoveInt() {

		assertEquals(two, sublist.remove(0));
		assertEquals(four, sublist.remove(1));
		assertEquals(six, sublist.remove(2));

		assertArrayEquals(new Integer[] { three, five, seven }, sublist.toArray(new Integer[0]));

	}

	@Test
	public final void testIndexOf() {

		PrologArrayList<Integer> list = new PrologArrayList<Integer>(

				Arrays.asList(

						one, two, three, four, nine, five, six, seven, eight, nine, five, one

				)

		);

		assertEquals(0, list.indexOf(one));
		assertEquals(5, list.indexOf(five));
		assertEquals(1, list.indexOf(two));
		assertEquals(4, list.indexOf(nine));

	}

	@Test
	public final void testLastIndexOf() {

		PrologArrayList<Integer> list = new PrologArrayList<Integer>(

				Arrays.asList(

						one, two, three, four, nine, five, six, seven, eight, nine, five, one

				)

		);

		assertEquals(0, list.indexOf(one));
		assertEquals(11, list.lastIndexOf(one));

		assertEquals(5, list.indexOf(five));
		assertEquals(10, list.lastIndexOf(five));

		assertEquals(4, list.indexOf(nine));
		assertEquals(9, list.lastIndexOf(nine));

	}

	@Test
	public final void testListIterator() {

		ListIterator<Integer> iterator = sublist.listIterator();

		int number = 2;
		for (; iterator.hasNext();) {
			Integer prologInteger = (Integer) iterator.next();
			assertEquals(new Integer(number++), prologInteger);
		}

	}

	@Test
	public final void testListIteratorInt() {

		ListIterator<Integer> iterator = sublist.listIterator(3);

		int number = 5;
		for (; iterator.hasNext();) {
			Integer prologInteger = (Integer) iterator.next();
			assertEquals(new Integer(number++), prologInteger);
		}
	}

	@Test
	public final void testSubList() {

		assertArrayEquals(new Integer[] { two, three, four, five, six, seven },
				sublist.subList(0, sublist.size()).toArray(new Integer[0]));
		assertArrayEquals(new Integer[] { four, five, six, seven }, sublist.subList(2, 6).toArray(new Integer[0]));
		assertArrayEquals(new Integer[] { four, five, }, sublist.subList(2, 4).toArray(new Integer[0]));
		assertEquals(6, sublist.subList(0, sublist.size()).size());
		assertEquals(4, sublist.subList(2, 6).size());
		assertEquals(2, sublist.subList(2, 4).size());

	}

}
