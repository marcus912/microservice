package com.evn.msf4j.eao;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	ServiceTester tester = new ServiceTester();
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTest(new ServiceTester("testServiceA"));
		suite.addTest(new ServiceTester("testServiceB"));
		suite.addTest(new ServiceTester("testSpringService"));
		return suite;
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		assertTrue(true);
	}
}
