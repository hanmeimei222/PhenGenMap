package com.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.constant.NodeType;
import com.model.PNode;
import com.model.cytoscape.Graph;
import com.service.QueryAssoService;
import com.service.QueryPhenService;

@Controller
public class GPQueryController {

	@Autowired
	QueryAssoService gpService;
	
	@Autowired
	QueryPhenService phenService;

	
	@RequestMapping("/queryAsso")
	@ResponseBody
	public Graph queryAssiciationByPathway(HttpServletRequest request)
	{
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
		//调用函数查询输入的节点，以及各种节点之间的关系
		
		
		return gpService.getAsso(map) ;
	}
	
	@RequestMapping("/queryGlobalAsso")
	@ResponseBody
	public Graph queryGlobalAssiciation(HttpServletRequest request)
	{
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
		//调用函数查询输入的节点，以及各种节点之间的关系
	
		return gpService.getGlobalAsso(map) ;
	}
}
