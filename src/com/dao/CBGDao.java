package com.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.model.CBG;

@Repository
public interface CBGDao {

	public List<CBG> loadCBGSummary();
	public List<String> getSelectedCBGDetail(String id,String type);
}
