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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Test;
import org.worklogic.db.prolog.PrologTreeSet;

public class TreeSetIterTest extends CollectionTest {

	private Iterator<Integer> iterator;
	private PrologTreeSet<Integer> expected = new PrologTreeSet<Integer>();
	private PrologTreeSet<Integer> actual = new PrologTreeSet<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));

	@Test
	public void testHasNext() {

		iterator = actual.iterator();
		assertTrue(iterator.hasNext());
		assertEquals(zero, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(one, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(two, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(three, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(four, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(five, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(six, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(seven, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(eight, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(nine, iterator.next());

		assertFalse(iterator.hasNext());

	}

	@Test
	public void testNext() {

		iterator = actual.iterator();
		assertTrue(iterator.hasNext());
		assertEquals(zero, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(one, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(two, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(three, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(four, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(five, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(six, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(seven, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(eight, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(nine, iterator.next());

		assertFalse(iterator.hasNext());

	}

	@Test
	public void testRemove() {

		iterator = actual.iterator();

		iterator.next();
		iterator.remove();

		iterator.next();
		iterator.remove();

		iterator.next();
		iterator.remove();

		iterator.next();
		iterator.remove();

		iterator.next();
		iterator.remove();

		iterator.next();
		iterator.remove();

		iterator.next();
		iterator.remove();

		iterator.next();
		iterator.remove();

		iterator.next();
		iterator.remove();

		iterator.next();
		iterator.remove();

		assertFalse(iterator.hasNext());
		assertEquals(expected, actual);

	}

}
