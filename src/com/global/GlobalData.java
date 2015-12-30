package com.global;

import java.util.HashMap;
import java.util.Map;

import com.model.GNode;
import com.model.PNode;
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
	public static Map<String,Pathway> allways = new HashMap<String,Pathway>();
	public static Map<String,Map<String,Map<Pathway,Boolean>>> classmap = new HashMap<String, Map<String,Map<Pathway,Boolean>>>();
	public static Map<String,String>pwnamemap = new HashMap<String,String>();
	public static Map<String,String>clsmap = new HashMap<String, String>();

	//g-p全局数据
	public static Map<GNode, Map<PNode,Boolean>>g_p_map = new HashMap<GNode, Map<PNode,Boolean>>();
	public static Map<PNode, Map<GNode,Boolean>>p_g_map = new HashMap<PNode, Map<GNode,Boolean>>();
	
	
	//项目的数据路径
	public static String PATH="";

}