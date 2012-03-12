package asgard.weapon.mapDisplay;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ZoomControls;
import asgard.weapon.R;
import asgard.weapon.map.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * @author Adam
 * 
 * 
 */
public class MapDisplay extends Activity implements OnClickListener {
	private static ImageView mapDisplay;
	private Button searchButton;
	private Button nextMapButton;
	private MapImageView mapScreen;
	private MapControl control;
	private ZoomControls zoomButton;

	Button b1;
	AlertDialog opt, showRoom, fromRoom, toRoom;
	AlertDialog.Builder builder;
	final CharSequence[] options = { "Show Room", "Find Path Between Rooms" };
	final CharSequence[] buildings = { "SEB", "TEB" };
	String room, fRoom, tRoom;
	final String selectMsg = "Enter Room Number Here";
	EditText input0, input1, input2;

	public MapDisplay() {
		super();
		control = new MapControl(this);

	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// mapScreen = new MapImageView(this);
		setContentView(R.layout.maplayout);
		// setContentView(R.layout.mainmap);
		// mapDisplay = (ImageView)this.findViewById(R.id.mapDisplay);
		mapScreen = (MapImageView) this.findViewById(R.id.mapImageView1);
		mapScreen.setImage(R.drawable.seb0);
		mapScreen.scale(0.5);

		searchButton = (Button) this.findViewById(R.id.button1);
		searchButton.setOnClickListener(this);

		nextMapButton = (Button) this.findViewById(R.id.btnNextMap);
		nextMapButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				control.nextMap();
			}

		});

		zoomButton = (ZoomControls) this.findViewById(R.id.zoomControls1);
		zoomButton.setOnZoomInClickListener(new OnClickListener() {
			public void onClick(View v) {
				mapScreen.scale(1.5);
			}
		});
		zoomButton.setOnZoomOutClickListener(new OnClickListener() {
			public void onClick(View v) {
				mapScreen.scale(0.5);
			}
		});
		this.steve();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		control.mapGetNodes();
		opt.show();

	}

	public void drawNodes(Node[] n) {
		mapScreen.drawNodes(n);
	}

	public void updateMap(int resource) {
		mapScreen.setImage(resource);
	}

	public void steve() {
		input0 = new EditText(this);
		input0.setInputType(InputType.TYPE_CLASS_NUMBER);
		input0.setText(selectMsg);
		input1 = new EditText(this);
		input1.setInputType(InputType.TYPE_CLASS_NUMBER);
		input1.setText(selectMsg);
		input2 = new EditText(this);
		input2.setInputType(InputType.TYPE_CLASS_NUMBER);
		input2.setText(selectMsg);

		builder = new AlertDialog.Builder(this);
		builder.setTitle("From");
		builder.setView(input0);
		builder.setItems(buildings, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int building) {

				switch (building) {
				case (0): {
					fRoom = (String) buildings[0] + input0.getText().toString();
					Toast.makeText(getApplicationContext(), fRoom,
							Toast.LENGTH_SHORT).show();
					toRoom.show();
					input0.setText(selectMsg);
					input1.selectAll();
					break;
				}
				case (1): {
					fRoom = (String) buildings[1] + input0.getText().toString();
					Toast.makeText(getApplicationContext(), fRoom,
							Toast.LENGTH_SHORT).show();
					toRoom.show();
					input0.setText(selectMsg);
					input1.selectAll();
					break;
				}
				default: {
					fRoom = "Invalid Input";
					Toast.makeText(getApplicationContext(), fRoom,
							Toast.LENGTH_SHORT).show();
				}
				}
			}
		});

		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						input0.setText(selectMsg);
					}
				});

		fromRoom = builder.create();

		builder = new AlertDialog.Builder(this);
		builder.setTitle("To");
		builder.setView(input1);
		builder.setItems(buildings, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int building) {

				switch (building) {
				case (0): {
					tRoom = (String) buildings[0] + input1.getText().toString();
					Toast.makeText(getApplicationContext(), tRoom,
							Toast.LENGTH_SHORT).show();
					input1.setText(selectMsg);
					break;
				}
				case (1): {
					tRoom = (String) buildings[1] + input1.getText().toString();
					Toast.makeText(getApplicationContext(), tRoom,
							Toast.LENGTH_SHORT).show();
					input1.setText(selectMsg);
					break;
				}
				default: {
					tRoom = "Invalid Input";
					Toast.makeText(getApplicationContext(), tRoom,
							Toast.LENGTH_SHORT).show();
					input1.setText(selectMsg);
				}
				}
			}
		});

		builder.setNeutralButton("Tim Horton's",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						tRoom = "SEBTH";
						Toast.makeText(getApplicationContext(), tRoom,
								Toast.LENGTH_SHORT).show();
						input1.setText(selectMsg);
					}
				});

		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						input1.setText(selectMsg);
					}
				});

		toRoom = builder.create();

		builder = new AlertDialog.Builder(this);
		builder.setTitle("Choose A Room");
		builder.setView(input2);
		builder.setItems(buildings, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int building) {

				switch (building) {
				case (0): {
					room = (String) buildings[0] + input2.getText().toString();
					Toast.makeText(getApplicationContext(), room,
							Toast.LENGTH_SHORT).show();
					input2.setText(selectMsg);
					break;
				}
				case (1): {
					room = (String) buildings[1] + input2.getText().toString();
					Toast.makeText(getApplicationContext(), room,
							Toast.LENGTH_SHORT).show();
					input2.setText(selectMsg);
					break;
				}
				default: {
					room = "Invalid Input";
					Toast.makeText(getApplicationContext(), room,
							Toast.LENGTH_SHORT).show();
					input2.setText(selectMsg);
				}
				}
			}
		});

		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						input2.setText(selectMsg);
					}
				});

		showRoom = builder.create();

		builder = new AlertDialog.Builder(this);
		builder.setTitle("Choose An Option").setItems(options,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						switch (id) {

						case (0):
							Toast.makeText(getApplicationContext(), options[0],
									Toast.LENGTH_SHORT).show();
							showRoom.show();
							input2.selectAll();
							break;
						case (1):
							Toast.makeText(getApplicationContext(), options[1],
									Toast.LENGTH_SHORT).show();
							fromRoom.show();
							input0.selectAll();
							break;
						default:
							// Do Nothing
						}
					}
				});

		opt = builder.create();

	}

}
