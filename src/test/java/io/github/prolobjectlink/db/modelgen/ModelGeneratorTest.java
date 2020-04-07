/*-
 * #%L
 * prolobjectlink-jpx-jlog
 * %%
 * Copyright (C) 2020 Prolobjectlink Project
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package io.github.prolobjectlink.db.modelgen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import io.github.prolobjectlink.BaseTest;
import io.github.prolobjectlink.db.DatabaseClass;
import io.github.prolobjectlink.db.StorageMode;
import io.github.prolobjectlink.db.modelgen.ModelGenerator;
import io.github.prolobjectlink.domain.model.Address;
import io.github.prolobjectlink.domain.model.Department;
import io.github.prolobjectlink.domain.model.Employee;
import io.github.prolobjectlink.web.application.WebApplication;

public class ModelGeneratorTest extends BaseTest {

	@Test
	public void testCreateSchema() {

		rschema = settings.createRelationalDatabase(StorageMode.STORAGE_POOL, "test", user).getSchema();

		DatabaseClass address = rschema.addClass("Address", "");
		address.addField("street", "", 0, String.class);
		address.addField("city", "", 1, String.class);
		address.addField("state", "", 2, String.class);
		address.addField("zip", "", 3, String.class);
		address.addField("country", "", 4, String.class);
//		rschema.addSequence("adddress_sequence", "", Address.class, 1);

		DatabaseClass person = rschema.addClass("Person", "");
		person.addField("id", "", 0, Integer.class, true);
		person.addField("firstName", "", 1, String.class);
		person.addField("middleName", "", 2, String.class);
		person.addField("lastName", "", 3, String.class);
		person.addField("address", "", 4, Address.class);
		person.addField("phones", "", 5, List.class, String.class);
		person.addField("emails", "", 6, Collection.class, String.class);
		person.addField("nickNames", "", 7, Set.class, String.class);
		person.addField("birthDate", "", 8, Date.class);
		person.addField("joinDate", "", 9, Date.class);
		person.addField("lastLoginDate", "", 10, Date.class);
		person.addField("loginCount", "", 11, Integer.class);
//		rschema.addSequence("person_sequence", "", Person.class, 1);

		DatabaseClass employee = rschema.addClass("Employee", "", person);
		employee.addField("salary", "", 0, Long.class);
		employee.addField("department", "", 1, Department.class);
//		rschema.addSequence("employee_sequence", "", Employee.class, 1);

		DatabaseClass department = rschema.addClass("Department", "");
		department.addField("id", "", 0, Integer.class, true);
		department.addField("name", "", 1, String.class);
		department.addField("employeesByCubicle", "", 2, Map.class, Employee.class);
//		rschema.addSequence("department_sequence", "", Department.class, 1);

		ModelGenerator rg = new ModelGenerator(rdb, WebApplication.ROOT + "/webpro/model.pl");
		assertEquals(4, rg.createSchema().countClasses());
//		assertEquals(rschema, rg.createSchema());
	}

	@Test
	public void testGenerateSchema() {

		ModelGenerator rg = new ModelGenerator(rdb, WebApplication.ROOT + "/webpro/model.pl");
		assertEquals(4, rg.createSchema().countClasses());
		assertFalse(rg.generateSchema().isEmpty());

	}

	@Test
	public void testCompileSchema() {

		ModelGenerator rg = new ModelGenerator(rdb, WebApplication.ROOT + "/webpro/model.pl");
		assertEquals(4, rg.createSchema().countClasses());
		System.out.println(rg.compileSchema());

	}

}
