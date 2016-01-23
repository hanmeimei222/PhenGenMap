package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	public List<String> loadDataVersionInfo()
	{
		return GlobalData.dataVersions;
	}
	
	@RequestMapping("/cutVersion")
	@ResponseBody
	public boolean cutDataVersion(@RequestParam("version")String version)
	{
		return initService.loadData(version);
	}
}
