package com.serviceImpl;

import java.util.HashSet;
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
	public Graph queryPhen(Map<String,Boolean> queryMap, PhenQueryType type,
			Map<String, String> param) {

		Set<PNode> result = null;
		
		String[]  ids = queryMap.keySet().toArray(new String[1]);
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
		
		//如果level参数不为空，则根据level过滤结果
		String levels = param.get("levels");
		Set<PNode> nodeInlevels = new HashSet<PNode>();
		if(levels !=null && levels != "")
		{
			Map<String,Set<PNode>> nodes = pheDao.getPNodeByMultiLevel1(levels);
			for (Set<PNode> set : nodes.values()) {
				nodeInlevels.addAll(set);
			}
			if(nodeInlevels.size()!=0)
			{
				result.retainAll(nodeInlevels);
			}
		}
		Graph g = ModelTransferUtil.pNode2graph(result,queryMap);
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
