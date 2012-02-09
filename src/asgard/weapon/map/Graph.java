package asgard.weapon.map;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.PriorityQueue;

import asgard.weapon.R;

public class Graph {
	private ArrayList <FloorPlan> maps;
	private NodeList <Node> nodes;
	private ArrayList <Edge> edges;
	public Graph() {
		//Load place holder maps
		maps = new ArrayList <FloorPlan>();
		maps.add(new FloorPlan("TEB2",R.drawable.map));
		maps.add(new FloorPlan("TEB1",R.drawable.map));
		//Make node array
		this.fillNodes();
		//Make edge array
		this.fillEdges();
		
		this.getPathBetween(nodes.get(1), nodes.get(2));
		
	}
	
	public ArrayList <Node> getPathBetween(Node start, Node goal) {
		if (start == null || goal == null) {
			return null;
		}
		PriorityQueue <Node> openSet = new PriorityQueue<Node>();
		openSet.add(start);
		Hashtable<String,Node> closedSet = new Hashtable<String,Node>();
		this.cleanNodes();
		while (!openSet.isEmpty()) {
			Node x = openSet.poll();
			if (x.equals(goal)) {
				//Yipiee Kiy Aye Motherfuckers
			}
			closedSet.put(x.getName(), x);
			ArrayList<Node> neighbours = x.getNeighbours();
			Iterator<Node> it = neighbours.iterator();
			while (it.hasNext()) {
				Node y = it.next();
				boolean newBetter = false;
				if (closedSet.containsKey(y.getName())){
					continue;
				}
				int t = y.getDistanceToNeighbour(x);
				if (t == -1) {
					return null;
				}
				int gTemp = x.getgScore() + t;
				if (!openSet.contains(y.getName())) {
					openSet.add(y);
					y.sethScore(this.h(y, goal));
					newBetter = true;					
				}
				else if (gTemp < y.getgScore()) {
					newBetter = true;
				}
				else {
					newBetter = false;
				}
				if (newBetter) {
					y.setCameFrom(x);
					y.setgScore(gTemp);
					y.setfScore(gTemp + y.gethScore());
				}
				
			}
		}
			
		
		return null;
	}
	
	private int h(Node x, Node goal) {
		int temp = (int) Math.sqrt(((x.getxPos() - goal.getxPos())* (x.getxPos() - goal.getxPos()))
				+ ((x.getyPos() - goal.getyPos()) * (x.getyPos() - goal.getyPos())));
		if (x.getName().charAt(0) == goal.getName().charAt(0)) {
			return temp;
		}
		else {
			return temp + 500;
		}
	}
	
	
	
	private FloorPlan getImageWithName(String name) {
		for (int i = 0; i < maps.size(); i++) { 
			if (maps.get(i).getName().equals(name)) {
				return maps.get(i);
			}				
		}
		return null;
	}
	private void fillNodes() {
		nodes = new NodeList <Node>();
		nodes.add(new Node(this.getImageWithName("TEB2"),100,100,"TEB244"));
		nodes.add(new Node(this.getImageWithName("TEB2"),90,100,"TEB230"));
		nodes.add(new Node(this.getImageWithName("TEB2"),90,140,"TEB220"));
		nodes.add(new Node(this.getImageWithName("TEB2"),100,140,"TEB210"));
		nodes.add(new Node(this.getImageWithName("TEB2"),150,140,"TEB200"));
		nodes.add(new Node(this.getImageWithName("TEB2"),110,100,"TEB250"));		
	}
	private void addEdge(String first, String second, int weight) {
		Node one = nodes.getNodeWithName(first);
		Node two = nodes.getNodeWithName(second);
		Edge temp = new Edge(one,two,weight);
		edges.add(temp);
		one.addEdge(temp);
		two.addEdge(temp);
	}
	private void fillEdges() {
		edges = new ArrayList<Edge>();
		this.addEdge("TEB244", "TEB230", 5);
		this.addEdge("TEB244", "TEB250", 10);
		this.addEdge("TEB200", "TEB250", 40);
		this.addEdge("TEB210", "TEB200", 30);
		this.addEdge("TEB220", "TEB210", 10);
		this.addEdge("TEB230", "TEB220", 30);
	}
	private void cleanNodes() {
		Iterator<Node> i = nodes.iterator();
		while (i.hasNext()) {
			i.next().clearForSearch();
		}
	}

}
