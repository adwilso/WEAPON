package asgard.weapon.mapDisplay;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import asgard.weapon.R;
import asgard.weapon.map.*;
/**
 * 
 * @author Adam
 * 
 * 
 */
public class MapDisplay extends Activity {
	private static ImageView mapDisplay;
	private Graph g;
	public MapDisplay() {
		super();
	}
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmap);
        mapDisplay = (ImageView)this.findViewById(R.id.mapDisplay);
        mapDisplay.setImageResource(R.drawable.map);
        g = new Graph();
	}
	private void drawNode() {
		ArrayList <Node> nodes = g.getNodes();
		Iterator <Node> i = nodes.iterator();
		while (i.hasNext()) {
			Node n = i.next();
			ImageView v = new ImageView(this);
			//v.setLeft(n.getxPos());
			v.setImageResource(R.drawable.node);
			
		}
	}

}
