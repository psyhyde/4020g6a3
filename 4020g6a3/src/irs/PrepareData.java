package irs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.terrier.terms.PorterStemmer;

import weka.core.Stopwords;

public class PrepareData {
	
	public static List<WordDoc> build(String[] args) throws Exception {
		List<WordDoc> wList = new ArrayList<WordDoc>();
		File indexFolder = new File("./indexes");
		if (!indexFolder.exists()) {
			indexFolder.mkdir();
		}
		File[] files = new File("./chunks").listFiles();
		for (int index=0; index>files.length; index++) {
			File output = new File("./indexes/index" + index + ".txt");
			FileOutputStream fos = new FileOutputStream(output);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			String data = FileUtils.readFileToString(files[index]);
			Document docset = Jsoup.parse(data);
			int a = 0;
			for (int i = 0; i < docset.getElementsByTag("DOC").size(); i++) {
				String s = data.substring(a, data.indexOf("</DOC>", a) + 6);
				a = data.indexOf("</DOC>", a) + 6;
				Document doc = Jsoup.parse(s);
				for (Element e : doc.getElementsByTag("DOC")) {
					WordDoc wd = new WordDoc();
					String docno = e.getElementsByTag("DOCNO").text();
					wd.setDocno(docno);
					String dText = e.text();
					String[] sText = dText.split("[\\W^\\d]+");
					List<String> wordList = new ArrayList<String>(
							Arrays.asList(sText));
					buildWordList(wordList);
					bw.write(wd.getDocno());
					buildWorkDoc(wd, wordList);
					bw.write(" (" + showWordDoc(wd) + ")\n");
					wList.add(wd);
				}
			}
			bw.close();
			index++;
		}
		return wList;
	}

	private static String showWordDoc(WordDoc wd) {
		String line = "";
		for (Entry<String, Integer> entry : wd.getIndexMap().entrySet()) {
			if (line.equals("")) {
				line = entry.getKey() + " " + entry.getValue();
			} else {
				line = line + ", " + entry.getKey() + " " + entry.getValue();
			}
		}
		return line;
	}

	private static void buildWorkDoc(WordDoc wd, List<String> wordList) {
		HashMap<String, Integer> countIndex = new HashMap<String, Integer>();
		for (String word : wordList) {
			if (countIndex.get(word) == null) {
				countIndex.put(word, 1);
			} else {
				countIndex.put(word, countIndex.get(word) + 1);
			}
		}
		wd.setIndexMap(countIndex);
	}

	private static void buildWordList(List<String> wordList) {
		for (int i = 0; i < wordList.size(); i++) {
			if (Stopwords.isStopword(wordList.get(i).toLowerCase())) {
				wordList.remove(i);
				i--;
			} else {
				PorterStemmer ps = new PorterStemmer();
				wordList.set(i, ps.stem(wordList.get(i).toLowerCase()));
			}
		}
	}
}
