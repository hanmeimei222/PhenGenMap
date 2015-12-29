package com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.Line;
import com.service.QueryPhenService;

@Controller
@RequestMapping("/")
public class MVCController {
	
	@Autowired
	QueryPhenService pService;
	
	@RequestMapping("/hello")
	public String hello()
	{
		return "hello";
		
	}
	
	@ResponseBody
	@RequestMapping("/getNStepNeighbor")
	public Set<Line> getNStepNeighbor(@RequestParam("mpoId") String mpoId,
			@RequestParam("step") int step)
	{
		return pService.getNStepNode(mpoId, step);
	}
	
	@ResponseBody
	@RequestMapping("/getPostInLevels")
	public Set<Line> getPostInLevels(@RequestParam("mpoId") String mpoId,
			@RequestParam("levels") String levels)
	{
		return pService.getInterlevelsAndRoot(mpoId,levels);
	}

}
