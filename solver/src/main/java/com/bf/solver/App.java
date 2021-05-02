package com.bf.solver;

import com.bf.semantics.Categoriser;
import com.bf.semantics.CycleCategoriser;
import com.bf.semantics.DiscussionBased;
import com.bf.semantics.CycleDiscBased;
import com.bf.utilities.*;

public class App {
    public static void main(String[] args) {
        int fo_index = -1;
        int p_index = -1;
        UtilArgs.parseArgs(args);
        if (!UtilArgs.getOptsList().isEmpty()) {
            for (int k = 0; k < UtilArgs.getOptsList().size() && fo_index == -1; k++) {
                if (UtilArgs.getOptsList().get(k).contentEquals("-fo")) {
                    fo_index = k;
                }
            }
            for (int i = 0; i < UtilArgs.getOptsList().size() && p_index == -1; i++) {
                if (UtilArgs.getOptsList().get(i).contentEquals("-p")) {
                    p_index = i;
                }
            }
            if (UtilArgs.getArgsList().get(fo_index).contentEquals("wapx")) {
                switch (UtilArgs.getArgsList().get(p_index)) {
                    case "R-Cat":
                        Categoriser<Double> cat = new Categoriser<Double>(UtilAF.parseWeightedAF());
                        cat.resolve();
                        break;
                    case "R-CCat":
                        CycleCategoriser<Double> cCat = new CycleCategoriser<Double>(UtilAF.parseWeightedAF());
                        cCat.resolve();
                        break;
                    case "R-Disc":
                        DiscussionBased<Double> disc = new DiscussionBased<Double>(UtilAF.parseWeightedAF());
                        disc.resolve();
                        break;
                    case "R-CDisc":
                        CycleDiscBased<Double> cDisc = new CycleDiscBased<Double>(UtilAF.parseWeightedAF());
                        cDisc.resolve();
                        break;
                    default:
                        System.err.println("There is no such problem !");
                        break;
                }
            } else {
                switch (UtilArgs.getArgsList().get(p_index)) {
                    case "R-Cat":
                        Categoriser<Integer> cat = new Categoriser<Integer>(UtilAF.parseRankingAF());
                        cat.resolve();
                        break;
                    case "R-CCat":
                        CycleCategoriser<Integer> cCat = new CycleCategoriser<Integer>(UtilAF.parseRankingAF());
                        cCat.resolve();
                        break;
                    case "R-Disc":
                        DiscussionBased<Integer> disc = new DiscussionBased<Integer>(UtilAF.parseRankingAF());
                        disc.resolve();
                        break;
                    case "R-CDisc":
                        CycleDiscBased<Integer> cDisc = new CycleDiscBased<Integer>(UtilAF.parseRankingAF());
                        cDisc.resolve();
                        break;
                    default:
                        System.err.println("There is no such problem !");
                        break;
                }
                    
                
            }
        }
    }
}
