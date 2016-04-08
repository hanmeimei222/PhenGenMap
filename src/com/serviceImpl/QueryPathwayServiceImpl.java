package com.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import com.model.d3.PathwayGraphAndTree;
import com.model.d3.TreeNode;
import com.model.ztree.ZtreeNode;
import com.service.QueryPathwayService;
import com.util.ModelTransferUtil;

@Service
public class QueryPathwayServiceImpl implements QueryPathwayService {

	@Autowired
	PathwayDao pwayDao;

	@Autowired
	GeneDao gDao;


	@Override
	public List<ZtreeNode> getpathwayztree() {
		List<ZtreeNode> pathwayztree;
		Map<String, Map<String, Map<Pathway, Boolean>>> allpathways = pwayDao.getAllPathways();
		pathwayztree = ModelTransferUtil.allpathway2ztree(allpathways);
		return pathwayztree;
	}
	
	
	@Override
	public Map<String,Set<Pathway>> queryPathway(Map<String,Boolean> info, int level) {
		//输入的是pathway的id
		Map<String,Set<Pathway>> result = new HashMap<String, Set<Pathway>>();
		Set<String> ids = info.keySet();
		if(level==3)
		{
			for (String id : ids) {
				Set<String> set = new HashSet<String>();
				set.add(id);
				result.put(id,pwayDao.getMultiPathway(set));
			}
		}
		//输入的是pathway的第二类别
		else if(level==2)
		{	
			for (String id : ids) {
				Set<String> set = new HashSet<String>();
				set.add(id);
				result.put(id,pwayDao.getSubCatalog(set));
			}
		}
		//输入的是pathway的第一类别,根据一级类别获取所有pathway
		else if(level == 1)
		{
			for(String id : ids) {
				Set<String>set = new HashSet<String>();
				set.add(id);
				result.put(id,pwayDao.getMainCatalog(set));
			}

		}
		// 返回所有pathway,按二级类别返回
		else if(level ==-1)
		{
//			result=pwayDao.getCls2PathwayMap();
			return result;
		}
		return result;
	}

	@Override
	public TreeNode allPathway(){
		TreeNode pathwaytree = new TreeNode();
		Map<String, Map<String, Map<Pathway, Boolean>>> allpathways = pwayDao.getAllPathways();
		pathwaytree = ModelTransferUtil.allpathways2graph(allpathways);
		return pathwaytree;
	}


	@Override
	public PathwayGraphAndTree getGenePathwayAsso(Map<NodeType, Map<String, Boolean>> map) {
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

		//如果pathway为空，则返回输入基因关联的pathway
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

		//绘制pathway和gene之间的图，构建pathway指向gnode的边
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

		if(gNodes!=null && pathways !=null){
			edges.add(new CytoEdge(new Edge("pathway", "gene","typelink")));
		}

		//构建一个父类的pathway节点
		if(pathways!=null){
			CytoNode cnode =new CytoNode(new Node("pathway","pathway","pathway",null,false));
			nodes.add(cnode);
		}

		Set<TreeNode> simPathway = new HashSet<TreeNode>();
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

			TreeNode treenode = getRelatedPathway(pathway);

			simPathway.add(treenode);

		}

		PathwayGraphAndTree graphAndTree = new PathwayGraphAndTree(g, simPathway);
		return graphAndTree;
	}


	/**
	 * 得到pathway与他相关的其他pathway的排序
	 * @param Map<NodeType,Map<String,Boolean>>map
	 * @return TreeNode
	 */
	private TreeNode getRelatedPathway(Pathway pathway){
		TreeNode root = new TreeNode();
		root.setId("root");
		root.setName("root");

		List<TreeNode>children = new ArrayList<TreeNode>();
		root.setChildren(children);

		// 把当前pathway放进去作为第一个节点
		List<TreeNode> genes = new ArrayList<TreeNode>();
		for (GNode gNode : pathway.getSymbols().keySet()) {
			genes.add(new TreeNode(gNode.getSymbol_name()));
		}

		TreeNode node = new TreeNode(pathway.getPw_id(),pathway.getPw_name(),genes);

		children.add(node);

		List<Pathway> list = pwayDao.getRelatedPathwayRank(pathway);
		for (Pathway pathway2 : list) {
			genes = new ArrayList<TreeNode>();
			Set<GNode> simGeneSet = pwayDao.getCommonSymbols(pathway, pathway2);
			for (GNode gNode : simGeneSet) {
				genes.add(new TreeNode(gNode.getSymbol_name()));
			}
			node = new TreeNode(pathway2.getPw_id(),pathway2.getPw_name(),genes);
			children.add(node);

		}
		return root;
	}

	

}
