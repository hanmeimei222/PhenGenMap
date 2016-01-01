package com.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.model.PNode;
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
