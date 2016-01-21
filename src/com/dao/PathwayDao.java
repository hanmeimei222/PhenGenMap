package com.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.model.GNode;
import com.model.Pathway;

@Repository
public interface PathwayDao {

	
	/**
	 * 输入pathway id查询该条pathway具体信息
	 */
	public Pathway getPathwayById(String id);
	
	
	/**
	 * 输入pathway name 查询该条pathway
	 * 
	 */
	public Pathway getPathwayByName(String name);
	
	
	/**
	 * 输入id或name 查询单条pathway
	 */
	public Pathway getSinglePathway(String query);
	
	
	/**
	 * 多条pathway信息的查询，query以分号隔开
	 */
	public Set<Pathway> getMultiPathway(Set<String> query);
	
	/**
	 * @Description 返回全部pathways 
	 * @return Map<String,Map<String,Map<Pathway,Boolean>>>
	 */
	public Map<String,Map<String,Map<Pathway,Boolean>>> getAllPathways();
	
	public Set<Pathway> getPathwaySet();
	/**
	 * @Description 返回class2-class1的map
	 * @return Map<String,String>
	 */
	public Map<String,String> getCls2cls1Map();
	
	/**
	 * 返回class2-pathways的map
	 * @return
	 */
	public Map<String,  Set<Pathway>> getCls2PathwayMap();
	
	/**
	 * 查询一级类别，输出该类下的子类，以及子类的相关pathways
	 */
	public Map<String,Map<Pathway,Boolean>> getMainCatalog(String class1);
	
	
	/**
	 * 输入一二级类别，查询具体pathways
	 */
	public Set<Pathway> getPathway(String class1,String class2);
	
	
	/**
	 * 输入二级类别返回所属一级类别
	 */
	public String getClass1ByClass2(String class2);
	
	/**
	 * 输入二级类别返回它下面的所有pathways
	 * @param class2
	 * @return
	 */
	public Set<Pathway> getSubCatalog(String class2);
	
	/**
	 * 按基因查询，输入symbolname，查询包含它的所有pathways
	 */
	public Map<Pathway,Boolean> getPathwayByGene(String symbolname);
	
	/**
	 * 查询当前pathway与其他pathway的关联度排序
	 * @param pw
	 * @return
	 */
	public List<Pathway> getRelatedPathwayRank(Pathway pw);
	
	/**
	 * 查询两个pathway同时包含的symbols集合
	 * @param pw1
	 * @param pw2
	 * @return
	 */
	public Set<GNode> getCommonSymbols(Pathway pw1,Pathway pw2);
	
}
