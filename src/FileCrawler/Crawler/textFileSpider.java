package FileCrawler.Crawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class textFileSpider extends Thread {

	static Multimap<String, String> txtFileIndex;
	File textFile;
	static{
		txtFileIndex =  ArrayListMultimap.create();
	}

	public static void main(String[] args) {
		textFileSpider test = new textFileSpider(new File("F:\\c.txt"));
		test.start();
	}
	
	public textFileSpider(File ptr) {
		this.textFile=ptr;
	}

	@Override
	public void run() {
		System.out.println("Scanning text file-\t"+this.textFile.getName());
		Scanner sc2 = null;
		if(!this.textFile.canRead()){
			return;
		}
		try {
			
			sc2 = new Scanner(this.textFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}

		while (sc2.hasNextLine()) {
			Scanner s2 = new Scanner(sc2.nextLine());
			while (s2.hasNext()) {
				String s = s2.next();
				if(s.length() >2){
					txtFileIndex.put(s.toLowerCase(), this.textFile.getAbsolutePath());
					System.out.println("TID["+this.getId()+"]\t-"+s);
				}
			}
			s2.close();
		}
		sc2.close();


	}
}
