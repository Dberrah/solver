package com.bf.utilities;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UtilArgsTest {

	private static String args1[];
	private static String args2[];
	private static String args3[];

	@BeforeAll
	static void beforeAll() {
		args1 = new String[3];
		args2 = new String[1];
		args3 = new String[1];
		args1[0] = "-a";
		args1[1] = "aze";
		args1[2] = "-f";
		args2[0] = "-f";
		args3[0] = "--";
	}

	@Test
	public void testInvalidArg1() {
		assertThrows(IllegalArgumentException.class, () -> {
			UtilArgs.parseArgs(args1);
		});
	}

	@Test
	public void testInvalidArg2() {
		assertThrows(IllegalArgumentException.class, () -> {
			UtilArgs.parseArgs(args2);
		});
	}

	@Test
	public void testInvalidArg3() {
		assertThrows(IllegalArgumentException.class, () -> {
			UtilArgs.parseArgs(args3);
		});
	}

	@Test
	public void testInvalidArg4() {
		assertThrows(IllegalArgumentException.class, () -> {
			UtilArgs.parseArgs(args3);
		});
	}
}
