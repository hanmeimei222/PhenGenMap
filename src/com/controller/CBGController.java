package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model.CBG;
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
	
	
	@RequestMapping("/cbg/detail")
	public Map<String,List<String>> getCBGDetail(@RequestParam("query")String query)
	{
		String[] arr = query.split("\t");
		Map<String,Boolean> ids = new HashMap<String, Boolean>();
		for (String str : arr) {
			if(!"".equals(str))
			{
				ids.put(str.trim(),true);
			}
		}
		
		return cbgService.getSelectedCBGDetail(ids);
	}
	
}
