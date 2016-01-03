package com.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.model.cytoscape.Graph;

@Service
public interface QueryGPService {

	public Graph getAssoByPhenoGene(Map<String,Boolean> pids,Map<String,Boolean>symbols,boolean showMPA,boolean showPathway);
}
