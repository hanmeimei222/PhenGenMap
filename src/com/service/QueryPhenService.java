package com.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.model.Line;

@Service
public interface QueryPhenService {
	public Set<Line> getNStepNode(String id,int n);
}
