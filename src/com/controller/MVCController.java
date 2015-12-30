package com.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.constant.PhenQueryType;
import com.model.cytoscape.Graph;
import com.service.QueryPhenService;

@Controller
@RequestMapping("/")
public class MVCController {
	
	@Autowired
	QueryPhenService pService;
	
	@RequestMapping("/hello")
	public String hello()
	{
		return "cytoscape";
	}

	@RequestMapping("/pheQuery")
	@ResponseBody
	public Graph pheQuery(@RequestParam("mpList") String mpoId,
			@RequestParam("queryType") String queryType,
			@RequestParam("levels")String levels,
			@RequestParam("step") String step)
	{
		String[] mpList =mpoId.split("\n");
		PhenQueryType type = PhenQueryType.getTypeByStr(queryType);
		
		Map<String,String>param = new HashMap<String, String>();
		param.put("levels", levels);
		param.put("step", step);
		return pService.queryPhen(mpList,type,param);
	}
	
	@ResponseBody
	@RequestMapping("/getNStepNeighbor")
	public Graph getNStepNeighbor(@RequestParam("mpoId") String mpoId,
			@RequestParam("step") int step)
	{
		return pService.getNStepNode(mpoId, step);
	}
	
	@ResponseBody
	@RequestMapping("/getPostInLevels")
	public Graph getPostInLevels(@RequestParam("mpoId") String mpoId,
			@RequestParam("levels") String levels)
	{
		return pService.getInterlevelsAndRoot(mpoId,levels);
	}

}
