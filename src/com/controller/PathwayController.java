package com.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.constant.NodeType;
import com.model.cytoscape.Graph;
import com.model.d3.TreeNode;
import com.service.QueryPathwayService;


@Controller
@RequestMapping("/")
public class PathwayController {

	@Autowired
	QueryPathwayService pwayService;

	@RequestMapping("/allPathway")
	@ResponseBody
	public TreeNode showAllPathways(){
		return pwayService.allPathway();
	}

	@RequestMapping("/pwayQuery")
	@ResponseBody
	public Graph pwayQuery(HttpServletRequest request){
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
		return pwayService.getGenePathwayAsso(map);

	}



}
