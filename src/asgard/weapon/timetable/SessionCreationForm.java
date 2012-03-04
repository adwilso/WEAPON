package asgard.weapon.timetable;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import asgard.weapon.ConditionCodes;
import asgard.weapon.R;

public class SessionCreationForm extends Activity implements OnClickListener,
		OnItemSelectedListener, Handler.Callback {

	private static String NEW_COURSE = "New";

	// Dealing with the options list
	private ArrayList<View> mItems;
	private EventAdapter mAdapter;
	private ListView mOptionsList;

	// Time Picker and its listener
	private TimePicker mTimePicker;
	private TimePicker.OnTimeChangedListener mTimeChangeListner;

	// Dialog for creating new courses
	private EditText mCourseName;
	private AlertDialog mCreateCourseDialog;

	// List of courses
	private ArrayList<Course> mCourses;
	
	// Dealing with the spinners
	private Spinner mCourseSpinner;
	private ArrayAdapter<Course> mCourseAdapter;
	
	private Spinner mWeekSpinner;
	private ArrayAdapter<String> mWeekAdapter;


	// handler and controller reference
	private Handler mHandler;
	private TimetableController mController;

	@Override
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.session_creation_form);

		mHandler = new Handler(this);

		mController = TimetableController.getController(this);
		mController.addHandler(mHandler);

		// Request courses from the controller
		Message message = mController.getHandler().obtainMessage(
				ConditionCodes.V_GET_COURSES);
		message.sendToTarget();

	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case ConditionCodes.C_COURSES_RETRIEVED:
			inflateLayout(msg.obj);
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private void inflateLayout(Object courses) {

		try {
			mCourses = (ArrayList<Course>) courses;
			if (mCourses == null) {
				mCourses = new ArrayList<Course>();
			}
			mCourses.add(new Course(NEW_COURSE));

			// Set the onTimeChangeListner to accept only 30 minute intervals
			mTimeChangeListner = new TimePicker.OnTimeChangedListener() {
				@Override
				public void onTimeChanged(TimePicker view, int hourOfDay,
						int minute) {
					updateDisplay(view, hourOfDay, minute);
				}
			};

			setViewItems();

			// Make a new adapter and inflate into mOptionsList
			mAdapter = new EventAdapter(this, mItems);
			mOptionsList = (ListView) findViewById(R.id.session_creation_options);
			mOptionsList.setAdapter(mAdapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	private void setViewItems() {
		mItems = new ArrayList<View>();

		mItems.add(findViewById(R.id.session_creation_time_item));
		mItems.add(findViewById(R.id.session_creation_day_item));
		mItems.add(findViewById(R.id.session_creation_duration_item));
		mItems.add(findViewById(R.id.session_creation_location_item));
		mItems.add(findViewById(R.id.session_creation_description_item));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// Make a new course if this button is clicked
		case R.id.session_course_dialog_create_button:
			// Retrieve the name
			String name = mCourseName.getText().toString();

			mCourses.add(mCourses.size() - 1, new Course(name));
			
			// Refresh the list
			mOptionsList.setAdapter(mAdapter);
			
			mCreateCourseDialog.dismiss();
	
			break;
			
		case R.id.session_course_dialog_cancel_button:
			mCreateCourseDialog.dismiss();
			break;
		case R.id.session_creation_add_button:
			
			break;

		case R.id.session_creation_cancel_button:
			break;
		}
	}

	// Detects if the user has clicked the New option
	@Override
	public void onItemSelected(AdapterView<?> adapterView, View clickedView,
			int pos, long id) {
		// New is always the last item in the list so check if the last item was
		// pressed
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

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * */

	// A private adapter to return appropriate item in List View
	private class EventAdapter extends BaseAdapter implements ListAdapter {

		// These items dictate the order in which items appear
		private static final int COURSE = 0;
		private static final int DAY_OF_WEEK = 1;
		private static final int TIME = 2;
		private static final int DURATION = 3;
		private static final int LOCATION = 4;
		private static final int DESCRIPTION = 5;

		// The total number of items
		private static final int NUM_ITEM = 6;

		private SessionCreationForm mParent;
		private LayoutInflater mInflator;
		private ArrayList<View> mItems;

		public EventAdapter(SessionCreationForm parent, ArrayList<View> items) {
			mParent = parent;
			mItems = items;
			mInflator = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getItemViewType(int position) {
			return position % NUM_ITEM;
		}

		@Override
		public int getViewTypeCount() {
			return NUM_ITEM;
		}

		@Override
		public int getCount() {
			return mItems.size();
		}

		@Override
		public Object getItem(int position) {
			return mItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View view = new View(mParent);

			// Get what type of view it should populate based on position
			int type = getItemViewType(position);

			// if the layout hasn't been displayed, initialize it
			if (convertView == null) {
				LinearLayout ll = new LinearLayout(mParent);

				// Load appropriate layout and set on click listener
				switch (type) {
				case COURSE:
					ll = (LinearLayout) mInflator.inflate(
							R.layout.session_creation_course_item, null);
					if (ll.getChildAt(1) instanceof Spinner) {		
						mCourseSpinner = (Spinner) ll.getChildAt(1);
						mCourseAdapter = new ArrayAdapter<Course>(mParent,
								android.R.layout.simple_spinner_item, mCourses);
						mCourseAdapter
								.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						mCourseSpinner.setAdapter(mCourseAdapter);
						mCourseSpinner.setOnItemSelectedListener(mParent);
					}
					view = (View) ll;
					break;

				case DAY_OF_WEEK:
					ll = (LinearLayout) mInflator.inflate(
							R.layout.session_creation_day_item, null);
					if (ll.getChildAt(1) instanceof Spinner) {
						mWeekSpinner = (Spinner) ll.getChildAt(1);
						String[] weeks = getResources().getStringArray(
								R.array.course_dates);
						mWeekAdapter = new ArrayAdapter<String>(mParent,
								android.R.layout.simple_spinner_item, weeks);
						mWeekAdapter
								.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						mWeekSpinner.setAdapter(mWeekAdapter);
						mWeekSpinner.setOnItemSelectedListener(mParent);
					}
					view = (View) ll;
					break;

				case TIME:
					ll = (LinearLayout) mInflator.inflate(
							R.layout.session_creation_time_item, null);
					if (ll.getChildAt(1) instanceof TimePicker) {
						mTimePicker = (TimePicker) ll.getChildAt(1);
						mTimePicker
								.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
						mTimePicker
								.setOnTimeChangedListener(mTimeChangeListner);
						updateDisplay(mTimePicker, 0, 0);
					}
					view = (View) ll;
					break;

				case DURATION:
					ll = (LinearLayout) mInflator.inflate(
							R.layout.session_creation_duration_item, null);
					view = (View) ll;
					break;
				case LOCATION:
					ll = (LinearLayout) mInflator.inflate(
							R.layout.session_creation_location_item, null);
					view = (View) ll;
					break;
				case DESCRIPTION:
					ll = (LinearLayout) mInflator.inflate(
							R.layout.session_creation_description_item, null);
					view = (View) ll;
					break;
				}
				ll.setOnClickListener(mParent);
			} else {
				view = (View) convertView;
			}
			return view;
		}
	}

}