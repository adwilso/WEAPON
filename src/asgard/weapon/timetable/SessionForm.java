package asgard.weapon.timetable;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import asgard.weapon.ConditionCodes;
import asgard.weapon.R;

public class SessionForm extends Activity implements Handler.Callback,
		OnClickListener {

	private Handler mHandler;
	private TimetableController mController;

	private Session mSession;

	private TextView mCourse;
	private TextView mDay;
	private TextView mTime;
	private TextView mDuration;
	private TextView mLocation;
	private TextView mDescription;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.session_view);

		// Make handler to pass to the controller
		mHandler = new Handler(this);

		// Get a reference to controller and register this view to its handlers
		mController = TimetableController.getController(this);
		mController.addHandler(mHandler);

		// Load resources and inflate the layout
		initialize();
	}

	// Get references to XML and retrieve the clicked session
	private void initialize() {

		mController.getHandler().obtainMessage(ConditionCodes.V_GET_SESSION)
				.sendToTarget();

		mCourse = (TextView) findViewById(R.id.session_course_text);
		mDay = (TextView) findViewById(R.id.session_day_text);
		mTime = (TextView) findViewById(R.id.session_time_text);
		mDuration = (TextView) findViewById(R.id.session_duration_text);
		mLocation = (TextView) findViewById(R.id.session_location_text);
		mDescription = (TextView) findViewById(R.id.session_description_text);

	}

	private void setText() {

		mCourse.setText(mSession.getCourse());
		mDay.setText(getDay());
		mTime.setText(getTime());
		mDuration.setText(Float.toString(mSession.getDuration()));
		mLocation.setText(mSession.getLocation());
		mDescription.setText(mSession.getDescription());
	}

	private String getTime() {
		String time;
		Integer hour = mSession.getHour();
		Integer minute = mSession.getMinute();
		time = hour.toString() + ":";
		time += minute.toString();
		if (minute == 0)
			time += "0";
		return time;
	}

	private String getDay() {
		int date = mSession.getDate();
		switch (date) {
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
		return "Fail";
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		mController.removeHandler(mHandler);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.session_view_delete_button:
			Message msg = mController.getHandler().obtainMessage(ConditionCodes.V_DELETE_SESSION);
			msg.obj = mSession;
			msg.sendToTarget();
			finish();
			break;
		case R.id.session_view_edit_button:
			Message message = mController.getHandler().obtainMessage(ConditionCodes.V_EDIT_SESSION);
			message.obj = mSession;
			message.sendToTarget();
			break;
		}
	}
	

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case ConditionCodes.C_SESSION_RECIEVED:
			mSession = (Session) msg.obj;
			setText();
			return true;
			
		case ConditionCodes.C_SESSION_UPDATED:
		
			Message message = mController.getHandler().obtainMessage();
			message.what = ConditionCodes.V_LAUNCH_COURSE_CREATION_FORM;
			message.obj = this;
			message.sendToTarget();
			
			finish();
		
			return true;
		}
		return false;
	}
}
