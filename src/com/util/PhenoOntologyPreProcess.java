package com.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PhenoOntologyPreProcess {

	public static void getPhenoFather(String input, String output) throws IOException{

		BufferedReader in = new BufferedReader(new FileReader(new File(input)));
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(output)));

		String line = "";
		String CurrMPId = "";
		String Ancestors = "";
		while (line != null) {
			line = in.readLine();
			if (line == null){
				continue;
			}
			if (line.equals("")){
				continue;
			}
			line = line.trim();

			if (line.substring(0, 3).equals("id:")){
				CurrMPId = line.substring(4);
			}

			if (line.substring(0, 5).equals("is_a:")){
				String temp = line.substring(6);
				int endIndex = temp.indexOf("!");
				Ancestors = temp.substring(0, endIndex - 1);
				out.write(CurrMPId + "\t" + Ancestors + "\r\n");
			}
		}
		in.close();
		out.close();
	}

	public static void getPhenoDetail(String input, String output)
	{
		BufferedReader in=null;
		BufferedWriter out=null;
		try {
			in = new BufferedReader(new FileReader(new File(input)));
			out = new BufferedWriter(new FileWriter(new File(output)));

			String line="";
			while(null!=(line=in.readLine()))
			{
				//新的一条term的开始
				if("[Term]".equals(line))
				{
					String id=in.readLine().split(" ")[1];
					if(id.equals("MP:0000003"))
					{
						System.out.println();
					}
					String name=in.readLine();
					name = name.substring(6,name.length());
					String def = "#";
					String parents = "";

					while(!"".equals(line))
					{
						line = in.readLine();
						//对于最后一个节点
						if(null==line)
						{
							break;
						}
						//读取其他关心的内容，如def：
						if(line.startsWith("def"))
						{
							def = line;
						}

						if(line.contains("is_a:"))
						{	
							StringBuilder builder = new StringBuilder();
							builder.append(line.split(" ")[1]);

							while((line=in.readLine())!=null && line!="" && line.contains("is_a:"))
							{
								builder.append(",").append(line.split(" ")[1]);
							}
							parents = builder.toString();
						}
					}

					out.write(id + "\t"+ parents + "\t"+ name + "\t"+def+"\r\n");

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//构造id和它的所有孩子的map
	public static Map<String, List<String>> getPhenoSon(String input){
		Map<String, List<String>> sonmap = new HashMap<String, List<String>>();
		BufferedReader in=null;
		try {
			in=new BufferedReader(new FileReader(new File(input)));
			String line="";
			while(null!=(line=in.readLine())){
				String[]temp = line.split("\t");
				if(!sonmap.containsKey(temp[1])){
					List<String> sonlist = new ArrayList<String>();
					sonlist.add(temp[0]);
					sonmap.put(temp[1], sonlist);	
				}
				else{
					List<String>tempson=sonmap.get(temp[1]);
					if(!tempson.contains(temp[0])){
						tempson.add(temp[0]);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sonmap;
	}

	//构建id和level的map
	public static Map<String,String> getPhenLevel(Map<String,List<String>> sonmap){
		Map<String,String>phenlevel=new HashMap<String,String>();

		int curLevel = 0;
		Set<String> currents = new HashSet<String>();
		Set<String> tmpSons = new HashSet<String>();

		currents.add("MP:0000001");

		while(!currents.isEmpty())
		{
			for (String child : currents) {
				List<String> s = sonmap.get(child);
				if(s!=null)
				{
					tmpSons.addAll(s);
				}
			}
			currents.clear();
			currents.addAll(tmpSons);
			tmpSons.clear();
			curLevel += 1;

			//现在curLevel的值对应的是currents中的node的level
			for (String phe : currents) {
				if(!phenlevel.containsKey(phe))
				{
					phenlevel.put(phe, "");
				}
				String old = phenlevel.get(phe);
				String tmp="";
				if(old == "")
				{
					tmp = String.valueOf(curLevel);
				}
				else{
					tmp = old + "," + (String.valueOf(curLevel));
				}

				phenlevel.put(phe,tmp);
			}
		}


		return phenlevel;
	}


	public static void getAllPhenoInfo(String input,String output_1,String output_2,String output_3) throws Exception{
		PhenoOntologyPreProcess.getPhenoFather(input, output_1);
		PhenoOntologyPreProcess.getPhenoDetail(input, output_2);
		Map<String, List<String>> pheSons = PhenoOntologyPreProcess.getPhenoSon(output_1);
		Map<String,String> pheLevel =  getPhenLevel(pheSons);

		BufferedReader in = new BufferedReader(new FileReader(new File(output_2)));
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(output_3)));
		String line="";
		while(null!=(line=in.readLine())){
			String[]temp=line.split("\t");
			String id = temp[0];
			String father = temp[1].trim();
			String name = temp[2];
			String def ="";
			if(!temp[3].equals("#"))
			{
				def = temp[3];
			}

			List<String>curson=pheSons.get(temp[0]);
			String sons= "";
			if(curson!=null)
			{
				StringBuilder builder = new StringBuilder();
				for (String son : curson) {
					builder.append(son).append(",");
				}
				sons=builder.toString();
			}
			String curlevel=pheLevel.get(temp[0]);
			if(curlevel==null){
				curlevel = "0";
			}

			if(father==null || father.equals(""))
			{
				father = "#";
			}

			if(sons == null || sons=="")
			{
				sons = "#";
			}
			out.write(id + "\t"+ name +"\t"+def +"\t"+curlevel+"\t"+father+"\t"+sons+"\r\n");

		}
		in.close();
		out.close();
	}


	public static void main(String[] args) throws IOException {
		String input = "WebContent/data/input_data/MPheno_OBO.ontology.txt";
		String output_1 = "WebContent/data/inter_data/mpo_id_pid.txt";
		String output_2 = "WebContent/data/inter_data/mpo_id_pid_name_def.txt";
		String output_3 = "WebContent/data/inter_data/phen_info.txt";
		//		getPhenoFather(input, output_1);
		//		getPhenoDetail(input, output_2);
		//		Map<String, List<String>> pheSons = getPhenoSon(output_1);
		//		Map<String,String> map =  getPhenLevel(pheSons);
		try {
			getAllPhenoInfo(input,output_1,output_2,output_3);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
