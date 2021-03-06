package com.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.constant.NodeType;
import com.model.Pathway;
import com.model.d3.PathwayGraphAndTree;
import com.model.d3.TreeNode;
import com.model.ztree.ZtreeNode;
@Service
public interface QueryPathwayService {

	/**
	 * 构造GlobalAsso中左侧Pathway的Ztree
	 * @return
	 */
	public  List<ZtreeNode> getpathwayztree();
	
	public TreeNode allPathway();
	
	public PathwayGraphAndTree getGenePathwayAsso(Map<NodeType,Map<String,Boolean>> map);
	
	/**
	 * 根据输入的level和pathway name/id查询pathway的信息
	 */
	public Map<String,Set<Pathway>> queryPathway(Map<String,Boolean> info,int level);
}
