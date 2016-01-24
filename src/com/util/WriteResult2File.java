package com.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.global.GlobalData;
import com.model.cytoscape.CytoEdge;
import com.model.cytoscape.Graph;
import com.model.d3.D3Graph;
import com.model.d3.Links;
import com.model.d3.Node;
import com.model.d3.TreeNode;

public class WriteResult2File {

	private static String path =GlobalData.PATH;
	
	public static String write2File(Graph g)
	{
		String fileName = generateFileName();
		String filePath = path+fileName;
		BufferedWriter out=null;
		try
		{
			out= new BufferedWriter(new FileWriter(new File(filePath)));
			Set<CytoEdge> list =g.getEdges();
			String title = "Link Type\tA\tB\n";
			out.write(title);
			for (CytoEdge links : list) {
				String source = links.getData().getSource();
				String target = links.getData().getTarget();
				String type = links.getData().getEdgeType();
				
				out.write(type+"\t"+source+"\t"+target+"\n");
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
			out.write(tree.toString());
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
