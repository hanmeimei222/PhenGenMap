package com.model.d3;

import java.util.Set;


public class TreeNode {

	private String id;
	private String name;
	private Set<TreeNode>children;
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
	public Set<TreeNode> getChildren() {
		return children;
	}
	public void setChildren(Set<TreeNode> children) {
		this.children = children;
	}
	
	
	public TreeNode() {
		super();
	}
	public TreeNode(String id, String name, Set<TreeNode> children) {
		super();
		this.id = id;
		this.name = name;
		this.children = children;
	}
	
	
	
	
}
