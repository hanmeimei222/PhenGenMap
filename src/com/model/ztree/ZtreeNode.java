package com.model.ztree;

import java.util.List;

import com.model.PNode;

public class ZtreeNode {

	private String id;
	private String text;
	private String value;
	private boolean showcheck;
	private boolean complete;
	private boolean isexpand;
	private int checkstate;
	private boolean hasChildren;
	private List<ZtreeNode> ChildNodes;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isShowcheck() {
		return showcheck;
	}
	public void setShowcheck(boolean showcheck) {
		this.showcheck = showcheck;
	}
	public boolean isComplete() {
		return complete;
	}
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	public boolean isIsexpand() {
		return isexpand;
	}
	public void setIsexpand(boolean isexpand) {
		this.isexpand = isexpand;
	}
	public int getCheckstate() {
		return checkstate;
	}
	public void setCheckstate(int checkstate) {
		this.checkstate = checkstate;
	}
	public boolean isHasChildren() {
		return hasChildren;
	}
	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}
	public List<ZtreeNode> getChildNodes() {
		return ChildNodes;
	}
	public void setChildNodes(List<ZtreeNode> childNodes) {
		ChildNodes = childNodes;
	}
	
	
	
	public ZtreeNode() {
		super();
	}
	public ZtreeNode(String id, String text, String value, boolean showcheck,
			boolean complete, boolean isexpand, int checkstate,
			boolean hasChildren, List<ZtreeNode> childNodes) {
		super();
		this.id = id;
		this.text = text;
		this.value = value;
		this.showcheck = showcheck;
		this.complete = complete;
		this.isexpand = isexpand;
		this.checkstate = checkstate;
		this.hasChildren = hasChildren;
		ChildNodes = childNodes;
	}
	
	
	public ZtreeNode(PNode p)
	{
		this.id = p.getPheno_id();
		this.text = p.getPheno_name();
		this.value = p.getPheno_id();
		this.showcheck = true;
		this.complete = true;
		this.isexpand = false;
		this.checkstate = 0;
		if(p.getSon()!=null && p.getSon().size()!=0)
		{
			this.hasChildren = true;
		}
		else
		{
			this.hasChildren = false;
		}

	}
	
}
