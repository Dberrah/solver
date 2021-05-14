package com.bf.semantics;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bf.utilities.UtilAF;
import com.bf.utilities.UtilArgs;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CategoriserTest {

	private static List<ArrayList<Integer>> actualRanking;
	private static List<ArrayList<Integer>> expectedRanking;
	private static List<ArrayList<Integer>> expectedRanking2;
	private static String args1[];
	private static String args2[];
	private static String args3[];

	@BeforeAll
	static void beforeAll() {
		actualRanking = new ArrayList<ArrayList<Integer>>();
		expectedRanking = new ArrayList<ArrayList<Integer>>();
		expectedRanking2 = new ArrayList<ArrayList<Integer>>();
		expectedRanking.add(new ArrayList<Integer>(Arrays.asList(0, 4, 9)));
		expectedRanking.add(new ArrayList<Integer>(Arrays.asList(2)));
		expectedRanking.add(new ArrayList<Integer>(Arrays.asList(1, 3, 5, 6, 7)));
		expectedRanking.add(new ArrayList<Integer>(Arrays.asList(8)));
		expectedRanking2.add(new ArrayList<Integer>(Arrays.asList(0)));
		expectedRanking2.add(new ArrayList<Integer>(Arrays.asList(2)));
		expectedRanking2.add(new ArrayList<Integer>(Arrays.asList(1)));
		args1 = new String[6];
		args2 = new String[6];
		args3 = new String[6];
		args1[0] = "-p";
		args1[1] = "R-Cat";
		args1[2] = "-f";
		args1[3] = "src/test/ressources/exemple_1.apx";
		args1[4] = "-fo";
		args1[5] = "apx";
		args2[0] = "-p";
		args2[1] = "R-Cat";
		args2[2] = "-f";
		args2[3] = "src/test/ressources/exemple_1.tgf";
		args2[4] = "-fo";
		args2[5] = "tgf";
		args3[0] = "-p";
		args3[1] = "R-Cat";
		args3[2] = "-f";
		args3[3] = "test.apx";
		args3[4] = "-fo";
		args3[5] = "apx";
	}

	@Test
	void testCategoriser1() {
		UtilArgs.parseArgs(args1);
		Categoriser<Integer> cat = new Categoriser<Integer>(UtilAF.parseRankingAF());
		cat.resolve();
		actualRanking = cat.getRanking();
		for (int i = 0; i < actualRanking.size(); i++) {
			for (int j = 0; j < actualRanking.get(i).size(); j++) {
				assertTrue(actualRanking.get(i).get(j).compareTo(expectedRanking.get(i).get(j)) == 0);
			}
		}
	}

	@Test
	void testCategoriser2() {
		UtilArgs.parseArgs(args2);
		Categoriser<Integer> cat = new Categoriser<Integer>(UtilAF.parseRankingAF());
		cat.resolve();
		actualRanking = cat.getRanking();
		for (int i = 0; i < actualRanking.size(); i++) {
			for (int j = 0; j < actualRanking.get(i).size(); j++) {
				assertTrue(actualRanking.get(i).get(j).compareTo(expectedRanking.get(i).get(j)) == 0);
			}
		}
	}

	@Test
	void testCategoriser3() {
		UtilArgs.parseArgs(args3);
		Categoriser<Integer> cat = new Categoriser<Integer>(UtilAF.parseRankingAF());
		cat.resolve();
		actualRanking = cat.getRanking();
		for (int i = 0; i < actualRanking.size(); i++) {
			for (int j = 0; j < actualRanking.get(i).size(); j++) {
				assertTrue(actualRanking.get(i).get(j).compareTo(expectedRanking2.get(i).get(j)) == 0);
			}
		}
	}
}
