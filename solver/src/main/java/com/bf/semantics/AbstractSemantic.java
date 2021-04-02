package com.bf.semantics;

import com.bf.utilities.UtilAF;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractSemantic<T> {
	private List<ArrayList<T>> AF = new ArrayList<ArrayList<T>>();
	private List<ArrayList<Integer>> ranking = new ArrayList<ArrayList<Integer>>();
	private Map<Integer, Double> values = new HashMap<Integer, Double>();

	public AbstractSemantic(List<ArrayList<T>> aF) {
		this.AF = aF;
	}

	/**
	 * Used to display the ranking by replacing the id of a node by its name
	 */
	@Override
	public String toString() {
		StringBuffer sBuffer = new StringBuffer();
		for (ArrayList<Integer> arrayList : ranking) {
			sBuffer.append("[");
			for (int i = 0; i < arrayList.size() - 1; i++) {
				sBuffer.append(UtilAF.getIdToName().get(arrayList.get(i)) + ",");
			}
			sBuffer.append(UtilAF.getIdToName().get(arrayList.get(arrayList.size() - 1)) + "]\n");
		}
		return sBuffer.toString();
	}

	/**
	 * Sort "getValues()" and put the sorted getValues() in "ranking"
	 */
	public void sort() {
		// First sort the id by values in an ArrayList
		Map<Integer, Double> sorted = values.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		// Use the ArrayList to fill the ranking List
		getRanking().add(new ArrayList<Integer>());
		int j = 0;
		for (Integer index : sorted.keySet()) {
			if (getRanking().get(j).isEmpty()) {
				getRanking().get(j).add(index);
			} else {
				if (values.get(getRanking().get(j).get(0)).compareTo(values.get(index)) == 0) {
					getRanking().get(j).add(index);
				} else {
					getRanking().add(new ArrayList<Integer>());
					j++;
					getRanking().get(j).add(index);
				}
			}
		}
	}

	/**
	 * Verify if a node is attacked
	 * 
	 * @param id the id of the node
	 * @return true : if the node is attacked, false otherwise
	 */
	public boolean isAttacked(int id) {
		for (int i = 0; i < this.AF.size(); i++) {
			if (this.AF.get(i).get(id) != null) {
				return true;
			}
		}
		return false;
	}

	public List<Integer> getAttacker(int id) {
		List<Integer> res = new ArrayList<Integer>();
		for (int i = 0; i < this.AF.size(); i++) {
			if (this.AF.get(i).get(id) != null) {
				res.add(i);
			}
		}
		return res;
	}

	public Map<Integer, Double> getValues() {
		return values;
	}

	public void setValues(Map<Integer, Double> values) {
		this.values = values;
	}

	public List<ArrayList<T>> getAF() {
		return AF;
	}

	public void setAF(List<ArrayList<T>> aF) {
		this.AF = aF;
	}

	public List<ArrayList<Integer>> getRanking() {
		return ranking;
	}

	public void setRanking(List<ArrayList<Integer>> rank) {
		this.ranking = rank;
	}

	abstract public void resolve();

}
