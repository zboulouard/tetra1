package com.tetra.test1.dao.beans;

import java.util.List;

public class Graphe {

	private List<Noeud> nodes;
	private List<Lien> links;

	public Graphe() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Graphe(List<Noeud> nodes, List<Lien> links) {
		super();
		this.nodes = nodes;
		this.links = links;
	}

	public List<Noeud> getNodes() {
		return nodes;
	}

	public void setNodes(List<Noeud> nodes) {
		this.nodes = nodes;
	}

	public List<Lien> getLinks() {
		return links;
	}

	public void setLinks(List<Lien> links) {
		this.links = links;
	}

}
