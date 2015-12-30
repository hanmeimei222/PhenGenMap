package com.dao;

import java.util.List;

import com.model.GPGraph;

public interface GPDao {

	/**
	 * 从表型开始查询
	 * 输入表型id集合，查出与它关联的GNode,相应Edge，返回查询结果的GPGraph
	 */
	public GPGraph getAssoByPheno(List<String>pids);
	
	/**
	 * 从基因开始查询
	 * 输入基因symbol_name的集合，查出与它关联的PNode，相应的Edge，返回查询结果的GPGraph
	 */
	public GPGraph getAssoByGene(List<String>symbols);
	
	/**
	 * 同时通过基因和表型查询
	 * 输入表型id集合和基因symbol_name的集合，查询出他们之间存在的关联Edge,并返回查询结果GPGraph
	 */
	public GPGraph getAssoByPhenoGene(List<String>pids,List<String>symbols);
}
