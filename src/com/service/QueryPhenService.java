package com.service;

import org.springframework.stereotype.Service;

import com.model.cytoscape.Graph;

@Service
public interface QueryPhenService {
	/**
	 * 根据给定MP，找到其n步内相邻的节点集合
	 * @param id
	 * @param n
	 * @return
	 */
	public Graph getNStepNode(String id,int n);
	
	/**
	 * 查找给定节点在特定层中的子孙节点
	 * @param id	例：MP:0000001
	 * @param levels	例：3;4;5
	 * @return
	 */
	public Graph getInterlevelsAndRoot(String id,String levels);
}
