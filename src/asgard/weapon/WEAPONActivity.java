package asgard.weapon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
<<<<<<< HEAD
import android.view.View.OnClickListener;

public class WEAPONActivity extends Activity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
	}

	@Override
	public void onClick(View v) {
		
=======
import android.widget.Button;
import android.widget.ImageView;

import asgard.weapon.map.*;
import asgard.weapon.mapDisplay.MapDisplay;
public class WEAPONActivity extends Activity implements View.OnClickListener{
    /** Called when the activity is first created. */
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final Button startMap = (Button)findViewById(R.id.button1);
        startMap.setOnClickListener(this);
        
        FloorPlan f = new FloorPlan("TEB2",R.drawable.map);
        final ImageView i = (ImageView)findViewById(R.id.imageView1);
        i.setImageResource(f.getImage());

   
        //Graph  g = new Graph();

       
       
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//MapControl m = new MapControl();
		Intent i = new Intent(this,MapDisplay.class);
		startActivity(i);
>>>>>>> MapDev
	}
}