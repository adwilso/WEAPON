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
	public MapControl (Activity c, View context) {
		g = new Graph();
		//MapDisplay m = new MapDisplay();
		//Intent i = new Intent(context.getContext(),MapDisplay.class);
		//c.startActivity(i);
		
	}

}
