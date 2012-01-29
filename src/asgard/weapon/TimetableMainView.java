package asgard.weapon;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import asgard.weapon.timetable.TimetableMainController;

public class TimetableMainView extends Activity implements OnClickListener,
		Handler.Callback {

	private Button mSaveButton;
	private Button mLoadButton;
	private TextView mStatusTextView;
	public TextView mTimetableName;

	private Handler mHandler;

	private TimetableMainController mTimetableController;

	/*
	 * Called when the Timetable Activity is created
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Do initial UI preparation
		mSaveButton = (Button) findViewById(R.id.save_button);
		mLoadButton = (Button) findViewById(R.id.load_button);
		mStatusTextView = (TextView) findViewById(R.id.status_text_view);
		mTimetableName = (TextView) findViewById(R.id.timetable_name);

		mSaveButton.setOnClickListener(this);
		mLoadButton.setOnClickListener(this);

		// Make handler and pass it to a new controller
		mHandler = new Handler(this);

		mTimetableController = new TimetableMainController(mHandler);
	}

	/*
	 * Defines what to do when certain widgets are pressed
	 */
	@Override
	public void onClick(View v) {
		Message message;

		switch (v.getId()) {
		
		case R.id.load_button:
			message = mTimetableController.getHandler().obtainMessage(
					ConditionCodes.V_LOAD_TIMETABLE, this);
			mTimetableController.getHandler().sendMessage(message);
			break;
			
		case R.id.save_button:
			message = mTimetableController.getHandler().obtainMessage(
					ConditionCodes.V_SAVE_TIMETABLE, this);
			mTimetableController.getHandler().sendMessage(message);
			break;
		}

	}

	/*
	 * Updates the UI based on messages received
	 */
	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case ConditionCodes.C_TIMETABLE_LOADED:
			mStatusTextView.setText("Timetable loaded!");
			return true;
		case ConditionCodes.C_TIMETABLE_SAVED:
			mStatusTextView.setText("Timetable saved!");
			return true;
		}
		mStatusTextView.setText("Unknown message received");
		return false;
	}
}