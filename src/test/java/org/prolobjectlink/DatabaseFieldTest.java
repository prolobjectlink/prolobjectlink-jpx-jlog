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
package org.prolobjectlink;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.prolobjectlink.db.DatabaseClass;
import org.prolobjectlink.db.DatabaseField;
import org.prolobjectlink.domain.model.Address;
import org.prolobjectlink.domain.model.link.PersonAddress;

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
