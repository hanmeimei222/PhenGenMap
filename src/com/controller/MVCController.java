package com.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.constant.PhenQueryType;
import com.model.PNode;
import com.model.cytoscape.Graph;
import com.service.QueryPhenService;

@Controller
@RequestMapping("/")
public class MVCController {
	
	@Autowired
	QueryPhenService pService;

	@RequestMapping("/pheQuery")
	@ResponseBody
	public Graph pheQuery(@RequestParam("mpList") String mpoId,
			@RequestParam("queryType") String queryType,
			@RequestParam("levels")String levels,
			@RequestParam("step") String step)
	{
		Map<String,Boolean> queryMap = new HashMap<String, Boolean>();
		
		String[] mpArr =mpoId.split("\n|\t");
		
		for (String str : mpArr) {
			if(!"".equals(str))
			{
				queryMap.put(str.trim(),true);
			}
		}
		PhenQueryType type = PhenQueryType.getTypeByStr(queryType);
		
		Map<String,String>param = new HashMap<String, String>();
		param.put("levels", levels);
		param.put("step", step);
		return pService.queryPhen(queryMap,type,param);
	}
	/**
	 * 根据用户输入完成自动补全
	 * @param query
	 * @return
	 */
	@RequestMapping("/autoComplete")
	@ResponseBody
	public Set<PNode> autoComplete(@RequestParam String query)
	{
		System.out.println(query);
		if(null!=query && !"".equals(query))
		{
			return pService.aucoComplete(query.toLowerCase());
		}
		else
		{
			return new HashSet<PNode>();
		}
	}

}
