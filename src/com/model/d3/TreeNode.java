package com.model.d3;

import java.util.List;


public class TreeNode {

	private String id;
	private String name;
	private List<TreeNode>children;
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
	public List<TreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	
	public TreeNode()
	{
		
	}
	
	public TreeNode(String name) {
		super();
		this.name = name;
	}
	
	public TreeNode(String id, String name, List<TreeNode> children) {
		super();
		this.id = id;
		this.name = name;
		this.children = children;
	}
	
	
	
	
}
