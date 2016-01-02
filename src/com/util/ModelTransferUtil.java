package com.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.model.PNode;
import com.model.Pathway;
import com.model.cytoscape.CytoEdge;
import com.model.cytoscape.CytoNode;
import com.model.cytoscape.Edge;
import com.model.cytoscape.Graph;
import com.model.cytoscape.Node;

public class ModelTransferUtil {
	
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
	
	public static Graph sglpathways2graph(Set<Pathway> set,Map<String,Boolean> queryInput){
		Graph g = new Graph();
		List<CytoNode> nodes = new ArrayList<CytoNode>();
		List<CytoEdge> edges = new ArrayList<CytoEdge>();
		g.setEdges(edges);
		g.setNodes(nodes);
		
		
		
		for (Pathway pway : set)
		{
			String pwayid = pway.getPw_id();
			
			//把所有节点加到nodes集合中
			boolean isQuery = false;
			if(queryInput!=null)
			{
				if(queryInput.containsKey(pwayid))
				{
					isQuery = true;
				}
			}
			nodes.add(new CytoNode(new Node(pway.getPw_id(), pway.getPw_name(),isQuery)));
		}
		
		
		return g;
	}
	
	public static Graph allpathways2graph(Map<String,String>cls2_cls1,Map<String,Set<Pathway>>cls2map){
		Graph g = new Graph();
		List<CytoNode> nodes = new ArrayList<CytoNode>();
		List<CytoEdge> edges = new ArrayList<CytoEdge>();
		g.setEdges(edges);
		g.setNodes(nodes);
		
//		先构造class1和class2的节点和边，再嵌套构造class2和它的pathways之间的节点和边
//		Map<String,Map<String,Map<Pathway,Boolean>>>
		Set<String>class2set = cls2_cls1.keySet();
		
		for (String cls2 : class2set) {
			
			//构造cls1和cls2的节点和边
			nodes.add(new CytoNode(new Node(cls2, cls2)));
			String cls1 = cls2_cls1.get(cls2);
			nodes.add(new CytoNode(new Node(cls1, cls1)));
			
			Edge l = new Edge(cls1, cls2);
			edges.add(new CytoEdge(l));
			
			//构造cls2和它的pathways之间的节点和边
			Set<Pathway>pwset = cls2map.get(cls2);
			
			for (Pathway pathway : pwset) {
				nodes.add(new CytoNode(new Node(pathway.getPw_id(), pathway.getPw_name())));
				Edge e = new Edge(cls2, pathway.getPw_id());
				edges.add(new CytoEdge(e));
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
