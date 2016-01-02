package com.daoImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.dao.PhenDao;
import com.global.GlobalData;
import com.model.PNode;

@Repository
public class PhenDaoImpl implements PhenDao{
	//按节点查找:
	//按照id查询单个节点全部信息
	@Override
	public PNode getSinglePNodeById(String id){
		PNode result = new PNode();
		result = GlobalData.allpnodes.get(id);
		return result;
	}

	//按照name查询单个节点全部信息
	@Override
	public PNode getSinglePNodeByName(String name){
		PNode result = new PNode();
		String id = GlobalData.namemap.get(name);
		result = GlobalData.allpnodes.get(id);
		return result;
	}

	//按照id/name查询单个节点全部信息
	@Override
	public PNode getSinglePNode(String query){
		PNode pn = new PNode();
		//按照id查询
		if(query.startsWith("MP:")){
			pn =getSinglePNodeById(query);

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
			pn = getSinglePNodeByName(query);
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


	
	//多个节点全部信息的查询
	@Override
	public Set<PNode> getMultiPNode(String [] query){
		Set<PNode> result = new HashSet<PNode>();
		for(int i=0;i<query.length;i++){
			PNode pn = new PNode();
			pn = getSinglePNode(query[i]);
			if(null!=pn){
				result.add(pn);
			}
		}
		return result;
	}

	//横向查询：
	//查询单层包含哪些节点
	@Override
	public Set<PNode> getPNodeBySingleLevel(String query){
		Set<PNode> result = new HashSet<PNode>();
		Map<PNode,Boolean>levelnodes = new HashMap<PNode,Boolean>();
		levelnodes = GlobalData.levelmap.get(query);
		result = levelnodes.keySet();

		return result;
	}

	//查询多层包含哪些节点的并集，多层用分号隔开，如query = "4;5;6"
	@Override
	public Map<String,Set<PNode>> getPNodeByMultiLevel1(String query){
		Map<String,Set<PNode>> result = new HashMap<String,Set<PNode>>();
		String[]temp=query.split(";");
		for(int i=0;i<temp.length;i++){
			Set<PNode>set = getPNodeBySingleLevel(temp[i]);
			result.put(temp[i], set);
		}
		//注：这里先存成不同level-nodes的map，前台输出时，可分开列出，然后将其加在一起，或者只取前者
		return result;
	}

	//查询多层包含哪些节点的交集
	//多层查询，例如：查找同时在第四层和第五层的点(交集)，输入的query以分号隔开"4;5"
	@Override
	public Set<PNode> getPNodeByMultiLevel2(String query){
		Set<PNode> result = new HashSet<PNode>();

		String[]temp = query.split(";");
		result=GlobalData.levelmap.get(temp[0]).keySet();

		for(int i=1;i<temp.length;i++){
			Set<PNode> levelnode = new HashSet<PNode>();
			levelnode = GlobalData.levelmap.get(temp[i]).keySet();
			result.retainAll(levelnode);
		}
		return result;
	}



	//纵向查询
	//输入待查询节点id，输出以他为起点的所有前驱节点
	@Override
	public Set<PNode>getPreNodes(String[] ids){
		Set<PNode> preNodes = new HashSet<PNode>();
		for (String id : ids) {
			preNodes.addAll(getNodes(id,"Pre"));
		}
		return preNodes;
	}
	//输入待查询节点id，输出以他为起点的所有后继节点
	@Override
	public Set<PNode>getPostNodes(String[] ids){

		Set<PNode> postNodes = new HashSet<PNode>();
		for (String id : ids) {
			postNodes.addAll(getNodes(id,"Post"));
		}
		return postNodes;
	}

	//输入单个节点id以及 查询方向，输出以它为起点的前驱/后继节点
	private Set<PNode> getNodes(String id,String direction)
	{
		Set<PNode>result = new HashSet<PNode>();
		Set<PNode> currNodes = new HashSet<PNode>();
		Set<PNode> nextNodes = new HashSet<PNode>();

		PNode node = GlobalData.allpnodes.get(id);
		if(node!=null)
		{
			currNodes.add(node);
		}
		while(!currNodes.isEmpty()){
			result.addAll(currNodes);

			for (PNode pNode : currNodes) {
				if(pNode==null)
				{
					System.out.println();
				}
				if(direction.equals("Pre")){
					nextNodes.addAll(pNode.getFather().keySet());
				}
				else if(direction.equals("Post")){
					nextNodes.addAll(pNode.getSon().keySet());
				}
			}
			currNodes.clear();
			if(!nextNodes.isEmpty())
			{
				currNodes.addAll(nextNodes);
				nextNodes.clear();
			}
		}
		return result;
	}



	//横纵结合查询
	//输入单个节点id/name，找n步以内可达的节点集合
	@Override
	public Set<PNode> getNStepNode(String[] ids,int n){
		Set<PNode>result = new HashSet<PNode>();
		Set<PNode> currNodes = new HashSet<PNode>();
		Set<PNode> nextNodes = new HashSet<PNode>();

		for (String id : ids) {
			currNodes.clear();
			nextNodes.clear();
			int count = 0;
			PNode node = GlobalData.allpnodes.get(id);

			currNodes.add(node);

			while(count<n){
				result.addAll(currNodes);

				for (PNode pNode : currNodes) {
					nextNodes.addAll(pNode.getFather().keySet());
					nextNodes.addAll(pNode.getSon().keySet());
					nextNodes.removeAll(result);
				}
				currNodes.clear();
				currNodes.addAll(nextNodes);
				nextNodes.clear();
				count++;
				if(currNodes.isEmpty()){
					System.out.println("n is too large");
					break;
				}
			}
		}

		return result;
	}

	
	//找到某个节点在某一层内的所有孩子节点
	@Override
	public Set<PNode> getPostNodesByLevel(String id,String level){
		Set<PNode>result = getNodes(id,"Post");
		Set<PNode>levelNodes = getPNodeBySingleLevel(level);
		//取交集
		result.retainAll(levelNodes);
		return result;
	}

	//找到某个节点在某一层内的所有父亲节点
	@Override
	public Set<PNode> getPreNodesByLevel(String id,String level){
		Set<PNode>result = getNodes(id,"Pre");
		Set<PNode>levelNodes  = getPNodeBySingleLevel(level);
		//取交集
		result.retainAll(levelNodes);
		return result;
	}

	//找两个不同层节点间的路径，通过level来区分起点终点
	//从start找后继，一直到包含end节点
	@Override
	public Set<PNode> getContainsNodes(String start, String end){
		Set<PNode>result = new HashSet<PNode>();

		Set<PNode> currNodes = new HashSet<PNode>();
		Set<PNode> nextNodes = new HashSet<PNode>();

		PNode startnode = GlobalData.allpnodes.get(start);
		PNode endnode = GlobalData.allpnodes.get(end);

		currNodes.add(startnode);
		//判断是否到叶节点
		while(!result.contains(endnode)){
			result.addAll(currNodes);

			for (PNode pNode : currNodes) {
				nextNodes.addAll(pNode.getFather().keySet());
				nextNodes.addAll(pNode.getSon().keySet());
				nextNodes.removeAll(result);
			}
			currNodes.clear();
			currNodes.addAll(nextNodes);
			nextNodes.clear();
			if(currNodes.isEmpty()){
				System.out.println("there is no way between start to end");
			}
		}
		return result;
	}

	@Override
	public Set<PNode> getAutoCompleteNodes(String query) {
		return getAutoCompleteNodes(query,10);
	}
	@Override
	public Set<PNode> getAutoCompleteNodes(String query, int n) {
		Set<PNode> set = new HashSet<PNode>();
		Collection<PNode> nodes = GlobalData.allpnodes.values();
		boolean isId = false;
		if(query.startsWith("mp:"))
		{
			isId = true;
		}
		
		for (PNode node : nodes) {
			String key;
			if(isId)
			{
				key = node.getPheno_id();
			}
			else
			{
				key = node.getPheno_name();
			}
			if(key.regionMatches(true, 0, query, 0, query.length()))
			{
				set.add(node);
				if(set.size()>n)
				{
					break;
				}
			}
		}

		return set;
	}
}
