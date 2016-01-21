package com.model.d3;

public class Node {

	private String id;
	private String name;
	private String group;
	private String type;
	
	
	public Node(String id, String name,String group, String type) {
		super();
		this.name = name;
		this.id = id;
		this.group = group;
		this.type = type;
	}
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
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object obj) {
		Node n =(Node)obj;
		
		if(n instanceof Node)
		{
			if(n.getId().equals(this.id))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		return false;
	}
}
