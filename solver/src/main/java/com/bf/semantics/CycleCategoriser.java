package com.bf.semantics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CycleCategoriser<N> extends AbstractSemantic<N> {

	private Map<Integer, Double> values = new HashMap<Integer, Double>();
	private List<Double> finalValuation = new ArrayList<Double>();
	private Double prescribedTolerance = new Double(0.001);

	public CycleCategoriser(List<ArrayList<N>> aF) {
		super(aF);
	}

	/**
	 * Sort "values" and put the sorted values in "ranking"
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

	public void fixedPointValuation(List<Double> lastValuation) {
		List<Double> newValuation = new ArrayList<Double>();
		for (int i = 0; i < lastValuation.size(); i++) {
			Double tmpValuation = new Double(1.0);
			for (int j = 0; j < lastValuation.size(); j++) {
				if(getAF().get(j).get(i) != null){ // if "j" attacks "i"
					tmpValuation += ((Integer) getAF().get(j).get(i)).doubleValue() * lastValuation.get(j);
				}
			}
			tmpValuation = 1/tmpValuation;
			newValuation.add(tmpValuation);
		}
		Boolean stable = true;
		for (int i = 0; i < lastValuation.size(); i++) {
			if (Math.abs(lastValuation.get(i) - newValuation.get(i)) > this.prescribedTolerance) {
				stable = false;
			}
		}
		if(stable){
			for (int i = 0; i < lastValuation.size(); i++) {
				this.finalValuation.add(newValuation.get(i));
			}
		}else{
			fixedPointValuation(newValuation);
		}
	}

	@Override
	public void resolve() {
		List<Double> firstValuation = new ArrayList<Double>();
		for (int i = 0; i < getAF().size(); i++) {
			firstValuation.add(1.0);
		}
		fixedPointValuation(firstValuation);
		for (int id = 0; id < finalValuation.size(); id++) {
			values.put(id, finalValuation.get(id));
		}
		this.sort();
		System.out.print(this.toString());
	}
}
