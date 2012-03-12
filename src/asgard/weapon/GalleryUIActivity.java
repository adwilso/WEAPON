package asgard.weapon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;
import asgard.weapon.mapDisplay.MapDisplay;

public class GalleryUIActivity extends Activity {
    
	AlertDialog.Builder builder;
	AlertDialog admiral, weapon;
	Gallery gallery;
	Intent i;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.mainmenu);
	    i = new Intent(this,MapDisplay.class);
	    gallery = (Gallery) findViewById(R.id.gallery);
	    gallery.setAdapter(new ImageAdapter(this)); 
	    gallery.setSelection(3);
	    
	    gallery.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
				switch (position){
	        	
	        	case (0): {
	        		admiral.show();
	        		break;
	        	}
	        	case (3): {	        		
	        		startActivity(i);
	        		break;
	        	}
	        	case (4):{
	        		break;
	        	}
	        	case (6): {
	        		admiral.show();
	        		break;
	        	}
	        	default: {
	        		weapon.show();
	        	}
        	}
				
				Toast.makeText(GalleryUIActivity.this, "Text", Toast.LENGTH_SHORT).show();
				return false;
			}	    	
	    });
	    
	    gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
	        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
	        	
	        	String function;
	        	switch (position){
	        	
		        	case (0): {
		        		function = "Admiral Asgard";
		        		break;
		        	}
		        	case (3): {
		        		function = "Room Locator";
		        		break;
		        	}
		        	case (4):{
		        		function = "Timetable";
		        		break;
		        	}
		        	case (6): {
		        		function = "Admiral Asgard";
		        		break;
		        	}
		        	default: {
		        		function = "WEAPON";
		        	}
	        	}
	            Toast.makeText(GalleryUIActivity.this, function, Toast.LENGTH_SHORT).show();
	        }

			public void onNothingSelected(AdapterView<?> parent) {				
			}
	    	});
	    
	    builder = new AlertDialog.Builder(this);
	    builder.setTitle("About Admiral Asgard");
	    builder.setMessage("Admiral \"Voldemort\" Asgard:\n aka \"He Who Must Not Be Named\"\n aka \"Terror of the Deep\"\n aka \"It's A Trap\"\n" +
	    		"Has led a brutal and bloody campaign against mankind. We the Asgard development team, have sworn fealty to our Feudal Lord and " +
	    		"wish that death come quickly to his enemies.\nWe will continue to serve him as will our lineage.\nAs the Admiral says, " +
	    		"\"Eeeeeeeeeeeeh ka eeeehk, Uh'k squa kkkkkk quaaaaaaa\"\nwhich loosely translated in english is;\n\"If there's a human I can't kill, " +
	    		"I haven't found him and killed him yet.\"\n~One Admiral to Rule Them All~");
	    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {	
		    public void onClick(DialogInterface dialog, int which) {  
		    	dialog.cancel();
		       }
	    });
	    
	    admiral = builder.create();
	    
	    builder = new AlertDialog.Builder(this);
	    builder.setTitle("WEAPON");
	    builder.setMessage("Western Engineering APplicatiON: WEAPON\n\nCreated by:\nAsgard Development Team\n\nAsgard Is:\nSteven Taillieu\nJarrett Horsman" +
	    		"\nBen Monis\nAdam Wilson\nRon Norris");
	    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {	
		    public void onClick(DialogInterface dialog, int which) {  
		    	dialog.cancel();
		       }
	    });
	    
	    weapon = builder.create();
		}
	
  
	
	
	/*
	 * ImageAdapter is a custom adapter class to handle the gallery items associated with the UI options.
	 */
	public class ImageAdapter extends BaseAdapter {
	    int mGalleryItemBackground;
	    private Context mContext;

	    private Integer[] mImageIds = {
	    		R.drawable.asgard,
	    		R.drawable.ui_back,
	    		R.drawable.ui_back,
	    		R.drawable.map_icon0,
	            R.drawable.timetable_icon0,
	            R.drawable.ui_back,
	            R.drawable.asgard
	    };

	    public ImageAdapter(Context c) {
	        mContext = c;
	        TypedArray attr = mContext.obtainStyledAttributes(R.styleable.HelloGallery);
	        mGalleryItemBackground = attr.getResourceId(
	                R.styleable.HelloGallery_android_galleryItemBackground, 0);
	        attr.recycle();
	    }

	    public int getCount() {
	        return mImageIds.length;
	    }

	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView imageView = new ImageView(mContext);
	        imageView.setImageResource(mImageIds[position]);
	        imageView.setLayoutParams(new Gallery.LayoutParams(144, 180));
	        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
	        imageView.setBackgroundResource(mGalleryItemBackground);

	        return imageView;
	    }
	}
}