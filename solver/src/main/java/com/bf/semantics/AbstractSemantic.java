package com.bf.semantics;

import com.bf.utilities.UtilAF;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSemantic<T> {
    private List<ArrayList<T>> AF = new ArrayList<ArrayList<T>>();
    private List<ArrayList<Integer>> ranking = new ArrayList<ArrayList<Integer>>();

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
     * Verify if a node is attacked
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
