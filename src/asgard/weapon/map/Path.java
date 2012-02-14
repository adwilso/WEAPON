package asgard.weapon.map;

import java.util.ArrayList;
/**
 * 
 * @author Adam
 * 
 * 
 */
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
		Node first = this.start;
		Node second = null;
		Edge edge = null;
		if ( route == null  || route.isEmpty()) {
			return false;
		}
		if (!route.get(0).isAdjacent(this.start)) {
			return false;
		}
		for (int i = 0; i < route.size(); i++) {
			edge = route.get(i);
			second = edge.getOtherNode(first);
			if (!route.get(i).isAdjacent(second)) {
				return false;
			}
			if (!route.get(i + 1).isAdjacent(second)) {
				return false;
			}
			first = second;
			second = route.get(i+1).getOtherNode(second);
			edge = route.get(i+1);
		}
		return true;
	}
	public Edge getNextEdge() {
		if (this.start == null  || this.end == null) {
			return null;
		}
		if (this.currentIndex == -2) {
			if (!this.isReady()) {
				return null;
			}
			this.currentIndex = 0;
		}
		if (route.size() <= currentIndex) return null;
		return route.get(currentIndex++);
	
	}
	public Edge lookAheadEdge() {
		if (this.currentIndex < 0) return null;
		return route.get(currentIndex  + 1);
	}
	
}
