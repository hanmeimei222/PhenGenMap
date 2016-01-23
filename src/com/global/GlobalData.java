package com.global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model.CBG;
import com.model.GNode;
import com.model.PNode;
import com.model.PPINode;
import com.model.Pathway;

/**
 * 
 * 这个类中存储所有的全局数据
 *
 */
public class GlobalData {
	//表型全局数据
	public static Map<String,PNode> allpnodes = new HashMap<String,PNode>();
	public static Map<String,Map<PNode,Boolean>> levelmap = new HashMap<String,Map<PNode,Boolean>>();
	public static Map<String,String>namemap = new HashMap<String,String>();
	
	//基因全局数据
	public static Map<String,GNode> allgnodes = new HashMap<String, GNode>();
	
	//pathway全局变量
	public static Map<String,Pathway> allways = new HashMap<String,Pathway>();
	public static Map<String,Map<String,Map<Pathway,Boolean>>> classmap = new HashMap<String, Map<String,Map<Pathway,Boolean>>>();
	public static Map<String,String>pwnamemap = new HashMap<String,String>();
	public static Map<String,String>clsmap = new HashMap<String, String>();
	public static Map<Pathway,Map<Pathway,Integer>>relatedPathway = new HashMap<Pathway, Map<Pathway,Integer>>();

	
	//g-p全局数据
	public static Map<GNode, Map<PNode,Boolean>>g_p_map = new HashMap<GNode, Map<PNode,Boolean>>();
	public static Map<PNode, Map<GNode,Boolean>>p_g_map = new HashMap<PNode, Map<GNode,Boolean>>();
	
	
	//记录文件中包含的ppi的节点信息
	public static Map<String, PPINode>allppis = new HashMap<String, PPINode>();
	//ppi交互数据
	public static Map<PPINode, Map<PPINode,Boolean>>ppi_ppi_map = new HashMap<PPINode, Map<PPINode,Boolean>>();
	//g-ppi 关联数据
	public static Map<GNode, Map<PPINode,Boolean>>gene_ppi_map = new HashMap<GNode, Map<PPINode,Boolean>>();
	public static Map<PPINode, Map<GNode,Boolean>>ppi_gene_map = new HashMap<PPINode, Map<GNode,Boolean>>();
	
	
	
	//cbg数据信息，每一个CBG对应一个phen大类别中的统计数据summary
	public static List<CBG> cbgSummary = new ArrayList<CBG>();
	//cbg详细信息，最外层map对应phen的大类别，第二个map对应cbg的type，list中的string表示一个cbg，做边是gene,右边是表型，？？
	public static Map<String,Map<String,List<String>>> cbgDetail = new HashMap<String, Map<String,List<String>>>();
	//存储cbg中基因行号和基因symbol的对应关系
	public static Map<String,String> mgiIdxSymbolMapping = new HashMap<String, String>();
	
	//存储数据版本信息
	public static List<String> dataVersions = new ArrayList<String>();
	//项目的数据路径
	public static String PATH="";
	public static String curVersion="";

}
