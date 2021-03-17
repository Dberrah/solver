package com.bf.solver;

import com.bf.semantics.Categoriser;
import com.bf.semantics.CycleCategoriser;
import com.bf.semantics.DiscussionBased;
import com.bf.utilities.*;

public class App 
{
    public static void main( String[] args )
    {
    	UtilArgs.parseArgs(args);
    	if(!UtilArgs.getOptsList().isEmpty()){
            for (int i = 0; i < UtilArgs.getOptsList().size(); i++) {
                if(UtilArgs.getOptsList().get(i).contentEquals("-p")){
                    switch (UtilArgs.getArgsList().get(i)) {
                        case "SE-Cat":
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
                        default:
                            System.err.println("There is no such problem !");
                            break;
                    }
                }
            }
        }
    }
}
