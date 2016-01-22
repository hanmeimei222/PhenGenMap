package com.model;

public class GNode
{
	private String id;
	private String symbol_name;
	private String group;
	
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSymbol_name() {
		return symbol_name;
	}
	
	public void setSymbol_name(String symbol_name) {
		this.symbol_name = symbol_name;
	}
	@Override
	public int hashCode() {
		return symbol_name.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		
		GNode other = (GNode) obj;
		if (symbol_name.equals(other.symbol_name))
		{
			return true;
		}
		return false;
	}

	public GNode(String id, String symbolName) {
		super();
		this.id = id;
		symbol_name = symbolName;
	}
	
	public GNode()
	{
		
	}
	
	
	
}