package com.model.d3;

import java.util.Set;

import com.model.cytoscape.Graph;

public class PathwayGraphAndTree {

	private Graph graph;
	private Set<TreeNode> treenode;
	public Graph getGraph() {
		return graph;
	}
	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	public Set<TreeNode> getTreenode() {
		return treenode;
	}
	public void setTreenode(Set<TreeNode> treenode) {
		this.treenode = treenode;
	}
	public PathwayGraphAndTree(Graph graph, Set<TreeNode> treenode) {
		super();
		this.graph = graph;
		this.treenode = treenode;
	}
	
}
