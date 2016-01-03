package com.model;

public class GNode
{
	private String id;
	private String symbol_name;
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
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((symbol_name == null) ? 0 : symbol_name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GNode other = (GNode) obj;
		if (symbol_name == null) {
			if (other.symbol_name != null)
				return false;
		} else if (!symbol_name.equals(other.symbol_name))
			return false;
		return true;
	}
	
	
	
	
}