package com.model;

import java.util.Map;

public class Pathway {
	private String pw_id;
	private String pw_name;
	private String class_1;
	private String class_2;
	private Map<GNode,Boolean> symbols;

	public String getPw_id() {
		return pw_id;
	}
	public void setPw_id(String pw_id) {
		this.pw_id = pw_id;
	}
	public String getPw_name() {
		return pw_name;
	}
	public void setPw_name(String pw_name) {
		this.pw_name = pw_name;
	}
	public String getClass_1() {
		return class_1;
	}
	public void setClass_1(String class_1) {
		this.class_1 = class_1;
	}
	public String getClass_2() {
		return class_2;
	}
	public void setClass_2(String class_2) {
		this.class_2 = class_2;
	}
	public Map<GNode, Boolean> getSymbols() {
		return symbols;
	}
	public void setSymbols(Map<GNode, Boolean> symbols) {
		this.symbols = symbols;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.pw_id.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		Pathway other = (Pathway)obj;
		if(other!=null && other.getPw_id().equals(this.pw_id))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
