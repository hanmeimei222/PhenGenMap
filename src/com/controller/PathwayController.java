package com.controller;

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
		
		
		String[] pwayList =pwayId.split("\n");
		PathwayQueryType type = PathwayQueryType.getTypeByStr(queryType);
		
		return pwayService.queryPathway(pwayList,type);
	}

}
