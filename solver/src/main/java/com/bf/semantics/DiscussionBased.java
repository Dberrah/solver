package com.bf.semantics;

import java.util.ArrayList;
import java.util.List;

public class DiscussionBased<N> extends AbstractSemantic<N> {
	
	private List<ArrayList<Integer>> discussionCount = new ArrayList<ArrayList<Integer>>();
	
	public DiscussionBased(List<ArrayList<N>> aF) {
		super(aF);
	}

	public List<Integer> getAttacker(int id, int iteration) throws IllegalArgumentException{
		if(iteration < 0) throw new IllegalArgumentException();

		List<Integer> res1 = new ArrayList<Integer>();
		
		for (int i = 0; i < getAF().size(); i++) {
			if (getAF().get(i).get(id) != null) {
				res1.add(i);
			}
		}
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
	
	public void discussion() {
		boolean done = false;
		int index = 0;
		do {
			boolean unique = true;
			List<Integer> containedValues = new ArrayList<Integer>(0);
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
		} while (!done);
	}
	
	@Override
	public void resolve() {
		int tmpMax = 0;
		Double max;
		for (int i = 0; i < getAF().size(); i++) {
			int size;
			discussionCount.add(new ArrayList<Integer>());
			size = getAttacker(i).size();
			discussionCount.get(i).add(size);
			if(size > tmpMax)
			{
				tmpMax = size;
			}
		}
		max = (((Integer) tmpMax).doubleValue());
		discussion();
		for (int i = 0; i < discussionCount.size(); i++) {
			double nodeValue = 1.0;
			for (int j = 0; j < discussionCount.get(i).size(); j++) {
				nodeValue -= (discussionCount.get(i).get(j)/max) * Math.pow(0.1, j+1.0);
			}
			getValues().put(i, nodeValue);
		}
		this.sort();
		System.out.print(this.toString());
	}
}
