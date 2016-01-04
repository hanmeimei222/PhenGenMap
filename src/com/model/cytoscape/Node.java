package com.model.cytoscape;

public class Node {
	private String id;
	private String name;
	private boolean queryInput;
	//标志其属于哪一层
	private String parent;
	
	private String nodeType;
	
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
	
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
//	public Node(String id, String name,String nodeType, boolean queryInput) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.nodeType = nodeType;
//		this.queryInput = queryInput;
//	}
	public Node(String id, String name, String nodeType,String parent,boolean queryInput) {
		super();
		this.id = id;
		this.name = name;
		this.nodeType = nodeType;
		this.queryInput = queryInput;
		this.parent = parent;
	}

}
