package com.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GPAssociationPreProcess {

	//处理MGI_Geno_Disease.txt文件，取第2,5,7列写入symbol_mp_mgi.txt
	public static void getAllGPAssociation(String input, String output){
		BufferedReader in = null;
		BufferedWriter out = null;
		try {
			in = new BufferedReader(new FileReader(new File(input)));
			out = new BufferedWriter(new FileWriter(new File(output)));
			String line="";
			while(null!=(line=in.readLine())){
				String[]temp=line.split("\t");
				String symbol_name = "";
				if(temp[1].equals("Pax3<+>|Pax3<Sp-2H>")){
					System.out.println();
				}
				
				if(temp[1].contains("|")){
					String test = temp[1];
					String []tmp = test.split("\\|");
					if(tmp[1].equals("+")){
						temp[1] = tmp[0];
					}
					else {
						temp[1] = tmp[1];
					}
				}
				if(temp[1].contains("<")){
					symbol_name = temp[1].substring(0,temp[1].indexOf("<"));
					symbol_name = symbol_name.toUpperCase();
				}
				else{
					symbol_name = temp[1];
				}

				String mp_id = temp[4];
				String mgi = temp[6];
				out.write(symbol_name + "\t"+ mp_id +"\t"+mgi +"\r\n");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		String input = "WebContent/data/input_data/MGI_Geno_Disease.txt";
		String output = "WebContent/data/inter_data/symbol_mp_mgi.txt";
		getAllGPAssociation(input, output);
	}
}
