package com.daoImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.dao.GeneDao;
import com.global.GlobalData;
import com.init.dataLoad.GeneDataLoad;
import com.model.GNode;
import com.model.Pathway;

public class GeneDaoImpl implements GeneDao{

	//输入pathway id查询该条pathway具体信息
	public Pathway getPathwayById(Map<String,Pathway> allways,String id){
		Pathway result = new Pathway();
		result = allways.get(id);
		return result;
	} 

	//输入pathway name 查询该条pathway
	public Pathway getPathwayByName(Map<String,Pathway> allways,Map<String,String>pwnamemap,String name){
		Pathway result = new Pathway();
		String id = pwnamemap.get(name);
		result = allways.get(id);
		return result;
	}

	//输入id或name 查询单条pathway
	public Pathway getSinglePathway(Map<String,Pathway> allways,Map<String,String>pwnamemap,String query){
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
	public List<Pathway> getMultiPNode(Map<String,Pathway> allways,Map<String,String>pwnamemap,String query){
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

	//查询一级类别，输出该类下的子类，以及子类的相关pathways
	public Map<String,Map<Pathway,Boolean>> getMainCatalog(Map<String,Map<String,Map<Pathway,Boolean>>> classmap,String class1){
		Map<String,Map<Pathway,Boolean>>subcatalog = new HashMap<String, Map<Pathway,Boolean>>();

		subcatalog = classmap.get(class1);
		return subcatalog;
	}

	//输入一二级类别，查询具体pathways
	public Set<Pathway> getPathway(Map<String,Map<String,Map<Pathway,Boolean>>> classmap,String class1,String class2){
		Set<Pathway>result = new HashSet<Pathway>();
		Map<String,Map<Pathway,Boolean>>subcatalog = classmap.get(class1);
		Map<Pathway,Boolean>pathways = subcatalog.get(class2);
		result = pathways.keySet();
		return result;
	}

	//输入二级类别返回所属一级类别
	public String getClass1ByClass2(Map<String,String>clsmap,String class2){
		String class1 = clsmap.get(class2);
		return class1;
	}

	//pathway二级类别查询,输出属于哪一个上级类别，以及包含哪些pathways
	public Set<Pathway> getSubCatalog(Map<String,Map<String,Map<Pathway,Boolean>>> classmap,Map<String,String>clsmap,String class2){
		Set<Pathway>result = new HashSet<Pathway>();
		String class1 = clsmap.get(class2);
		result = classmap.get(class1).get(class2).keySet();
		return result;
	}

	//按基因查询，输入symbolname，查询包含它的所有pathways
	public Set<Pathway> getPathwayByGene(Map<String,Pathway> allways,String symbolname){
		Set<Pathway>result = new HashSet<Pathway>();
		GNode gn = new GNode();
		gn.setSymbol_name(symbolname);
		for (Entry<String, Pathway> entry : allways.entrySet()) {
			Pathway pw = entry.getValue();
			Map<GNode,Boolean>symbols = entry.getValue().getSymbols();
			if(symbols!=null&&symbols.containsKey(gn)){
			result.add(pw);	
			}
		}
		return result;
	}
	
	
	

	public static void main(String[] args) {
		GeneDataLoad gdl = new GeneDataLoad();
		GeneDaoImpl gdi = new GeneDaoImpl();
		gdl.loadGeneData();
		Set<Pathway>result = gdi.getPathwayByGene(GlobalData.allways,"HK2");
		System.out.println();
		
	}

}
