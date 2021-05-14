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

		do {
			done = true;
			cycle = true;
			for (int i = 0; i < copyAF.size(); i++) {
				//verifies the argument is not attacked, that it has not been treated yet and that it used to be attacked in the original AF
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
					
					/**
					 * Since we don't know the number of steps of each attacker
					 * and every attacker does not have the same number of steps,
					 * this for loop allows us to construct the discussion count of the attacked argument
					 */
					for (Integer id : attacker) {
						for (int j = 0; j < discussionCount.get(id).size(); j++) {
							if (toAdd.size() <= j) {
								toAdd.add(discussionCount.get(id).get(j));
							} else {
								toAdd.set(j, toAdd.get(j) + discussionCount.get(id).get(j));
							}
						}
					}
					
					/**
					 * To have the right polarity according to the relation meaning,
					 * we have to invert the polarity obtained from the attackers
					 */
					for (int j = 0; j < toAdd.size(); j++) {
						if (toAdd.get(j) == 0) {
							discussionCount.get(i).add(toAdd.get(j));
						} else {
							discussionCount.get(i).add(toAdd.get(j) * -1);
						}
					}

					// We suppress all attacks coming from this argument
					for (int j = 0; j < copyAF.size(); j++) {
						copyAF.get(i).set(j, null);
					}
					
				}
			}

			// Verify that every argument discussion count is calculated by verifying that it ends with 0
			for (int i = 0; i < copyAF.size(); i++) {
				if (discussionCount.get(i).get(discussionCount.get(i).size() - 1) != 0) {
					done = false;
					break;
				}
			}

			if (cycle) {
				
				List<Integer> idCycle = new ArrayList<Integer>();
				
				// gets the argument's id that are part of a cycle
				for (int i = 0; i < copyAF.size(); i++) {
					if (isCopyAttacked(i)) {
						idCycle.add(i);
					}
				}

				// Goes through the cycle
				for (int i = 0; i < idCycle.size(); i++) {
					// Goes through the attackers
					for (Integer id : getAttacker(idCycle.get(i))) {
						// If the attacker that does not belong to a cycle
						if (!idCycle.contains(id)) {
							// the discussion count is added only once
							for (int j = 0; j < discussionCount.get(id).size(); j++) {
								if (discussionCount.get(idCycle.get(i)).size() <= j+1) {
									if (discussionCount.get(id).get(j) != 0) {
										discussionCount.get(idCycle.get(i)).add(discussionCount.get(id).get(j) * -1);
									} else {
										discussionCount.get(idCycle.get(i)).add(discussionCount.get(id).get(j));
									}
								}else{
									discussionCount.get(idCycle.get(i)).set(j+1, discussionCount.get(idCycle.get(i)).get(j+1) + (discussionCount.get(id).get(j) * -1));
								}
							}
						}
					}
				}
				
				// Threshold is set to n-1
				for (int j = 1; j < copyAF.size()-1; j++) {
					// Goes through the cycle
					for (int i = 0; i < idCycle.size(); i++) {
						// Goes through the attackers
						for (Integer id : getAttacker(idCycle.get(i))) {
							// If the attacker is in a cycle
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
		} while (!done);
	}
	
	public void resolve() {
		int tmpMax = 0;
		Double max;
		
		// Make the copy but without the attacks from the arguments that are never attacked
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
