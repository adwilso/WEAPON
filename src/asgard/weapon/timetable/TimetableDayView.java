package asgard.weapon.timetable;

import java.util.ArrayList;
import java.util.Calendar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import asgard.weapon.ConditionCodes;
import asgard.weapon.R;

public class TimetableDayView extends Activity implements OnClickListener,
		Handler.Callback {

	private Handler mHandler;

	private TimetableController mController;

	private String[] mHours;

	private ArrayList<Session> mSessions;

	private RelativeLayout mScreen;
	private TextView mTitle;
	private ImageView mLeftButton;
	private ImageView mRightButton;

	private int mWeekday;
	private Timetable mTimetable;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timetable_day_view);

		// Make handler to pass to the controller
		mHandler = new Handler(this);

		// Get a reference to controller and register this view to its handlers
		mController = TimetableController.getController(this);
		mController.addHandler(mHandler);

		// Load resources and inflate the layout
		initialize();
	}

	private void initialize() {

		// Inflate all UI elements and resources
		mScreen = (RelativeLayout) findViewById(R.id.day_view_container);
		mHours = getResources().getStringArray(R.array.course_times);
		mTitle = (TextView) findViewById(R.id.timetable_day_view_day_text);

		mWeekday = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;

		mTitle.setText(getDayOfWeek());

		mLeftButton = (ImageView) findViewById(R.id.timetable_day_view_left_button);
		mRightButton = (ImageView) findViewById(R.id.timetable_day_view_right_button);

		mLeftButton.setOnClickListener(this);
		mRightButton.setOnClickListener(this);

		drawBackground();
		mController.getHandler().obtainMessage(ConditionCodes.V_GET_TIMETABLE)
				.sendToTarget();
	}

	// Get the day of the week
	private String getDayOfWeek() {
		switch (mWeekday) {
		case 0:
			return "Sunday";
		case 1:
			return "Monday";
		case 2:
			return "Tuesday";
		case 3:
			return "Wednesday";
		case 4:
			return "Thursday";
		case 5:
			return "Friday";
		case 6:
			return "Saturday";
		}
		return "Error";
	}

	// Make the background
	public void drawBackground() {

		LayoutInflater li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		for (int i = 0; i < mHours.length; i += 2) {
			RelativeLayout rl = (RelativeLayout) li.inflate(
					R.layout.timetable_day_view_item, null);

			// Get three elements of background
			TextView hourItem = (TextView) rl
					.findViewById(R.id.day_view_hour_text_box);
			RelativeLayout topSection = (RelativeLayout) rl
					.findViewById(R.id.day_view_top_section);
			RelativeLayout bottomSection = (RelativeLayout) rl
					.findViewById(R.id.day_view_bottom_section);

			topSection.setOnClickListener(this);
			bottomSection.setOnClickListener(this);

			hourItem.setText(mHours[i]);
			hourItem.setId(i + 2);
			topSection.setId(hourItem.getId() * 100);
			bottomSection.setId(hourItem.getId() * 10000);

			View aboveView = findViewById(i);

			LayoutParams hourLP = (LayoutParams) hourItem.getLayoutParams();
			LayoutParams topLP = (LayoutParams) topSection.getLayoutParams();
			LayoutParams bottomLP = (LayoutParams) bottomSection
					.getLayoutParams();

			// Align views appropriately
			if (i != 0) {
				hourLP.addRule(RelativeLayout.BELOW, aboveView.getId());
				hourItem.setLayoutParams(hourLP);

				topLP.addRule(RelativeLayout.BELOW, aboveView.getId());
				topLP.addRule(RelativeLayout.RIGHT_OF, hourItem.getId());
				topSection.setLayoutParams(topLP);

				bottomLP.addRule(RelativeLayout.BELOW, topSection.getId());
				bottomLP.addRule(RelativeLayout.RIGHT_OF, hourItem.getId());
				bottomSection.setLayoutParams(bottomLP);

			} else {
				hourLP.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				hourItem.setLayoutParams(hourLP);

				topLP.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				topLP.addRule(RelativeLayout.RIGHT_OF, hourItem.getId());
				topSection.setLayoutParams(topLP);

				bottomLP.addRule(RelativeLayout.BELOW, topSection.getId());
				bottomLP.addRule(RelativeLayout.RIGHT_OF, hourItem.getId());
				bottomSection.setLayoutParams(bottomLP);
			}

			// Detach views from the old layout
			rl.removeAllViews();

			// Attach to the new one
			mScreen.addView(hourItem);
			mScreen.addView(topSection);
			mScreen.addView(bottomSection);
		}
	}

	// Get (for now add) the sessions from the controller
	private void getSessions() {

		mSessions = (ArrayList<Session>) mTimetable.getSessionsAtDate(mWeekday);
	}

	// Loads the appropriate view for each session
	public void drawSessions() {

		// Reset the title bar
		mTitle.setText(getDayOfWeek());

		// Loop over all sessions
		for (Session s : mSessions) {

			// Get the position, offset and duration of the session
			int position = s.getHour() * 3;
			int offset = s.getMinute();
			int duration = (int) (s.getDuration() * 2);

			// Make a new text view to hold the session information
			SessionView sessionView = new SessionView(this, s);
			sessionView.setOnClickListener(this);
			sessionView.setText(" Test");
			sessionView.setPadding(5, 1, 0, 0);
			sessionView.setBackgroundColor(Color.rgb(80, 39, 132));

			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

			// Set to the right of the text view
			View v = mScreen.getChildAt(position);
			lp.addRule(RelativeLayout.RIGHT_OF, v.getId());

			if (offset == 0) {

				// Get the view to brace the top to
				position += 1;
				View brace = mScreen.getChildAt(position);
				lp.addRule(RelativeLayout.ALIGN_TOP, brace.getId());

				// Find the id of the view to align the bottom with
				for (int i = 1; i < duration; i++) {
					if (i % 2 == 0) {
						position++;
					}
					position++;
				}
			} else {
				position += 2;
				View brace = mScreen.getChildAt(position);
				lp.addRule(RelativeLayout.ALIGN_TOP, brace.getId());

				for (int i = 1; i < duration; i++) {
					if (i % 2 != 0) {
						position++;
					}
					position++;
				}
			}

			// Ensure that layout being aligned to exists
			if (position < 72) {
				lp.addRule(RelativeLayout.ALIGN_BOTTOM,
						mScreen.getChildAt(position).getId());
			}

			mScreen.addView(sessionView, lp);
		}
	}

	// Removes the sessions drawn on the foreground
	public void clearSessions() {
		for (int i = 72; i < mScreen.getChildCount(); i++) {
			if (mScreen.getChildAt(i) instanceof SessionView) {
				mScreen.removeViewAt(i);
			}
		}
	}

	@Override
	public void onClick(View v) {
		if (v instanceof SessionView) {
			SessionView sv = (SessionView) v;
			sv.setText(sv.getSession().getDescription());
		}

		if (v.getId() == R.id.timetable_day_view_left_button) {
			if (--mWeekday < 0) {
				mWeekday = 6;
			}
			clearSessions();
			getSessions();
			drawSessions();
		}

		if (v.getId() == R.id.timetable_day_view_right_button) {
			if (++mWeekday > 6) {
				mWeekday = 0;
			}
			clearSessions();
			getSessions();
			drawSessions();
		}
		if (v.getId() > 100 && v.getId() % 100 == 0) {
			mController
					.getHandler()
					.obtainMessage(
							ConditionCodes.V_LAUNCH_COURSE_CREATION_FORM, this)
					.sendToTarget();
		}
	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case (ConditionCodes.C_TIMETABLE_RETRIEVED):
			mTimetable = (Timetable) msg.obj;
			getSessions();
			drawSessions();
			return true;

		case (ConditionCodes.C_SESSION_ADDED):
			try {
				clearSessions();
				getSessions();
				drawSessions();

			} catch (Exception e) {

			}
			return true;
		}
		return false;
	}

	// A private view that stores a reference to the session it represents
	private class SessionView extends TextView {

		private Session mSession;

		public SessionView(Context context, Session session) {
			super(context);
			mSession = session;
		}

		public Session getSession() {
			return mSession;
		}

		public void setSession(Session mSession) {
			this.mSession = mSession;
		}
	}
}