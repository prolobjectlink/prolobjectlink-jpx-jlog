package org.worklogic.util;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import org.junit.Test;
import org.worklogic.db.util.JavaMaps;

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
