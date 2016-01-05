package com.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.constant.NodeType;
import com.model.cytoscape.Graph;
import com.model.d3.TreeNode;
@Service
public interface QueryPathwayService {

	public TreeNode allPathway();
	
	public Graph getGenePathwayAsso(Map<NodeType,Map<String,Boolean>> map);
}
