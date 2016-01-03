package com.constant;

public enum NodeType {
	MP("mp"),GENE("gene"),PATHWAY("pathway"),PPI("ppi"),UNDEFINED(null);
	private String type;
	private NodeType(String type) {
		this.type = type;
	}
	public static NodeType getTypeByStr(String name)
	{
		//遍历所有枚举值
		for (NodeType it : NodeType.values())
		{
			if (it.type.equals(name)) {
				return it;
			}
		}
		return UNDEFINED;
	}
}
