package com.bf.utilities;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UtilAFTest {

	private static List<ArrayList<Integer>> actualAF;
	private static List<ArrayList<Integer>> expectedAF;
	private static List<ArrayList<Integer>> testAF;
	private static String args1[];
	private static String args2[];
	private static String args3[];

	@BeforeAll
	static void beforeAll(){
		actualAF = new ArrayList<ArrayList<Integer>>();
		expectedAF = new ArrayList<ArrayList<Integer>>();
		testAF = new ArrayList<ArrayList<Integer>>();
		testAF.add(new ArrayList<Integer>(Arrays.asList(null, 1, null)));
		testAF.add(new ArrayList<Integer>(Arrays.asList(null, null, 1)));
		testAF.add(new ArrayList<Integer>(Arrays.asList(null, null, null)));
		expectedAF.add(new ArrayList<Integer>(Arrays.asList(null, 1, null, null, null, null, null, null, null, null)));
		expectedAF.add(new ArrayList<Integer>(Arrays.asList(null, null, 1, null, null, 1, null, null, null, null)));
		expectedAF.add(new ArrayList<Integer>(Arrays.asList(null, null, null, null, null, null, null, null, null, null)));
		expectedAF.add(new ArrayList<Integer>(Arrays.asList(null, null, null, null, null, 1, 1, null, null, null)));
		expectedAF.add(new ArrayList<Integer>(Arrays.asList(null, null, null, 1, null, null, null, 1, 1, null)));
		expectedAF.add(new ArrayList<Integer>(Arrays.asList(null, null, null, null, null, null, null, null, null, null)));
		expectedAF.add(new ArrayList<Integer>(Arrays.asList(null, null, null, null, null, null, null, null, null, null)));
		expectedAF.add(new ArrayList<Integer>(Arrays.asList(null, null, null, null, null, null, 1, null, null, null)));
		expectedAF.add(new ArrayList<Integer>(Arrays.asList(null, null, null, null, null, null, null, null, null, null)));
		expectedAF.add(new ArrayList<Integer>(Arrays.asList(null, null, null, null, null, null, null, null, 1, null)));
		args1 = new String[6];
		args2 = new String[6];
		args3 = new String[6];
		args1[0] = "-p";
		args1[1] = "SE-Cat";
		args1[2] = "-f";
		args1[3] = "src/test/ressources/exemple_1.apx";
		args1[4] = "-fo";
		args1[5] = "apx";
		args2[0] = "-p";
		args2[1] = "SE-Cat";
		args2[2] = "-f";
		args2[3] = "src/test/ressources/exemple_1.tgf";
		args2[4] = "-fo";
		args2[5] = "tgf";
		args3[0] = "-p";
		args3[1] = "SE-Cat";
		args3[2] = "-f";
		args3[3] = "test.apx";
		args3[4] = "-fo";
		args3[5] = "apx";
	}
	
	@Test
	void testParseRankingApx1() {
		UtilArgs.parseArgs(args1);
		actualAF = UtilAF.parseRankingAF();
		for (int i = 0; i < actualAF.size(); i++) {
			for (int j = 0; j < actualAF.get(i).size(); j++) {
				assertTrue(actualAF.get(i).get(j) == expectedAF.get(i).get(j));
			}
		}
	}

	@Test
	void testParseRankingApx2() {
		UtilArgs.parseArgs(args2);
		actualAF = UtilAF.parseRankingAF();
		for (int i = 0; i < actualAF.size(); i++) {
			for (int j = 0; j < actualAF.get(i).size(); j++) {
				assertTrue(actualAF.get(i).get(j) == expectedAF.get(i).get(j));
			}
		}
	}

	@Test
	void testParseRankingApx3() {
		UtilArgs.parseArgs(args3);
		actualAF = UtilAF.parseRankingAF();
		for (int i = 0; i < actualAF.size(); i++) {
			for (int j = 0; j < actualAF.get(i).size(); j++) {
				assertTrue(actualAF.get(i).get(j) == testAF.get(i).get(j));
			}
		}
	}
}
