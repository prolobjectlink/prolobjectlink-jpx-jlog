package org.worklogic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.worklogic.db.DatabaseSequence;
import org.worklogic.domain.model.Address;
import org.worklogic.domain.model.Person;

public class DatabaseSequenceTest extends BaseTest {

	@Test
	public void testGetName() {

		DatabaseSequence dbp = rdb.getSchema().addSequence("person_sequence", "", Person.class, 1);
		assertEquals("person_sequence", rdb.getSchema().getSequence("person_sequence").getName());
		assertEquals(dbp, rdb.getSchema().getSequence("person_sequence"));

		DatabaseSequence dba = rdb.getSchema().addSequence("address_sequence", "", Address.class, 1);
		assertEquals("address_sequence", rdb.getSchema().getSequence("address_sequence").getName());
		assertEquals(dba, rdb.getSchema().getSequence("address_sequence"));

	}

	@Test
	public void testGetIncrement() {

		DatabaseSequence dbp = rdb.getSchema().addSequence("person_sequence", "", Person.class, 1);
		assertEquals(1, rdb.getSchema().getSequence("person_sequence").getIncrement());
		assertEquals(dbp, rdb.getSchema().getSequence("person_sequence"));

		DatabaseSequence dba = rdb.getSchema().addSequence("address_sequence", "", Address.class, 1);
		assertEquals(1, rdb.getSchema().getSequence("address_sequence").getIncrement());
		assertEquals(dba, rdb.getSchema().getSequence("address_sequence"));

	}

	@Test
	public void testGetValue() {

		DatabaseSequence dbp = rdb.getSchema().addSequence("person_sequence", "", Person.class, 1);
		assertEquals(0, rdb.getSchema().getSequence("person_sequence").getValue());
		assertEquals(dbp, rdb.getSchema().getSequence("person_sequence"));
		dbp.setValue(20);
		assertEquals(20, rdb.getSchema().getSequence("person_sequence").getValue());

	}

	@Test
	public void testSetValue() {

		DatabaseSequence dbp = rdb.getSchema().addSequence("person_sequence", "", Person.class, 1);
		assertEquals(0, rdb.getSchema().getSequence("person_sequence").getValue());
		assertEquals(dbp, rdb.getSchema().getSequence("person_sequence"));
		dbp.setValue(20);
		assertEquals(20, rdb.getSchema().getSequence("person_sequence").getValue());

	}

	@Test
	public void testGetSchema() {
		assertNotNull(rdb.getSchema().addSequence("address_sequence", "", Address.class, 1).getSchema());
	}

	@Test
	public void testToString() {
		assertEquals("address_sequence",
				rdb.getSchema().addSequence("address_sequence", "", Address.class, 1).toString());
	}

	@Test
	public void testHashCode() {
		assertEquals(rdb.getSchema().addSequence("address_sequence", "", Address.class, 1).hashCode(),
				rdb.getSchema().addSequence("address_sequence", "", Address.class, 1).hashCode());
		assertTrue(rdb.getSchema().addSequence("address_sequence", "", Address.class, 1).hashCode() != rdb.getSchema()
				.addSequence("address_sequence", "", Address.class, 2).hashCode());
		assertTrue(rdb.getSchema().addSequence("address_sequence", "", Address.class, 1).hashCode() != rdb.getSchema()
				.addSequence("other_address_sequence", "", Address.class, 1).hashCode());
		assertTrue(rdb.getSchema().addSequence("address_sequence", "", Address.class, 1).hashCode() != rdb.getSchema()
				.addSequence(null, "", Address.class, 2).hashCode());
	}

	@Test
	public void testEquals() {

		DatabaseSequence s = rdb.getSchema().addSequence("address_sequence", "", Address.class, 1);
		assertEquals(s, s);

		s = rdb.getSchema().addSequence("address_sequence", "", Address.class, 1);
		assertFalse(s.equals(null));

		assertEquals(rdb.getSchema().addSequence("address_sequence", "", Address.class, 1),
				rdb.getSchema().addSequence("address_sequence", "", Address.class, 1));

		assertFalse(rdb.getSchema().addSequence("address_sequence", "", Address.class, 1)
				.equals(rdb.getSchema().addSequence(null, "", Address.class, 2)));

		assertFalse(rdb.getSchema().addSequence("address_sequence", "", Address.class, 1)
				.equals(rdb.getSchema().addSequence(null, "", Address.class, 0)));

		assertFalse(rdb.getSchema().addSequence("address_sequence", "", Address.class, 1)
				.equals(rdb.getSchema().addSequence("address_sequence", "", Address.class, 2)));

		assertFalse(rdb.getSchema().addSequence("address_sequence", "", Address.class, 1).equals(new Object()));

		assertFalse(rdb.getSchema().addSequence(null, "", Address.class, 1)
				.equals(rdb.getSchema().addSequence(null, "", Address.class, 2)));

	}

}
