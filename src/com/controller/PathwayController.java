package com.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.constant.NodeType;
import com.model.d3.PathwayGraphAndTree;
import com.model.d3.TreeNode;
import com.service.QueryPathwayService;
import com.util.WriteResult2File;


@Controller
@RequestMapping("/")
public class PathwayController {

	@Autowired
	QueryPathwayService pwayService;

	@RequestMapping("/allPathway")
	@ResponseBody
	public ModelMap showAllPathways(){
		ModelMap map = new ModelMap();
		TreeNode tree = pwayService.allPathway();
		String filename = WriteResult2File.write2File(tree);
		map.put("data", tree);
		map.put("path", filename);
		return map;
	}
//	public TreeNode showAllPathways(){
//		return pwayService.allPathway();
//	}

//	@RequestMapping("/relatedPathway")
//	@ResponseBody
//	public TreeNode getRelatedPathway(HttpServletRequest request){
//		String queryType = request.getParameter("queryType");
//		String[]nodeType=queryType.split("_");
//		//解析出输入的节点集合及类型
//		Map<NodeType,Map<String,Boolean>> map = new HashMap<NodeType, Map<String,Boolean>>();
//		for (String type : nodeType)
//		{
//			if(!"".equals(type))
//			{
//				String param = request.getParameter("param["+type+"List]");
//				Map<String,Boolean> ids = new HashMap<String, Boolean>();
//				if(param!=null && !param.equals(""))
//				{
//					String[] arr =param.split("\n|\t");
//
//					for (String str : arr) {
//						if(!"".equals(str))
//						{
//							ids.put(str.trim(),true);
//						}
//					}
//				}
//				map.put(NodeType.getTypeByStr(type), ids);
//			}
//		}
//		return pwayService.getRelatedPathway(map);
//	}

	@RequestMapping("/pwayQuery")
	@ResponseBody
	public PathwayGraphAndTree pwayQuery(HttpServletRequest request){
		String queryType = request.getParameter("queryType");
		String[]nodeType=queryType.split("_");
		//解析出输入的节点集合及类型
		Map<NodeType,Map<String,Boolean>> map = new HashMap<NodeType, Map<String,Boolean>>();
		for (String type : nodeType)
		{
			if(!"".equals(type))
			{
				String param = request.getParameter("param["+type+"List]");
				Map<String,Boolean> ids = new HashMap<String, Boolean>();
				if(param!=null && !param.equals(""))
				{
					String[] arr =param.split("\n|\t");

					for (String str : arr) {
						if(!"".equals(str))
						{
							ids.put(str.trim(),true);
						}
					}
				}
				map.put(NodeType.getTypeByStr(type), ids);
			}
		}
		PathwayGraphAndTree graphAndTree = pwayService.getGenePathwayAsso(map);
		
		return graphAndTree;
	}



}
