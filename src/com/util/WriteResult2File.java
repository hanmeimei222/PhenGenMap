package com.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.constant.NodeType;
import com.global.GlobalData;
import com.model.cytoscape.CytoEdge;
import com.model.cytoscape.CytoNode;
import com.model.cytoscape.Graph;
import com.model.d3.D3Graph;
import com.model.d3.Links;
import com.model.d3.Node;
import com.model.d3.TreeNode;
public class WriteResult2File {

	private static String path =GlobalData.PATH;
	
	private static void writeLinks2File(BufferedWriter out,Graph g) throws IOException{
		Set<CytoEdge> links =g.getEdges();
		String title = "Links Data\nLink Type\tA(Start)\tB(End)\n";
		out.write(title);
		for (CytoEdge link : links) {
			String source = link.getData().getSource();
			String target = link.getData().getTarget();
			String type = link.getData().getEdgeType();
			
			out.write(type+"\t"+source+"\t"+target+"\n");
		}
	}
	private static void writeNodes2File(BufferedWriter out,Graph g) throws IOException{
		Set<CytoNode> nodes = g.getNodes();
		String title = "\nNodes Data\nNode Type\tNode Id\tNode Name\tLevel\n";
		out.write(title);
		for(CytoNode node : nodes){
			String type = node.getData().getNodeType();
			NodeType TYPE = NodeType.getTypeByStr(type);
			switch (TYPE){
			case MP:
				if(node.getData().getId().equals("phen")||node.getData().getParent().equals("phen")){
					continue;
				}
				String id = node.getData().getId();
				String name = node.getData().getName();
				String level = node.getData().getParent();
				
				out.write(type+"\t"+id+"\t"+name+"\t"+level+"\n");
				break;
			default:
				break;
			}
		}
		
	}
	public static String write2File(Graph g)
	{
		String fileName = generateFileName();
		String filePath = path+fileName;
		BufferedWriter out=null;
		try
		{
			out= new BufferedWriter(new FileWriter(new File(filePath)));
			writeLinks2File(out,g);
			writeNodes2File(out,g);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				out.close();
			} catch (IOException e) {
			}
		}
		return fileName;
	}

	public static String write2File(D3Graph g)
	{
		String fileName = generateFileName();
		String filePath = path+fileName;
		BufferedWriter out=null;
		try
		{
			out= new BufferedWriter(new FileWriter(new File(filePath)));
			List<Node> nodes = g.getNodes(); 
			List<Links> link =g.getLinks();
			String title = "Atype\tAgroup\tAid\tAname\tBtype\tBgroup\tBid\tBname\n";
			out.write(title);
			for (Links links : link) {
				int source = links.getSource();
				int target = links.getTarget();
				if(source<0 || target<0)
				{
					continue;
				}
				Node start = nodes.get(source);
				Node end = nodes.get(target);
				String startNode = start.getType()+"\t"+ start.getGroup()+"\t"+start.getId()+"\t"+start.getName();
				String endNode = end.getType()+"\t"+ end.getGroup()+"\t"+end.getId()+"\t"+end.getName();
				out.write(startNode+"\t"+endNode+"\n");
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				out.close();
			} catch (IOException e) {
			}
		}
		return fileName;
	}
	public static String write2File(TreeNode tree)
	{
		String fileName = generateFileName();
		String filePath = path+fileName;

		BufferedWriter out=null;
		try
		{
			out= new BufferedWriter(new FileWriter(new File(filePath)));
			String title = "Classified Pathway Dataset\nMainClass\tSubClass\tPathway Id\tPathway Name\n";
			out.write(title);
			List<TreeNode>Tree1=tree.getChildren();
			for (TreeNode tree1 : Tree1) {
				String class1name = tree1.getId();
				List<TreeNode>Tree2 = tree1.getChildren();
				for (TreeNode tree2 : Tree2) {
					String class2name = tree2.getId();
					List<TreeNode>pathway = tree2.getChildren();
					for (TreeNode pw : pathway) {
						String pwid = pw.getId();
						String pwname = pw.getName();
						out.write(class1name+"\t"+class2name+"\t"+pwid+"\t"+pwname+"\n");
					}
				}
			}

		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				out.close();
			} catch (IOException e) {
			}
		}
		
		return fileName;
	}

	private static String generateFileName()
	{
		Random random = new Random(); 
		int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数 
		String fileName = "tmp/"+System.currentTimeMillis() + rannum + ".txt";
		return fileName;
	}
}
