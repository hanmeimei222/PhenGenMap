package com.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.global.GlobalData;
import com.model.PNode;
import com.model.Pathway;
import com.model.cytoscape.CytoEdge;
import com.model.cytoscape.CytoNode;
import com.model.cytoscape.Edge;
import com.model.cytoscape.Graph;
import com.model.cytoscape.Node;

public class ModelTransferUtil {
	
	public static Graph pNode2graph(Set<PNode> set)
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
			nodes.add(new CytoNode(new Node(pNode.getPheno_id(), pNode.getPheno_name())));
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
					Edge l = new Edge(pid, fpid);
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
