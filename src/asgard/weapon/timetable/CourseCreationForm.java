package asgard.weapon.timetable;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import asgard.weapon.ConditionCodes;
import asgard.weapon.R;

public class CourseCreationForm extends Activity implements OnClickListener,
		Handler.Callback {

	private TimetableController mController;

	private Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_creation_form);

		mHandler = new Handler(this);

		mController = TimetableController.getController();
		mController.addHandler(mHandler);
	}

	@Override
	public void onClick(View v) {
		Handler handler = mController.getHandler();
		Message message = handler.obtainMessage(ConditionCodes.V_DO_NOTHING,
				this);

		switch (v.getId()) {
		case R.id.timetable_creation_form_new_course_button:
			message.what = ConditionCodes.V_LAUNCH_COURSE_CREATION_FORM;

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
