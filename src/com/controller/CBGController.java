package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.model.CBG;
import com.model.cytoscape.Graph;
import com.service.CBGService;

@Controller
public class CBGController {

	@Autowired
	CBGService cbgService;
	
	@RequestMapping("/summary")
	public ModelAndView getCBGSummary()
	{
		List<CBG> result = cbgService.loadCBGSummary();
		
		ModelMap map = new ModelMap();
		map.put("result", result);
		return new ModelAndView("cbgSummary",map);
	}
	
	
	@RequestMapping("/detail")
	@ResponseBody
	public Graph getCBGDetail(@RequestParam("id")String id,
			@RequestParam("type")String type)
	{
		Graph result=cbgService.getSelectedCBGDetail(id,type);
		return result;
	}
	
}
