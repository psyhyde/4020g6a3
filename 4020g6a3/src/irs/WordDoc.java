package irs;

import java.util.HashMap;

public class WordDoc {
	
	/**
	 * DOCNO
	 */
	private String docno = "";
	
	/**
	 * COUNT INDEX
	 */
	private HashMap<String, Integer> countIndex = new HashMap<String, Integer>();

	public void setDocno(String docno) {
		this.docno = docno;
	}

	public String getDocno() {
		return this.docno;
	}

	public void setIndexMap(HashMap<String, Integer> countIndex) {
		this.countIndex = countIndex;
	}

	public HashMap<String, Integer> getIndexMap() {
		return this.countIndex;
	}
}
