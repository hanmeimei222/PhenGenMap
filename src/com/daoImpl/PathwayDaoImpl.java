package com.daoImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.dao.PathwayDao;
import com.global.GlobalData;
import com.model.GNode;
import com.model.Pathway;

@Repository
public class PathwayDaoImpl implements PathwayDao{


	@Override
	public Set<Pathway> getPathwaySet() {
		Set<Pathway> result = new HashSet<Pathway>();
		result.addAll(GlobalData.allways.values());
		return result;
	}
	//输入pathway id查询该条pathway具体信息
	@Override
	public Pathway getPathwayById(String id){
		//Pathway result = new Pathway();
		Pathway result = GlobalData.allways.get(id);
		return result;
	} 

	//输入pathway name 查询该条pathway
	@Override
	public Pathway getPathwayByName(String name){
		//Pathway result = new Pathway();
		String id = GlobalData.pwnamemap.get(name);
		Pathway result = GlobalData.allways.get(id);
		return result;
	}

	//输入id或name 查询单条pathway
	@Override
	public Pathway getSinglePathway(String query){
		Pathway result;// = new Pathway();
		if(query.startsWith("mmu")){
			result = getPathwayById(query);
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
			result = getPathwayByName(query);
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
	@Override
	public Set<Pathway> getMultiPathway(Set<String> query){
		Set<Pathway> result = new HashSet<Pathway>();

		for (String str : query) {
//			Pathway pw = new Pathway();
			Pathway	pw = getSinglePathway(str);
			if(null!=pw){
				result.add(pw);
			}
		}
		return result;
	}


	//返回全部pathways 
	@Override
	public Map<String, Map<String, Map<Pathway, Boolean>>> getAllPathways() {
		return GlobalData.classmap;
	}

	//返回class2-class1的map
	@Override
	public Map<String, String> getCls2cls1Map() {
		return GlobalData.clsmap;
	}

	//返回class2-pathways的map
	@Override
	public Map<String, Set<Pathway>> getCls2PathwayMap(){
		Map<String, Set<Pathway>>result = new HashMap<String, Set<Pathway>>();
		Set<String>cls2set = GlobalData.clsmap.keySet();
		for (String cls2 : cls2set) {
			String cls1 = GlobalData.clsmap.get(cls2);
			Set<Pathway>pwset = GlobalData.classmap.get(cls1).get(cls2).keySet();
			result.put(cls2, pwset);
		}
		return result;
	}


	//查询一级类别，输出该类下的子类，以及子类的相关pathways
	@Override
	public Map<String,Map<Pathway,Boolean>> getMainCatalog(String class1){
		Map<String,Map<Pathway,Boolean>>subcatalog = new HashMap<String, Map<Pathway,Boolean>>();

		subcatalog = GlobalData.classmap.get(class1);
		return subcatalog;
	}

	//输入一二级类别，查询具体pathways
	@Override
	public Set<Pathway> getPathway(String class1,String class2){
		Set<Pathway>result = new HashSet<Pathway>();
		Map<String,Map<Pathway,Boolean>>subcatalog =  GlobalData.classmap.get(class1);
		Map<Pathway,Boolean>pathways = subcatalog.get(class2);
		result = pathways.keySet();
		return result;
	}

	//输入二级类别返回所属一级类别
	@Override
	public String getClass1ByClass2(String class2){
		String class1 = GlobalData.clsmap.get(class2);
		return class1;
	}

	//pathway二级类别查询,输出包含哪些pathways
	@Override
	public Set<Pathway> getSubCatalog(String class2){
		Set<Pathway>result = new HashSet<Pathway>();
		Map<String, Set<Pathway>> cls2map = getCls2PathwayMap();
		result = cls2map.get(class2);
		return result;
	}

	//按基因查询，输入symbolname，查询包含它的所有pathways
	@Override
	public Map<Pathway,Boolean> getPathwayByGene(String symbolname){

		Map<Pathway,Boolean> result = new HashMap<Pathway,Boolean>();
		GNode gn = new GNode();
		gn.setSymbol_name(symbolname);
		for (Entry<String, Pathway> entry : GlobalData.allways.entrySet()) {
			Pathway pw = entry.getValue();
			Map<GNode,Boolean>symbols = entry.getValue().getSymbols();
			if(symbols!=null&&symbols.containsKey(gn)){
				result.put(pw,true);	
			}
		}
		return result;
	}

	@Override
	public Set<GNode> getCommonSymbols(Pathway pw1, Pathway pw2) {
		Set<GNode>gn1 = pw1.getSymbols().keySet();
		Set<GNode>gn2 = pw2.getSymbols().keySet();
		Set<GNode>result = new HashSet<GNode>();
		for (GNode gNode : gn1) {
			result.add(gNode);
		}
		result.retainAll(gn2);
		return result;
	}

	@Override
	//Map<Pathway,Map<Pathway,Integer>>
	public List<Pathway> getRelatedPathwayRank(Pathway pw) {
		//存放排序好的Pathway 的List作为结果返回
		List<Pathway>result = new ArrayList<Pathway>();
		Map<Pathway,Integer>related = GlobalData.relatedPathway.get(pw);
		//第一次查询时进行计算，并进行缓存
		if(related == null)
		{
			related = new HashMap<Pathway,Integer>();
			Collection<Pathway> allpathways = GlobalData.allways.values();
			//			对于每一个其他pathway，计算与当前pathway的交集，并将对应的pathway,num存储到related中
			for (Pathway pathway : allpathways)
			{
				if(pathway==null||pathway == pw || pathway.getSymbols()==null)
				{
					continue;
				}
			//取交集
				Set<GNode> intersectGene = getCommonSymbols(pw,pathway);
	
				if(!intersectGene.isEmpty()){
					related.put(pathway, intersectGene.size());
				}
			}
		}
		
		//		对related进行排序，返回前5个pathway
		List<Entry<Pathway,Integer>> list =  new ArrayList<Entry<Pathway,Integer>>(related.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Pathway, Integer>>() {			
			public int compare(Map.Entry<Pathway, Integer> o1,			
					Map.Entry<Pathway, Integer> o2) {				
				return (o2.getValue() - o1.getValue());				
			}
		});

		for (Entry<Pathway, Integer> map : list) {
			result.add(map.getKey());
		}
		if(result.size()>5)
		{
			result = result.subList(0, 5);
		}
		return result;
	}
}
