package ca.ualberta.cmput291TeamNYC.project1.test;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.ualberta.cmput291TeamNYC.project1.data.TestRecord;

public class TestRecordTest {

	@Test
	public void test() {
		TestRecord testRecord = new TestRecord();
		assertEquals(testRecord.foo, "foo");
	}

}
