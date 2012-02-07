package asgard.weapon;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import asgard.weapon.map.*;
public class WEAPONActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        FloorPlan f = new FloorPlan("TEB2");
        ImageView i = new ImageView(this);
        i.setImageResource(f.getImage());
        setContentView(i);
        Graph  g = new Graph();
       
    }
}