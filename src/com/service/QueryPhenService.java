package com.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.model.Line;

@Service
public interface QueryPhenService {
	/**
	 * 根据给定MP，找到其n步内相邻的节点集合
	 * @param id
	 * @param n
	 * @return
	 */
	public Set<Line> getNStepNode(String id,int n);
	
	/**
	 * 查找给定节点在特定层中的子孙节点
	 * @param id	例：MP:0000001
	 * @param levels	例：3;4;5
	 * @return
	 */
	public Set<Line> getInterlevelsAndRoot(String id,String levels);
}
