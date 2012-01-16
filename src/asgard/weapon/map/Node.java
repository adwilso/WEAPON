package asgard.weapon.map;

import java.util.ArrayList;
public class Node {
	private int xPos;
	private int yPos;
	private String name;
	private ArrayList<Edge> edges;
	private Image image;
	
	public Node ( Image imageRef , int x , int y , String n) {
		name = n;
		image = imageRef;
		xPos = x;
		yPos = y;
	}
	private Node(){}
	private Node (Node n){}
	public boolean addEdge(Edge in)	{
		if (edges.contains(in)) {
			return false;
		}
		edges.add(in);
		return true;
	}
	public void getXYPos(Integer x, Integer y) {
		x = new Integer(xPos);
		y = new Integer(yPos);		
	}
	public Image getImage() {
		return image;
	}
	
	public String getName() {
		return name;
	}
	
}
