package com.model.d3;

import java.util.List;

public class D3Graph {

	private List<Links> links;
	private List<Node> nodes;
	
	public List<Links> getLinks() {
		return links;
	}
	public void setLinks(List<Links> links) {
		this.links = links;
	}
	
	public List<Node> getNodes() {
		return nodes;
	}
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
}
