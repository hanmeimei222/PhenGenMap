package com.daoImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.dao.PPIDao;
import com.global.GlobalData;
import com.model.GNode;
import com.model.PPINode;

@Repository
public class PPIDaoImpl implements PPIDao{

	@Override
	public Map<PPINode, Map<GNode, Boolean>> getConnectedGenes(
			Set<PPINode> nodes) {
		
		Map<PPINode, Map<GNode, Boolean>> result = new HashMap<PPINode, Map<GNode,Boolean>>();
		for (PPINode ppi : nodes) {
			Map<GNode, Boolean> map = GlobalData.ppi_gene_map.get(ppi);
			if(map!=null)
			{
				result.put(ppi, map);
			}
		}
		return result;
	}
	
	@Override
	public Set<PPINode> getPPINodes(Set<String> entrezId) {
		
		Set<PPINode> ppiNodes = new HashSet<PPINode>();
		for (String id : entrezId) {
			
			PPINode node = GlobalData.allppis.get(id);
			if(node != null)
			{
				ppiNodes.add(node);
			}
		}
		return ppiNodes;
	}
	
	@Override
	public Map<PPINode, Map<PPINode, Boolean>> getConnectedPPI(
			Set<PPINode> nodes) {
		Map<PPINode, Map<PPINode, Boolean>> result = new HashMap<PPINode, Map<PPINode,Boolean>>();
		for (PPINode ppi : nodes) {
			Map<PPINode, Boolean> map = GlobalData.ppi_ppi_map.get(ppi);
			if(map!=null)
			{
				result.put(ppi, map);
			}
		}
		return result;
	}
}
