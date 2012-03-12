package asgard.weapon.timetable;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import asgard.weapon.ConditionCodes;
import asgard.weapon.R;

public class TimetableMainView extends TabActivity implements Handler.Callback {

	private final String LOG = "MAIN_VIEW";

	private Handler mHandler;

	private TimetableController mController;

	/*
	 * Called when the Timetable Activity is created
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timetable_main_view);

		// Make handler to pass to the controller
		mHandler = new Handler(this);

		// Get a reference to controller and register this view to its handlers
		mController = TimetableController.getController(this);
		mController.addHandler(mHandler);

		initializeTabs();
	}

	/*
	 * Updates the UI based on messages received
	 */
	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {

		case ConditionCodes.C_TIMETABLE_LOADED:
			Log.d(LOG, "Loaded");
			return true;

		case ConditionCodes.C_TIMETABLE_SAVING:
			Log.d(LOG, "Saving");
			return true;

		case ConditionCodes.C_TIMETABLE_SAVED:
			Log.d(LOG, "Saved");
			return true;

		case ConditionCodes.C_TIMETABLE_CLOSED:
			Log.d(LOG, "Closed");
			return true;

		case ConditionCodes.C_TIMETABLE_DELETED:
			Log.d(LOG, "Deleted");
			return true;

		case ConditionCodes.C_TIMETABLE_CREATED:
			Log.d(LOG, "Created");
			return true;
		}

		Log.d(LOG, "Unknown message");
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
		
		case R.id.timetable_menu_new:
			message.what = ConditionCodes.V_LAUNCH_TIMETABLE_CREATION_FORM;
			handler.sendMessage(message);
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

	private void initializeTabs() {

		// Create the tabs
		Resources res = getResources();
		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;

		// Create an Intent to launch an Activity for the tab
		intent = new Intent().setClass(this, TimetableDayView.class);
		
		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost
				.newTabSpec("day")
				.setIndicator("Day",
						res.getDrawable(android.R.drawable.ic_menu_day))
				.setContent(intent);
		tabHost.addTab(spec);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		mController.removeHandler(mHandler);
	}
}