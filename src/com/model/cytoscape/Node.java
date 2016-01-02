package com.model.cytoscape;

public class Node {
	private String id;
	private String name;
	private boolean queryInput;
	//标志其属于哪一层
	private String parent;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isQueryInput() {
		return queryInput;
	}
	public void setQueryInput(boolean queryInput) {
		this.queryInput = queryInput;
	}
	
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public Node(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Node(String id, String name, boolean queryInput) {
		super();
		this.id = id;
		this.name = name;
		this.queryInput = queryInput;
	}
	public Node(String id, String name,  String parent,boolean queryInput) {
		super();
		this.id = id;
		this.name = name;
		this.queryInput = queryInput;
		this.parent = parent;
	}

}
