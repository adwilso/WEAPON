package asgard.weapon.timetable;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class TimetableWeekView extends Activity implements Handler.Callback {

	private Handler mHandler;

	private TimetableController mController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Make handler to pass to the controller
		mHandler = new Handler(this);

		// Get a reference to controller and register this view to its handlers
		mController = TimetableController.getController(this);
		mController.addHandler(mHandler);
	}

	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		mController.removeHandler(mHandler);
	}

}
