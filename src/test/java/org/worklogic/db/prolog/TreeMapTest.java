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
package org.worklogic.db.prolog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.junit.Test;
import org.worklogic.db.prolog.PrologTreeMap;

public class TreeMapTest extends CollectionTest {

	private PrologTreeMap<Integer, String> integerMap = new PrologTreeMap<Integer, String>();

	@Test
	public final void testHashCode() {

		assertEquals(integerMap.hashCode(), new PrologTreeMap<Integer, String>().hashCode());

		integerMap.put(zero, "zero");
		integerMap.put(one, "one");
		integerMap.put(two, "two");
		integerMap.put(three, "three");
		integerMap.put(four, "four");
		integerMap.put(five, "five");
		integerMap.put(six, "six");
		integerMap.put(seven, "seven");
		integerMap.put(eight, "eight");
		integerMap.put(nine, "nine");

		PrologTreeMap<Integer, String> integers = new PrologTreeMap<Integer, String>();
		integers.put(zero, "zero");
		integers.put(one, "one");
		integers.put(two, "two");
		integers.put(three, "three");
		integers.put(four, "four");
		integers.put(five, "five");
		integers.put(six, "six");
		integers.put(seven, "seven");
		integers.put(eight, "eight");
		integers.put(nine, "nine");

		assertEquals(integerMap.hashCode(), integers.hashCode());

	}

	@Test
	public final void testSize() {

		assertEquals(0, new HashMap<Integer, String>().size());

		integerMap.put(zero, "zero");
		assertEquals(1, integerMap.size());
		integerMap.put(one, "one");
		assertEquals(2, integerMap.size());
		integerMap.put(two, "two");
		assertEquals(3, integerMap.size());
		integerMap.put(three, "three");
		assertEquals(4, integerMap.size());
		integerMap.put(four, "four");
		assertEquals(5, integerMap.size());
		integerMap.put(five, "five");
		assertEquals(6, integerMap.size());
		integerMap.put(six, "six");
		assertEquals(7, integerMap.size());
		integerMap.put(seven, "seven");
		assertEquals(8, integerMap.size());
		integerMap.put(eight, "eight");
		assertEquals(9, integerMap.size());
		integerMap.put(nine, "nine");
		assertEquals(10, integerMap.size());

		// size is not affected
		integerMap.put(nine, "nine");
		assertEquals(10, integerMap.size());

	}

	@Test
	public final void testEqualsObject() {

		assertEquals(integerMap, new PrologTreeMap<Integer, String>());

		integerMap.put(zero, "zero");
		integerMap.put(one, "one");
		integerMap.put(two, "two");
		integerMap.put(three, "three");
		integerMap.put(four, "four");
		integerMap.put(five, "five");
		integerMap.put(six, "six");
		integerMap.put(seven, "seven");
		integerMap.put(eight, "eight");
		integerMap.put(nine, "nine");

		PrologTreeMap<Integer, String> integers = new PrologTreeMap<Integer, String>();
		integers.put(zero, "zero");
		integers.put(one, "one");
		integers.put(two, "two");
		integers.put(three, "three");
		integers.put(four, "four");
		integers.put(five, "five");
		integers.put(six, "six");
		integers.put(seven, "seven");
		integers.put(eight, "eight");
		integers.put(nine, "nine");

		assertEquals(integerMap, integers);

	}

	@Test
	public final void testPut() {

		HashMap<Integer, String> integers = new HashMap<Integer, String>();

		assertNull(integers.put(zero, "zero"));
		assertEquals(1, integers.size());

		assertNull(integers.put(one, "one"));
		assertEquals(2, integers.size());

		assertNull(integers.put(two, "two"));
		assertEquals(3, integers.size());

		assertNull(integers.put(three, "three"));
		assertEquals(4, integers.size());

		//

		assertEquals("zero", integers.put(zero, "zero"));
		assertEquals("one", integers.put(one, "one"));
		assertEquals("two", integers.put(two, "two"));
		assertEquals("three", integers.put(three, "three"));

		assertEquals(4, integers.size());
	}

	@Test
	public final void testRemove() {

		integerMap.put(zero, "zero");
		integerMap.put(one, "one");
		integerMap.put(two, "two");
		integerMap.put(three, "three");
		integerMap.put(four, "four");
		integerMap.put(five, "five");
		integerMap.put(six, "six");
		integerMap.put(seven, "seven");
		integerMap.put(eight, "eight");
		integerMap.put(nine, "nine");

		assertEquals("four", integerMap.remove(four));
		assertEquals("six", integerMap.remove(six));
		assertEquals("eight", integerMap.remove(eight));
		assertEquals("nine", integerMap.remove(nine));

	}

	@Test
	public final void testClear() {

		integerMap.put(zero, "zero");
		integerMap.put(one, "one");
		integerMap.put(two, "two");
		integerMap.put(three, "three");
		integerMap.put(four, "four");
		integerMap.put(five, "five");
		integerMap.put(six, "six");
		integerMap.put(seven, "seven");
		integerMap.put(eight, "eight");
		integerMap.put(nine, "nine");

		integerMap.clear();
		assertEquals(new PrologTreeMap<Integer, String>(), integerMap);
		assertEquals(0, integerMap.size());
	}

	@Test
	public final void testKeySet() {

		integerMap.put(zero, "zero");
		integerMap.put(one, "one");
		integerMap.put(two, "two");
		integerMap.put(three, "three");
		integerMap.put(four, "four");
		integerMap.put(five, "five");
		integerMap.put(six, "six");
		integerMap.put(seven, "seven");
		integerMap.put(eight, "eight");
		integerMap.put(nine, "nine");

		Set<Integer> integers = new java.util.HashSet<Integer>();
		for (Integer integer : integerMap.keySet()) {
			integers.add(integer);
		}
		assertTrue(integers.containsAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)));

	}

	@Test
	public final void testValues() {

		integerMap.put(zero, "zero");
		integerMap.put(one, "one");
		integerMap.put(two, "two");
		integerMap.put(three, "three");
		integerMap.put(four, "four");
		integerMap.put(five, "five");
		integerMap.put(six, "six");
		integerMap.put(seven, "seven");
		integerMap.put(eight, "eight");
		integerMap.put(nine, "nine");

		Collection<String> v = Arrays.asList(

				"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"

		);
		Collection<String> values = integerMap.values();
		assertTrue(values.containsAll(v));

	}

	@Test
	public final void testEntrySet() {

		integerMap.put(zero, "zero");
		integerMap.put(one, "one");
		integerMap.put(two, "two");
		integerMap.put(three, "three");
		integerMap.put(four, "four");
		integerMap.put(five, "five");
		integerMap.put(six, "six");
		integerMap.put(seven, "seven");
		integerMap.put(eight, "eight");
		integerMap.put(nine, "nine");

		Collection<?> k = Arrays.asList(

				new java.util.Map.Entry[] {

						new PrologTreeMap<Integer, String>(zero, "zero"),

						new PrologTreeMap<Integer, String>(one, "one"),

						new PrologTreeMap<Integer, String>(two, "two"),

						new PrologTreeMap<Integer, String>(three, "three"),

						new PrologTreeMap<Integer, String>(four, "four"),

						new PrologTreeMap<Integer, String>(five, "five"),

						new PrologTreeMap<Integer, String>(six, "six"),

						new PrologTreeMap<Integer, String>(seven, "seven"),

						new PrologTreeMap<Integer, String>(eight, "eight"),

						new PrologTreeMap<Integer, String>(nine, "nine")

				}

		);

		Set<java.util.Map.Entry<Integer, String>> integers = new java.util.HashSet<java.util.Map.Entry<Integer, String>>();
		for (java.util.Map.Entry<Integer, String> entry : integerMap.entrySet()) {
			integers.add(entry);
		}

		assertTrue(integers.containsAll(k));

	}

	@Test
	public final void testIsEmpty() {

		assertTrue(new HashMap<Integer, String>().isEmpty());

		integerMap.put(zero, "zero");
		integerMap.put(one, "one");
		integerMap.put(two, "two");
		integerMap.put(three, "three");
		integerMap.put(four, "four");
		integerMap.put(five, "five");
		integerMap.put(six, "six");
		integerMap.put(seven, "seven");
		integerMap.put(eight, "eight");
		integerMap.put(nine, "nine");

		assertFalse(integerMap.isEmpty());

	}

	@Test
	public final void testContainsKey() {

		integerMap.put(zero, "zero");
		integerMap.put(one, "one");
		integerMap.put(two, "two");
		integerMap.put(three, "three");
		integerMap.put(four, "four");
		integerMap.put(five, "five");
		integerMap.put(six, "six");
		integerMap.put(seven, "seven");
		integerMap.put(eight, "eight");
		integerMap.put(nine, "nine");

		assertTrue(integerMap.containsKey(zero));
		assertTrue(integerMap.containsKey(one));
		assertTrue(integerMap.containsKey(two));
		assertTrue(integerMap.containsKey(three));
		assertTrue(integerMap.containsKey(four));
		assertTrue(integerMap.containsKey(five));
		assertTrue(integerMap.containsKey(six));
		assertTrue(integerMap.containsKey(seven));
		assertTrue(integerMap.containsKey(eight));
		assertTrue(integerMap.containsKey(nine));

	}

	@Test
	public final void testContainsValue() {

		integerMap.put(zero, "zero");
		integerMap.put(one, "one");
		integerMap.put(two, "two");
		integerMap.put(three, "three");
		integerMap.put(four, "four");
		integerMap.put(five, "five");
		integerMap.put(six, "six");
		integerMap.put(seven, "seven");
		integerMap.put(eight, "eight");
		integerMap.put(nine, "nine");

		assertTrue(integerMap.containsValue("zero"));
		assertTrue(integerMap.containsValue("one"));
		assertTrue(integerMap.containsValue("two"));
		assertTrue(integerMap.containsValue("three"));
		assertTrue(integerMap.containsValue("four"));
		assertTrue(integerMap.containsValue("five"));
		assertTrue(integerMap.containsValue("six"));
		assertTrue(integerMap.containsValue("seven"));
		assertTrue(integerMap.containsValue("eight"));
		assertTrue(integerMap.containsValue("nine"));

	}

	@Test
	public final void testGet() {
		integerMap.put(zero, "zero");
		integerMap.put(one, "one");
		integerMap.put(two, "two");
		integerMap.put(three, "three");
		integerMap.put(four, "four");
		integerMap.put(five, "five");
		integerMap.put(six, "six");
		integerMap.put(seven, "seven");
		integerMap.put(eight, "eight");
		integerMap.put(nine, "nine");

		assertEquals("zero", integerMap.get(zero));
		assertEquals("one", integerMap.get(one));
		assertEquals("two", integerMap.get(two));
		assertEquals("three", integerMap.get(three));
		assertEquals("four", integerMap.get(four));
		assertEquals("five", integerMap.get(five));
		assertEquals("six", integerMap.get(six));
		assertEquals("seven", integerMap.get(seven));
		assertEquals("eight", integerMap.get(eight));
		assertEquals("nine", integerMap.get(nine));

	}

	@Test
	public final void testPutAll() {

		integerMap.put(zero, "zero");
		integerMap.put(one, "one");
		integerMap.put(two, "two");
		integerMap.put(three, "three");
		integerMap.put(four, "four");
		integerMap.put(five, "five");
		integerMap.put(six, "six");
		integerMap.put(seven, "seven");
		integerMap.put(eight, "eight");
		integerMap.put(nine, "nine");

		PrologTreeMap<Integer, String> integers = new PrologTreeMap<Integer, String>();
		integers.putAll(integerMap);
		assertEquals(integerMap, integers);

	}

	@Test
	public final void testToString() {

		assertEquals("[]", integerMap.toString());

		integerMap.put(zero, "zero");
		integerMap.put(one, "one");
		integerMap.put(two, "two");
		integerMap.put(three, "three");
		integerMap.put(four, "four");
		integerMap.put(five, "five");
		integerMap.put(six, "six");
		integerMap.put(seven, "seven");
		integerMap.put(eight, "eight");
		integerMap.put(nine, "nine");

		assertEquals("[0=zero, 1=one, 2=two, 3=three, 4=four, 5=five, 6=six, 7=seven, 8=eight, 9=nine]",
				integerMap.toString());

	}

}
