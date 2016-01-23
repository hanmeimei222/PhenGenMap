package com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.global.GlobalData;
import com.model.CBG;

public class CBGDataLoad {

	private static String cbgPath = GlobalData.PATH+"data/cbg/";
	
	
	public static Map<String,List<String>> loadCBGDetail(String fname)
	{
		int id = Integer.parseInt(fname.split(":")[1]);
		Map<String,List<String>>map=new HashMap<String,List<String>>();
		BufferedReader in = null;
		try{
			in= new BufferedReader(new FileReader(new File(cbgPath+"cbgdetail/"+id+".txt")));

			String line="";
			
			
			StringBuffer buf = new StringBuffer();
			while(null!=(line=in.readLine()))
			{
				String[]temp=line.split("\t");
				//将读入的每一行的前两个字符拼成map的key
				String key=temp[0]+"_"+temp[1];
				
				for(int i =2;i<temp.length;i++)
				{
					buf.append(temp[i]).append("\t");
				}
				line = buf.toString();
				buf.setLength(0);

				List<String> lines = map.get(key);
				if(lines==null)
				{
					lines=new ArrayList<String>();
					map.put(key, lines);
				}
				lines.add(line);

			}

		}catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				in.close();
			} catch (IOException e) {
			}
		}
		return map;
	}
	public static List<CBG>loadCBGsummarisetable()
	{
		List<CBG> cbgsum = new ArrayList<CBG>();
		BufferedReader in = null;
		try{
			in= new BufferedReader(new FileReader(new File(cbgPath+"cbg.txt")));


			String line="";
			while(null!=(line=in.readLine()))
			{
				String[]temp=line.split("\t");
				CBG cbg=new CBG();
				cbg.setPhen_id(temp[0]);
				cbg.setAssociation(temp[16]);
				cbg.setPheno(temp[17]);
				cbg.setStep1(new String[]{temp[1],temp[2],temp[3]});
				cbg.setStep2(new String[]{temp[4],temp[5],temp[6],temp[7],temp[8]});
				cbg.setStep3(new String[]{temp[9],temp[10],temp[11],temp[12],temp[13],temp[14],temp[15]});	
				cbgsum.add(cbg);
			}
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				in.close();
			} catch (IOException e) {
			}
		}

		return cbgsum;

	}
}
