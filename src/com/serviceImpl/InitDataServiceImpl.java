package com.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.GlobalData;
import com.init.dataLoad.GPDataLoad;
import com.init.dataLoad.GeneDataLoad;
import com.init.dataLoad.PhenoDataLoad;
import com.service.InitDataService;

@Service
public class InitDataServiceImpl implements InitDataService{

	@Autowired
	private PhenoDataLoad phenLoad;
	@Autowired
	private GeneDataLoad geneLoad;
	
	@Autowired
	private GPDataLoad gpLoad;

	@Override
	public void initData()	{
		//获取服务器绝对路径
		GlobalData.PATH = System.getProperty("search.root");
		//初始化表型数据
		phenLoad.loadPhenoData();
		//初始化基因数据
		geneLoad.loadGeneData();
		//初始化基因-表型数据
		gpLoad.loadGPData();
	}
}
