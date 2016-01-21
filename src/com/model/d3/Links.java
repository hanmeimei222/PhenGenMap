package com.model.d3;

public class Links {

	private int source;
	private int target;
	private int value;
	private String class_id;

	public Links(int source, int target, int value, String class_id) {
		super();
		this.source = source;
		this.target = target;
		this.value = value;
		this.class_id = class_id;
	}
	
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public int getTarget() {
		return target;
	}
	public void setTarget(int target) {
		this.target = target;
	}
	public int getValue() {
		return 1;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getClass_id() {
		return class_id;
	}
	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
	
}
