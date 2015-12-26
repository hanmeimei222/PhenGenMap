package com.data.model;

public class Line {
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Line(String source, String target, String type) {
		super();
		this.source = source;
		this.target = target;
		this.type = type;
	}
	
	
	
}
