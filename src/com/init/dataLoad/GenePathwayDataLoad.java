package com.init.dataLoad;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.global.GlobalData;
import com.model.GNode;
import com.model.Pathway;

@Repository
public class GenePathwayDataLoad {

	//根据读入的一行新建一条pathway
	public Pathway makeNodeByString(String str)
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
					String symbol = templine[i].toUpperCase();
					gn.setSymbol_name(symbol);
					GlobalData.allgnodes.put(symbol, gn);
					symbols.put(gn, true);
				}
			}
		}
		return pw;
	}

	//从文件中逐行读入，构造需要的数据结构
	public void readPathway(String infile){
		BufferedReader in=null;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(infile),"UTF-8"));
			String line="";


			while(null!=(line= in.readLine())){
				Pathway pw = makeNodeByString(line);
				//构造Map<String id,Pathway>
				String pw_id = pw.getPw_id();
				GlobalData.allways.put(pw_id, pw);

				//构造Map<String,String>pwnamemap
				String pw_name = pw.getPw_name();
				GlobalData.pwnamemap.put(pw_name,pw_id);
				
				//构造Map<String,Map<String,Map<Pathway,Boolean>>> classmap
				String class1 = pw.getClass_1();
				String class2 = pw.getClass_2();
				

				if(class1!=null &&class2!=null){
					GlobalData.clsmap.put(class2, class1);
					Map<String,Map<Pathway,Boolean>>class2map = GlobalData.classmap.get(class1);

					if(class2map == null){
						class2map = new HashMap<String,Map<Pathway,Boolean>>();
						GlobalData.classmap.put(class1, class2map);
					}

					Map<Pathway,Boolean>pwmap = class2map.get(class2);
					if(pwmap == null){
						pwmap = new HashMap<Pathway,Boolean>();
						class2map.put(class2, pwmap);
					}
					pwmap.put(pw, true);
				}

			}
			
			//构造Map<Pathway,Map<Pathway,Integer>>relatedPathway
			Set<Pathway>allpathways = new HashSet<Pathway>();
			Set<String>pids = GlobalData.allways.keySet();
			for (String string : pids) {
				allpathways.add(GlobalData.allways.get(string));
			}
			for (Pathway pathway : allpathways) {
				
				Map<Pathway,Integer>related = new HashMap<Pathway,Integer>();
				GlobalData.relatedPathway.put(pathway, related);
				
				//构造Map<Pathway,Integer>related
				Map<GNode,Boolean>gn = pathway.getSymbols();
				if(gn==null){
					continue;
				}
				Set<GNode>symbols = gn.keySet();
				Set<Pathway>others = new HashSet<Pathway>();
				for (Pathway otherpathway : others) {
					Set<GNode>othersymbols = otherpathway.getSymbols().keySet();
					//求交集
					othersymbols.retainAll(symbols);
					if(!othersymbols.isEmpty()){
						related.put(otherpathway, othersymbols.size());
					}
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

	//加载gene数据
	public void loadGeneData() {
		String infile = GlobalData.PATH+"/data/inter_data/mmu_pathway_id_name_class_symbols.txt";
//		String infile = "WebContent/data/inter_data/mmu_pathway_id_name_class_symbols.txt";
		readPathway(infile);
	}

}
