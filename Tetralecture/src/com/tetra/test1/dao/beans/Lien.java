package com.tetra.test1.dao.beans;

public class Lien {

	private long source;
	private long target;
	private int value;
	private boolean oriented;
	private String date;

	public Lien() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Lien(int source, int target, int value, boolean oriented, String date) {
		super();
		this.source = source;
		this.target = target;
		this.value = value;
		this.oriented = oriented;
		this.date = date;
	}

	public long getSource() {
		return source;
	}

	public void setSource(long source) {
		this.source = source;
	}

	public long getTarget() {
		return target;
	}

	public void setTarget(long target) {
		this.target = target;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isOriented() {
		return oriented;
	}

	public void setOriented(boolean oriented) {
		this.oriented = oriented;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Lien [source=" + source + ", target=" + target + ", value="
				+ value + ", oriented=" + oriented + ", date=" + date + "]";
	}

}
