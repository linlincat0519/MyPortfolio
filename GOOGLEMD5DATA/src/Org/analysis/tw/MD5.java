package Org.analysis.tw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

public class MD5 {
	public static void main(String[] args) throws IOException {
	//建立 stringLine
	String stringLine,uploadLine;
	//讀取MD5檔案
	File file = new File("report/5769.R720.ER");
	//讀取檔案大小相關資料
	File files = new File("report/uploadfilelist.txt");
    FileWriter fw = new FileWriter (files);
    
    //寫入讀取內容
    fw.write("filepathway"+"\t\t\t\t\t\t\t\t\t\t\t"+ "DATA NAME"+"\n");
//    HashSet<String> uploadsites = new HashSet<String>();
    HashSet<String> MD5sites = new HashSet<String>();
//    try (BufferedReader brs = new BufferedReader( new FileReader(file))){
//    	while((uploadLine=brs.readLine())!=null) {
//    String [] beginIndex = uploadLine.split("/");
//    String [] datasize = uploadLine.replaceAll("  ", " ").split(" ");
//  System.out.println(datasize[4]);
//	
//    uploadsites.add(beginIndex[1]);
    
//    	}
//    	System.out.println(uploadsites);
    	
//    	System.out.println(rawData);
//    }
    
    
    try (BufferedReader br = new BufferedReader( new FileReader(file))) {	
    	while ((stringLine=br.readLine())!=null) {
    	//	   System.out.println(stringLine);
    	//進行抓取MD5
    		int beginIndex = 0;
    		beginIndex = stringLine.indexOf("MD5 =");
    		//		System.out.println(beginIndex);
    		//抓取MD5的結果並列印出來
    		if(beginIndex != -1) {	
    				String [] MD5data = stringLine.split(":");
    				//		System.out.println(MD5data[3]+MD5data[4]);
//    				fw.write(MD5data[3]+"\t"+MD5data[4]+"\n");
    				MD5sites.add(MD5data[3]);

				}        		
    		}
 
    	
		fw.flush();
		fw.close();
    }	 
		

}

}
