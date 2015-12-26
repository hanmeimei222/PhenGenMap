package com.serviceImpl;

import org.springframework.stereotype.Service;
import com.service.InitDataService;

@Service
public class InitDataServiceImpl implements InitDataService{

	@Override
	public void initData()	{
		System.out.println("这里加载初始化数据");

	}
}
