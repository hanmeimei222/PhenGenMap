package com.constant;

public enum PathwayQueryType {
	SINGLE_WAYS("sglways"),SINGLE_GENES("sglgenes"),UNDEFINED("undefined");
	
	private String type;

	private PathwayQueryType(String type) {
		this.type = type;
	}
	
	
	public static PathwayQueryType getTypeByStr(String name)
	{
		//遍历所有枚举值
		for (PathwayQueryType it : PathwayQueryType.values())
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
