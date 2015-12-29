package com.model.cytoscape;

public class CytoEdge {
	private Edge data;

	public Edge getData() {
		return data;
	}

	public void setData(Edge data) {
		this.data = data;
	}

	public CytoEdge(Edge data) {
		super();
		this.data = data;
	}
}
