package com.service;

import java.util.List;

import org.springframework.stereotype.Service;


/**
 * 
 * 项目启动时，完成初始化数据加载的任务
 *
 */
@Service
public interface InitDataService {
	public void initData();
	public boolean loadData(String version);
	public List<String> getSourceFile();
}
