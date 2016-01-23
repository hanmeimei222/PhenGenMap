package com.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.CBGDao;
import com.dao.PhenDao;
import com.model.CBG;
import com.model.PNode;
import com.service.CBGService;

@Service
public class CBGServiceImpl implements CBGService{

	@Autowired
	CBGDao cbgDao;
	
	@Autowired
	PhenDao pheDao;
	
	@Override
	public List<CBG> loadCBGSummary() {
		
		List<CBG> list = cbgDao.loadCBGSummary();
		for (CBG cbg : list) {
			PNode n = pheDao.getSinglePNode(cbg.getPhen_id());
			cbg.setNode(n);
		}
		return list;
	}
	
	@Override
	public Map<String, List<String>> getSelectedCBGDetail(Map<String, Boolean> query) {
		Map<String, List<String>> result = null;
		
		result = cbgDao.getSelectedCBGDetail(query);
		
		return result;
	}
	
}
