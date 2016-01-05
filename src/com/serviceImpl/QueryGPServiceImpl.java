package com.serviceImpl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.constant.NodeType;
import com.dao.GPDao;
import com.dao.GeneDao;
import com.dao.PathwayDao;
import com.dao.PhenDao;
import com.model.GNode;
import com.model.PNode;
import com.model.Pathway;
import com.model.cytoscape.CytoEdge;
import com.model.cytoscape.CytoNode;
import com.model.cytoscape.Edge;
import com.model.cytoscape.Graph;
import com.model.cytoscape.Node;
import com.service.QueryAssoService;
import com.util.ModelTransferUtil;


@Service
public class QueryGPServiceImpl implements QueryAssoService{

	@Autowired
	GPDao gpDao;

	@Autowired
	PhenDao pDao;

	@Autowired
	GeneDao gDao;

	@Autowired
	PathwayDao pathwayDao;

	/**
	 * 策略：1.如果未输入gene集合，用其他节点关联的gene先构造出基因集合
	 * 2.在有了gene集合的情况下，对于未输入节点的类型，用gene关联的集合对其进行补充
	 */
	@Override
	public Graph getAsso(Map<NodeType,Map<String,Boolean>> map)
	{
		Set<PNode> pNodes = null;
		Set<GNode> gNodes = null;
		Set<Pathway> pathways = null;

		for (NodeType type : map.keySet()) {
			switch (type) {
			case MP:
				Set<String> mpIds=map.get(type).keySet();
				pNodes= pDao.getMultiPNode(mpIds);
				break;
			case GENE:
				Set<String> symbols = map.get(type).keySet();
				gNodes= gDao.getGnodesBySymbol(symbols);
				break;
			case PATHWAY:
				Set<String> pathwayIds = map.get(type).keySet();
				pathways = pathwayDao.getMultiPathway(pathwayIds);
				break;
			default:
				break;
			}
		}
		// 如果gene为空 根据其他节点来构造gene节点集合,取各自关联的gene的并集
		if(gNodes == null)
		{
			gNodes = new HashSet<GNode>();
			if(pNodes!=null)
			{
				//找到输入的mp关联的基因，
				Map<PNode,Map<GNode,Boolean>> mp_genes = pDao.getAssociatedGenes(pNodes);
				Set<PNode> pnodes = mp_genes.keySet();

				for (PNode pNode : pnodes) {
					Map<GNode,Boolean> geneMap = mp_genes.get(pNode);
					gNodes.addAll(geneMap.keySet());
				}
			}

			if(pathways!=null)
			{
				for (Pathway pathway : pathways) {
					gNodes.addAll(pathway.getSymbols().keySet());
				}
			}
		}
		//如果未输入表型，则返回输入基因关联的表型
		if(pNodes == null)
		{
			pNodes = new HashSet<PNode>();
			Map<GNode,Map<PNode,Boolean>> gene_mps = gDao.getAssociatedMP(gNodes);

			Set<GNode> gnodes = gene_mps.keySet();
			for (GNode gNode : gnodes) {
				Map<PNode,Boolean> mpMap = gene_mps.get(gNode);
				pNodes.addAll(mpMap.keySet());
			}
		}
		
		//如果未输入pathway，则返回输入基因关联的pathway
		if(pathways==null)
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
		
		//构建用户绘图的边
		Graph g = new Graph();
		Set<CytoNode> nodes = new HashSet<CytoNode>();
		Set<CytoEdge> edges = new HashSet<CytoEdge>();
		g.setNodes(nodes);
		g.setEdges(edges);

		//构建mp之间的
		Graph ppgraph = getAssoByPhenoPhen(pNodes,map.get(NodeType.MP));
		
		Graph gpgraph = getAssoByPhenoGene(gNodes, pNodes, map.get(NodeType.GENE), map.get(NodeType.MP));
		
		Graph pathwayGenegraph = getAssoByPathwayGene(gNodes, pathways, map.get(NodeType.PATHWAY));


		nodes.addAll(ppgraph.getNodes());
		nodes.addAll(gpgraph.getNodes());
		nodes.addAll(pathwayGenegraph.getNodes());
		
		edges.addAll(ppgraph.getEdges());
		edges.addAll(gpgraph.getEdges());
		edges.addAll(pathwayGenegraph.getEdges());
		
		return g;

	}


	public Graph getAssoByPhenoGene(Set<GNode> genes,Set<PNode> mps,Map<String,Boolean> symbols,Map<String,Boolean> mpIds)
	{
		Graph g = new Graph();
		Set<CytoNode> nodes = new HashSet<CytoNode>();
		Set<CytoEdge> edges = new HashSet<CytoEdge>();
		g.setEdges(edges);
		g.setNodes(nodes);
		
		if(genes!=null && mps !=null)
		{
			edges.add(new CytoEdge(new Edge("gene", "phen","typelink")));
		}

		Map<PNode,Map<GNode,Boolean>> mp_genes = pDao.getAssociatedGenes(mps);
		for (PNode pn : mps) {
			Map<GNode,Boolean> associatedGenes = mp_genes.get(pn);
			for (GNode gn : genes) {
				if(gn==null)
				{
					continue;
				}
				if(associatedGenes.containsKey(gn))
				{
					//构造一条边
					edges.add(new CytoEdge(new Edge(gn.getSymbol_name(), pn.getPheno_id(), "gplink")));
				}
			}
		}
		
		//将gene节点加入到CytoNodes中
		if(genes!=null)
		{
			CytoNode cnode =new CytoNode(new Node("gene","gene","gene",null,false));
			nodes.add(cnode);
			
			for (GNode gNode : genes) {
				boolean isQuery = false;
				if(gNode == null)
				{
					continue;
				}
				String symbol = gNode.getSymbol_name();
				if(symbols!=null && symbols.containsKey(symbol))
				{
					isQuery = true;
				}
				cnode =new CytoNode(new Node(gNode.getSymbol_name(), gNode.getId(),"gene","gene",isQuery));
				nodes.add(cnode);
			}
		}
		
		return g;
	}

	public Graph getAssoByPathwayGene(Set<GNode> genes,Set<Pathway> pathways,Map<String,Boolean> pathwayIds)
	{

		Graph g = new Graph();
		Set<CytoNode> nodes = new HashSet<CytoNode>();
		Set<CytoEdge> edges = new HashSet<CytoEdge>();
		g.setEdges(edges);
		g.setNodes(nodes);
		
		if(genes!=null && pathways !=null)
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

			//构造pathway和基因的关联
			Map<GNode, Boolean> pathway_genes = pathway.getSymbols();
			for (GNode gn : genes) {
				if(pathway_genes.containsKey(gn))
				{
					CytoEdge pathwayGeneEdge =new CytoEdge(new Edge(pathwayId,gn.getSymbol_name(),"gpathwaylink"));
					edges.add(pathwayGeneEdge);
				}

			}
		}
		return g;
	}


	public Graph getAssoByPhenoPhen(Set<PNode> pnodes,Map<String,Boolean> mpId)
	{
		return ModelTransferUtil.pNode2graph(pnodes,mpId);
	}
}
