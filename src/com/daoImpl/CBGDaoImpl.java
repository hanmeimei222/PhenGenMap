package com.daoImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	public Map<String, List<String>> getSelectedCBGDetail(
			Map<String, Boolean> query) {
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		Set<String> ids = query.keySet();
		for (String id : ids) {
			List<String> list= GlobalData.cbgDetail.get(id);
			if(list == null)
			{
				//从文件读取数据
				list =new ArrayList<String>();
				GlobalData.cbgDetail.put(id, list);
				
//				list.addAll(CBGDataLoad.loadCBGDetail(id));
			}
			result.put(id, list);
		}
		return result;
	}
}
