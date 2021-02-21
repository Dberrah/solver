package com.bf.utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UtilAF {

	private static Map<Integer, String> idToName = new HashMap<Integer, String>();
	private static Map<String, Integer> nameToId = new HashMap<String, Integer>();

	public static Map<Integer, String> getIdToName() {
		return idToName;
	}
	
	public static Map<String, Integer> getNameToId() {
		return nameToId;
	}
	
	public static void setNameToId(Map<String, Integer> nameToId) {
		UtilAF.nameToId = nameToId;
	}
	
	public static void setIdToName(Map<Integer, String> idToName) {
		UtilAF.idToName = idToName;
	}

	/**
	 * Read and parse the file given as parameter
	 * Only used when the graph is not weighted
	 * @return AF : List<ArrayList<Integer>>
	 */
	public static List<ArrayList<Integer>> parseRankingAF() {
		String fileName = null;
		String fileExtension = null;
		ArrayList<ArrayList<Integer>> AF = new ArrayList<ArrayList<Integer>>();
		
		/*
		 * Can be done when reading the options by adding variables
		 */
		for (int i = 0; i < UtilArgs.getOptsList().size(); i++) {
			if(UtilArgs.getOptsList().get(i) == "-f"){
				fileName = UtilArgs.getArgsList().get(i);
			}
			if(UtilArgs.getOptsList().get(i) == "-fo"){
				fileExtension = UtilArgs.getArgsList().get(i);
			}
		}
		if(fileName == null) {
			throw new IllegalArgumentException("Expected arg -f");
		}
		if (fileExtension == null){
			throw new IllegalArgumentException("Expected arg -fo");
		}
		try {

			FileReader fReader = new FileReader(fileName);
			// Use of a BufferedReader might add exec time
			BufferedReader bReader = new BufferedReader(fReader);
			String line = null;

			switch (fileExtension) {
				case "tgf":
					int i=0;
					Boolean isNode = true;
				try {
					while ((line = bReader.readLine()) != null) {
						if(line == "#") isNode = false; // # is the separator between nodes and edges
						if(isNode){
							idToName.put(i, line);
							nameToId.put(line, i);
							AF.add(new ArrayList<Integer>());
				            i++;
							// Init of AF node by node
				            for (int k = AF.size()-1; k >= 0; k--) {
				                if(k==AF.size()-1){
				                    for (int l = k; l >= 0; l--) {
				                        AF.get(k).add(0);
				                    }
				                } else {
				                    AF.get(k).add(0);
				                }
				            }
						} else {
							// Parsing using regex
							String node1 = line.split(" ")[0];
							String node2 = line.split(" ")[1];
							int id1 = nameToId.get(node1);
							int id2 = nameToId.get(node2);
							AF.get(id1).set(id2, 1);
						}
					}
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				try {
					bReader.close();
					fReader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
					break;
				case "apx":
					int j=0;
				try {
					while ((line = bReader.readLine()) != null) {
						if(line.startsWith("arg")) { // Indicates that it is a node
							idToName.put(j, line);
							nameToId.put(line, j);
							AF.add(new ArrayList<Integer>());
				            j++;
				            for (int k = AF.size()-1; k >= 0; k--) {
				                if(k==AF.size()-1){
				                    for (int l = k; l >= 0; l--) {
				                        AF.get(k).add(0);
				                    }
				                } else {
				                    AF.get(k).add(0);
				                }
				            }
						} else {
							String node1 = line.split("\\(|\\)|,")[0];
							String node2 = line.split("\\(|\\)|,")[1];
							int id1 = nameToId.get(node1);
							int id2 = nameToId.get(node2);
							AF.get(id1).set(id2, 1);
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					bReader.close();
					fReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
					break;
				default:
				try {
					bReader.close();
					fReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
					throw new IllegalArgumentException("Invalid file format : " + fileExtension);
			}
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		return null;
	}

	
}
