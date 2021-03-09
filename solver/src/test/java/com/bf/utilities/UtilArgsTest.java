package com.bf.utilities;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UtilArgsTest {

	private static String args1[];
	private static String args2[];
	private static String args3[];
	private static String args4[];
	private static String args[];
	private static String opts[];

	@BeforeAll
	static void beforeAll() {
		args = new String[3];
		opts = new String[3];
		args1 = new String[3];
		args2 = new String[1];
		args3 = new String[1];
		args4 = new String[6];
		args1[0] = "-a";
		args1[1] = "aze";
		args1[2] = "-f";
		args2[0] = "-f";
		args3[0] = "--";
		args4[0] = "-p";
		args4[1] = "SE-Cat";
		args4[2] = "-f";
		args4[3] = "solver/src/test/ressources/exemple_1.apx";
		args4[4] = "-fo";
		args4[5] = "apx";
		args[0] = "SE-Cat";
		args[1] = "solver/src/test/ressources/exemple_1.apx";
		args[2] = "apx";
		opts[0] = "-p";
		opts[1] = "-f";
		opts[2] = "-fo";
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

	@Test
	public void testArgsParsing() {
		UtilArgs.parseArgs(args4);
		for (int i = 0; i < UtilArgs.getArgsList().size(); i++) {
			assertTrue(UtilArgs.getArgsList().get(i).contentEquals(args[i]));
		}
		for (int i = 0; i < UtilArgs.getArgsList().size(); i++) {
			assertTrue(UtilArgs.getOptsList().get(i).contentEquals(opts[i]));
		}
	}

}
