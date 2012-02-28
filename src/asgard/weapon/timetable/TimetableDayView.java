package asgard.weapon.timetable;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import asgard.weapon.ConditionCodes;
import asgard.weapon.R;

public class TimetableDayView extends ListActivity implements OnClickListener,
		Handler.Callback {

	private Handler mHandler;
	private TimetableController mController;
	private Timetable mTimetable;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timetable_main_view);

		// Make handler to pass to the controller
		mHandler = new Handler(this);

		// Get a reference to controller and register this view to its handlers
		mController = TimetableController.getController();
		mController.addHandler(mHandler);
		
		// Request the current Timetable from the controller
		Message message = mHandler.obtainMessage();
		message.what = ConditionCodes.V_GET_TIMETABLE;
		mController.getHandler().sendMessage(message);
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case ConditionCodes.C_TIMETABLE_RETRIEVED:
			mTimetable = (Timetable)msg.obj;
			displayListItems();
			return true;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}
	
	private void displayListItems()
	{
		
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		
		mController.removeHandler(mHandler);
	}
}
