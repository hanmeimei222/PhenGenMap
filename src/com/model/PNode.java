package com.model;

import java.util.Map;


public class PNode {

	private String pheno_id;
	private String pheno_name;
	private String pheno_def;
	private String pheno_level;
	private Map<PNode,Boolean> father;
	private Map<PNode,Boolean> son;

	private String group;

	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getPheno_id() {
		return pheno_id;
	}
	public void setPheno_id(String pheno_id) {
		this.pheno_id = pheno_id;
	}
	public String getPheno_name() {
		return pheno_name;
	}
	public void setPheno_name(String pheno_name) {
		this.pheno_name = pheno_name;
	}
	public String getPheno_def() {
		return pheno_def;
	}
	public void setPheno_def(String pheno_def) {
		this.pheno_def = pheno_def;
	}
	public String getPheno_level() {
		return pheno_level;
	}
	public void setPheno_level(String pheno_level) {
		this.pheno_level = pheno_level;
	}
	public Map<PNode, Boolean> getFather() {
		return father;
	}
	public void setFather(Map<PNode, Boolean> father) {
		this.father = father;
	}
	public Map<PNode, Boolean> getSon() {
		return son;
	}
	public void setSon(Map<PNode, Boolean> son) {
		this.son = son;
	}
	
	@Override  
	public int hashCode() {  

		return pheno_id.hashCode();  
	}  
	@Override
	public boolean equals(Object obj) {
		PNode n=(PNode)obj;
		if (n instanceof PNode)	
		{
			if(n.getPheno_id().equals(this.pheno_id))
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
