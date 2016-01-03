package com.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.cytoscape.Graph;
import com.service.QueryGPService;

@Controller
public class GPQueryController {

	@Autowired
	QueryGPService gpService;

	/**
	 * 根据用户输入，查询给定的mpList和geneList及其之间的关联
	 * @return
	 */
	@RequestMapping("/queryGPA")
	@ResponseBody
	public Graph getPGAssociation(@RequestParam("mpList") String mpoIds,
			@RequestParam("geneList") String geneSymbols,
			@RequestParam("showMPA") boolean showMPA,
			@RequestParam("showPathway") boolean showPathway)
	{
		Map<String,Boolean> pids = new HashMap<String, Boolean>();
		if(mpoIds!=null && !mpoIds.equals(""))
		{
			String[] mpArr =mpoIds.split("\n|\t");

			for (String str : mpArr) {
				if(!"".equals(str))
				{
					pids.put(str.trim(),true);
				}
			}
		}
		Map<String,Boolean> symbols = new HashMap<String, Boolean>();
		if(geneSymbols!=null && !geneSymbols.equals(""))
		{
			String[] geneArr =geneSymbols.split("\n|\t");

			for (String str : geneArr) {
				if(!"".equals(str))
				{
					symbols.put(str.trim(),true);
				}
			}
		}
		return gpService.getAssoByPhenoGene(pids, symbols,showMPA,showPathway);
	}
}
