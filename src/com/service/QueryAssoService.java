package com.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.constant.NodeType;
import com.model.cytoscape.Graph;

@Service
public interface QueryAssoService {
	
	/**
	 * 根据输入的不同类型的节点，查询出节点间的关联以及节点内的关联
	 * @param map
	 * @return
	 */
	public Graph getAsso(Map<NodeType,Map<String,Boolean>> map);
}
