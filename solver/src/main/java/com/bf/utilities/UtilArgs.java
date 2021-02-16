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
     * Permet de gérer les arguments passés lors de l'appel du programme
     * 
     * @param args
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
                            parseDoubleArgs(args);
                        } else {
                            if (args.length - 1 == i || args[i + 1].charAt(0) == '-')
                                throw new IllegalArgumentException("Expected arg after: " + args[i]);
                            // -opt
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

    /**
     * Permet de gérer les arguments spéciaux (- -$)
     * @see parseArgs
     * @param args
     */
    public static void parseDoubleArgs( String [] args ) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].length() < 3)
                throw new IllegalArgumentException("Not a valid argument: "+args[i]);
            switch (args[i]) {
                case "--formats":
                    System.out.println("[tgf,apx]");
                    break;
                case "--problems":
                    System.out.println("[SE-Cat]");
                    break;
                default:
                    throw new IllegalArgumentException("Unknown argument: "+args[i]);
            }
        }
    }
}