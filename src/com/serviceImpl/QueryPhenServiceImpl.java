package com.serviceImpl;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.constant.PhenQueryType;
import com.dao.PhenDao;
import com.model.PNode;
import com.model.cytoscape.Graph;
import com.service.QueryPhenService;
import com.util.ModelTransferUtil;

@Service
public class QueryPhenServiceImpl implements QueryPhenService{

	@Autowired
	PhenDao pheDao;
	
	
	@Override
	public Graph queryPhen(String[] ids, PhenQueryType type,
			Map<String, String> param) {

		Set<PNode> result = null;
		switch (type) {
		case SINGLE_NODES:
			result = pheDao.getMultiPNode(ids);
			break;
		case POST_NODES:
			result = pheDao.getPostNodes(ids);
			break;
		case PRE_NODES:
			result = pheDao.getPreNodes(ids);
			break;
		case NSTEP_NODES:
			result = pheDao.getNStepNode(ids, Integer.valueOf(param.get("step")));
		default:
			break;
		}
		Graph g = ModelTransferUtil.pNode2graph(result);
		return g;
	}
	
	
	@Override
	public Graph getNStepNode(String id, int n) {
//		Set<PNode> set =  pheDao.getNStepNode(id, n);
//		//将查询到的节点集合以Set<Line>的形式返回到controller
//		return ModelTransferUtil.pNode2graph(set);
		return null;
	}
	
	@Override
	public Graph getInterlevelsAndRoot(String id, String levels) {
		// TODO Auto-generated method stub
	
//		Set<PNode> postNodes = pheDao.getPostNodes(id);
//		Map<String,Set<PNode>> levelmap =pheDao.getPNodeByMultiLevel1(levels);
//		Set<PNode> levelNodes = new HashSet<PNode>();
//		for (Set<PNode> set : levelmap.values()) {
//			levelNodes.addAll(set);
//		}
//		levelNodes.retainAll(postNodes);
//		
//		return ModelTransferUtil.pNode2graph(levelNodes);
		return null;
	}
}
