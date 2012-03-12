package asgard.weapon.mapDisplay;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
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
	private MapImageView mapScreen;
	private Graph g;
	public MapDisplay() {
		super();
	}
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mapScreen = new MapImageView(this);
        setContentView(R.layout.maplayout);
        //setContentView(R.layout.mainmap);
        //mapDisplay = (ImageView)this.findViewById(R.id.mapDisplay);
        mapScreen = (MapImageView)this.findViewById(R.id.mapImageView1);
        mapScreen.setImage(R.drawable.seb0);
        //mapDisplay.setImageResource(R.drawable.map);
        g = new Graph();
        //mapScreen.setImage(R.drawable.seb0);
        //mapScreen.scale(0.5);
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
