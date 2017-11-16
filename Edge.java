/*
Author: Jimmy Li, #29903320, NetID: jli119
Date: April 29, 2017
Course: CSC172, Professor Ted Pawlicki
Assignment: Project 4 - Street Mapping
Title: Edge.java
*/

public class Edge {
	private Vertex v;
	private Vertex w; // an edge from v to w
	private String id;
	private boolean partOfMWST;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private double weight;
	private boolean isPartOfPath;
	
	public Edge(){
		
	}
	public Edge(Vertex v, Vertex w) {
		this.v = v;
		this.w = w;
		this.partOfMWST = false;
	}
	public Edge(Vertex v, Vertex w, double weight) {
		this.v = v;
		this.w = w;
		this.weight = weight;
		this.isPartOfPath = false;
	}

	public boolean isPartOfPath() {
		return isPartOfPath;
	}

	public void setPartOfPath(boolean isPartOfPath) {
		this.isPartOfPath = isPartOfPath;
	}

	public Vertex getV() {
		return v;
	}

	public void setV(Vertex v) {
		this.v = v;
	}

	public Vertex getW() {
		return w;
	}

	public void setW(Vertex w) {
		this.w = w;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public boolean isPartOfMWST() {
		return partOfMWST;
	}

	public void setPartOfMWST(boolean partOfMWST) {
		this.partOfMWST = partOfMWST;
	}
}