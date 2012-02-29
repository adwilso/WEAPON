package asgard.weapon.timetable;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import asgard.weapon.ConditionCodes;
import asgard.weapon.R;

public class TimetableMainView extends Activity implements OnClickListener,
		Handler.Callback {

	private TextView mStatusTextView;
	public EditText mEditTextView;

	private Handler mHandler;

	private TimetableController mController;

	/*
	 * Called when the Timetable Activity is created
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timetable_main_view);

		// Do initial UI preparation
		mStatusTextView = (TextView) findViewById(R.id.status_text_view);

		// Make handler to pass to the controller
		mHandler = new Handler(this);

		// Get a reference to controller and register this view to its handlers
		mController = TimetableController.getController();
		mController.addHandler(mHandler);
	}

	/*
	 * Defines what to do when certain widgets are pressed
	 */
	@Override
	public void onClick(View v) {
		// Retrieve handler and message with default values
		Handler handler = mController.getHandler();
		Message message = handler.obtainMessage(ConditionCodes.V_DO_NOTHING,
				this);

		// Set the message depending on what UI element was interacted with
		switch (v.getId()) {

		case R.id.load_button:
			message.what = ConditionCodes.V_LOAD_TIMETABLE;
			handler.sendMessage(message);
			break;

		case R.id.save_button:
			message.what = ConditionCodes.V_SAVE_TIMETABLE;
			handler.sendMessage(message);
			break;

		case R.id.new_timetable:
			message.what = ConditionCodes.V_LAUNCH_TIMETABLE_CREATION_FORM;
			handler.sendMessage(message);
			break;
			
		case R.id.main_delete_button:
			message.what = ConditionCodes.V_DELETE_TIMETABLE;
			handler.sendMessage(message);
			break;
			
		case R.id.main_select_timetable_button:
			message.what = ConditionCodes.V_SELECT_TIMETABLE;
			handler.sendMessage(message);
			break;
			
			// Finally, send the message to the controller
		default:
			handler.sendMessage(message);
		}	
	}

	/*
	 * Updates the UI based on messages received
	 */
	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case ConditionCodes.C_GET_ACTIVITY:
			msg.what = ConditionCodes.V_LOAD_TIMETABLE;
			msg.obj = this;
			mController.getHandler().sendMessage(msg);
			return true;
		
		case ConditionCodes.C_TIMETABLE_LOADING:
			mStatusTextView.setText("Loading timetable");
			return true;
			
		case ConditionCodes.C_TIMETABLE_LOADED:
			mStatusTextView.setText(msg.obj.toString());
			return true;
			
		case ConditionCodes.C_TIMETABLE_SAVING:
			mStatusTextView.setText("Saving timetable");
			
		case ConditionCodes.C_TIMETABLE_SAVED:
			mStatusTextView.setText("Timetable saved");
			return true;
		
		case ConditionCodes.C_TIMETABLE_CLOSED:
			mStatusTextView.setText("Creation form closed");
			return true;
		
		case ConditionCodes.C_TEST_NULL:
			mStatusTextView.setText((String)msg.obj);
			return true;
		
		case ConditionCodes.C_TIMETABLE_DELETED:
			mStatusTextView.setText("Timetable deleted");
			return true;
			
		case ConditionCodes.C_TIMETABLE_CREATED:
			mStatusTextView.setText("Timetable created");
			return true;
		}
		
		mStatusTextView.setText("Unknown message received");
		return false;
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		
		mController.removeHandler(mHandler);
	}
}