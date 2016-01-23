package com.service;

import java.util.List;

import com.model.CBG;
import com.model.cytoscape.Graph;

public interface CBGService {
	public List<CBG> loadCBGSummary();
	public List<Graph> getSelectedCBGDetail(String id,String type);
}
