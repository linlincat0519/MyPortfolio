package org.analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

public class md5data {

	public static void main(String[] args) throws IOException {
		//�إߧ��
		String stringLine;
		String uploadLine;
		//�D�n�ɮ��ɮ�
		File file = new File("report/5769.R720.ER");
		//�x�s�ɮ�1		
		String []array= new String[2];
		File outfile = new File("report/Filepathway.txt") ;
		FileWriter fw = new FileWriter (outfile);
		//�x�s�ɮ�2
		String []arrays= new String[2];
		File files = new File("report/uploadfilelist.txt");
		FileWriter fws = new FileWriter (files);
		//Ū��MD5
		fw.write("Filepathway"+"\t\t\t\t\t\t\t\t\t\t\t"+ "DATA NAME"+"\n");
		HashSet<String> MD5sites = new HashSet<String>();
		HashSet<String> uploadsites = new HashSet<String>();
	    try (BufferedReader br = new BufferedReader( new FileReader(file))) {	
	        while ((stringLine=br.readLine())!=null) {

	        	//�i����MD5
	        		int beginIndex = 0;
	        		beginIndex = stringLine.indexOf("MD5 =");

	        		//���MD5�����G�æC�L�X��
	        		if(beginIndex != -1) {	
	        				String [] MD5data = stringLine.split(":");

	        				fw.write(MD5data[3]+"\t"+MD5data[4]+"\n");
        				MD5sites.add(MD5data[3]);
			}        		
	        	}

	    try (BufferedReader brs = new BufferedReader( new FileReader(file))){
	    	while((uploadLine=brs.readLine())!=null) {
	        	int checkdata = 0;
	        	checkdata = uploadLine.indexOf("Copied (new)");

    		if(checkdata != -1) {	
				String [] Copieddata = uploadLine.split(":");
				fws.write(Copieddata[3]+"\t"+Copieddata[4]+"\n");

    		} 		
	        }
	     }
	     }
     
	}

}