package com.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.model.CBG;

@Repository
public interface CBGDao {

	public List<CBG> loadCBGSummary();
	public Map<String, List<String>> getSelectedCBGDetail(Map<String, Boolean> query);
}
