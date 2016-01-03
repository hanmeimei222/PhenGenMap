package com.model.cytoscape;

import java.util.Set;

public class Graph {
	private Set<CytoNode> nodes;
	private Set<CytoEdge> edges;
	public Set<CytoNode> getNodes() {
		return nodes;
	}
	public void setNodes(Set<CytoNode> nodes) {
		this.nodes = nodes;
	}
	public Set<CytoEdge> getEdges() {
		return edges;
	}
	public void setEdges(Set<CytoEdge> edges) {
		this.edges = edges;
	}
}
