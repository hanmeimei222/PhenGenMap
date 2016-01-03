package com.model.cytoscape;

public class Edge {
	private String source;
	private String target;
	private String type;

	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}

	public Edge(String source, String target,String type) {
		super();
		this.source = source;
		this.target = target;
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
