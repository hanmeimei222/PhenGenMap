package com.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.model.Pathway;

public interface GeneDao {

	//输入pathway id查询该条pathway具体信息
	public Pathway getPathwayById(Map<String,Pathway> allways,String id);
	//输入pathway name 查询该条pathway
	public Pathway getPathwayByName(Map<String,Pathway> allways,Map<String,String>pwnamemap,String name);
	//输入id或name 查询单条pathway
	public Pathway getSinglePathway(Map<String,Pathway> allways,Map<String,String>pwnamemap,String query);
	//多条pathway信息的查询，query以分号隔开
	public List<Pathway> getMultiPNode(Map<String,Pathway> allways,Map<String,String>pwnamemap,String query);
	
	//查询一级类别，输出该类下的子类，以及子类的相关pathways
	public Map<String,Map<Pathway,Boolean>> getMainCatalog(Map<String,Map<String,Map<Pathway,Boolean>>> classmap,String class1);
	
	//输入一二级类别，查询具体pathways
	public Set<Pathway> getPathway(Map<String,Map<String,Map<Pathway,Boolean>>> classmap,String class1,String class2);
	//输入二级类别返回所属一级类别
	public String getClass1ByClass2(Map<String,String>clsmap,String class2);
	public Set<Pathway> getSubCatalog(Map<String,Map<String,Map<Pathway,Boolean>>> classmap,Map<String,String>clsmap,String class2);
	
	//按基因查询，输入symbolname，查询包含它的所有pathways
	public Set<Pathway> getPathwayByGene(Map<String,Pathway> allways,String symbolname);
	
}
