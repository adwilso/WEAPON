package asgard.weapon.timetable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import asgard.weapon.ConditionCodes;
import asgard.weapon.R;

public class TimetableDayView extends ListActivity implements OnClickListener,
		Handler.Callback {

	private Handler mHandler;
	private TimetableController mController;
	private Timetable mTimetable;
	private int mDayOfWeek;
	private SessionAdapter mSessionAdapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timetable_day_view);

		// Make handler to pass to the controller
		mHandler = new Handler(this);

		// Get a reference to controller and register this view to its handlers
		mController = TimetableController.getController();
		mController.addHandler(mHandler);

		// Retrieve the current day of the week
		mDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

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
			mTimetable = (Timetable) msg.obj;
			displayListItems();
			return true;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}

	// Displays all of the sessions in the day
	private void displayListItems()
	{
		ArrayList<Session> dates = (ArrayList<Session>) mTimetable.getSessionsAtDate(mDayOfWeek);
		
		mSessionAdapter = new SessionAdapter(this, R.layout.timetable_day_view_item, dates);
		this.setListAdapter(mSessionAdapter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		mController.removeHandler(mHandler);
	}

	private class SessionAdapter extends ArrayAdapter<Session> {
		ArrayList<Session> mItems;

		SessionAdapter(Context context, int textViewResourceId,
				ArrayList<Session> items) {
			super(context, textViewResourceId, items);
			mItems = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.timetable_day_view_item, null);
			}
			Session ses = mItems.get(position);
			if (ses != null) {
				TextView tt = (TextView) v.findViewById(R.id.top_item);
				TextView bt = (TextView) v.findViewById(R.id.bottom_item);
				if (tt != null) {
					tt.setText("Name: " + ses.getDescription());
				}

				if (bt != null) {
					bt.setText("Status: " + ses.getTime());
				}
			}
			return v;
		}
	}
}
