package com.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.constant.NodeType;
import com.model.d3.PathwayGraphAndTree;
import com.model.d3.TreeNode;
@Service
public interface QueryPathwayService {

	public TreeNode allPathway();
	
	public PathwayGraphAndTree getGenePathwayAsso(Map<NodeType,Map<String,Boolean>> map);
}
