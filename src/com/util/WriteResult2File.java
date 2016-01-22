package com.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import com.global.GlobalData;
import com.model.cytoscape.Graph;
import com.model.d3.D3Graph;
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
			//TODO:将查询结果写到文件中
			out.write(g.toString());
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
			//TODO:将查询结果写到文件中
			out.write(g.toString());
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

		return filePath;
	}

	private static String generateFileName()
	{
		Random random = new Random(); 
		int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数 
		String fileName = "tmp/"+System.currentTimeMillis() + rannum + ".txt";
		return fileName;
	}
}
