package asgard.weapon.timetable;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import asgard.weapon.ConditionCodes;
import asgard.weapon.R;

public class SessionCreationForm extends Activity implements OnClickListener,
		OnItemSelectedListener, Handler.Callback {

	private static String NEW_COURSE = "New";
	private Session mCreatedSession;

	// Time Picker and its listener
	private TimePicker mTimePicker;
	private TimePicker.OnTimeChangedListener mTimeChangeListner;

	// List of courses
	private ArrayList<String> mCourses;
	private List<Course> mRetrievedCourses;

	// Dialog for creating new courses
	private EditText mCourseName;
	private AlertDialog mCreateCourseDialog;

	// TextBoxes for description and location
	private EditText mDescription;
	private EditText mLocation;

	// Dealing with the spinners
	private Spinner mCourseSpinner;
	private ArrayAdapter<String> mCourseAdapter;

	private String[] mWeek;
	private Spinner mWeekSpinner;
	private ArrayAdapter<String> mWeekAdapter;

	private String[] mDurations;
	private Spinner mDurationSpinner;
	private ArrayAdapter<String> mDurationAdapter;

	// handler and controller reference
	private Handler mHandler;
	private TimetableController mController;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.session_creation_in_one_layout);

		mCreatedSession = new Session();

		mHandler = new Handler(this);

		mController = TimetableController.getController(this);
		mController.addHandler(mHandler);

		// Request courses from the controller
		mController.getHandler().obtainMessage(ConditionCodes.V_GET_COURSES)
				.sendToTarget();

	}

	@SuppressWarnings("unchecked")
	private void inflateLayout(Message msg) {

		mCourses = new ArrayList<String>();
		int dayOfWeek = 0;
		int startHour = 0;
		int startMinute = 0;

		if (msg.obj != null) {
			try {
				mRetrievedCourses = (List<Course>) msg.obj;
				startHour = msg.arg1 / 2;
				startMinute = msg.arg1 % 2 * 30;
				dayOfWeek = msg.arg2;
			} catch (Exception e) {

			}

			for (int i = 0; i < mRetrievedCourses.size(); i++) {
				mCourses.add(mRetrievedCourses.get(i).getCourseCode());
			}
		} else {
			mCourses.add("Default");
		}

		// fill the array with the courses
		mCourses.add(NEW_COURSE);

		mCourseAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mCourses);
		mCourseAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// fill the spinner with the adapter
		mCourseSpinner = (Spinner) this
				.findViewById(R.id.session_course_course);
		mCourseSpinner.setAdapter(mCourseAdapter);
		mCourseSpinner.setOnItemSelectedListener(this);

		// fill the week with days
		mWeek = getResources().getStringArray(R.array.course_dates);
		mWeekAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mWeek);
		mWeekAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		mWeekSpinner = (Spinner) this.findViewById(R.id.session_day_spinner);
		mWeekSpinner.setAdapter(mWeekAdapter);
		mWeekSpinner.setSelection(dayOfWeek);

		// Create the time picker listener with overridden method
		mTimeChangeListner = new TimePicker.OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				updateDisplay(view, hourOfDay, minute);
			}
		};

		// instantiate time picker
		mTimePicker = (TimePicker) this
				.findViewById(R.id.session_time_time_picker);

		mTimePicker
				.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
		mTimePicker.setOnTimeChangedListener(mTimeChangeListner);
		updateDisplay(mTimePicker, startHour, startMinute);

		// fill the duration with timeslots
		mDurations = getResources().getStringArray(R.array.course_durations);
		mDurationAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mDurations);
		mDurationAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		mDurationSpinner = (Spinner) this
				.findViewById(R.id.session_duration_spinner);
		mDurationSpinner.setAdapter(mDurationAdapter);

		mDescription = (EditText) this
				.findViewById(R.id.session_description_description);
		mLocation = (EditText) this
				.findViewById(R.id.session_location_location);

	}

	private void updateDisplay(TimePicker timePicker, int hourOfDay, int minute) {
		Integer min;
		Integer hr;
		if (minute > 30 && minute < 59) {
			min = 0;
			hr = (hourOfDay + 1) % 24;
		} else if (minute < 30 && minute > 1) {
			min = 0;
			hr = hourOfDay;
		} else if (minute >= 59) {
			min = 30;
			if (hourOfDay == 0)
				hr = 23;
			else {
				hr = (hourOfDay - 1) % 24;
			}
		} else if (minute == 1) {
			min = 30;
			hr = hourOfDay;
		} else {
			min = minute;
			hr = hourOfDay;
		}

		timePicker
				.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
					@Override
					public void onTimeChanged(TimePicker view, int hourOfDay,
							int minute) {

					}
				});
		timePicker.setCurrentHour(hr);
		timePicker.setCurrentMinute(min);
		timePicker.setOnTimeChangedListener(mTimeChangeListner);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// Make a new course if this button is clicked
		case R.id.session_course_dialog_create_button:
			// Retrieve the name
			String name = mCourseName.getText().toString();

			mCourses.add(mCourses.size() - 1, name);
			mRetrievedCourses.add(new Course(name));

			// Refresh the list
			mCourseAdapter.notifyDataSetChanged();

			mCreateCourseDialog.dismiss();
			break;

		case R.id.session_course_dialog_cancel_button:
			mCreateCourseDialog.dismiss();
			break;

		case R.id.session_creation_add_button:

			int position = 0;
			try {
				mCreatedSession.setCourse(mCourseSpinner.getSelectedItem()
						.toString());
				position = mCourseSpinner.getSelectedItemPosition();
				mCreatedSession.setDay(mWeekSpinner.getSelectedItemPosition());
				mCreatedSession.setDescription(mDescription.getText()
						.toString());
				mCreatedSession.setLocation(mLocation.getText().toString());
				mCreatedSession.setTime(mTimePicker.getCurrentHour(),
						mTimePicker.getCurrentMinute(), Float
								.parseFloat(mDurationSpinner.getSelectedItem()
										.toString()));
			} catch (Exception e) {

			}

			mRetrievedCourses.get(position).addSession(mCreatedSession);
			mController
					.getHandler()
					.obtainMessage(ConditionCodes.V_ADD_COURSES,
							mRetrievedCourses).sendToTarget();

			break;

		case R.id.session_creation_cancel_button:
			finish();
			break;
		}
	}

	// Detects if the user has clicked the New option
	@Override
	public void onItemSelected(AdapterView<?> adapterView, View clickedView,
			int pos, long id) {
		// New is always the last item in the list so check if the last item was
		// pressed
		int temp = adapterView.getSelectedItemPosition();

		int current = mCourses.size() - 1;
		if (pos == current) {
			// Make a new dialog to get course information
			Builder builder = new Builder(this);

			LayoutInflater li = getLayoutInflater();
			LinearLayout creationView = (LinearLayout) li.inflate(
					R.layout.session_creation_course_dialog, null);

			if (creationView.getChildAt(1) instanceof EditText) {
				mCourseName = (EditText) creationView.getChildAt(1);
			}
			creationView.setOnClickListener(this);
			builder.setView((View) creationView);
			mCreateCourseDialog = builder.create();
			mCreateCourseDialog.show();
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		return;
	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case ConditionCodes.C_COURSES_RETRIEVED:
			inflateLayout(msg);
			return true;

		case ConditionCodes.C_SESSION_ADDED:
			mController.getHandler()
					.obtainMessage(ConditionCodes.V_SAVE_TIMETABLE, this)
					.sendToTarget();
			finish();
			return true;

		case ConditionCodes.C_SESSION_NOT_ADDED:
			Toast.makeText(this, "New Session not created", 1000).show();
			return true;
		}
		return false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mController.removeHandler(mHandler);
	}

}