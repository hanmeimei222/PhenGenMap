package com.model.cytoscape;

public class CytoNode {
	private Node data;

	public Node getData() {
		return data;
	}
	public void setData(Node data) {
		this.data = data;
	}
	public CytoNode(Node data) {
		super();
		this.data = data;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.data.getId().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		CytoNode other =(CytoNode)obj;
		if(other.data.getId().equals(this.data.getId()) && other.data.getName().equals(this.data.getName()))
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
}
