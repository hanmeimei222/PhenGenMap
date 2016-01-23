package com.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dao.CBGDao;
import com.global.GlobalData;
import com.model.CBG;
import com.util.CBGDataLoad;

@Repository
public class CBGDaoImpl implements CBGDao{




	@Override
	public List<CBG> loadCBGSummary() {
		//第一次访问cbg页面，从文件中读取数据
		if(GlobalData.cbgSummary.size()==0)
		{
			GlobalData.cbgSummary.addAll(CBGDataLoad.loadCBGsummarisetable());	
		}
		//返回summary
		return GlobalData.cbgSummary;	
	}

	@Override
	public List<String> getSelectedCBGDetail(String id, String type) {
		Map<String,List<String>> map= GlobalData.cbgDetail.get(id);
		if(map == null)
		{
			map = CBGDataLoad.loadCBGDetail(id);
			GlobalData.cbgDetail.put(id, map);
		}
		List<String> result =map.get(type);
		return result;
	}
}
