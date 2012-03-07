package asgard.weapon.timetable;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import asgard.weapon.R;

public class TimetableSessionView extends ListActivity implements Handler.Callback {

	private Handler mHandler;
	private TimetableController mController;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timetable_day_view);
		
		// Make handler to pass to the controller
		mHandler = new Handler(this);
		
		// Get a reference to controller and register this view to its handlers
		mController = TimetableController.getController(this);
		mController.addHandler(mHandler);
	}

	private void getCourse() {
		
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
