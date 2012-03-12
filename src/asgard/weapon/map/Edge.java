package asgard.weapon.map;

import java.util.Arrays;
/**
 * 
 * @author Adam
 * 
 * 
 */
public class Edge {
	private int weight;
	private Node[] nodes; 
	
	@SuppressWarnings("unused")
	private Edge(){}
	
	public Edge (Node first, Node second, int weight) {
		nodes = new Node[2];
		nodes[0] = first;
		nodes[1] = second;
		this.weight = weight;		
	}
	public int getWeight(){
		return weight;
	}
	public Node getOtherNode(Node current) {
		if (current == null)  return null;
		if (current == nodes[0] || current.equals(nodes[0]))
			return nodes[1];
		if (current == nodes[1] || current.equals(nodes[1]))
			return nodes[0];
		return null;
			            
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(nodes);
		result = prime * result + weight;
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
		Edge other = (Edge) obj;
		if (!Arrays.equals(nodes, other.nodes))
			return false;
		if (weight != other.weight)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Edge [weight=" + weight + ", nodes=" + nodes[0].getName() 
			+ " and " +	nodes[1].getName();
	}
	public boolean isAdjacent(Node n) {
		if (n == null) return false;
		if (n.equals(nodes[0]) || n.equals(nodes[1])){
			return true;
		}
		return false;
	}
	
	
}
