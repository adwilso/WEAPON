package asgard.weapon.map;
import android.graphics.Bitmap;

import java.util.ArrayList;
public class Node {
	private int xPos;
	private int yPos;
	private String name;
	private ArrayList<Edge> edges;
	private FloorPlan image;
	
	public Node (FloorPlan imageRef , int x , int y , String n) {
		name = n;
		image = imageRef;
		xPos = x;
		yPos = y;
		edges = new ArrayList<Edge>();
	}
	@SuppressWarnings("unused")
	private Node(){}
	@SuppressWarnings("unused")
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
	public FloorPlan getImage() {
		return image;
	}
	
	public String getName() {
		return name;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + xPos;
		result = prime * result + yPos;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (xPos != other.xPos)
			return false;
		if (yPos != other.yPos)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Node [xPos=" + xPos + ", yPos=" + yPos + ", name=" + name + "]";
	}
	
}
