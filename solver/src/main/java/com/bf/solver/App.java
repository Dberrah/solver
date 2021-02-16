package com.bf.solver;

import com.bf.semantics.Categoriser;
import com.bf.utilities.*;

public class App 
{
    public static void main( String[] args )
    {
    	
    	UtilArgs.parseArgs(args);
    	if(!UtilArgs.getOptsList().isEmpty()){
            for (int i = 0; i < UtilArgs.getOptsList().size(); i++) {
                if(UtilArgs.getOptsList().get(i) == "-p"){
                    switch (UtilArgs.getArgsList().get(i)) {
                        case "SE-Cat":
                            Categoriser AF = new Categoriser(UtilAF.parseRankingAF());
                            AF.resolve();
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }
}
