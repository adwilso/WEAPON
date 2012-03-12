package asgard.weapon.mapDisplay;


import asgard.weapon.map.*;
/**
 * 
 * @author Adam
 * 
 * 
 */
public class MapControl {
	private Graph g;
	private Node[] nodes;
	private MapDisplay view;
	private int index;
	private boolean buttonAllowed;
	public MapControl (MapDisplay m) {
		buttonAllowed = false;
		g = new Graph();	
		view = m;
		index = -1;
		view.setNextMapState(false);
	}
	public void mapGetNodes(){
		
		nodes = g.getNodes().toArray(new Node[1]);
		this.nextMap();
		
	}
	public void nextMap() {
		if (nodes == null) return;
		if (index >= nodes.length || index < 0 || !buttonAllowed) {
			buttonAllowed = false;
			return;
		}
		
		buttonAllowed = true;
		int start = index;
		String temp = nodes[index].getImage().getName();
		while(index < nodes.length && nodes[index].getImage().getName()
				.equals(temp))
		{
			index++;
		}
		Node[] onMap = new Node[index-start];
		for(int i = start; i < index; i++)
		{
			onMap[i - start] = nodes[i] ;
		}
		view.drawNodes(onMap);
		view.updateMap(nodes[start].getImage().getImage());
	}
	public void findPath(String start, String end)
	{
		buttonAllowed = true;
		try
		{
			nodes = g.getPathBetween(start, end).toArray(new Node[1]);
		}
		catch (Exception ex)
		{
			return;
		}
		index = 0;
		this.nextMap();
	}
	public void findPoint(String node)
	{
		buttonAllowed = false;
		Node n = g.findNodeWithName(node);
		if (n == null) return;
		view.drawNode(n.getImage().getImage(), n.getxPos(), n.getyPos());		
	}


}
