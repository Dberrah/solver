package com.bf.semantics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Categoriser<N> extends AbstractSemantic<N> {

    private Map<Integer, Double> values = new HashMap<Integer, Double>();

    public Categoriser(List<ArrayList<N>> aF) {
        super(aF);
    }

    /**
     * Calculate the score of a node
     * Allows to call it recursively
     * @param id the id of the node
     * @return the score
     */
    public Double calculate(int id){
        Double sum = 0.0;
        if (!this.isAttacked(id)) {
            return 1.0;
        } else {
            for (Integer i : this.getAttacker(id)) {
                if(!values.containsKey(i)){
                    values.put(i, calculate(i));
                }
                sum += values.get(i);
            }
            return (1/(1+sum));
        }
    }

    public void sort() {
        // First sort the id by values in an ArrayList
        List<Integer> sortedIdList = new ArrayList<Integer>();
        for (Integer id : values.keySet()) {
            if(sortedIdList.isEmpty()){
                sortedIdList.add(id);
            } else {
                for (int i = 0; i < sortedIdList.size(); i++) {
                    if (values.get(id)<values.get(sortedIdList.get(i))) {
                        sortedIdList.add(i, id);
                    }
                }
            }
        }
        // Use the ArrayList to fill the ranking List
        getRanking().add(new ArrayList<Integer>());
        int j=0;
        for (Integer index : sortedIdList) {
            if (getRanking().get(j).isEmpty()) {
                getRanking().get(j).add(index);
            } else {
                if(values.get(getRanking().get(j).get(0)) == values.get(index)){
                    getRanking().get(j).add(index);
                } else {
                    getRanking().add(new ArrayList<Integer>());
                    j++;
                    getRanking().get(j).add(index);
                }
            }
        }
    }

    @Override
    public void resolve() {
        for (int i = 0; i < this.getAF().size(); i++) {
            if(!values.containsKey(i)){
                values.put(i, calculate(i));
            }
        }
        this.sort();
        System.out.println(this.toString());
    }

}
