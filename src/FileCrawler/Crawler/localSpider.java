package FileCrawler.Crawler;

import java.io.File;
import java.util.ArrayList;



import com.google.common.collect.*;



public class localSpider extends Thread {
	
	static Multimap<String, String> masterIndex;			//e.g. abc.docx - E:/abc.docx
	static Multimap<String, String> fileExtensionIndex;	//e.g .docx - abc.docx
	static Multimap<String, String> fileNameWOExtIndex;	//e.g wordDOc - abc.docx
	String root;
	ArrayList<textFileSpider> childThreads =  new ArrayList<textFileSpider>();
	static{
		masterIndex =  ArrayListMultimap.create();
		fileExtensionIndex = ArrayListMultimap.create();
		fileNameWOExtIndex = ArrayListMultimap.create();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	//	crawl("E:\\7Diagnols");
	//	Enumeration<String> a = fileNameWOExtIndex.keys();
//		while(a.hasMoreElements()){
			
	//		System.out.println(a.nextElement());
		//}	
	}
	
	public localSpider(String dir) {
		this.root=dir;
	}
	
	@Override
	public void run() {
		crawl(this.root);
		for (textFileSpider td : childThreads) {
			try {
				td.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public  void crawl(String rootPath){
		System.out.println("TID["+this.getId()+"] - Crawling Root Path:\t"+rootPath);
		
		File f = new File(rootPath);
		crawl(f);
		
	}

	public static String[] search(String token){
		
		String find = token.toLowerCase();
		System.out.println(token+"attatat\n\n");
		ArrayList<String> a = new ArrayList<String>(); 
		if(masterIndex.containsKey(find)){
			a.addAll(masterIndex.get(find));
		}
		if(fileNameWOExtIndex.containsKey(find)){
			ArrayList<String> tmp = new ArrayList<String>();
			tmp.addAll(fileNameWOExtIndex.get(find));
			for (String str : tmp) {
				if(masterIndex.containsKey(str)){
					a.addAll(masterIndex.get(str));
				}
			}
		}
		if(textFileSpider.txtFileIndex.containsKey(find)){
			a.addAll(textFileSpider.txtFileIndex.get(find));
			//System.out.println("hey-="+);
		}
		
		String[] stringArray = a.toArray(new String[a.size()]);
		return stringArray;
	
	}
	
	
	private void crawl(File current){
		if(current.isHidden() || !current.canRead()|| !current.isDirectory() || current.getName().startsWith(".")){		//Doesnt cater hidden files else too much garbage problem
			
			return;
		}
		else{						//(current.isDirectory() && !current.isHidden()){
			
			File[] subFiles = current.listFiles();
			for (File file : subFiles) {
				String filename = file.getName();
				if(filename.startsWith(".")){
					continue;	//ignore system meta files
				}
				int ext = filename.lastIndexOf(".");
				
				if(ext != -1){
					String fileExtension = filename.substring(ext+1).toLowerCase();
					fileExtensionIndex.put(filename.substring(ext+1).toLowerCase(), filename);
					fileNameWOExtIndex.put(filename.substring(0, ext).toLowerCase(),filename);
					String name = filename.substring(0, ext);
					if(name.contains(" ")){
						String[] keys = name.split(" ");
						for (String string : keys) {
							fileNameWOExtIndex.put(string,filename);
						}
					}
					System.out.println("Extension:\t"+fileExtension);
					if(fileExtension.compareToIgnoreCase("txt")==0  ){
						System.out.println("txt file \t"+filename);
						textFileSpider textCrawler = new textFileSpider(file);
						textCrawler.start();
					}
					
				}
				masterIndex.put(filename.toLowerCase(), file.getAbsolutePath());
				//System.out.println("TID["+ this.getId()+ "]\t"+file.getName()+"\t-\t"+ file.getAbsolutePath());
				
				crawl(file);
			}
		}
	}
	

}
