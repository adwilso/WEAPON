package asgard.weapon.mapDisplay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
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
	public MapControl (MapDisplay m) {
		g = new Graph();	
		view = m;
		index = 0;
	}
	public void mapGetNodes(){
		
		nodes = g.getNodes().toArray(new Node[1]);
		this.nextMap();
	}
	public void nextMap() {
		if (index >= nodes.length || index < 0) return;
		int start = index;
		String temp = nodes[index].getImage().getName();
		while(index < nodes.length && nodes[index].getImage().getName().equals(temp))
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
		nodes = g.getPathBetween(start, end).toArray(new Node[1]);
		index = 0;
		this.nextMap();
	}
	public void findPoint(String node)
	{
		Node n = g.findNodeWithName(node);
		view.drawNode(n.getImage().getImage(), n.getxPos(), n.getyPos());		
	}

}
