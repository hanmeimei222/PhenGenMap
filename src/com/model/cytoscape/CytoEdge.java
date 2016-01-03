package com.model.cytoscape;

public class CytoEdge {
	private Edge data;

	public Edge getData() {
		return data;
	}

	public void setData(Edge data) {
		this.data = data;
	}

	public CytoEdge(Edge data) {
		super();
		this.data = data;
	}
	
	@Override
	public int hashCode() {
	return (this.data.getSource()+this.data.getTarget()).hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		CytoEdge other =(CytoEdge)obj;
		if(other.data.getSource().equals(this.data.getSource()) && other.data.getTarget().equals(this.data.getTarget()))
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
}
