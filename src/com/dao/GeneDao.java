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
	
	//pathway二级类别查询
	public Set<Pathway> getSubCatalog(Map<String,Map<String,Map<Pathway,Boolean>>> classmap,String query);
}
