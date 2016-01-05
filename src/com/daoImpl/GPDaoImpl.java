package com.daoImpl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.dao.GPDao;
import com.global.GlobalData;
import com.model.GPEdge;
import com.model.GNode;
import com.model.GPGraph;
import com.model.PNode;

@Repository
public class GPDaoImpl implements GPDao{
	/**
	 * 从表型开始查询
	 * 输入表型id集合，查出与它关联的GNode,相应Edge，返回查询结果的GPGraph
	 */
	@Override
	public GPGraph getAssoByPheno(Set<String>pids){
		GPGraph graph = new GPGraph();
		Set<PNode>pns = new HashSet<PNode>();
		Set<GPEdge>edges = new HashSet<GPEdge>();
		Set<GNode>gns = new HashSet<GNode>();

		graph.setGnodes(gns);
		graph.setPnodes(pns);
		graph.setEdges(edges);

		for (String pid : pids) {
			//构建点集
			PNode pn = GlobalData.allpnodes.get(pid);
			pns.add(pn);
			Map<GNode,Boolean>gmap = GlobalData.p_g_map.get(pn);
			Set<GNode>tmpgns = gmap.keySet();
			gns.addAll(tmpgns);

			//构建边集
			for (GNode gn : tmpgns) {
				if(gn == null)
				{
					continue;
				}
				GPEdge eg = new GPEdge(gn,pn,"gplink");
				edges.add(eg);
			}
		}
		return graph;
	}


	/**
	 * 从基因开始查询
	 * 输入基因symbol_name的集合，查出与它关联的PNode，相应的Edge，返回查询结果的GPGraph
	 */
	@Override
	public GPGraph getAssoByGene(Set<String>symbols){
		GPGraph graph = new GPGraph();
		Set<PNode>pns = new HashSet<PNode>();
		Set<GPEdge>edges = new HashSet<GPEdge>();
		Set<GNode>gns = new HashSet<GNode>();

		graph.setGnodes(gns);
		graph.setPnodes(pns);
		graph.setEdges(edges);

		for (String symbol : symbols) {
			//构建点集
			GNode gn = GlobalData.allgnodes.get(symbol.toUpperCase());
			gns.add(gn);
			Map<PNode,Boolean>pmap = GlobalData.g_p_map.get(gn);
			Set<PNode>tmppns = pmap.keySet();
			pns.addAll(tmppns);

			//构建边集
			for (PNode pn : tmppns) {
				GPEdge eg = new GPEdge(gn,pn,"gplink");
				edges.add(eg);
			}
		}
		return graph;
	}

	@Override
	public GPGraph getAssoByPhenoGene(Set<String> pids,Set<String> symbols){
		GPGraph graph = new GPGraph();
		Set<PNode>pns = new HashSet<PNode>();
		Set<GPEdge>edges = new HashSet<GPEdge>();
		Set<GNode>gns = new HashSet<GNode>();

		graph.setGnodes(gns);
		graph.setPnodes(pns);
		graph.setEdges(edges);

		for (String pid : pids) {
			//构建表型点集
			PNode pn = GlobalData.allpnodes.get(pid);
			pns.add(pn);
		}
		for (String symble : symbols) {
			//构建基因点集
			GNode gn = GlobalData.allgnodes.get(symble.toUpperCase());
			gns.add(gn);
			Map<PNode,Boolean>pmap = GlobalData.g_p_map.get(gn);
			if(pmap!=null)
			{
				for (PNode pn : pns) {
					if(pmap.containsKey(pn))
					{
						GPEdge eg = new GPEdge(gn,pn,"gplink");
						edges.add(eg);
					}
				}
			}
		}
		return graph;
	}



	//	//test function
	//	public static void main(String[] args) {
	//		GPDataLoad gpdl = new GPDataLoad();
	//		gpdl.loadGPData();
	//		//		List<String> pids = new ArrayList<String>();
	//		//		pids.add("MP:0011868");
	//		//		pids.add("MP:0011534");
	//		//		GPDaoImpl dao = new GPDaoImpl();
	//		//		GPGraph graph = dao.getAssoByPheno(pids);
	//		List<String>symbols = new ArrayList<String>();
	//		symbols.add("COL4A3");
	//		symbols.add("TRP63");
	//		GPDaoImpl dao = new GPDaoImpl();
	//		GPGraph graph = dao.getAssoByGene(symbols);
	//
	//		System.out.println();
	//
	//
	//	}
}
