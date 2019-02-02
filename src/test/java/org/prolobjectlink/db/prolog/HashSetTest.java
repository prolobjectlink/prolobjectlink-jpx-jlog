/*
 * #%L
 * prolobjectlink-jpd
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package org.prolobjectlink.db.prolog;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.prolobjectlink.db.prolog.PrologHashSet;

public class HashSetTest extends CollectionTest {

	private PrologHashSet<Integer> integerSet = new PrologHashSet<Integer>();

	@Test
	public final void testSize() {

		assertEquals(0, new PrologHashSet<Integer>().size());

		integerSet.add(zero);
		assertEquals(1, integerSet.size());
		integerSet.add(one);
		assertEquals(2, integerSet.size());
		integerSet.add(two);
		assertEquals(3, integerSet.size());
		integerSet.add(three);
		assertEquals(4, integerSet.size());
		integerSet.add(four);
		assertEquals(5, integerSet.size());
		integerSet.add(five);
		assertEquals(6, integerSet.size());
		integerSet.add(six);
		assertEquals(7, integerSet.size());
		integerSet.add(seven);
		assertEquals(8, integerSet.size());
		integerSet.add(eight);
		assertEquals(9, integerSet.size());
		integerSet.add(nine);
		assertEquals(10, integerSet.size());

		// size is not affected
		integerSet.add(nine);
		assertEquals(10, integerSet.size());

	}

	@Test
	public final void testIterator() {
		PrologHashSet<Integer> list = new PrologHashSet<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
		Iterator<Integer> iterator = list.iterator();
		assertNotNull(iterator);

		int number = 0;
		for (; iterator.hasNext();) {
			Integer integer = (Integer) iterator.next();
			assertEquals(new Integer(number++), integer);
		}
	}

	@Test
	public final void testAdd() {

		PrologHashSet<Integer> integers = new PrologHashSet<Integer>();

		assertTrue(integers.add(zero));
		assertEquals(1, integers.size());

		assertTrue(integers.add(one));
		assertEquals(2, integers.size());

		assertTrue(integers.add(two));
		assertEquals(3, integers.size());

		assertTrue(integers.add(three));
		assertEquals(4, integers.size());

		//

		assertFalse(integers.add(zero));
		assertFalse(integers.add(one));
		assertFalse(integers.add(two));
		assertFalse(integers.add(three));

		assertEquals(4, integers.size());

	}

	@Test
	public final void testRemove() {

		integerSet = new PrologHashSet<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));

		assertTrue(integerSet.remove(four));
		assertTrue(integerSet.remove(six));
		assertTrue(integerSet.remove(eight));
		assertTrue(integerSet.remove(nine));

	}

	@Test
	public final void testClear() {

		integerSet.add(zero);
		integerSet.add(one);
		integerSet.add(two);
		integerSet.add(three);
		integerSet.add(four);
		integerSet.add(five);
		integerSet.add(six);
		integerSet.add(seven);
		integerSet.add(eight);
		integerSet.add(nine);

		integerSet.clear();
		assertEquals(new PrologHashSet<Integer>(), integerSet);
		assertEquals(0, integerSet.size());

	}

	@Test
	public final void testIsEmpty() {
		assertTrue(new HashMap<Integer, String>().isEmpty());

		integerSet.add(zero);
		integerSet.add(one);
		integerSet.add(two);
		integerSet.add(three);
		integerSet.add(four);
		integerSet.add(five);
		integerSet.add(six);
		integerSet.add(seven);
		integerSet.add(eight);
		integerSet.add(nine);

		assertFalse(integerSet.isEmpty());
	}

	@Test
	public final void testToArray() {
		PrologHashSet<Integer> actual = new PrologHashSet<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
		assertArrayEquals(new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }, actual.toArray());
	}

	@Test
	public final void testToArrayTArray() {
		PrologHashSet<Integer> actual = new PrologHashSet<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
		assertArrayEquals(new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }, actual.toArray(new Integer[0]));
	}

	@Test
	public final void testContains() {

		integerSet.add(zero);
		integerSet.add(one);
		integerSet.add(two);
		integerSet.add(three);
		integerSet.add(four);
		integerSet.add(five);
		integerSet.add(six);
		integerSet.add(seven);
		integerSet.add(eight);
		integerSet.add(nine);

		integerSet.add(10);
		integerSet.add(11);
		integerSet.add(12);
		integerSet.add(13);
		integerSet.add(14);
		integerSet.add(15);
		integerSet.add(16);
		integerSet.add(17);

		assertTrue(integerSet.contains(zero));
		assertTrue(integerSet.contains(one));
		assertTrue(integerSet.contains(two));
		assertTrue(integerSet.contains(three));
		assertTrue(integerSet.contains(four));
		assertTrue(integerSet.contains(five));
		assertTrue(integerSet.contains(six));
		assertTrue(integerSet.contains(seven));
		assertTrue(integerSet.contains(eight));
		assertTrue(integerSet.contains(nine));

	}

	@Test
	public final void testContainsAll() {

		PrologHashSet<Integer> actual = new PrologHashSet<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
		List<Integer> list = Arrays.asList(zero, one, two, three, four, five, six, seven, eight, nine);
		assertTrue(actual.containsAll(Arrays.asList(5, 6, 7, 8, 9)));
		assertTrue(actual.containsAll(list));

	}

	@Test
	public final void testAddAll() {

		integerSet.add(zero);
		integerSet.add(one);
		integerSet.add(two);
		integerSet.add(three);
		integerSet.add(four);
		integerSet.add(five);
		integerSet.add(six);
		integerSet.add(seven);
		integerSet.add(eight);
		integerSet.add(nine);

		PrologHashSet<Integer> integers = new PrologHashSet<Integer>();
		assertTrue(integers.addAll(integerSet));
		assertEquals(integerSet, integers);

	}

	@Test
	public final void testRetainAll() {

		integerSet.add(zero);
		integerSet.add(one);
		integerSet.add(two);
		integerSet.add(three);
		integerSet.add(four);
		integerSet.add(five);
		integerSet.add(six);
		integerSet.add(seven);
		integerSet.add(eight);
		integerSet.add(nine);

		assertTrue(integerSet.retainAll(Arrays.asList(one, three, five, seven, nine)));
		assertArrayEquals(new Integer[] { one, three, five, seven, nine }, integerSet.toArray());
		assertFalse(integerSet.retainAll(Arrays.asList(one, three, five, seven, nine)));

	}

	@Test
	public final void testRemoveAll() {

		integerSet.add(zero);
		integerSet.add(one);
		integerSet.add(two);
		integerSet.add(three);
		integerSet.add(four);
		integerSet.add(five);
		integerSet.add(six);
		integerSet.add(seven);
		integerSet.add(eight);
		integerSet.add(nine);

		assertTrue(integerSet.removeAll(Arrays.asList(zero, two, four, six, eight)));
		assertArrayEquals(new Integer[] { one, three, five, seven, nine }, integerSet.toArray());
		assertFalse(integerSet.removeAll(Arrays.asList(zero, two, four, six, eight)));

	}

}
