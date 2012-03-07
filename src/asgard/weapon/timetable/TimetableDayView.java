package asgard.weapon.timetable;

import java.util.ArrayList;

import asgard.weapon.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class TimetableDayView extends Activity {

	String[] mHours;

	ArrayList<Session> mSessions;

	RelativeLayout mScreen;
	RelativeLayout mSessionForeground;

	LayoutInflater mLayoutInflator;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.day_view);

		// Load resources and inflate the layout
		initialize();
	}

	private void initialize() {

		// Inflate all UI elements and resources
		mSessionForeground = (RelativeLayout) findViewById(R.id.day_view_container);
		mHours = getResources().getStringArray(R.array.course_times);

		drawBackground();
		getSessions();
		drawSessions();
	}

	// Make the background
	public void drawBackground() {

		LayoutInflater li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

		for (int i = 0; i < mHours.length; i += 2) {

			// Inflate an item and add it to the background list
			LinearLayout ll = (LinearLayout) li.inflate(R.layout.blank_session,
					null);
			ll.setId(i + 2);
			TextView tv = (TextView) ll.findViewById(R.id.hour_text_box);
			tv.setText(mHours[i]);

			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			try {
				LinearLayout aboveView = (LinearLayout) findViewById(i);
				int id = aboveView.getId();
				lp.addRule(RelativeLayout.BELOW, id);
				ll.setLayoutParams(lp);
			} catch (Exception e) {
				lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				ll.setLayoutParams(lp);
			}
			mSessionForeground.addView(ll);
		}

	}

	// Get (for now add) the sessions from the controller
	private void getSessions() {
		mSessions = new ArrayList<Session>();

		mSessions.add(new Session(8, 30, 0.5f, 1, "My first class", "course1",
				null));
		mSessions.add(new Session(9, 00, 2.0f, 1, "My second class", "course2",
				null));
		mSessions.add(new Session(11, 00, 1.0f, 1, "My third class", "course3",
				null));
		mSessions.add(new Session(15, 30, 3.0f, 1, "My final class", "course4",
				null));
	}
	
	// Loads the appropriate view for each session
	public void drawSessions() {
		for (Session s : mSessions) {
			int position = s.getHour();
			TextView tv = new TextView(this);
			tv.setText("Test");
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			try {
				View v = mSessionForeground.getChildAt(position);
				int id = v.getId();
				lp.addRule(RelativeLayout.ALIGN_TOP, id);
				mSessionForeground.addView(tv, lp);
			} catch (Exception e) {

			}

		}
	}
}