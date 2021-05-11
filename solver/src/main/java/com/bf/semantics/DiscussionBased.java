package com.bf.semantics;

import java.util.ArrayList;
import java.util.List;

public class DiscussionBased<N> extends AbstractSemantic<N> {
	
	private List<ArrayList<Integer>> discussionCount = new ArrayList<ArrayList<Integer>>();
	
	public DiscussionBased(List<ArrayList<N>> aF) {
		super(aF);
	}

	/**
	 * Get the list of arguments that attacks the "id" argument with a size of path recursively increased until "iteration" equals 0
	 * 
	 * @param id the id of the node
	 * @param iteration the size of path that needs to be reached
	 * 
	 * @throws IllegalArgumentException if iteration < 0
	 * 
	 * @return a list of arguments id
	 */
	public List<Integer> getAttacker(int id, int iteration) throws IllegalArgumentException{
		if(iteration < 0) throw new IllegalArgumentException();

		List<Integer> res1 = new ArrayList<Integer>();
		
		for (int i = 0; i < getAF().size(); i++) {
			if (getAF().get(i).get(id) != null) {
				res1.add(i);
			}
		}

		// if iteration equals 0 it returns the direct attackers of "id"

		while (iteration > 0) {
			List<Integer> res2 = new ArrayList<Integer>();
			for (int i = 0; i < res1.size(); i++) {
				if(isAttacked(res1.get(i)))
				{
					for (int j = 0; j < getAF().size(); j++) {
						if (getAF().get(j).get(res1.get(i)) != null) {
							res2.add(j);
						}
					}
				}
			}
			res1 = res2;
			iteration--;
		}
		return res1;
	}
	
	/**
	 * Represents the discussion according to the definition of the Discussion-based semantic
	 */
	public void discussion() {
		boolean done = false;
		int index = 0;
		
		do {
			
			boolean unique = true;
			List<Integer> containedValues = new ArrayList<Integer>(0);
			
			// Verifies if there is a difference in the discussion counts
			for (int i = 0; i < getAF().size() && unique; i++) {
				
				if(!containedValues.contains(discussionCount.get(i).get(index)) || discussionCount.get(i).get(index) == 0)
				{
					if(discussionCount.get(i).get(index) != 0)
					{
						containedValues.add(discussionCount.get(i).get(index));
					}
				}else{
					unique = false;
				}
			}

			if(unique)
			{
				done = true;
			}else{
				// adds a step in the discussion
				for (int i = 0; i < getAF().size(); i++) {
					if(index%2 == 0)
					{
						discussionCount.get(i).add(-getAttacker(i,index+1).size());
					}else{
						discussionCount.get(i).add(getAttacker(i,index+1).size());
					}
				}
			}
			index++;
		} while (!done && index < getAF().size()); // while a difference or the threshold is not reached
	}
	
	public void resolve() {
		int tmpMax = 0;
		Double max;
		// Initialise the discussion count for each argument
		for (int i = 0; i < getAF().size(); i++) {
			int size;
			discussionCount.add(new ArrayList<Integer>());
			// size is the number of direct attackers
			size = getAttacker(i).size();
			discussionCount.get(i).add(size);
			if(size > tmpMax)
			{
				tmpMax = size;
			}
		}
		// max is used to pass from the discussion count to a value
		max = (((Integer) tmpMax).doubleValue());
		discussion();
		// pass from discussion count to a value with respect to the weight of each step of the discussion
		for (int i = 0; i < discussionCount.size(); i++) {
			double nodeValue = 1.0;
			for (int j = 0; j < discussionCount.get(i).size(); j++) {
				/**
				 * Divide by max to get a value between -1 and 1
				 * Multiply by 0.1**(j+1.0) to weight the steps of the discussion
				 */
				nodeValue -= (discussionCount.get(i).get(j)/max) * Math.pow(0.1, j+1.0);
			}
			getValues().put(i, nodeValue);
		}
		this.sort();
		System.out.print(this.toString());
	}
}
