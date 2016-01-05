package com.dao;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.model.GNode;
import com.model.PPINode;

@Repository
public interface PPIDao {
	
	/**
	 * 输入一组entrezId 返回其对应的ppi节点
	 * @param entrezId
	 * @return
	 */
	public Set<PPINode> getPPINodes(Set<String> entrezId);
	
	/**
	 * 根据给定的ppi节点，查询各自关联的ppi节点集合
	 */
	public Map<PPINode,Map<PPINode,Boolean>> getConnectedPPI(Set<PPINode> nodes);
	
	public Map<PPINode,Map<GNode,Boolean>> getConnectedGenes(Set<PPINode> nodes);
}
