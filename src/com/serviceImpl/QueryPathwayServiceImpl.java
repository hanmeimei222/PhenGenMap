package com.serviceImpl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.constant.NodeType;
import com.dao.GeneDao;
import com.dao.PathwayDao;
import com.model.GNode;
import com.model.Pathway;
import com.model.cytoscape.CytoEdge;
import com.model.cytoscape.CytoNode;
import com.model.cytoscape.Edge;
import com.model.cytoscape.Graph;
import com.model.cytoscape.Node;
import com.model.d3.TreeNode;
import com.service.QueryPathwayService;
import com.util.ModelTransferUtil;

@Service
public class QueryPathwayServiceImpl implements QueryPathwayService {

	@Autowired
	PathwayDao pwayDao;

	@Autowired
	GeneDao gDao;


	@Override
	public TreeNode allPathway(){
		TreeNode pathwaytree = new TreeNode();
		Map<String, Map<String, Map<Pathway, Boolean>>> allpathways = pwayDao.getAllPathways();
		pathwaytree = ModelTransferUtil.allpathways2graph(allpathways);
		return pathwaytree;
	}


	@Override
	public Graph getGenePathwayAsso(Map<NodeType, Map<String, Boolean>> map) {
		Set<GNode> gNodes = null;
		Set<Pathway> pathways = null;
		for (NodeType type : map.keySet()) {
			switch (type) {
			case GENE:
				Set<String> symbols = map.get(type).keySet();
				gNodes= gDao.getGnodesBySymbol(symbols);
				break;
			case PATHWAY:
				Set<String> pathwayIds = map.get(type).keySet();
				pathways = pwayDao.getMultiPathway(pathwayIds);
				break;
			default:
				break;
			}
		}

		// 如果gene为空 则返回输入pathway包含的gene
		if(gNodes == null && pathways!=null)
		{
			gNodes = new HashSet<GNode>();
			for (Pathway pathway : pathways) {
				gNodes.addAll(pathway.getSymbols().keySet());
			}
		}

		//如果未输入pathway，则返回输入基因关联的pathway
		if(pathways==null&&gNodes!=null)
		{
			pathways = new HashSet<Pathway>();
			//			用基因集合查相关的pathway
			Map<GNode,Map<Pathway,Boolean>> gene_pathways = gDao.getAssociatedPathway(gNodes);

			if(gene_pathways!=null)
			{
				Set<GNode> genes= gene_pathways.keySet();
				for (GNode gn : genes) {

					//构建一个关联
					Map<Pathway,Boolean> pathwayMap = gene_pathways.get(gn);
					pathways.addAll(pathwayMap.keySet());
				}
			}
		}

		//绘图，构建pathway指向gnode的边
		Graph g = new Graph();
		Set<CytoNode> nodes = new HashSet<CytoNode>();
		Set<CytoEdge> edges = new HashSet<CytoEdge>();
		g.setNodes(nodes);
		g.setEdges(edges);

		Map<String,Boolean>pathwayIds = map.get(NodeType.PATHWAY);
		Map<String,Boolean> symbols = map.get(NodeType.GENE);
		//构造基因节点
		if(gNodes!=null){
			CytoNode cnode =new CytoNode(new Node("gene","gene","gene",null,false));
			nodes.add(cnode);
			for (GNode gn : gNodes){
				boolean isQuery = false;
				if(gn == null)
				{
					continue;
				}
				String symbol = gn.getSymbol_name();
				if(symbols!=null && symbols.containsKey(symbol))
				{
					isQuery = true;
				}
				cnode =new CytoNode(new Node(gn.getSymbol_name(), gn.getId(),"gene","gene",isQuery));
				nodes.add(cnode);
			}
		}
		
		if(gNodes!=null && pathways !=null)
		{
			edges.add(new CytoEdge(new Edge("pathway", "gene","typelink")));
		}

		//构建一个父类的pathway节点
		if(pathways!=null)
		{

			CytoNode cnode =new CytoNode(new Node("pathway","pathway","pathway",null,false));
			nodes.add(cnode);

		}
		for (Pathway pathway : pathways) {
			//构建pathway的节点
			boolean queryInput = false;
			String pathwayId = pathway.getPw_id();
			if(pathwayIds!=null && pathwayIds.containsKey(pathwayId))
			{
				queryInput = true;
			}
			CytoNode cnode =new CytoNode(new Node(pathwayId,pathway.getPw_name(),"pathway","pathway",queryInput));
			nodes.add(cnode);


			Map<GNode, Boolean> pathway_genes = pathway.getSymbols();
			for (GNode gn : gNodes) {
				//构造pathway和基因的关联
				if(pathway_genes.containsKey(gn))
				{
					CytoEdge pathwayGeneEdge =new CytoEdge(new Edge(pathwayId,gn.getSymbol_name(),"gpathwaylink"));
					edges.add(pathwayGeneEdge);
				}

			}
		}
		return g;
	}



	//	@Override
	//	public TreeNode queryPathway(Map<String,Boolean> queryMap, PathwayQueryType type) {
	////		Set<Pathway> result = null;
	////		Graph g = new Graph();
	//		TreeNode pathwaytree = new TreeNode();
	////		Set<String> ids = queryMap.keySet();
	//		switch (type) {
	//		case SINGLE_WAYS:
	////			result = pwayDao.getMultiPathway(ids);
	////			g = ModelTransferUtil.sglpathways2graph(result,queryMap);
	//			break;
	//		case SINGLE_GENES:
	////			result = pwayDao.getPathwayByGene(ids);
	//			break;
	//		case ALL_PATHWAYS:
	////			Map<String,String>cls2_cls1 = pwayDao.getCls2cls1Map();
	////			Map<String,Set<Pathway>>cls2map = pwayDao.getCls2PathwayMap();
	//			Map<String, Map<String, Map<Pathway, Boolean>>> allpathways = pwayDao.getAllPathways();
	//			pathwaytree = ModelTransferUtil.allpathways2graph(allpathways);
	//			
	//		default:
	//			break;
	//		}
	//		return pathwaytree;
	//		
	//	}
}
