package com.init.dataLoad;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.data.model.PNode;

public class PhenoDataLoad {
	//根据读入的一行新建一个节点
	public static PNode makeNodeByString(String str)
	{
		String []templine = str.split("\t");
		PNode pd = new PNode();
		Map<PNode,Boolean>father = new HashMap<PNode,Boolean>();
		Map<PNode,Boolean>son = new HashMap<PNode,Boolean>();
		pd.setPheno_id(templine[0]);
		pd.setPheno_name(templine[1]);
		pd.setPheno_def(templine[2]);
		pd.setPheno_level(templine[3]);
		pd.setFather(father);
		pd.setSon(son);

		return pd;
	}

	//从phen_info.txt中读入所有表型节点的信息，构建id-PNode、level-PNodes和name-id
	public static void readPNodes(String infile,Map<String,PNode> allnodes,Map<String,Map<PNode,Boolean>>levelmap,Map<String,String>namemap){
		Map<String,String> linemap = new HashMap<String,String>();
		BufferedReader in=null;
		//把每个节点的信息放到map中
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(infile),"UTF-8"));
			String line="";

			//从文件中逐行读入放入Map<String id,String line>
			while(null!=(line= in.readLine())){
				String []temp = line.split("\t");
				linemap.put(temp[0], line);
			}

			List<PNode> currLevel = new ArrayList<PNode>();
			List<PNode> nexLevel = new ArrayList<PNode>();
			//c初始化当前层节点为0000001节点
			PNode node = makeNodeByString(linemap.get("MP:0000001"));
			currLevel.add(node);

			//将孤立节点如MP:0000002加入allnodes里面
			for (Map.Entry<String, String> entry : linemap.entrySet()) {
				String [] temp = entry.getValue().split("\t");

				if(temp[4].equals("#")&&temp[5].equals("#")){
					PNode pd = makeNodeByString(entry.getValue());
					currLevel.add(pd);

				}
			}

			while(currLevel.size()!=0)
			{
				//所有节点都会从currLevel里走一遍，所以对他进行遍历，构造出层数和该层包含哪些节点的Map

				for (PNode n : currLevel) {

					//添加name -- id map
					String name = n.getPheno_name();
					String id = n.getPheno_id();

					if(!namemap.containsKey(name)){
						namemap.put(name, id);
					}

					//添加 level -- nodes map
					String levels = n.getPheno_level();
					String level[]= levels.split(",");
					for (String l : level) {
						Map<PNode,Boolean>map  = levelmap.get(l);
						if( map == null)
						{
							map = new HashMap<PNode,Boolean>();
							levelmap.put(l, map);
						}
						map.put(n, true);
					}

					//根据当前节点的string：child去找到所有他的孩子节点

					allnodes.put(id, n);
					String [] info =linemap.get(n.getPheno_id()).split("\t");
					if(!info[5].equals("#")){
						String []sonId = info[5].split(",");
						for (String sid : sonId) {
							PNode child = makeNodeByString(linemap.get(sid));

							//对于他的孩子节点，把当前节点放入父亲属性
							child.getFather().put(n, true);
							//对于当前节点，把孩子节点放到孩子的属性中
							n.getSon().put(child, true);
							allnodes.put(sid, child);
							nexLevel.add(child);
						}
					}
					if(info[4].equals("#")&&info[5].equals("#")){
						allnodes.put(n.getPheno_id(), n);
					}
				}
				//更新当前节点集合为孩子节点集合
				currLevel.clear();
				currLevel.addAll(nexLevel);
				nexLevel.clear();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	//按节点查找:
	//按照id查询单个节点全部信息
	public static PNode getSinglePNodeById(String id,Map<String,PNode> allnodes){
		PNode result = new PNode();
		result = allnodes.get(id);
		return result;
	}

	//按照name查询单个节点全部信息
	public static PNode getSinglePNodeByName(String name, Map<String,PNode> allnodes, Map<String,String>namemap){
		PNode result = new PNode();
		String id = namemap.get(name);
		result = allnodes.get(id);
		return result;
	}

	//按照id/name查询单个节点全部信息
	public static PNode getSinglePNode(Map<String,PNode> allnodes,Map<String,String>namemap, String query){
		PNode pn = new PNode();
		//按照id查询
		if(query.startsWith("MP:")){
			pn =getSinglePNodeById(query, allnodes);

			if(pn!=null){
				return pn;
			}
			else{
				System.out.println("没有找到该节点");
				return null;
			}
		}
		//按照name查询
		else if(query.startsWith("Name:")){
			query = query.substring(5);
			pn = getSinglePNodeByName(query, allnodes, namemap);
			if(pn!=null){
				return pn;
			}
			else{
				System.out.println("没有找到该节点!");
				return null;
			}
		}
		else{
			System.out.println("您的输入不符合要求!");
			return null;
		}
	}

	//多个节点全部信息的查询，query以分号隔开
	public static List<PNode> getMultiPNode(Map<String,PNode> allnodes,Map<String,String>namemap, String query){
		List<PNode> result = new ArrayList<PNode>();
		String []temp = query.split(";");
		for(int i=0;i<temp.length;i++){
			PNode pn = new PNode();
			pn = getSinglePNode(allnodes, namemap,temp[i]);
			if(null!=pn){
				result.add(pn);
			}
		}
		return result;
	}

	//横向查询：
	//查询单层包含哪些节点
	public static Set<PNode> getPNodeBySingleLevel(Map<String,Map<PNode,Boolean>>levelmap,String query){
		Set<PNode> result = new HashSet<PNode>();
		Map<PNode,Boolean>levelnodes = new HashMap<PNode,Boolean>();
		levelnodes = levelmap.get(query);
		result = levelnodes.keySet();

		return result;
	}

	//查询多层包含哪些节点的并集，多层用分号隔开，如query = "4;5;6"
	public static Map<String,Set<PNode>> getPNodeByMultiLevel1(Map<String,Map<PNode,Boolean>>levelmap,String query){
		Map<String,Set<PNode>> result = new HashMap<String,Set<PNode>>();
		String[]temp=query.split(";");
		for(int i=0;i<temp.length;i++){
			Set<PNode>set = getPNodeBySingleLevel(levelmap, temp[i]);
			result.put(temp[i], set);
		}
		//注：这里先存成不同level-nodes的map，前台输出时，可分开列出，然后将其加在一起，或者只取前者
		return result;
	}

	//查询多层包含哪些节点的交集
	//多层查询，例如：查找同时在第四层和第五层的点(交集)，输入的query以分号隔开"4;5"
	public static Set<PNode> getPNodeByMultiLevel2(Map<String,Map<PNode,Boolean>>levelmap,String query){
		Set<PNode> result = new HashSet<PNode>();

		String[]temp = query.split(";");
		result=levelmap.get(temp[0]).keySet();

		for(int i=1;i<temp.length;i++){
			Set<PNode> levelnode = new HashSet<PNode>();
			levelnode = levelmap.get(temp[i]).keySet();
			result.retainAll(levelnode);
		}
		return result;
	}



	//纵向查询
	//输入单个节点id/name 查询出以它为起点的纵向全部路径，直到叶子节点




	//横纵结合查询
	//输入单个节点id/name，找n步以内可达的节点集合
	public static Set<PNode> getNStepNode(Map<String,PNode> allnodes,Map<String,String>namemap,String query,int n){
		Set<PNode>result = new HashSet<PNode>();
		if(query.startsWith("MP:")){
			
		}
		
		return result;
	}



	//测试函数
	public static void main(String[] args) {
		String infile = "WebRoot/data/inter_data/phen_info.txt";
		//		String query = "MP:0000003";
		//		String query = "Name:obsolete loss of subcutaneous adipose tissue";
		String query = "MP:0000003;Name:obsolete loss of subcutaneous adipose tissue";
		PNode result= new PNode();
		List<PNode>mulresult = new ArrayList<PNode>();
		Map<String,Set<PNode>> levelsetmap = new HashMap<String,Set<PNode>>();
		Set<PNode>levelset = new HashSet<PNode>();


		//读取所有节点，只一次

		Map<String,PNode> allnodes = new HashMap<String,PNode>();
		Map<String,Map<PNode,Boolean>> levelmap = new HashMap<String,Map<PNode,Boolean>>();
		Map<String,String>namemap = new HashMap<String,String>();
		readPNodes(infile,allnodes,levelmap,namemap);
		System.out.println();

		//		result = getSinglePNode(allnodes,namemap, query);
		//		mulresult = getMultiPNode(allnodes,namemap, query);
		//		levelnode = getPNodeBySingleLevel(levelmap, "3");
		//		for (PNode pn : levelnode) {
		//			System.out.println(pn.getPheno_level());
		//		}

		//		levelsetmap = getPNodeByMultiLevel1(levelmap,"4;5");


		//				levelset = getPNodeByMultiLevel2(levelmap, "4;5");
		//						for (PNode pn : levelset) {
		//							System.out.println(pn.getPheno_level());
		//						}

		System.out.println();
	}

}
