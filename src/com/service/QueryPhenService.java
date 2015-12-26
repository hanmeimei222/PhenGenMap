package com.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.model.PNode;

@Service
public interface QueryPhenService {
	public Set<PNode> getNStepNode(String id,int n);
}
