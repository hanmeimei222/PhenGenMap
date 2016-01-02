package com.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.global.GlobalData;
import com.model.GNode;
import com.model.GPEdge;
import com.model.GPGraph;
import com.model.PNode;
import com.model.Pathway;
import com.model.cytoscape.CytoEdge;
import com.model.cytoscape.CytoNode;
import com.model.cytoscape.Edge;
import com.model.cytoscape.Graph;
import com.model.cytoscape.Node;

public class ModelTransferUtil {

	public static Graph gpgraph2Graph(GPGraph gpGraph,Map<String, Boolean> pids,
			Map<String, Boolean> symbols)
	{
		Graph g = new Graph();
		List<CytoNode> nodes = new ArrayList<CytoNode>();
		List<CytoEdge> edges = new ArrayList<CytoEdge>();
		g.setNodes(nodes);
		g.setEdges(edges);

		//在集合中额外添加一个gene节点表示所有基因的父类
		CytoNode cnode =new CytoNode(new Node("gene","gene",null,false));
		nodes.add(cnode);
		//在集合中额外添加一个phen节点表示所有表型的父类
		cnode =new CytoNode(new Node("phen","phen",null,false));
		nodes.add(cnode);
		edges.add(new CytoEdge(new Edge("gene", "phen")));

		Set<GNode>gNodes = gpGraph.getGnodes();
		if(gNodes!=null)
		{
			for (GNode gNode : gNodes) {
				boolean isQuery = false;
				if(gNode == null)
				{
					continue;
				}
				String symbol = gNode.getSymbol_name();
				if(symbols.containsKey(symbol))
				{
					isQuery = true;
				}
				cnode =new CytoNode(new Node(gNode.getSymbol_name(), gNode.getId(),"gene",isQuery));
				nodes.add(cnode);
			}
		}
		Set<PNode>pNodes = gpGraph.getPnodes();
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
					cnode =new CytoNode(new Node(level,level,"phen",false));
					nodes.add(cnode);
				}
				if(symbols.containsKey(pid))
				{
					isQuery = true;
				}
				cnode =new CytoNode(new Node(pNode.getPheno_id(),pNode.getPheno_name(),level,isQuery));
				nodes.add(cnode);
			}
		}
		Set<GPEdge>edge = gpGraph.getEdges();
		if(edge!=null)
		{
			for (GPEdge gpEdge : edge) {

				Edge l = new Edge(gpEdge.getSource().getSymbol_name(), gpEdge.getTarget().getPheno_id());
				edges.add(new CytoEdge(l));
			}
		}
		return g;
	}
	public static List<CytoNode> pNode2CytoNode(Set<PNode> set)
	{
		List<CytoNode> list= new ArrayList<CytoNode>();
		for (PNode pNode : set) {
			CytoNode cnode =new CytoNode(new Node(pNode.getPheno_id(), pNode.getPheno_name(),pNode.getPheno_level(),false));
			list.add(cnode);
		}
		return list;
	}
	public static Graph pNode2graph(Set<PNode> set,Map<String,Boolean> queryInput)
	{

		Graph g = new Graph();
		List<CytoNode> nodes = new ArrayList<CytoNode>();
		List<CytoEdge> edges = new ArrayList<CytoEdge>();
		g.setEdges(edges);
		g.setNodes(nodes);

		Map<String,PNode> mps = new HashMap<String,PNode>();
		for (PNode pNode : set)
		{
			mps.put(pNode.getPheno_id(), pNode);
			//把所有节点加到nodes集合中
			String pid = pNode.getPheno_id();
			boolean isQuery = false;
			if(queryInput!=null)
			{
				if(queryInput.containsKey(pid))
				{
					isQuery = true;
				}
			}
			nodes.add(new CytoNode(new Node(pid, pNode.getPheno_name(),pNode.getPheno_level(),isQuery)));
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
					Edge l = new Edge(fpid, pid);
					edges.add(new CytoEdge(l));
				}
			}
		}

		return g;
	}

	public static Graph sglpathways2graph(Set<Pathway> set){
		Graph g = new Graph();
		List<CytoNode> nodes = new ArrayList<CytoNode>();
		List<CytoEdge> edges = new ArrayList<CytoEdge>();
		g.setEdges(edges);
		g.setNodes(nodes);

		Map<String,Pathway> pways = new HashMap<String,Pathway>();

		for (Pathway pway : set)
		{
			pways.put(pway.getPw_id(), pway);
			//把所有节点加到nodes集合中
			nodes.add(new CytoNode(new Node(pway.getPw_id(), pway.getPw_name())));
		}


		return g;
	}

	public static Graph allpathways2graph(){
		Graph g = new Graph();
		List<CytoNode> nodes = new ArrayList<CytoNode>();
		List<CytoEdge> edges = new ArrayList<CytoEdge>();
		g.setEdges(edges);
		g.setNodes(nodes);

		//		先构造class1和class2的节点和边，再嵌套构造class2和它的pathways之间的节点和边
		//		Map<String,Map<String,Map<Pathway,Boolean>>>
		Set<String>class1set = GlobalData.classmap.keySet();
		for (String cls1 : class1set) {
			CytoNode cls1node = new CytoNode(new Node(cls1, cls1));
			nodes.add(cls1node);

			Map<String,Map<Pathway,Boolean>>class2map = GlobalData.classmap.get(cls1);
			Set<String>class2set = class2map.keySet();
			for (String cls2 : class2set) {
				CytoNode cls2node = new CytoNode(new Node(cls2, cls2));
				nodes.add(cls2node);
				Edge l = new Edge(cls1, cls2);
				edges.add(new CytoEdge(l));
				//				Map<Pathway,Boolean> pwmap = 

			}

		}



		return g;
	}

	//	public static Set<Edge> pNode2Line(Set<PNode> set)
	//	{
	//		
	//		Map<String,PNode> nodes = new HashMap<String,PNode>();
	//		for (PNode pNode : set)
	//		{
	//			nodes.put(pNode.getPheno_id(), pNode);
	//		}
	//		
	//		Set<Edge> lines = new HashSet<Edge>();
	//		
	//		Set<String> keys = nodes.keySet();
	//		
	//		for (String pid : keys) {
	//			PNode n = nodes.get(pid);
	//			Set<PNode> parents =n.getFather().keySet();
	//			for (PNode father : parents) {
	//				String fpid = father.getPheno_id();
	//				if(keys.contains(fpid))
	//				{
	//					Edge l = new Edge(pid, fpid);
	//					lines.add(l);
	//				}
	//			}
	//		}
	//		
	//		return lines;
	//	}


}
