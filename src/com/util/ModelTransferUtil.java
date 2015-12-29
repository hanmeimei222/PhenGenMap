package com.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.model.Line;
import com.model.PNode;

public class ModelTransferUtil {
	
	public static Set<Line> pNode2Line(Set<PNode> set)
	{
		
		Map<String,PNode> nodes = new HashMap<String,PNode>();
		for (PNode pNode : set)
		{
			nodes.put(pNode.getPheno_id(), pNode);
		}
		
		Set<Line> lines = new HashSet<Line>();
		
		Set<String> keys = nodes.keySet();
		
		for (String pid : keys) {
			PNode n = nodes.get(pid);
			Set<PNode> parents =n.getFather().keySet();
			for (PNode father : parents) {
				String fpid = father.getPheno_id();
				if(keys.contains(fpid))
				{
					Line l = new Line(pid, fpid, "suit");
					lines.add(l);
				}
			}
		}
		
		return lines;
	}
}
