package com.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.constant.NodeType;
import com.model.PNode;
import com.model.Pathway;
import com.model.cytoscape.Graph;
import com.model.d3.D3Graph;
import com.service.QueryAssoService;
import com.service.QueryPathwayService;
import com.service.QueryPhenService;
import com.util.WriteResult2File;

@Controller
public class GPQueryController {

	@Autowired
	QueryAssoService gpService;
	
	@Autowired
	QueryPhenService phenService;

	@Autowired
	QueryPathwayService pathwayService;
	
	@RequestMapping("/queryAsso")
	@ResponseBody
	public ModelMap queryAssiciationByPathway(HttpServletRequest request)
	{
		ModelMap modelmap = new ModelMap();
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
		
		Graph graph = gpService.getAsso(map) ;
		String filename = WriteResult2File.write2File(graph);
		modelmap.put("data", graph);
		modelmap.put("path", filename);
		return modelmap;
	}
	
//	@RequestMapping("/queryGlobalAsso")
//	@ResponseBody
//	public ModelMap queryAssiciation(@RequestParam("phenList")String phenList,
//			@RequestParam("pathwayList")String pathwayList,
//			@RequestParam("level")int level,
//			@RequestParam("selected_type") String selected_type)
//	{
//		String[] arr = phenList.split("\t");
//		Map<String,Boolean> ids = new HashMap<String, Boolean>();
//		for (String str : arr) {
//			if(!"".equals(str))
//			{
//				ids.put(str.trim(),true);
//			}
//		}
//
//		Map<String,Boolean> pathwayInfos = new HashMap<String, Boolean>();
//		arr = pathwayList.split("\t");
//		for (String str : arr) {
//			if(!"".equals(str))
//			{
//				pathwayInfos.put(str.trim(),true);
//			}
//		}
//		Map<String,Set<PNode>> phenNodes = phenService.expandPhens(ids);
//		
//		Map<String,Set<Pathway>> pathways =  pathwayService.queryPathway(pathwayInfos, level);
//		
//		D3Graph graph = gpService.getGlobalAsso(phenNodes,pathways,selected_type);
//		String filename = WriteResult2File.write2File(graph);
//		ModelMap map = new ModelMap();
//		map.put("data", graph);
//		map.put("path", filename);
//		return map;
//	}
	
	
	@RequestMapping("/queryGlobalAsso")
	@ResponseBody
	public ModelMap queryAssiciation(@RequestParam("phenList")String phenList,
			@RequestParam("pathwayList")String pathwayList,
			@RequestParam("selected_type") String selected_type)
	{
		String[] arr = phenList.split("\t");
		Map<String,Boolean> ids = new HashMap<String, Boolean>();
		for (String str : arr) {
			if(!"".equals(str))
			{
				ids.put(str.trim(),true);
			}
		}

		Map<String,Boolean> pathwayInfos = new HashMap<String, Boolean>();
		arr = pathwayList.split("\t");
		for (String str : arr) {
			if(!"".equals(str))
			{
				pathwayInfos.put(str.trim(),true);
			}
		}
//		Map<String,Set<PNode>> phenNodes = phenService.expandPhens(ids);
		
		Set<PNode> phenNodes = phenService.getPhenInfo(ids);
		
		Map<String,Set<Pathway>> pathways =  pathwayService.queryPathway(pathwayInfos,3);
		
		D3Graph graph = gpService.getGlobalAsso(phenNodes,pathways,selected_type);
		String filename = WriteResult2File.write2File(graph);
		ModelMap map = new ModelMap();
		map.put("data", graph);
		map.put("path", filename);
		return map;
	}
}
