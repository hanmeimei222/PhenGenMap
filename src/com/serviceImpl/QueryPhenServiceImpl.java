package com.serviceImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.constant.PhenQueryType;
import com.dao.PhenDao;
import com.model.PNode;
import com.model.Pathway;
import com.model.cytoscape.Graph;
import com.model.ztree.ZtreeNode;
import com.service.QueryPhenService;
import com.util.ModelTransferUtil;

@Service
public class QueryPhenServiceImpl implements QueryPhenService{

	@Autowired
	PhenDao pheDao;
	
	
	@Override
	public Map<String,Set<PNode>> expandPhens(Map<String, Boolean> queryMap) {

		Map<String,Set<PNode>> map =new HashMap<String, Set<PNode>>();
		Set<String> query = queryMap.keySet();
		for (String q : query) {
			Set<String> param = new HashSet<String>();
			param.add(q);
			Set<PNode>result = pheDao.getPostNodes(param);
			map.put(q, result);
		}
		return map;
	}
	@Override
	public Set<PNode> getPhenInfo(Map<String,Boolean> queryMap) {
		Set<String>  ids = queryMap.keySet();
		Set<PNode> result = pheDao.getMultiPNode(ids);

		return result;
	}
	
	@Override
	public Graph queryPhen(Map<String,Boolean> queryMap, PhenQueryType type,
			Map<String, String> param) {

		Set<PNode> result = null;
		
		Set<String>  ids = queryMap.keySet();
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
	public Set<PNode> aucoComplete(String query) {
		Set<PNode> result =pheDao.getAutoCompleteNodes(query);
		return result;
//		return ModelTransferUtil.pNode2CytoNode(result);
	}
	@Override
	public List<ZtreeNode> getphenztree() {
		List<ZtreeNode> phenztree;
		
		PNode root = pheDao.getSinglePNodeById("MP:0000001");
		
		phenztree = ModelTransferUtil.allphen2ztree(root);
		return phenztree;

	}
}
