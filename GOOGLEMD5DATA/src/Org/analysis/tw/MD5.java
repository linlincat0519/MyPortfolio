package Org.analysis.tw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

public class MD5 {
	public static void main(String[] args) throws IOException {
	//�إ� stringLine
	String stringLine,uploadLine;
	//Ū��MD5�ɮ�
	File file = new File("report/5769.R720.ER");
	//Ū���ɮפj�p�������
	File files = new File("report/uploadfilelist.txt");
    FileWriter fw = new FileWriter (files);
    
    //�g�JŪ�����e
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
    	//�i����MD5
    		int beginIndex = 0;
    		beginIndex = stringLine.indexOf("MD5 =");
    		//		System.out.println(beginIndex);
    		//���MD5�����G�æC�L�X��
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
