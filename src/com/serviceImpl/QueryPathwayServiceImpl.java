package com.serviceImpl;

import java.util.Map;
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
	public Graph queryPathway(Map<String,Boolean> queryMap, PathwayQueryType type) {
		Set<Pathway> result = null;
		Graph g = new Graph();
		Set<String> ids = queryMap.keySet();
		switch (type) {
		case SINGLE_WAYS:
			result = pwayDao.getMultiPathway(ids);
			g = ModelTransferUtil.sglpathways2graph(result,queryMap);
			break;
		case SINGLE_GENES:
//			result = pwayDao.getPathwayByGene(ids);
			break;
		case ALL_PATHWAYS:
			Map<String,String>cls2_cls1 = pwayDao.getCls2cls1Map();
			Map<String,Set<Pathway>>cls2map = pwayDao.getCls2PathwayMap();
			g = ModelTransferUtil.allpathways2graph(cls2_cls1,cls2map);
		default:
			break;
		}
		return g;
		
	}

	
	
}
