package com.serviceImpl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.PhenDao;
import com.model.Line;
import com.model.PNode;
import com.service.QueryPhenService;
import com.util.ModelTransferUtil;

@Service
public class QueryPhenServiceImpl implements QueryPhenService{

	@Autowired
	PhenDao pheDao;
	@Override
	public Set<Line> getNStepNode(String id, int n) {
		Set<PNode> set =  pheDao.getNStepNode(id, n);
		//将查询到的节点集合以Set<Line>的形式返回到controller
		return ModelTransferUtil.pNode2Line(set);
		
	}
	
	@Override
	public Set<Line> getInterlevelsAndRoot(String id, String levels) {
		// TODO Auto-generated method stub
		
		
		Set<PNode> postNodes = pheDao.getPostNodes(id);
		Map<String,Set<PNode>> levelmap =pheDao.getPNodeByMultiLevel1(levels);
		Set<PNode> levelNodes = new HashSet<PNode>();
		for (Set<PNode> set : levelmap.values()) {
			levelNodes.addAll(set);
		}
		levelNodes.retainAll(postNodes);
		
		return ModelTransferUtil.pNode2Line(levelNodes);
	}
}
