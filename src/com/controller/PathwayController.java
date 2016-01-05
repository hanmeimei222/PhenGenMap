package com.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.constant.PathwayQueryType;
import com.model.GNode;
import com.model.d3.TreeNode;
import com.service.QueryPathwayService;


@Controller
@RequestMapping("/")
public class PathwayController {
	
	@Autowired
	QueryPathwayService pwayService;
	
	@RequestMapping("/pwayQuery")
	@ResponseBody
	public TreeNode pwayQuery()
	{
		//@RequestParam("pwayList") String pwayId,	@RequestParam("queryType") String queryType
//		Map<String,Boolean> queryMap = new HashMap<String, Boolean>();
//		String[] pwayList =pwayId.split("\n|\t");
//		for (String str : pwayList) {
//			if(!"".equals(str))
//			{
//				queryMap.put(str.trim(),true);
//			}
//		}
		
		String queryType = "";
		Map<String,Boolean> queryMap = new HashMap<String, Boolean>();
		
		PathwayQueryType type = PathwayQueryType.getTypeByStr("allpathways");
		
		return pwayService.queryPathway(queryMap,type);
	}
	
	

}
