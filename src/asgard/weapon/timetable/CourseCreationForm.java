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
import asgard.weapon.ConditionCodes;
import asgard.weapon.R;

public class CourseCreationForm extends Activity implements OnClickListener,
		Handler.Callback {

	private TimetableController mController;

	private Handler mHandler;

	private Spinner mDateSpinner;
	private Spinner mTimeSpinner;
	private EditText mDescriptionText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_creation_form);

		mHandler = new Handler(this);

		mController = TimetableController.getController();
		mController.addHandler(mHandler);

		mDateSpinner = (Spinner) findViewById(R.id.course_creation_form_date_spinner);
		mTimeSpinner = (Spinner) findViewById(R.id.course_creation_form_time_spinner);
		mDescriptionText = (EditText) findViewById(R.id.course_creation_form_description_box);

		ArrayAdapter<CharSequence> dateAdapter = ArrayAdapter
				.createFromResource(this, R.array.course_dates,
						android.R.layout.simple_spinner_item);
		dateAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mDateSpinner.setAdapter(dateAdapter);

		ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter
				.createFromResource(this, R.array.course_times,
						android.R.layout.simple_spinner_item);
		timeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mTimeSpinner.setAdapter(timeAdapter);
	}

	@Override
	public void onClick(View v) {
		Handler handler = mController.getHandler();
		Message message = handler.obtainMessage(ConditionCodes.V_DO_NOTHING,
				this);

		switch (v.getId()) {
		case R.id.course_creation_form_add_button:
			message.what = ConditionCodes.V_ADD_SESSION;
			Session s = new Session(mTimeSpinner.getSelectedItem().toString(),
					Integer.parseInt(mDateSpinner.getSelectedItem().toString()), 
					mDescriptionText.getText().toString());
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
