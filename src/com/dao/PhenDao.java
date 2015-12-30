package com.dao;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.model.PNode;

@Repository
public interface PhenDao {
	
	/**
	 * 按节点查找:
	 * 按照id查询单个节点全部信息
	 */
	public PNode getSinglePNodeById(String id);
	
	
	/**
	 * 按照name查询单个节点全部信息
	 */
	public PNode getSinglePNodeByName(String name);
	

	/**
	 * 按照id/name查询单个节点全部信息
	 */
	public PNode getSinglePNode(String query);
	
	
	/**
	 * 多个节点全部信息的查询，query以分号隔开
	 */
	public  Set<PNode> getMultiPNode(String[] query);
	
	/**
	 * 横向查询：
	 * 查询单层包含哪些节点
	 */
	public Set<PNode> getPNodeBySingleLevel(String query);
	
	/**
	 * 查询多层包含哪些节点的并集，多层用分号隔开，如query = "4;5;6"
	 * 
	 */
	public Map<String,Set<PNode>> getPNodeByMultiLevel1(String query);
	
	
	/**
	 * 多层查询，例如：查找同时在第四层和第五层的点(交集)，输入的query以分号隔开"4;5"
	 */
	public Set<PNode> getPNodeByMultiLevel2(String query);
	
	
	
	/**
	 * 纵向查询
	 * 输入待查询节点id，输出以他为起点的所有前驱节点
	 */
	public Set<PNode>getPreNodes(String[] ids);
	
	/**
	 * 输入待查询节点id，输出以他为起点的所有后继节点
	 */
	public Set<PNode>getPostNodes(String[] ids);
	
	
	
	
	/**
	 * 横纵结合查询
	 * 输入单个节点id/name，找n步以内可达的节点集合
	 */
	public Set<PNode> getNStepNode(String[] ids,int n);
	
	
	/**
	 * 找到某个节点在某一层内的所有孩子节点
	 */
	public Set<PNode> getPostNodesByLevel(String id,String level);
	
	
	/**
	 * 找到某个节点在某一层内的所有父亲节点
	 */
	public Set<PNode> getPreNodesByLevel(String id,String level);
	
	
	/**
	 * 找两个不同层节点间的路径，从start找后继，一直到包含end节点
	 */
	public Set<PNode> getContainsNodes(String start, String end);
	
}
