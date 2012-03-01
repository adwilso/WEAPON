package asgard.weapon.timetable;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import asgard.weapon.ConditionCodes;
import asgard.weapon.R;

public class TimetableMainView extends Activity implements Handler.Callback {

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
		mController = TimetableController.getController(this);
		mController.addHandler(mHandler);
	}

	/*
	 * Updates the UI based on messages received
	 */
	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {

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
			mStatusTextView.setText((String) msg.obj);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.timetable_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Retrieve handler and message with default values
		Handler handler = mController.getHandler();
		Message message = handler.obtainMessage(ConditionCodes.V_DO_NOTHING,
				this);

		switch (item.getItemId()) {
		case R.id.timetable_menu_load:
			message.what = ConditionCodes.V_LOAD_TIMETABLE;
			handler.sendMessage(message);
			return true;

		case R.id.timetable_menu_new:
			message.what = ConditionCodes.V_LAUNCH_TIMETABLE_CREATION_FORM;
			handler.sendMessage(message);
			return true;

		case R.id.timetable_menu_save:
			message.what = ConditionCodes.V_SAVE_TIMETABLE;
			handler.sendMessage(message);
			return true;

		case R.id.timetable_menu_delete:
			message.what = ConditionCodes.V_DELETE_TIMETABLE;
			handler.sendMessage(message);
			return true;

		case R.id.timetable_menu_edit:
			return true;

		case R.id.timetable_menu_select:
			message.what = ConditionCodes.V_SELECT_TIMETABLE;
			handler.sendMessage(message);
			return true;

		default:
			handler.sendMessage(message);
			return super.onOptionsItemSelected(item);

		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		mController.removeHandler(mHandler);
	}
}