package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
