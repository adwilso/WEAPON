package asgard.weapon.timetable;

import java.util.ArrayList;
import java.util.Calendar;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
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

	private TextView mDayTitle;
	private ImageView mLeftImage;
	private ImageView mRightImage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timetable_day_view);

		// Make handler to pass to the controller
		mHandler = new Handler(this);

		// Get a reference to controller and register this view to its handlers
		mController = TimetableController.getController(this);
		mController.addHandler(mHandler);

		// Retrieve the current day of the week
		mDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

		// Initialize UI elements
		mDayTitle = (TextView) findViewById(R.id.timetable_day_view_day_text);
		setDayOfWeek();
		
		mLeftImage = (ImageView) findViewById(R.id.timetable_day_view_left_button);
		mRightImage = (ImageView) findViewById(R.id.timetable_day_view_right_button);
		mLeftImage.setOnClickListener(this);
		mRightImage.setOnClickListener(this);
	
		// Retrieve the current Timetable from the controller
		Message message = mHandler.obtainMessage();
		message.what = ConditionCodes.V_GET_TIMETABLE;
		mController.getHandler().sendMessage(message);
	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case ConditionCodes.C_TIMETABLE_RETRIEVED:
			mTimetable = (Timetable) msg.obj;
			refresh();
			return true;
		}
		return false;
	}

	private void setDayOfWeek() {
		String day;
		switch (mDayOfWeek) {
		case 1:
			day = "Sunday";
			break;
		case 2:
			day = "Monday";
			break;
		case 3:
			day = "Tuesday";
			break;
		case 4:
			day = "Wednesday";
			break;
		case 5:
			day = "Thursday";
			break;
		case 6:
			day = "Friday";
			break;
		case 7:
			day = "Saturday";
			break;			
		default:
			day = "ERROR";
			break;

		}
		mDayTitle.setText(day);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.timetable_day_view_left_button:
			if (--mDayOfWeek == 0)
				mDayOfWeek = 7;
			break;
		case R.id.timetable_day_view_right_button:
			if (++mDayOfWeek == 8)
				mDayOfWeek = 1;
			break;
		}
		refresh();
	}

	// Get all the sessions and set the date
	private void refresh() {
		ArrayList<Session> dates = new ArrayList<Session>();// (ArrayList<Session>)
		setDayOfWeek();										// mTimetable.getSessionsAtDate(mDayOfWeek);

		Session sess1 = new Session(8, 30, 2, 5, "Worst lab", "TEB 244");
		Session sess2 = new Session(11, 30, 1, 5, "Best class", "SEB 1054");
		dates.add(sess1);
		dates.add(sess2);

		mSessionAdapter = new SessionAdapter(this,
				R.layout.timetable_day_view_item, dates);
		this.setListAdapter(mSessionAdapter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		mController.removeHandler(mHandler);
	}

	private class SessionAdapter extends ArrayAdapter<Session> implements
			ListAdapter {
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
