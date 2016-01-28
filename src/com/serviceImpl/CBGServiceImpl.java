package com.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.CBGDao;
import com.dao.PhenDao;
import com.global.GlobalData;
import com.model.CBG;
import com.model.PNode;
import com.model.cytoscape.CytoEdge;
import com.model.cytoscape.CytoNode;
import com.model.cytoscape.Edge;
import com.model.cytoscape.Graph;
import com.model.cytoscape.Node;
import com.service.CBGService;

@Service
public class CBGServiceImpl implements CBGService{

	@Autowired
	CBGDao cbgDao;

	@Autowired
	PhenDao pheDao;

	@Override
	public List<CBG> loadCBGSummary() {

		List<CBG> list = cbgDao.loadCBGSummary();
		for (CBG cbg : list) {
			PNode n = pheDao.getSinglePNode(cbg.getPhen_id());
			cbg.setNode(n);
		}
		return list;
	}

	@Override
	public Graph getSelectedCBGDetail(String id, String type) {

		Graph g = new Graph();

		Set<CytoNode> nodes = new HashSet<CytoNode>();
		Set<CytoEdge> edges = new HashSet<CytoEdge>();
		g.setEdges(edges);
		g.setNodes(nodes);

		List<String> cbgs = cbgDao.getSelectedCBGDetail(id, type);
		String[] tmp =type.split("_");

		int geneNum = Integer.parseInt(tmp[0]);
		int mpNum = Integer.parseInt(tmp[1]);



		int totalNodes = geneNum+mpNum+2;//加起点和终点
		if(cbgs!=null){

			for (String cbg : cbgs)
			{

				String []info = cbg.split("\t");
				List<String>ns = new ArrayList<String>(info.length);

				String symbol = GlobalData.mgiIdxSymbolMapping.get(info[0]);
				ns.add(symbol);
				String mpId = "MP:"+String.format("%07d", Integer.parseInt(info[1]));
				ns.add(mpId);

				for(int i=0;i<geneNum;i++)
				{
					symbol = GlobalData.mgiIdxSymbolMapping.get(info[i+2]);
					ns.add(symbol);
				}
				for(int i=geneNum+2;i<totalNodes;i++)
				{
					mpId = "MP:"+String.format("%07d", Integer.parseInt(info[i]));
					ns.add(mpId);
				}

				if(ns.size()!=totalNodes)
				{
					System.out.println("数据格式有问题");
					System.out.println(cbg);
					continue;
				}

				Node n = new Node(ns.get(0), ns.get(0), "gene", "", true);
				CytoNode cyNode = new CytoNode(n);
				nodes.add(cyNode);

				n = new Node(ns.get(1), ns.get(1), "mp", "", true);
				cyNode = new CytoNode(n);
				nodes.add(cyNode);
				//基因节点
				for(int i=2;i<geneNum+2;i++)
				{
					n = new Node(ns.get(i), ns.get(i), "gene", "", false);
					cyNode = new CytoNode(n);
					nodes.add(cyNode);
				}
				//表型节点
				for(int i=geneNum+2;i<totalNodes;i++)
				{
					n = new Node(ns.get(i), ns.get(i), "mp", "", false);
					cyNode = new CytoNode(n);
					nodes.add(cyNode);
				}

				//构造gene间的边

				for(int i=2;i<geneNum+1;i++)
				{
					Edge e = new Edge(ns.get(i), ns.get(i+1), "ppi2ppilink");
					CytoEdge edge = new CytoEdge(e);
					edges.add(edge);
				}
				//起点
				if(geneNum>0)
				{
					Edge e = new Edge(ns.get(0), ns.get(2), "ppi2ppilink");
					CytoEdge edge = new CytoEdge(e);
					edges.add(edge);
				}
				else//起点连接到mp上
				{
					Edge e = new Edge(ns.get(0), ns.get(2), "gplink");
					CytoEdge edge = new CytoEdge(e);
					edges.add(edge);
				}
				Edge e = null;
				CytoEdge edge = null;
				if(geneNum>0 && mpNum>0)
				{
					e = new Edge(ns.get(geneNum+2-1), ns.get(geneNum+2), "gplink");
					edge = new CytoEdge(e);
					edges.add(edge);
				}
				//构造mp间的边
				for(int i=geneNum+2;i<totalNodes-1;i++)
				{
					e = new Edge(ns.get(i), ns.get(i+1), "pplink");
					edge = new CytoEdge(e);
					edges.add(edge);
				}
				//终点
				if(mpNum>0)
				{
					e = new Edge(ns.get(totalNodes-1), ns.get(1), "pplink");
					edge = new CytoEdge(e);
					edges.add(edge);
				}
				else
				{
					e = new Edge(ns.get(totalNodes-1), ns.get(1), "gplink");
					edge = new CytoEdge(e);
					edges.add(edge);
				}

				//构造基因和mp间的边
				e = new Edge(ns.get(0), ns.get(1), "gplink");
				edge = new CytoEdge(e);
				edges.add(edge);

			}
		}

		return g;
	}
}
