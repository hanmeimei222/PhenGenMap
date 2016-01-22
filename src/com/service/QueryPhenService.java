package com.service;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.constant.PhenQueryType;
import com.model.PNode;
import com.model.cytoscape.Graph;

@Service
public interface QueryPhenService {
	
	/**
	 * 表型本体的常规查询
	 * @param queryMap
	 * @param type
	 * @param param
	 * @return
	 */
	public Graph queryPhen(Map<String,Boolean> queryMap,PhenQueryType type,Map<String,String> param);
	
	/**
	 * 表型本体查询时自动补全
	 */
	public Set<PNode> aucoComplete(String query);
	
	/**
	 * 用于初始化页面的mp信息
	 * @param phenId
	 * @return
	 */
	public Set<PNode> getPhenInfo(Map<String,Boolean> queryMap);
	
	/**
	 * 全局查询关联时，扩展mp节点为其子孙
	 * @param queryMap
	 * @return
	 */
	public  Map<String,Set<PNode>> expandPhens(Map<String,Boolean> queryMap);
}
