package com.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.GlobalData;
import com.init.dataLoad.PhenoDataLoad;
import com.service.InitDataService;

@Service
public class InitDataServiceImpl implements InitDataService{

	@Autowired
	private PhenoDataLoad phenLoad;

	@Override
	public void initData()	{
		//获取服务器绝对路径
		GlobalData.PATH = System.getProperty("search.root");
		//初始化表型数据
		phenLoad.loadPhenoData();
		//TODO：初始化基因数据
	}
}
