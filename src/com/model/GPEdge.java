package com.model;

public class GPEdge {

	private GNode source;
	private PNode target;
	private String type;
	public GNode getSource() {
		return source;
	}
	public void setSource(GNode source) {
		this.source = source;
	}
	public PNode getTarget() {
		return target;
	}
	public void setTarget(PNode target) {
		this.target = target;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public GPEdge(GNode source, PNode target, String type) {
		super();
		this.source = source;
		this.target = target;
		this.type = type;
	}
	
	
	
}
