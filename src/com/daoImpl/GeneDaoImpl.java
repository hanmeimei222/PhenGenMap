package com.daoImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dao.GeneDao;
import com.dao.PathwayDao;
import com.global.GlobalData;
import com.model.GNode;
import com.model.PNode;
import com.model.PPINode;
import com.model.Pathway;

@Repository
public final class GeneDaoImpl implements GeneDao{

	@Autowired
	PathwayDao pathwayDao;

	
	@Override
	public Set<GNode> getAllGenes() {
		
		Set<GNode> result = new HashSet<GNode>();
		result.addAll(GlobalData.allgnodes.values());
		
		return result;
	}
	
	@Override
	public Map<GNode, Map<PPINode, Boolean>> getAssociatedPPI(Set<GNode> gNodes) {
	
		 Map<GNode, Map<PPINode, Boolean>> map = new HashMap<GNode, Map<PPINode,Boolean>>();
			for (GNode gn : gNodes) {
				Map<PPINode,Boolean>pmap = GlobalData.gene_ppi_map.get(gn);
				if(pmap!=null)
				{
					map.put(gn, pmap);
				}
			}
			
			return map;
	}
	
	@Override
	public Set<GNode> getGnodesBySymbol(Set<String> symbols) {
		// TODO Auto-generated method stub
		Set<GNode> set = new HashSet<GNode>();
		for (String string : symbols) {
			GNode gn = GlobalData.allgnodes.get(string.toUpperCase());
			if(gn!=null)
			{
				set.add(gn);
			}
		}
		
		return set;
	}

	@Override
	public Map<GNode, Map<PNode, Boolean>> getAssociatedMP(Set<GNode> gNodes) {
		
		 Map<GNode, Map<PNode, Boolean>> map = new HashMap<GNode, Map<PNode,Boolean>>();
		for (GNode gn : gNodes) {
			Map<PNode,Boolean>pmap = GlobalData.g_p_map.get(gn);
			if(pmap!=null)
			{
				map.put(gn, pmap);
			}
		}
		
		return map;
	}

	@Override
	public Map<GNode, Map<Pathway, Boolean>> getAssociatedPathway(
			Set<GNode> gNodes) {
		
		Map<GNode, Map<Pathway, Boolean>> map = new HashMap<GNode, Map<Pathway,Boolean>>();
		for (GNode gene : gNodes) {
			if(gene == null)
			{
				continue;
			}
			String symbol = gene.getSymbol_name();
			Map<Pathway,Boolean> pathway = pathwayDao.getPathwayByGene(symbol);
			map.put(gene, pathway);
		}
		
		
		return map;
	}
}
