package com.model;

import java.util.Set;

public class GPGraph {

	private Set<GNode> gnodes;
	private Set<PNode> pnodes;
	private Set<Edge> edges;
	public Set<GNode> getGnodes() {
		return gnodes;
	}
	public void setGnodes(Set<GNode> gnodes) {
		this.gnodes = gnodes;
	}
	public Set<PNode> getPnodes() {
		return pnodes;
	}
	public void setPnodes(Set<PNode> pnodes) {
		this.pnodes = pnodes;
	}
	public Set<Edge> getEdges() {
		return edges;
	}
	public void setEdges(Set<Edge> edges) {
		this.edges = edges;
	}

	
}
