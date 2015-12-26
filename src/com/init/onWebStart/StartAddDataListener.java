package com.init.onWebStart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.service.InitDataService;

@Service
public class StartAddDataListener implements ApplicationListener<ContextRefreshedEvent>{

	@Autowired
	private InitDataService initService;
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		if(event.getApplicationContext().getParent()==null)
		{
			initService.initData();
		}
	}
}
