package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.global.GlobalData;
import com.service.InitDataService;

@Controller
public class InitHomeController {
	
	@Autowired
	InitDataService initService;
	
	@RequestMapping("/init")
	@ResponseBody
	public ModelMap loadDataVersionInfo()
	{
		ModelMap map = new ModelMap();
		
		map.put("versions", GlobalData.dataVersions);
		map.put("curVersion", GlobalData.curVersion);
		
		return map;
	}
	
	@RequestMapping("/cutVersion")
	@ResponseBody
	public boolean cutDataVersion(@RequestParam("version")String version)
	{
		return initService.loadData(version);
	}
	
	@RequestMapping("/downloadSource")
	@ResponseBody
	public ModelMap downloadSourceFile(){
		ModelMap map = new ModelMap();
		List<String> filename = initService.getSourceFile();
		
		for(int i=0;i<filename.size();i++){
			map.put("path"+i, filename.get(i));
		}
		return map;
	}
}
