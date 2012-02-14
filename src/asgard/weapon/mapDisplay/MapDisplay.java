package asgard.weapon.mapDisplay;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import asgard.weapon.R;
/**
 * 
 * @author Adam
 * 
 * 
 */
public class MapDisplay extends Activity {
	private static ImageView mapDisplay;
	
	public MapDisplay() {
		super();
	}
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmap);
        mapDisplay = (ImageView)this.findViewById(R.id.mapDisplay);
        mapDisplay.setImageResource(R.drawable.map);
                
	}

}
