package com.bf.utilities;

import java.util.ArrayList;
import java.util.List;

public class UtilArgs {

    private static List<String> argsList = new ArrayList<String>();
    private static List<String> optsList = new ArrayList<String>();

    public static List<String> getArgsList() {
        return argsList;
    }

    public static void setArgsList(List<String> argsList) {
        UtilArgs.argsList = argsList;
    }

    public static List<String> getOptsList() {
        return optsList;
    }

    public static void setOptsList(List<String> optsList) {
        UtilArgs.optsList = optsList;
    }

    /**
     * Parse and stores the parameters
     * Does the necessary prints
     * @param args args of main
     */
    public static void parseArgs(String[] args) {

        if (args.length < 1) {
            System.out.println("BFSolver v1.0");
            System.out.println("Danil Berrah, danil.berrah@outlook.fr");
            System.out.println("Thalya Fossey, thalyafossey1@hotmail.fr");
        } else {
            for (int i = 0; i < args.length; i++) {
                switch (args[i].charAt(0)) {
                    case '-':
                        if (args[i].length() < 2) {
                            throw new IllegalArgumentException("Not a valid argument: " + args[i]);
                        } else if (args[0].charAt(0) == '-' && args[0].charAt(1) == '-') {
                            // --opt
                            /*
                             * The method used to parse --args has been removed to gain exec time 
                             * In the same spirit, using switch is faster than using else if
                             */
                            if (args[i].length() < 3)
                                throw new IllegalArgumentException("Not a valid argument: " + args[i]);
                            switch (args[i]) {
                                case "--formats":
                                    /**
                                     * tgf  : Trivial Graph Format
                                     * apx  : ASPARTIX Format
                                     * wapx : Weighted ASPARTIX Format
                                     */
                                    System.out.println("[tgf,apx,wapx]");
                                    break;
                                case "--problems":
                                    /**
                                     * SE-Cat : the ranking with respect to the Categoriser Semantic
                                     * R-CCat : the ranking with respect to the Categoriser Semantic generalised to graphs
                                     * R-Disc : the ranking with respect to the Discussion-Based Semantic
                                     */
                                    System.out.println("[R-Cat, R-CCat, R-Disc, R-CDisc]");
                                    break;
                                default:
                                    throw new IllegalArgumentException("Unknown argument: " + args[i]);
                            }
                        } else {
                            // -opt goes with an arg
                            if (args.length - 1 == i || args[i + 1].charAt(0) == '-')
                                throw new IllegalArgumentException("Expected arg after: " + args[i]);
                            optsList.add(args[i]);
                        }
                        break;
                    default:
                        // arg
                        argsList.add(args[i]);
                        break;
                }
            }
        }
    }
}