package com.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.constant.PathwayQueryType;
import com.model.d3.TreeNode;
@Service
public interface QueryPathwayService {

	public TreeNode queryPathway(Map<String,Boolean> queryMap, PathwayQueryType type);
	
	
}
