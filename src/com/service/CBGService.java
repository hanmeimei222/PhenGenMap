package com.service;

import java.util.List;
import java.util.Map;

import com.model.CBG;

public interface CBGService {
	public List<CBG> loadCBGSummary();
	public Map<String,List<String>> getSelectedCBGDetail(Map<String,Boolean> query);
}
