package com.dao;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.model.GNode;
import com.model.PNode;
import com.model.PPINode;
import com.model.Pathway;

@Repository
public interface GeneDao {
	
	public Set<GNode> getGnodesBySymbol(Set<String> symbols);
	/**
	 * 根据输入的基因返回其关联的表型集合
	 * @param gNodes
	 * @return
	 */
	public Map<GNode,Map<PNode,Boolean>> getAssociatedMP(Set<GNode> gNodes);
	
	/**
	 * 根据输入的基因返回其关联的pathway列表
	 */
	public Map<GNode,Map<Pathway,Boolean>> getAssociatedPathway(Set<GNode> gNodes);
	
	/**
	 * 根据输入的基因节点集合获取其各自关联的PPI集合
	 * @param gNodes
	 * @return
	 */
	public Map<GNode,Map<PPINode,Boolean>> getAssociatedPPI(Set<GNode> gNodes);
	
	public Set<GNode> getAllGenes();

}
