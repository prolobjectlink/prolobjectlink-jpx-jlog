package org.prolobjectlink.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.prolobjectlink.BaseTest;
import org.prolobjectlink.db.util.JavaLists;

public class JavaListsTest extends BaseTest {

	@Test
	public void testArrayList() {
		assertEquals(new ArrayList<Object>(), JavaLists.arrayList());
	}

	@Test
	public void testArrayListInt() {
		assertEquals(new ArrayList<Object>(10), JavaLists.arrayList(10));
	}

	@Test
	public void testArrayListCollectionOfT() {
		assertEquals(new ArrayList<Object>(Arrays.asList(1, 2, 3, 4, 5)),
				JavaLists.arrayList(Arrays.asList(1, 2, 3, 4, 5)));
	}

}
