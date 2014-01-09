package ece351.util;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;

import ece351.TestImports;
import ece351.TestPrelab;

public class BaseTest351 {

	@BeforeClass
	public static void sanity() {
		assertTrue(TestPrelab.areAssertionsEnabled());
		assertTrue(TestImports.check());
	}
	
}
