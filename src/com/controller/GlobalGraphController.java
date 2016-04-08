package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.PNode;
import com.model.ztree.ZtreeNode;
import com.service.QueryPathwayService;
import com.service.QueryPhenService;

@Controller
public class GlobalGraphController {

	@Autowired
	QueryPathwayService pathwayService;
	@Autowired
	QueryPhenService phenService;
	
	@RequestMapping("/initPathwayClassInfo.do")
	@ResponseBody
	public  List<ZtreeNode> getPathwayClassInfo()
	{
		List<ZtreeNode> pathwayInfo = pathwayService.getpathwayztree();
		return pathwayInfo;
	}

	@RequestMapping("/initPhenClassInfo.do")
	@ResponseBody
	public Map<String,Set<PNode>> getPhenSonInfo(@RequestParam("fatherId")String fatherId)
	{
		Map<String,Boolean> queryMap = new HashMap<String, Boolean>();

		String []pids = fatherId.split("\n|\t");

		for (String str : pids) {
			if(!"".equals(str))
			{
				queryMap.put(str.trim(),true);
			}
		}
		
		Set<PNode> phenInfo = phenService.getPhenInfo(queryMap);
		Map<String,Set<PNode>> phenSons = new HashMap<String, Set<PNode>>();
		for (PNode pNode : phenInfo) {
			phenSons.put(pNode.getPheno_name(), pNode.getSon().keySet());
		}
		return phenSons;
	}
}
