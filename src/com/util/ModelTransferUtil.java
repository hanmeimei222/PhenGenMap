package com.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.model.GNode;
import com.model.PNode;
import com.model.Pathway;
import com.model.cytoscape.CytoEdge;
import com.model.cytoscape.CytoNode;
import com.model.cytoscape.Edge;
import com.model.cytoscape.Graph;
import com.model.cytoscape.Node;
import com.model.d3.D3Graph;
import com.model.d3.Links;
import com.model.d3.TreeNode;

public class ModelTransferUtil {


	public static D3Graph cytoGraph2D3Graph(Graph g)
	{
		D3Graph d3g = new D3Graph();
		List<Links> links = new ArrayList<Links>(0);
		List<com.model.d3.Node> nodes = new ArrayList<com.model.d3.Node>();
		d3g.setNodes(nodes);
		d3g.setLinks(links);
		//记录节点的顺序
		Map<String,Integer> filterMap = new HashMap<String, Integer>();
		Set<CytoNode> cyNodes = g.getNodes();
		int count =0;
		for (CytoNode n : cyNodes) {
			String id = n.getData().getId();

			String name = n.getData().getName();
			String type= n.getData().getNodeType();
			com.model.d3.Node node = new com.model.d3.Node(id, name, n.getData().getGroup(),type);
			nodes.add(node);
			filterMap.put(id, count++);
		}
		Set<CytoEdge> cyedges = g.getEdges();
		for (CytoEdge edge : cyedges) {
			String source =edge.getData().getSource();
			String target = edge.getData().getTarget();
			String type = edge.getData().getEdgeType();
			if(source!=null && target!=null)
			{
				int sourceid = -1;
				if(filterMap.containsKey(source))
				{
					sourceid = filterMap.get(source);
				}
				int targetid = -1;
				if(filterMap.containsKey(target))
				{
					targetid = filterMap.get(target);
				}
				if(targetid!=-1)
				{
					Links l = new Links(sourceid, targetid, 0, type);
					links.add(l);
				}
			}
		}

		return d3g;
	}

	public static Set<CytoNode> pNode2CytoNode(Set<PNode> pnodes)
	{
		Set<CytoNode> set= new HashSet<CytoNode>();
		for (PNode pNode : pnodes) {
			CytoNode cnode =new CytoNode(new Node(pNode.getPheno_id(), pNode.getPheno_name(),"mp",pNode.getPheno_level(),false));
			set.add(cnode);
		}
		return set;
	}

	/**
	 * 将根据gene查到的Set<Pathway> 构建 gene-pathway的关联图
	 * @return
	 */
	//	public static Set<CytoNode> genePathways2Graph(Set<Pathway> pathways,String symbol){
	//		
	//		Graph g = new Graph();
	//		Set<CytoNode> nodes = new HashSet<CytoNode>();
	//		Set<CytoEdge> edges = new HashSet<CytoEdge>();
	//		g.setEdges(edges);
	//		g.setNodes(nodes);
	//		//建立一个pathway的父层节点
	//		if(pathways!=null && pathways.size()!=0)
	//		{
	//			CytoNode cnode =new CytoNode(new Node("pathway","pathway",null,false));
	//			nodes.add(cnode);
	//			CytoEdge edge = new CytoEdge(new Edge("pathway", "gene"));
	//			edges.add(edge);
	//		}
	//		for (Pathway pathway : pathways) {
	//			String pathwayId = pathway.getPw_id();
	//			CytoNode cnode =new CytoNode(new Node(pathwayId,pathway.getPw_name(),"pathway",false));
	//			nodes.add(cnode);
	//			CytoEdge edge = new CytoEdge(new Edge(pathwayId, symbol));
	//			edges.add(edge);
	//		}
	//
	//		return g;
	//	}


	/**
	 * 根据g-p 关联graph构建用于展示的cytoGraph，对于gene和phen分别建立父层，对phen不同level的节点建立各自的父层
	 * @param gpGraph
	 * @param pids
	 * @param symbols
	 * @return
	 */
	public static Set<CytoNode> gpgraph2Graph(Set<GNode>gNodes,Set<PNode>pNodes,Map<String, Boolean> pids,
			Map<String, Boolean> symbols)
			{

		Set<CytoNode> nodes = new HashSet<CytoNode>();
		//		Set<CytoEdge> edges = new HashSet<CytoEdge>();
		//		g.setNodes(nodes);
		//		g.setEdges(edges);

		//在集合中额外添加一个gene节点表示所有基因的父类
		CytoNode cnode =new CytoNode(new Node("gene","gene","gene",null,false));
		nodes.add(cnode);
		//在集合中额外添加一个phen节点表示所有表型的父类
		cnode =new CytoNode(new Node("phen","phen","mp",null,false));
		nodes.add(cnode);
		//		

		if(gNodes!=null)
		{
			for (GNode gNode : gNodes) {
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
		//构造一个临时的map用于标记本次返回包含了多少层
		if(pNodes!=null){
			Map<String,Boolean> isLevelContained = new HashMap<String, Boolean>();
			for (PNode pNode : pNodes) {
				boolean isQuery = false;
				String pid = pNode.getPheno_id();
				//为了简单，只取第一个level信息
				String level = pNode.getPheno_level().split(",")[0];
				if(!isLevelContained.containsKey(level))
				{
					//创建一个新的节点表示该level的父层
					cnode =new CytoNode(new Node(level,level,"mp","phen",false));
					nodes.add(cnode);
				}
				if(pids!=null && pids.containsKey(pid))
				{
					isQuery = true;
				}
				cnode =new CytoNode(new Node(pNode.getPheno_id(),pNode.getPheno_name(),"mp",level,isQuery));
				nodes.add(cnode);
			}
		}
		return nodes;
			}

	/**
	 * 根据一组phen 构建CytoGraph
	 * @param set
	 * @param queryInput
	 * @return
	 */
	public static Graph pNode2graph(Set<PNode> set,Map<String,Boolean> queryInput)
	{

		Graph g = new Graph();
		Set<CytoNode> nodes = new HashSet<CytoNode>();
		Set<CytoEdge> edges = new HashSet<CytoEdge>();
		g.setEdges(edges);
		g.setNodes(nodes);

		CytoNode cnode;
		if(set!=null && set.size()!=0)
		{
			cnode =new CytoNode(new Node("phen","phen","mp",null,false));
			nodes.add(cnode);
		}
		Map<String,PNode> mps = new HashMap<String,PNode>();
		Map<String,Boolean> isLevelContained = new HashMap<String, Boolean>();

		for (PNode pNode : set)
		{
			mps.put(pNode.getPheno_id(), pNode);
			//把所有节点加到nodes集合中

			//为了简单，只取第一个level信息
			String level = pNode.getPheno_level().split(",")[0];
			if(!isLevelContained.containsKey(level))
			{
				//创建一个新的节点表示该level的父层
				Node n = new Node(level,level,"mp","phen",false);
				n.setGroup(pNode.getGroup());
				cnode =new CytoNode(n);
				nodes.add(cnode);
			}

			String pid = pNode.getPheno_id();
			boolean isQuery = false;
			if(queryInput!=null)
			{
				if(queryInput.containsKey(pid))
				{
					isQuery = true;
				}
			}
			Node n = new Node(pid, pNode.getPheno_name(),"mp",level,isQuery);
			n.setGroup(pNode.getGroup());
			nodes.add(new CytoNode(n));
		}

		Set<String> keys = mps.keySet();
		//对于每一个MP，构建其
		for (String pid : keys) {
			PNode n = mps.get(pid);
			Set<PNode> parents =n.getFather().keySet();
			for (PNode father : parents) {
				String fpid = father.getPheno_id();
				if(keys.contains(fpid))
				{
					Edge l = new Edge(fpid, pid,"pplink");
					edges.add(new CytoEdge(l));
				}
			}
		}

		return g;
	}

	/**
	 * 使用d3.js绘出全体pathway的树形图
	 * @param allpathways
	 * @return
	 */
	public static TreeNode allpathways2graph(Map<String, Map<String, Map<Pathway, Boolean>>> allpathways){
		//构造根节点
		List<TreeNode> children = new ArrayList<TreeNode>();
		TreeNode root = new TreeNode("pathway","pathway",children);

		//六棵子树，逐个构造
		Set<String>class1set = allpathways.keySet();
		for (String class1 : class1set) {
			//构造class1 的 treenode
			List<TreeNode>cls1child = new ArrayList<TreeNode>();
			children.add(new TreeNode(class1,class1,cls1child));
			Map<String, Map<Pathway, Boolean>>class2map = allpathways.get(class1);
			Set<String>class2set = class2map.keySet();

			for (String class2 : class2set) {
				List<TreeNode>cls2child = new ArrayList<TreeNode>();
				cls1child.add(new TreeNode(class2,class2,cls2child));
				Set<Pathway>pwset = class2map.get(class2).keySet();
				for (Pathway pw : pwset) {

					cls2child.add(new TreeNode(pw.getPw_id(),pw.getPw_name(),null));

				}
			}
		}
		return root;
	}


	//	public static Graph sglpathways2graph(Set<Pathway> set,Map<String,Boolean> queryInput){
	//		Graph g = new Graph();
	//		Set<CytoNode> nodes = new HashSet<CytoNode>();
	//		Set<CytoEdge> edges = new HashSet<CytoEdge>();
	//		g.setEdges(edges);
	//		g.setNodes(nodes);
	//
	//		if(set!=null)
	//		{
	//			
	//			for (Pathway pway : set)
	//			{
	//				String pwayid = pway.getPw_id();
	//
	//				//把所有节点加到nodes集合中
	//				boolean isQuery = false;
	//				if(queryInput!=null)
	//				{
	//					if(queryInput.containsKey(pwayid))
	//					{
	//						isQuery = true;
	//					}
	//				}
	//				nodes.add(new CytoNode(new Node(pwayid, pway.getPw_name(),isQuery)));
	//			}
	//
	//		}
	//		return g;
	//	}

	//	public static Graph allpathways2graph(Map<String,String>cls2_cls1,Map<String,Set<Pathway>>cls2map){
	//		Graph g = new Graph();
	//		Set<CytoNode> nodes = new HashSet<CytoNode>();
	//		Set<CytoEdge> edges = new HashSet<CytoEdge>();
	//		g.setEdges(edges);
	//		g.setNodes(nodes);
	//
	//
	//		//		先构造class1和class2的节点和边，再嵌套构造class2和它的pathways之间的节点和边
	//		//		Map<String,Map<String,Map<Pathway,Boolean>>>
	//		Set<String>class2set = cls2_cls1.keySet();
	//		for (String cls2 : class2set) {
	//
	//			//构造cls1和cls2的节点和边
	//			nodes.add(new CytoNode(new Node(cls2, cls2)));
	//			String cls1 = cls2_cls1.get(cls2);
	//
	//			nodes.add(new CytoNode(new Node(cls1, cls1)));
	//
	//			Edge l = new Edge(cls1, cls2);
	//			edges.add(new CytoEdge(l));
	//
	//			//构造cls2和它的pathways之间的节点和边
	//			Set<Pathway>pwset = cls2map.get(cls2);
	//
	//			for (Pathway pathway : pwset) {
	//				nodes.add(new CytoNode(new Node(pathway.getPw_id(), pathway.getPw_name())));
	//				Edge e = new Edge(cls2, pathway.getPw_id());
	//				edges.add(new CytoEdge(e));
	//			}
	//		}
	//		return g;
	//	}
}
