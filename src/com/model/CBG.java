package com.model;

import java.util.List;
import java.util.Map;

public class CBG {
	
	private String phen_id;
	private PNode node;
	private Map<String,List<String>> map;
	private String association;
	private String pheno;
	private String[]step1;
	private String[]step2;
	private String[]step3;


	public PNode getNode() {
		return node;
	}
	public void setNode(PNode node) {
		this.node = node;
	}
	public String[] getStep1() {
		return step1;
	}
	public void setStep1(String[] step1) {
		this.step1 = step1;
	}
	public String[] getStep2() {
		return step2;
	}
	public void setStep2(String[] step2) {
		this.step2 = step2;
	}
	public String[] getStep3() {
		return step3;
	}
	public void setStep3(String[] step3) {
		this.step3 = step3;
	}
	public String getPhen_id() {
		return phen_id;
	}
	public void setPhen_id(String phen_id) {
		this.phen_id = phen_id;
	}
	public Map<String, List<String>> getMap() {
		return map;
	}
	public void setMap(Map<String, List<String>> map) {
		this.map = map;
	}
	
	public String getAssociation() {
		return association;
	}
	public void setAssociation(String association) {
		this.association = association;
	}
	public String getPheno() {
		return pheno;
	}
	public void setPheno(String pheno) {
		this.pheno = pheno;
	}
	
	
	
}
