package com.serviceImpl;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.GPDao;
import com.dao.PhenDao;
import com.model.GPGraph;
import com.model.PNode;
import com.model.cytoscape.Graph;
import com.service.QueryGPService;
import com.util.ModelTransferUtil;


@Service
public class QueryGPServiceImpl implements QueryGPService{

	@Autowired
	GPDao gpDao;
	
	@Autowired
	PhenDao pDao;
	
	@Override
	public Graph getAssoByPhenoGene(Map<String, Boolean> pids,
			Map<String, Boolean> symbols,boolean showMPA) {

		Graph result = null;
		GPGraph graph = null;
		
		if(pids.size()==0 &&symbols.size()!=0)
		{
			graph = gpDao.getAssoByGene(symbols.keySet());
		}
		else if(pids.size()!=0 &&symbols.size()==0)
		{
			graph = gpDao.getAssoByPheno(pids.keySet());
		}
		else{
			graph= gpDao.getAssoByPhenoGene(pids.keySet(), symbols.keySet());
		}
		result = ModelTransferUtil.gpgraph2Graph(graph,pids,symbols);
		if(showMPA)
		{
			Set<PNode> pnodes = graph.getPnodes();
			Graph pGraph = ModelTransferUtil.pNode2graph(pnodes, pids);
			result.getEdges().addAll(pGraph.getEdges());
		}

		return result;		
	}
}
