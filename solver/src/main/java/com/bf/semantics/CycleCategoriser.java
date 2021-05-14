package com.bf.semantics;

import java.util.ArrayList;
import java.util.List;
import java.lang.Number;

public class CycleCategoriser<N> extends AbstractSemantic<N> {

	private List<Double> finalValuation = new ArrayList<Double>();
	private Double prescribedTolerance = new Double(0.001);	// Difference tolerance accepted between to valuation to reach a fixed point, arbritary

	public CycleCategoriser(List<ArrayList<N>> aF) {
		super(aF);
	}

	/**
	 * Calculate the valuation of the fix point
	 * 
	 * @param lastValution List of the last calculated valuation
	 */
	public void fixedPointValuation(List<Double> lastValuation) {
		
		List<Double> newValuation = new ArrayList<Double>();
		
		// Uses the Categoriser function to get the new valuation
		for (int i = 0; i < lastValuation.size(); i++) {
			Double tmpValuation = new Double(1.0);		// Initialised to 1 in case the argument is not attacked
			for (int j = 0; j < lastValuation.size(); j++) {
				if(getAF().get(j).get(i) != null){ 		// if "j" attacks "i"
					tmpValuation += ((Number) getAF().get(j).get(i)).doubleValue() * lastValuation.get(j);
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
			getValues().put(id, finalValuation.get(id));
		}
		this.sort();
		System.out.print(this.toString());
	}
}
