package asgard.weapon.map;

import java.util.ArrayList;

public class Path {
	private Node start;
	private Node end;
	ArrayList <Edge> route;
	int currentIndex;
	
	public Path () {
		currentIndex = -2;
		start = null;
		end = null;
		route = null; 
	}
	public Path (Node start) {
		this.start = start;
		currentIndex = -2;
		end = null;
		route = null;
	}
	public Path (Node start, Node end) {
		this.start = start;
		this.end = end;
		route = null;
		currentIndex = -2;
	}
	public boolean setStartNode(Node start) {
		if (currentIndex != -2) return false;
		this.start = start;
		return true;
	}
	public boolean setEndNode(Node end) {
		if (currentIndex != -2) return false;
		this.end = end;
		return false;
	}
	
	public Node getStartNode() {
		return this.start;
	}
	public Node getEndNode() {
		return this.end;
	}
	public boolean addNextEdge(Edge e) {
		if (route == null) route = new ArrayList<Edge>();
		if (currentIndex != -2) return false;
		route.add(e);	
		return true;
	}
	private boolean isReady(){
		//go through the path to make sure that it is okay.
		return false;
	}
	
	
}
