package com.service;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.constant.NodeType;
import com.model.PNode;
import com.model.Pathway;
import com.model.cytoscape.Graph;
import com.model.d3.D3Graph;

@Service
public interface QueryAssoService {
	
	/**
	 * 根据输入的不同类型的节点，查询出节点间的关联以及节点内的关联
	 * @param map
	 * @return
	 */
	public Graph getAsso(Map<NodeType,Map<String,Boolean>> map);
	
	public D3Graph getGlobalAsso();
	public D3Graph getGlobalAsso(Map<String,Set<PNode>> phenNodes,Map<String,Set<Pathway>> pathways,String selected_type);
}
