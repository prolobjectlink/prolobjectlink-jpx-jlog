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
