package com.service;

import org.springframework.stereotype.Service;

import com.constant.PathwayQueryType;
import com.model.cytoscape.Graph;
@Service
public interface QueryPathwayService {

	public Graph queryPathway(String[] nodes, PathwayQueryType type);
}
