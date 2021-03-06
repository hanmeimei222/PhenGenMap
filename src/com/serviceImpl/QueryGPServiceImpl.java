package com.serviceImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.constant.NodeType;
import com.dao.GPDao;
import com.dao.GeneDao;
import com.dao.PPIDao;
import com.dao.PathwayDao;
import com.dao.PhenDao;
import com.model.GNode;
import com.model.PNode;
import com.model.PPINode;
import com.model.Pathway;
import com.model.cytoscape.CytoEdge;
import com.model.cytoscape.CytoNode;
import com.model.cytoscape.Edge;
import com.model.cytoscape.Graph;
import com.model.cytoscape.Node;
import com.model.d3.D3Graph;
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

	@Autowired
	PPIDao ppiDao;


	@Override
	public D3Graph getGlobalAsso(Set<PNode> phenNodes,Map<String,Set<Pathway>> queryPathways,String selected_type) {
//		@Override
		//		public D3Graph getGlobalAsso(Map<String,Set<PNode>> phenNodes,Map<String,Set<Pathway>> queryPathways,String selected_type) {
				
		//MP节点
//		Set<PNode> pNodes = new HashSet<PNode>();
//		Set<String> ancesstors = phenNodes.keySet();
//		for (String anc : ancesstors) {
//			Set<PNode> nodes= phenNodes.get(anc);
//			for (PNode n : nodes) {
//				n.setGroup(anc);
//				pNodes.add(n);
//			}
//		}
		
		Set<PNode> pNodes = phenNodes;
		
		//gene节点
		Set<GNode> gNodes = new HashSet<GNode>();
		Set<String> pathwayClass = queryPathways.keySet();
		for (String cla : pathwayClass) {
			Set<Pathway> ps = queryPathways.get(cla);
			if(ps!=null)
			{
				for (Pathway pathway : ps) {
					Map<GNode, Boolean> symbols = pathway.getSymbols();
					if(symbols!=null)
					{
						Set<GNode> genes = symbols.keySet();
						for (GNode g : genes) {
							g.setGroup(cla);
							gNodes.add(g);
						}
					}
				}
			}
		}
		
		//ppi节点
		Set<PPINode>ppis = new HashSet<PPINode>();
		Map<GNode,Map<PPINode,Boolean>> gene_ppis = gDao.getAssociatedPPI(gNodes);
		Set<GNode> gnodes = gene_ppis.keySet();
		for (GNode gNode : gnodes) {
			Map<PPINode,Boolean> mpMap = gene_ppis.get(gNode);
			ppis.addAll(mpMap.keySet());
		}
		
		//pathway节点
		Set<String> pathwayInput=queryPathways.keySet();
		Set<Pathway>pathways = new HashSet<Pathway>();
		for (String p : pathwayInput) {
			pathways.addAll(queryPathways.get(p));
		}

		
		//构建用户绘图的边
		Graph g = new Graph();
		Set<CytoNode> nodes = new HashSet<CytoNode>();
		Set<CytoEdge> edges = new HashSet<CytoEdge>();
		g.setNodes(nodes);
		g.setEdges(edges);

		
		//g-ppi(16),g-mp(8),g-pathway(4),mp-mp(2),ppi-ppi(1)
		int type = Integer.parseInt(selected_type,2);
		
		
		//基因表型关联和基因节点
		Graph gpgraph = null;
		//基因-pathway关联和pathway节点
		Graph pathwayGenegraph = null;
		//mp-mp关联和mp节点
		Graph ppgraph = null;
		//基因-ppi关联
		Graph gppigraph =null;
		//ppi-ppi关联和ppi节点
		Graph ppigraph =null;
		//要包含基因节点
		if((type&16)>0||(type&8)>0||(type&4)>0)
		{
			gpgraph = getAssoByPhenoGene(gNodes, pNodes,new HashMap<String, Boolean>(),new HashMap<String, Boolean>());
			//gene节点
			nodes.addAll(gpgraph.getNodes());
			
			//pathway-gene
			if((type&4)>0)
			{
				pathwayGenegraph = getAssoByPathwayGene(gNodes, pathways, new HashMap<String, Boolean>());
				nodes.addAll(pathwayGenegraph.getNodes());
				edges.addAll(pathwayGenegraph.getEdges());
			}
			//mp-gene
			if((type&8)>0)
			{
				ppgraph = getAssoByPhenoPhen(pNodes,new HashMap<String, Boolean>());
				nodes.addAll(ppgraph.getNodes());
				//表型基因关联节点
				edges.addAll(gpgraph.getEdges());
			}
			//ppi-gene
			if((type&16)>0)
			{
				gppigraph = getAssoByPPIGene(gNodes, ppis,new HashMap<String, Boolean>(), new HashMap<String, Boolean>());
				//gene-ppi的关联
				edges.addAll(gppigraph.getEdges());
				ppigraph = getAssoByPPI2PPI(ppis, new HashMap<String, Boolean>());
				//ppi的node
				nodes.addAll(ppigraph.getNodes());
			}
		}
		//mp-mp
		if((type&2)>0)
		{
			//构建mp之间的
			if(ppgraph==null)
			{
				ppgraph = getAssoByPhenoPhen(pNodes,new HashMap<String, Boolean>());
				nodes.addAll(ppgraph.getNodes());
			}
			//表型关联
			edges.addAll(ppgraph.getEdges());
		}
		//ppi-ppi
		if((type&1)>0)
		{
			//构建ppi-ppi之间的
			if(ppigraph==null)
			{
				ppigraph = getAssoByPPI2PPI(ppis, new HashMap<String, Boolean>());
			nodes.addAll(ppigraph.getNodes());
			}
			//ppi之间的关联
			edges.addAll(ppigraph.getEdges());
		}
		
		D3Graph d3g = ModelTransferUtil.cytoGraph2D3Graph(g);
		return d3g;
	}

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
		Set<PPINode> ppis = null;

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
			case PPI:
				Set<String> entrezIds = map.get(type).keySet();
				ppis = ppiDao.getPPINodes(entrezIds);
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
					if(geneMap==null){
						continue;
					}
					gNodes.addAll(geneMap.keySet());
				}
			}

			if(pathways!=null)
			{
				for (Pathway pathway : pathways) {
					gNodes.addAll(pathway.getSymbols().keySet());
				}
			}
			if(ppis!=null)
			{
				Map<PPINode, Map<GNode, Boolean>> genes = ppiDao.getConnectedGenes(ppis);
				for ( Map<GNode, Boolean> connectedGenes : genes.values()) {
					gNodes.addAll(connectedGenes.keySet());
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

		if(ppis ==null)
		{
			ppis = new HashSet<PPINode>();
			Map<GNode,Map<PPINode,Boolean>> gene_ppis = gDao.getAssociatedPPI(gNodes);

			Set<GNode> gnodes = gene_ppis.keySet();
			for (GNode gNode : gnodes) {
				Map<PPINode,Boolean> mpMap = gene_ppis.get(gNode);
				ppis.addAll(mpMap.keySet());
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

		Graph gppigraph = getAssoByPPIGene(gNodes, ppis, map.get(NodeType.GENE), map.get(NodeType.PPI));

		Graph ppigraph = getAssoByPPI2PPI(ppis, map.get(NodeType.PPI));

		nodes.addAll(ppgraph.getNodes());
		nodes.addAll(gpgraph.getNodes());
		nodes.addAll(pathwayGenegraph.getNodes());
		nodes.addAll(gppigraph.getNodes());
		nodes.addAll(ppigraph.getNodes());

		edges.addAll(ppgraph.getEdges());
		edges.addAll(gpgraph.getEdges());
		edges.addAll(pathwayGenegraph.getEdges());
		edges.addAll(gppigraph.getEdges());
		edges.addAll(ppigraph.getEdges());

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
			if(associatedGenes==null)
			{
				continue;
			}
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
		if(genes!=null && genes.size()!=0)
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
				Node n = new Node( gNode.getSymbol_name(),gNode.getSymbol_name(),"gene","gene",isQuery);
				n.setGroup(gNode.getGroup());
				cnode =new CytoNode(n);
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
		if(pathways!=null && pathways.size()!=0)
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
			Node n =new Node(pathwayId,pathway.getPw_name(),"pathway","pathway",queryInput);
			n.setGroup(pathway.getClass_1());
			CytoNode cnode =new CytoNode(n);
			nodes.add(cnode);

			//构造pathway和基因的关联
			Map<GNode, Boolean> pathway_genes = pathway.getSymbols();
			for (GNode gn : genes) {
				if(pathway_genes!=null && pathway_genes.containsKey(gn))
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

	/*
	 * 构建ppi和ppi之间的关联
	 */
	public Graph getAssoByPPI2PPI(Set<PPINode> ppis,Map<String,Boolean> entrezIds)
	{
		Graph g = new Graph();
		Set<CytoNode> nodes = new HashSet<CytoNode>();
		Set<CytoEdge> edges = new HashSet<CytoEdge>();
		g.setEdges(edges);
		g.setNodes(nodes);

		Map<PPINode,Map<PPINode,Boolean>> connectedMap = ppiDao.getConnectedPPI(ppis);

		//构建一个父类的ppi节点
		if(ppis!=null && ppis.size()!=0)
		{
			CytoNode cnode =new CytoNode(new Node("ppi","ppi","ppi",null,false));
			nodes.add(cnode);
		}
		for (PPINode ppi : ppis) {
			//构建pathway的节点
			boolean queryInput = false;
			String entrezId = ppi.getId();

			if(entrezIds!=null && entrezIds.containsKey(entrezId))
			{
				queryInput = true;
			}
			Node n =new Node(entrezId,entrezId,"ppi","ppi",queryInput);
			n.setGroup("ppi");
			CytoNode cnode =new CytoNode(n);
			nodes.add(cnode);

			//构造ppi和ppi的关联
			Map<PPINode,Boolean> map = connectedMap.get(ppi);
			if(map == null)
			{
				continue;
			}
			for (PPINode other : map.keySet()) {
				if(ppis.contains(other))
				{
					CytoEdge ppiEdge =new CytoEdge(new Edge(entrezId,other.getId(),"ppi2ppilink"));
					edges.add(ppiEdge);
				}
			}

		}

		return g;
	}


	public Graph getAssoByPPIGene(Set<GNode> genes,Set<PPINode> ppi,Map<String,Boolean> symbols,Map<String,Boolean> entrezId)
	{
		Graph g = new Graph();
		Set<CytoNode> nodes = new HashSet<CytoNode>();
		Set<CytoEdge> edges = new HashSet<CytoEdge>();
		g.setEdges(edges);
		g.setNodes(nodes);

		if(genes!=null && ppi !=null)
		{
			edges.add(new CytoEdge(new Edge("gene", "ppi","typelink")));
		}

		Map<PPINode,Map<GNode,Boolean>> mp_genes = ppiDao.getConnectedGenes(ppi);
		for (PPINode n : ppi) {
			Map<GNode,Boolean> associatedGenes = mp_genes.get(n);
			if(associatedGenes == null)
			{
				continue;
			}
			for (GNode gn : genes) {
				if(gn==null)
				{
					continue;
				}
				if(associatedGenes.containsKey(gn))
				{
					//构造一条边
					edges.add(new CytoEdge(new Edge(gn.getSymbol_name(), n.getId(), "gppilink")));
				}
			}
		}

		return g;
	}

}
