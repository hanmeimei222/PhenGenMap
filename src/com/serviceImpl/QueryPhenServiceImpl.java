package com.serviceImpl;

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
}
