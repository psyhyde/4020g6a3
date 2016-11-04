package irs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;

public class PrepareProcess {
	
	public static void build(String[] args) throws Exception {
		File dataset = new File("Dataset.txt");
		int chunk = 0;
		BufferedReader br = new BufferedReader(new FileReader(dataset));
		String line;
		int buffer = 0;
		File chunkFolder = new File("./chunks");
		if (!chunkFolder.exists()) {
			chunkFolder.mkdir();
		}
		File output = new File("./chunks/set" + chunk + ".txt");
		FileOutputStream fos = new FileOutputStream(output);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		while ((line = br.readLine()) != null) {
			bw.write(line + "\n");
			if (line.equals("</DOC>")) {
				buffer++;
				if (buffer == 40) {
					bw.close();
					output = new File("./chunks/set" + ++chunk + ".txt");
					fos = new FileOutputStream(output);
					bw = new BufferedWriter(new OutputStreamWriter(fos));
					buffer = 0;
				}
			}
		}
		br.close();
		bw.close();
		System.out.println("done");
	}
}
