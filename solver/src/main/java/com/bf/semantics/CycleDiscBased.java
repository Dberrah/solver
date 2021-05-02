package com.bf.semantics;

import java.util.ArrayList;
import java.util.List;

public class CycleDiscBased<N> extends AbstractSemantic<N> {
	private List<ArrayList<Integer>> discussionCount = new ArrayList<ArrayList<Integer>>();
	private List<ArrayList<N>> copyAF = new ArrayList<ArrayList<N>>();
	
	public CycleDiscBased(List<ArrayList<N>> aF) {
		super(aF);
	}

	/**
	 * Verify if a node is attacked in the copy AF
	 * 
	 * @param id the id of the node
	 * @return true : if the node is attacked in the copy AF, false otherwise
	 */
	public boolean isCopyAttacked(int id) {
		for (int i = 0; i < copyAF.size(); i++) {
			if (copyAF.get(i).get(id) != null) {
				return true;
			}
		}
		return false;
	}
	
	public void discussion() {
		boolean done = true;
		boolean cycle = true;
		// int index = 0;
		do {
			done = true;
			cycle = true;
			for (int i = 0; i < copyAF.size(); i++) {
				if (!isCopyAttacked(i) && discussionCount.get(i).get(0) != 0 && discussionCount.get(i).size() <= 1) {
					cycle = false;
					List<Integer> attacker = new ArrayList<Integer>();
					/**
					 * No need to verifiy that getAttacker is not empty
					 * since from the beginning there is only attacked args.
					 * Indeed, every arg that is not attacked before this iteration 
					 * has either his first element of discussionCount equal to 0 
					 * or the length of discussionCount for his id is greater than 1
					 */
					for (Integer id : getAttacker(i)) {
						attacker.add(id);
					}
					List<Integer> toAdd = new ArrayList<Integer>();
					for (Integer id : attacker) {
						for (int j = 0; j < discussionCount.get(id).size(); j++) {
							if (toAdd.size() <= j) {
								toAdd.add(discussionCount.get(id).get(j));
							} else {
								toAdd.set(j, toAdd.get(j) + discussionCount.get(id).get(j));
							}
						}
					}
					for (int j = 0; j < toAdd.size(); j++) {
						if (toAdd.get(j) == 0) {
							discussionCount.get(i).add(toAdd.get(j));
						} else {
							discussionCount.get(i).add(toAdd.get(j) * -1);
						}
					}
					for (int j = 0; j < copyAF.size(); j++) {
						copyAF.get(i).set(j, null);
					}
					System.out.println(discussionCount);
				}
			}
			for (int i = 0; i < copyAF.size(); i++) {
				if (discussionCount.get(i).get(discussionCount.get(i).size() - 1) != 0) {
					done = false;
					break;
				}
			}
			if (cycle) {
				System.out.println("cycle");
				List<Integer> idCycle = new ArrayList<Integer>();
				for (int i = 0; i < copyAF.size(); i++) {
					if (isCopyAttacked(i)) {
						idCycle.add(i);
					}
				}
				System.out.println(idCycle);
				System.out.println(discussionCount);
				for (int i = 0; i < idCycle.size(); i++) {
					for (Integer id : getAttacker(idCycle.get(i))) {
						if (!idCycle.contains(id)) {
							for (int j = 0; j < discussionCount.get(id).size(); j++) {
								if (discussionCount.get(id).get(j) != 0) {
									discussionCount.get(idCycle.get(i)).add(discussionCount.get(id).get(j) * -1);
								} else {
									discussionCount.get(idCycle.get(i)).add(discussionCount.get(id).get(j));
								}
							}
						}
					}
				}
				for (int j = 1; j < copyAF.size()-1; j++) {
					for (int i = 0; i < idCycle.size(); i++) {
						for (Integer id : getAttacker(idCycle.get(i))) {
							if (idCycle.contains(id)) {
									if (discussionCount.get(idCycle.get(i)).size() <= j) {
										discussionCount.get(idCycle.get(i)).add(discussionCount.get(id).get(j-1) * -1);
									} else {
										discussionCount.get(idCycle.get(i)).set(j, discussionCount.get(idCycle.get(i)).get(j) + (discussionCount.get(id).get(j-1) * -1));
									}
							}
						}
					}
				}
				done = true;
			}
			// index++;
		} while (!done);// && index < getAF().size());
	}
	
	public void resolve() {
		int tmpMax = 0;
		Double max;
		for (int i = 0; i < getAF().size(); i++) {
			copyAF.add(new ArrayList<N>());
			if (isAttacked(i)) {
				for (int j = 0; j < getAF().get(i).size(); j++) {
					copyAF.get(i).add(getAF().get(i).get(j));
				}
			} else {
				for (int j = 0; j < getAF().get(i).size(); j++) {
					copyAF.get(i).add(null);
				}
			}
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
