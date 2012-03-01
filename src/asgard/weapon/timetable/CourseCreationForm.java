package asgard.weapon.timetable;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import asgard.weapon.ConditionCodes;
import asgard.weapon.R;

public class CourseCreationForm extends Activity implements OnClickListener,
		Handler.Callback {

	private TimetableController mController;

	private Handler mHandler;

	private Spinner mDaySpinner;
	private Spinner mDurationSpinner;
	private TimePicker mTimePicker;
	private EditText mDescriptionText;
	private EditText mLocationText;
	private EditText mNameText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_creation_form);

		mHandler = new Handler(this);

		mController = TimetableController.getController(this);
		mController.addHandler(mHandler);

		mDaySpinner = (Spinner) findViewById(R.id.course_creation_form_day_spinner);
		mDurationSpinner = (Spinner) findViewById(R.id.course_creation_form_duration_spinner);
		mTimePicker = (TimePicker) findViewById(R.id.course_creation_form_time_picker);
		mDescriptionText = (EditText) findViewById(R.id.course_creation_form_description_box);
		mLocationText = (EditText) findViewById(R.id.course_creation_form_location_box);
		mNameText = (EditText) findViewById(R.id.course_creation_form_name_box);

		ArrayAdapter<CharSequence> dateAdapter = ArrayAdapter
				.createFromResource(this, R.array.course_dates,
						android.R.layout.simple_spinner_item);
		dateAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mDaySpinner.setAdapter(dateAdapter);

		ArrayAdapter<CharSequence> durationAdapter = ArrayAdapter
				.createFromResource(this, R.array.course_durations,
						android.R.layout.simple_spinner_item);
		durationAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mDurationSpinner.setAdapter(durationAdapter);

	}

	@Override
	public void onClick(View v) {
		Handler handler = mController.getHandler();
		Message message = handler.obtainMessage(ConditionCodes.V_DO_NOTHING,
				this);

		switch (v.getId()) {
		case R.id.course_creation_form_add_button:
			message.what = ConditionCodes.V_ADD_SESSION;

			Session s = new Session(mTimePicker.getCurrentHour().intValue(),
					mTimePicker.getCurrentMinute().intValue(),
					Float.parseFloat(mDurationSpinner.getSelectedItem()
							.toString()),
					mDaySpinner.getSelectedItemPosition() + 1, mDescriptionText
							.getText().toString(), mLocationText.getText()
							.toString());
			message.obj = s;
			handler.dispatchMessage(message);

		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		mController.removeHandler(mHandler);
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		return false;
	}

}
