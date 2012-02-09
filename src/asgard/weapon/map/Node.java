package asgard.weapon.map;

import java.util.ArrayList;
import java.util.Iterator;

public class Node implements Comparable <Node>{
	private int xPos;
	private int yPos;
	private String name;
	private ArrayList<Edge> edges;
	private FloorPlan image;
	private int fScore;
	private int hScore;
	private int gScore;
	private Node cameFrom;
	
	
	
	public Node (FloorPlan imageRef , int x , int y , String n) {
		name = n;
		image = imageRef;
		xPos = x;
		yPos = y;
		edges = new ArrayList<Edge>();
		fScore = gScore = hScore = 0;
		cameFrom = null;
	}
	@SuppressWarnings("unused")
	private Node(){}
	@SuppressWarnings("unused")
	private Node (Node n){}
	
	public void clearForSearch() {
		fScore = gScore = hScore = 0;
		cameFrom = null;		
	}
	
	public int getDistanceToNeighbour(Node n) {
		if (n == null) { 
			return -1;
		}
		
		Iterator<Edge> it = edges.iterator();
		while (it.hasNext()) {
			Edge e = it.next();
			if (e.getOtherNode(this).equals(n)) {
				return e.getWeight();
			}
			
		}
		return -1;		
	}
	/**
	 * @return the xPos
	 */
	public int getxPos() {
		return xPos;
	}
	/**
	 * @return the yPos
	 */
	public int getyPos() {
		return yPos;
	}
	public boolean isNeighbour(Node n) {
		if (n == null) {
			return false;
		}
		ArrayList<Node> list = this.getNeighbours();
		Iterator<Node> it = list.iterator();
		while (it.hasNext()) {
			if (it.next().equals(this)) {
				return true;
			}		
		}
		return false;
	}

	
	@Override
	public int compareTo(Node another) {
		if (this.getfScore() > another.getfScore()) {
			return 1;
		}
		else if (this.getfScore() == another.getfScore()) {
			return 0;
		}
		else {
			return -1;
		}
	}
	
	public ArrayList<Node> getNeighbours() {
		if (edges == null) {
			return null;
		}
		ArrayList <Node> n = new ArrayList<Node>();
		Iterator<Edge> t = edges.iterator();
		while (t.hasNext()) {
			n.add(t.next().getOtherNode(this));
		}
		return n;
	}
	
	
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
	/**
	 * @return the fScore
	 */
	public int getfScore() {
		return fScore;
	}
	/**
	 * @param fScore the fScore to set
	 */
	public void setfScore(int fScore) {
		this.fScore = fScore;
	}
	/**
	 * @return the hScore
	 */
	public int gethScore() {
		return hScore;
	}
	/**
	 * @param hScore the hScore to set
	 */
	public void sethScore(int hScore) {
		this.hScore = hScore;
	}
	/**
	 * @return the gScore
	 */
	public int getgScore() {
		return gScore;
	}
	/**
	 * @param gScore the gScore to set
	 */
	public void setgScore(int gScore) {
		this.gScore = gScore;
	}
	/**
	 * @return the cameFrom
	 */
	public Node getCameFrom() {
		return cameFrom;
	}
	/**
	 * @param cameFrom the cameFrom to set
	 */
	public void setCameFrom(Node cameFrom) {
		this.cameFrom = cameFrom;
	}


	
}
