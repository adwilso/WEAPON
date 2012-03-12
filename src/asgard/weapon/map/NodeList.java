package asgard.weapon.map;

import java.util.ArrayList;
/**
 * 
 * @author Adam
 * 
 * 
 */
public class NodeList<E extends Node> extends ArrayList<E> {

	private static final long serialVersionUID = 772889791857605233L;
	
	public NodeList() {
		super();	
	}
	
	public E getNodeWithName(String name) {
		for (int i = 0; i < this.size(); i++) {
			if (this.get(i).getName().equals(name))
			{
				return this.get(i);
			}
		}
		
		return null;
		
	}
	

}
