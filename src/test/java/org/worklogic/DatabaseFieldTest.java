package org.worklogic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.worklogic.db.DatabaseClass;
import org.worklogic.db.DatabaseField;
import org.worklogic.domain.model.Address;
import org.worklogic.domain.model.link.PersonAddress;

public class DatabaseFieldTest extends BaseTest {

	@Test
	public void testGenerate() {
		StringBuilder builder = new StringBuilder();
		rdb.getSchema().addClass("Person", "").addField("name", "", 0, String.class).generateField(builder);
		assertEquals("\tprivate String name;\n", builder.toString());
	}

	@Test
	public void testCompareTo() {

		DatabaseField left = rdb.getSchema().addClass("Person", "").addField("name", "", 0, Integer.class);
		DatabaseField right = rdb.getSchema().addClass("Person", "").addField("name", "", 0, String.class);
		assertEquals(0, left.compareTo(right));

		left = rdb.getSchema().addClass("Person", "").addField("age", "", 1, Integer.class);
		right = rdb.getSchema().addClass("Person", "").addField("name", "", 0, String.class);
		assertEquals(1, left.compareTo(right));

	}

	@Test
	public void testGetName() {
		DatabaseField f = rdb.getSchema().addClass("Person", "").addField("name", "", 0, String.class);
		assertEquals("name", f.getName());
	}

	@Test
	public void testGetFullName() {
		DatabaseField f = rdb.getSchema().addClass("Person", "").addField("name", "", 0, String.class);
		assertEquals("Person.name", f.getFullName());
	}

	@Test
	public void testGetType() {

		DatabaseField f = rdb.getSchema().addClass("Person", "").addField("name", "", 0, String.class);
		assertEquals(String.class, f.getType());

	}

	@Test
	public void testSetType() {

		DatabaseField f = rdb.getSchema().addClass("Person", "").addField("name", "", 0, String.class);
		assertEquals(String.class, f.getType());
		f.setType(Integer.class);
		assertEquals(Integer.class, f.getType());

	}

	@Test
	public void testGetLinkedClass() {

		DatabaseField a = rdb.getSchema().addClass("Person", "").addField("address", "", 0, String.class);
		DatabaseClass pa = rdb.getSchema().addClass("PersonAddress", "");
		assertEquals(pa, a.setLinkedClass(pa).getLinkedClass());

	}

	@Test
	public void testSetLinkedClass() {

		DatabaseField a = rdb.getSchema().addClass("Person", "").addField("address", "", 0, Address.class);
		DatabaseClass pa = rdb.getSchema().addClass("PersonAddress", "");
		assertEquals(pa, a.setLinkedClass(pa).getLinkedClass());

	}

	@Test
	public void testGetLinkedType() {

		DatabaseField a = rdb.getSchema().addClass("Person", "").addField("address", "", 0, Address.class);
		assertEquals(PersonAddress.class, a.setLinkedType(PersonAddress.class).getLinkedType());

	}

	@Test
	public void testSetLinkedType() {

		DatabaseField a = rdb.getSchema().addClass("Person", "").addField("address", "", 0, Address.class);
		assertEquals(PersonAddress.class, a.setLinkedType(PersonAddress.class).getLinkedType());

	}

	@Test
	public void testIsNotNull() {

		DatabaseField f = rdb.getSchema().addClass("Person", "").addField("name", "", 0, String.class);
		assertTrue(f.setNotNull(true).isNotNull());

	}

	@Test
	public void testSetNotNull() {

		DatabaseField f = rdb.getSchema().addClass("Person", "").addField("name", "", 0, String.class);
		assertTrue(f.setNotNull(true).isNotNull());

	}

	@Test
	public void testGetMinValue() {

		DatabaseField f = rdb.getSchema().addClass("Person", "").addField("age", "", 0, Integer.class);
		assertEquals(1, f.setMinValue(1).getMinValue());

	}

	@Test
	public void testSetMinValue() {

		DatabaseField f = rdb.getSchema().addClass("Person", "").addField("age", "", 0, Integer.class);
		assertEquals(1, f.setMinValue(1).getMinValue());

	}

	@Test
	public void testGetMaxValue() {

		DatabaseField f = rdb.getSchema().addClass("Person", "").addField("age", "", 0, Integer.class);
		assertEquals(120, f.setMaxValue(120).getMaxValue());

	}

	@Test
	public void testSetMax() {

		DatabaseField f = rdb.getSchema().addClass("Person", "").addField("age", "", 0, Integer.class);
		assertEquals(120, f.setMaxValue(120).getMaxValue());

	}

	@Test
	public void testGetDefaultValue() {

		DatabaseField f = rdb.getSchema().addClass("Person", "").addField("age", "", 0, Integer.class);
		assertEquals(0, f.setDefaultValue(0).getDefaultValue());

	}

	@Test
	public void testSetDefaultValue() {

		DatabaseField f = rdb.getSchema().addClass("Person", "").addField("age", "", 0, Integer.class);
		assertEquals(0, f.setDefaultValue(0).getDefaultValue());

	}

}
