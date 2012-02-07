package asgard.weapon.map;

import java.util.ArrayList;

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

}
