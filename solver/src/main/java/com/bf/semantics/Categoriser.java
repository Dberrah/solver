package com.bf.semantics;

import java.util.ArrayList;
import java.util.List;

public class Categoriser<N> extends AbstractSemantic<N> {

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
                if(!getValues().containsKey(i)){
                    getValues().put(i, calculate(i));
                }
                sum += getValues().get(i);
            }
            return (1/(1+sum));
        }
    }

    @Override
    public void resolve() {
        for (int i = 0; i < this.getAF().size(); i++) {
            if(!getValues().containsKey(i)){
                getValues().put(i, calculate(i));
            }
        }
        this.sort();
        System.out.print(this.toString());
    }

}
