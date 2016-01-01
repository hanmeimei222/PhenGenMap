package com.serviceImpl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.constant.PathwayQueryType;
import com.dao.PathwayDao;
import com.model.Pathway;
import com.model.cytoscape.Graph;
import com.service.QueryPathwayService;
import com.util.ModelTransferUtil;

@Service
public class QueryPathwayServiceImpl implements QueryPathwayService {

	@Autowired
	PathwayDao pwayDao;
	
	@Override
	public Graph queryPathway(String[] ids, PathwayQueryType type) {
		Set<Pathway> result = null;
		Graph g = new Graph();
		switch (type) {
		case SINGLE_WAYS:
			result = pwayDao.getMultiPathway(ids);
			g = ModelTransferUtil.sglpathways2graph(result);
			break;
		case SINGLE_GENES:
//			result = pwayDao.getPathwayByGene(ids);
			break;
		case ALL_PATHWAYS:
			g = ModelTransferUtil.allpathways2graph();
		default:
			break;
		}
		
		
		
		return g;
		
	}

	
	
}
