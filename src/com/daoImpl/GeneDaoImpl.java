package com.daoImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dao.GeneDao;
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

	
	public Set<Pathway> getSubCatalog(Map<String,Map<String,Map<Pathway,Boolean>>> classmap,String query){
		Set<Pathway>result = new HashSet<Pathway>();
		return result;
	}
}
