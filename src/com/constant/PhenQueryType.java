package com.constant;

public enum PhenQueryType {
	
	SINGLE_NODES("single"),POST_NODES("postNodes"),PRE_NODES("preNodes"),
	NSTEP_NODES("nStepNodes"),PATH("path"),UNDEFINED("undefined");

	private String type;

	private PhenQueryType(String type) {
		this.type = type;
	}
	
	public static PhenQueryType getTypeByStr(String name)
	{
		for (PhenQueryType it : PhenQueryType.values())
		{
			if (it.getType().equals(name)) {
				return it;
			}
		}
		return UNDEFINED;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	

}
