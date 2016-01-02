package com.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.constant.PathwayQueryType;
import com.model.cytoscape.Graph;
import com.service.QueryPathwayService;


@Controller
@RequestMapping("/")
public class PathwayController {
	
	@Autowired
	QueryPathwayService pwayService;
	
	@RequestMapping("/pwayQuery")
	@ResponseBody
	public Graph pwayQuery(@RequestParam("pwayList") String pwayId,	@RequestParam("queryType") String queryType)
	{
		
		Map<String,Boolean> queryMap = new HashMap<String, Boolean>();
		String[] pwayList =pwayId.split("\n|\t");
		for (String str : pwayList) {
			if(!"".equals(str))
			{
				queryMap.put(str.trim(),true);
			}
		}
		
		PathwayQueryType type = PathwayQueryType.getTypeByStr(queryType);
		
		return pwayService.queryPathway(queryMap,type);
	}

}
