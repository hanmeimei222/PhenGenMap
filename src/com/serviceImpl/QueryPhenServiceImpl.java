package com.serviceImpl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.PhenDao;
import com.global.GlobalData;
import com.model.PNode;
import com.service.QueryPhenService;

@Service
public class QueryPhenServiceImpl implements QueryPhenService{

	@Autowired
	PhenDao pheDao;
	@Override
	public Set<PNode> getNStepNode(String id, int n) {
		// TODO Auto-generated method stub
		return pheDao.getNStepNode(GlobalData.allnodes, id, n);
	}
}
