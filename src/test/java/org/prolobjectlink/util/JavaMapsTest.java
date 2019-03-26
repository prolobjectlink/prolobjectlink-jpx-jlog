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
package org.prolobjectlink.util;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import org.junit.Test;
import org.prolobjectlink.db.util.JavaMaps;

public class JavaMapsTest {

	@Test
	public void testTreeMap() {
		assertEquals(new TreeMap<Object, Object>(), JavaMaps.treeMap());
	}

	@Test
	public void testTreeMapMapOfQextendsKQextendsV() {
		assertEquals(new TreeMap<String, Object>(), JavaMaps.treeMap(new TreeMap<String, Object>()));
	}

	@Test
	public void testHashMap() {
		assertEquals(new HashMap<Object, Object>(), JavaMaps.hashMap());
	}

	@Test
	public void testHashMapInt() {
		assertEquals(new HashMap<Object, Object>(16), JavaMaps.hashMap(16));
	}

	@Test
	public void testHashMapMapOfQextendsKQextendsV() {
		assertEquals(new HashMap<Object, Object>(), JavaMaps.hashMap(new HashMap<Object, Object>()));
	}

	@Test
	public void testLinkedHashMap() {
		assertEquals(new LinkedHashMap<Object, Object>(), JavaMaps.linkedHashMap());
	}

	@Test
	public void testLinkedHashMapInt() {
		assertEquals(new LinkedHashMap<Object, Object>(16), JavaMaps.linkedHashMap(16));
	}

	@Test
	public void testLinkedHashMapMapOfQextendsKQextendsV() {
		assertEquals(new LinkedHashMap<Object, Object>(), JavaMaps.linkedHashMap(new LinkedHashMap<Object, Object>()));
	}

}
