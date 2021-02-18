package com.bf.semantics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Categoriser {

	private List<ArrayList<Boolean>> AF = new ArrayList<ArrayList<Boolean>>();

	public Categoriser(List<ArrayList<Boolean>> aF) {
		setAF(aF);
	}
	
	public List<ArrayList<Boolean>> getAF() {
		return AF;
	}

	public void setAF(List<ArrayList<Boolean>> aF) {
		AF = aF;
	}
	
	public void resolve() {
		Map<Integer,ArrayList<Integer>> res = new HashMap<Integer,ArrayList<Integer>>();
		// TODO Algo et affichage de res avec UtilAF.getIdToName()
		res.toString();
	}
	
}
