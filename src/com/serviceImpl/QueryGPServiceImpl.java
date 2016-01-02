package com.serviceImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.GPDao;
import com.model.GPGraph;
import com.model.cytoscape.Graph;
import com.service.QueryGPService;
import com.util.ModelTransferUtil;


@Service
public class QueryGPServiceImpl implements QueryGPService{

	@Autowired
	GPDao gpDao;
	@Override
	public Graph getAssoByPhenoGene(Map<String, Boolean> pids,
			Map<String, Boolean> symbols) {
		GPGraph graph = gpDao.getAssoByPhenoGene(pids.keySet(), symbols.keySet());

		return ModelTransferUtil.gpgraph2Graph(graph,pids,symbols);		
	}
}
