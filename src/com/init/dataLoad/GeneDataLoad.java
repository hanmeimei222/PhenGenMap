package com.init.dataLoad;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.model.GNode;
import com.model.Pathway;

public class GeneDataLoad {

	public static Pathway makeNodeByString(String str)
	{
		String []templine = str.split("\t");
		Pathway pw = new Pathway();

		Map<GNode,Boolean>symbols = new HashMap<GNode,Boolean>();

		pw.setPw_id(templine[0]);
		pw.setPw_name(templine[1].substring(0,templine[1].length()-23));
		if(templine.length>2){
			String []classes = templine[2].split(";");
			classes[0] = classes[0].replace("\"", "");
			classes[1] = classes[1].replace("\"", "");

			pw.setClass_1(classes[0]);
			pw.setClass_2(classes[1].trim());
			pw.setSymbols(symbols);
			if(templine.length>3){
				//初始化GNode，暂时gene节点的id为空，后面再修改
				for(int i=3; i<templine.length;i++){
					GNode gn = new GNode();
					gn.setSymbol_name(templine[i]);
					symbols.put(gn, true);
				}
			}
		}
		return pw;
	}

	//从文件中逐行读入，构造需要的数据结构
	public static void readPathway(String infile,Map<String,Pathway> allways,Map<String,String>pwnamemap,Map<String,Map<String,Map<Pathway,Boolean>>> classmap){
		BufferedReader in=null;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(infile),"UTF-8"));
			String line="";


			while(null!=(line= in.readLine())){
				Pathway pw = makeNodeByString(line);
				//构造Map<String id,Pathway>
				String pw_id = pw.getPw_id();
				allways.put(pw_id, pw);

				//构造Map<String,String>pwnamemap
				String pw_name = pw.getPw_name();
				pwnamemap.put(pw_name,pw_id);

				//构造Map<String,Map<String,Map<Pathway,Boolean>>> classmap
				String class1 = pw.getClass_1();
				String class2 = pw.getClass_2();

				if(class1!=null &&class2!=null){
					Map<String,Map<Pathway,Boolean>>class2map = classmap.get(class1);

					if(class2map == null){
						class2map = new HashMap<String,Map<Pathway,Boolean>>();
						classmap.put(class1, class2map);
					}

					Map<Pathway,Boolean>pwmap = class2map.get(class2);
					if(pwmap == null){
						pwmap = new HashMap<Pathway,Boolean>();
						class2map.put(class2, pwmap);
					}
					pwmap.put(pw, true);
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	//输入pathway id查询该条pathway具体信息
	public static Pathway getPathwayById(Map<String,Pathway> allways,String id){
		Pathway result = new Pathway();
		result = allways.get(id);
		return result;
	} 

	//输入pathway name 查询该条pathway
	public static Pathway getPathwayByName(Map<String,Pathway> allways,Map<String,String>pwnamemap,String name){
		Pathway result = new Pathway();
		String id = pwnamemap.get(name);
		result = allways.get(id);
		return result;
	}

	//输入id或name 查询单条pathway
	public static Pathway getSinglePathway(Map<String,Pathway> allways,Map<String,String>pwnamemap,String query){
		Pathway result = new Pathway();
		if(query.startsWith("mmu")){
			result = getPathwayById(allways, query);
			if(result!=null){
				return result;
			}
			else{
				System.out.println("没有找到该节点");
				return null;
			}
		}
		else if(query.startsWith("Name:")){
			query = query.substring(5);
			result = getPathwayByName(allways, pwnamemap,query);
			if(result!=null){
				return result;
			}
			else{
				System.out.println("没有找到该节点!");
				return null;
			}
		}
		else{
			System.out.println("您的输入不符合要求!");
			return null;
		}
	}
	
	//多条pathway信息的查询，query以分号隔开
	public static List<Pathway> getMultiPNode(Map<String,Pathway> allways,Map<String,String>pwnamemap,String query){
		List<Pathway> result = new ArrayList<Pathway>();
		String []temp = query.split(";");
		for(int i=0;i<temp.length;i++){
			Pathway pw = new Pathway();
			pw = getSinglePathway(allways, pwnamemap,temp[i]);
			if(null!=pw){
				result.add(pw);
			}
		}
		return result;
	}

	
	public static Set<Pathway> getSubCatalog(Map<String,Map<String,Map<Pathway,Boolean>>> classmap,String query){
		Set<Pathway>result = new HashSet<Pathway>();
		return result;
	}

	public static void main(String[] args) {
		String infile = "WebContent/data/inter_data/mmu_pathway_id_name_class_symbols.txt";
		Map<String,Pathway> allways = new HashMap<String,Pathway>();
		Map<String,Map<String,Map<Pathway,Boolean>>> classmap = new HashMap<String, Map<String,Map<Pathway,Boolean>>>();
		Map<String,String>pwnamemap = new HashMap<String,String>();
		readPathway(infile, allways, pwnamemap, classmap);
//		Map<String,Map<Pathway,Boolean>>temp = classmap.get("METABOLISM");
//		Pathway pw = getPathwayById(allways, "mmu00232");
//		Pathway pw = getPathwayByName(allways, pwnamemap, "PURINE METABOLISM");
//		Pathway pw = getSinglePathway(allways, pwnamemap, "Name:PURINE METABOLISM");
		System.out.println();
	}
}
