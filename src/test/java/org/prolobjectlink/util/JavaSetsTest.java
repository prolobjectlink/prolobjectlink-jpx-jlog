/*-
 * #%L
 * prolobjectlink-db-jlog
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
package org.prolobjectlink.util;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

import org.junit.Test;
import org.prolobjectlink.db.util.JavaSets;

public class JavaSetsTest {

	@Test
	public void testTreeSet() {
		assertEquals(new TreeSet<Object>(), JavaSets.treeSet());
	}

	@Test
	public void testTreeSetCollectionOfQextendsT() {
		assertEquals(new TreeSet<String>(), JavaSets.treeSet(new TreeSet<String>()));
	}

	@Test
	public void testHashSet() {
		assertEquals(new HashSet<Object>(), JavaSets.hashSet());
	}

	@Test
	public void testLinkedHashSet() {
		assertEquals(new LinkedHashSet<Object>(), JavaSets.linkedHashSet());
	}

	@Test
	public void testHashSetInt() {
		assertEquals(new HashSet<Object>(16), JavaSets.hashSet(16));
	}

	@Test
	public void testLinkedHashSetInt() {
		assertEquals(new LinkedHashSet<Object>(16), JavaSets.linkedHashSet(16));
	}

	@Test
	public void testHashSetCollectionOfQextendsT() {
		assertEquals(new HashSet<Object>(), JavaSets.hashSet(new HashSet<Object>()));
	}

}
