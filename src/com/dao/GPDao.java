package com.dao;

import java.util.Set;

import org.springframework.stereotype.Repository;

import com.model.GPGraph;

@Repository
public interface GPDao {

	/**
	 * 从表型开始查询
	 * 输入表型id集合，查出与它关联的GNode,相应Edge，返回查询结果的GPGraph
	 */
	public GPGraph getAssoByPheno(Set<String> pids);
	
	/**
	 * 从基因开始查询
	 * 输入基因symbol_name的集合，查出与它关联的PNode，相应的Edge，返回查询结果的GPGraph
	 */
	public GPGraph getAssoByGene(Set<String> symbols);
	
	/**
	 * 同时通过基因和表型查询
	 * 输入表型id集合和基因symbol_name的集合，查询出他们之间存在的关联Edge,并返回查询结果GPGraph
	 */
	public GPGraph getAssoByPhenoGene(Set<String> pids,Set<String> symbols);
}
