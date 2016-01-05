package com.serviceImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.constant.PathwayQueryType;
import com.dao.PathwayDao;
import com.model.Pathway;
import com.model.d3.TreeNode;
import com.service.QueryPathwayService;
import com.util.ModelTransferUtil;

@Service
public class QueryPathwayServiceImpl implements QueryPathwayService {

	@Autowired
	PathwayDao pwayDao;
	
	@Override
	public TreeNode queryPathway(Map<String,Boolean> queryMap, PathwayQueryType type) {
//		Set<Pathway> result = null;
//		Graph g = new Graph();
		TreeNode pathwaytree = new TreeNode();
//		Set<String> ids = queryMap.keySet();
		switch (type) {
		case SINGLE_WAYS:
//			result = pwayDao.getMultiPathway(ids);
//			g = ModelTransferUtil.sglpathways2graph(result,queryMap);
			break;
		case SINGLE_GENES:
//			result = pwayDao.getPathwayByGene(ids);
			break;
		case ALL_PATHWAYS:
//			Map<String,String>cls2_cls1 = pwayDao.getCls2cls1Map();
//			Map<String,Set<Pathway>>cls2map = pwayDao.getCls2PathwayMap();
			Map<String, Map<String, Map<Pathway, Boolean>>> allpathways = pwayDao.getAllPathways();
			pathwaytree = ModelTransferUtil.allpathways2graph(allpathways);
			
		default:
			break;
		}
		return pathwaytree;
		
	}
}
