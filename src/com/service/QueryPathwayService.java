package com.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.constant.PathwayQueryType;
import com.model.cytoscape.Graph;
@Service
public interface QueryPathwayService {

	public Graph queryPathway(Map<String,Boolean> queryMap, PathwayQueryType type);
}
