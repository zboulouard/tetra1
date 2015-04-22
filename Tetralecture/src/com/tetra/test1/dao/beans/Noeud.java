package com.tetra.test1.dao.beans;

public class Noeud {

	private String name;
	private int group;

	public Noeud() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Noeud(String name, int group) {
		super();
		this.name = name;
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return "Noeud [name=" + name + ", group=" + group + "]";
	}

}
