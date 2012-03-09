package asgard.weapon.timetable;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;
import asgard.weapon.R;

public class TimetableWeekView extends Activity implements Handler.Callback {

	private Handler mHandler;

	private TimetableController mController;

	private String[] mHours;

	private RelativeLayout mScreen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timetable_week_view);

		// Make handler to pass to the controller
		mHandler = new Handler(this);

		// Get a reference to controller and register this view to its handlers
		mController = TimetableController.getController(this);
		mController.addHandler(mHandler);

		initialize();
	}

	private void initialize() {
		// Inflate all UI elements and resources
		mScreen = (RelativeLayout) findViewById(R.id.week_view_container);
		mHours = getResources().getStringArray(R.array.course_times);
		
		drawBackground();
	}
	
	// Make the background
		public void drawBackground() {

			LayoutInflater li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			for (int i = 0; i < mHours.length; i += 2) {
				RelativeLayout rl = (RelativeLayout) li.inflate(
						R.layout.timetable_day_view_item, null);

				// Get three elements of background
				TextView hourItem = (TextView) rl
						.findViewById(R.id.day_view_hour_text_box);
				RelativeLayout topSection = (RelativeLayout) rl
						.findViewById(R.id.day_view_top_section);
				RelativeLayout bottomSection = (RelativeLayout) rl
						.findViewById(R.id.day_view_bottom_section);

				hourItem.setText(mHours[i]);
				hourItem.setId(i + 2);
				topSection.setId(hourItem.getId() * 100);
				bottomSection.setId(hourItem.getId() * 10000);

				View aboveView = findViewById(i);

				LayoutParams hourLP = (LayoutParams) hourItem.getLayoutParams();
				LayoutParams topLP = (LayoutParams) topSection.getLayoutParams();
				LayoutParams bottomLP = (LayoutParams) bottomSection
						.getLayoutParams();

				// Align views appropriately
				if (i != 0) {
					hourLP.addRule(RelativeLayout.BELOW, aboveView.getId());
					hourItem.setLayoutParams(hourLP);

					topLP.addRule(RelativeLayout.BELOW, aboveView.getId());
					topLP.addRule(RelativeLayout.RIGHT_OF, hourItem.getId());
					topSection.setLayoutParams(topLP);

					bottomLP.addRule(RelativeLayout.BELOW, topSection.getId());
					bottomLP.addRule(RelativeLayout.RIGHT_OF, hourItem.getId());
					bottomSection.setLayoutParams(bottomLP);

				} else {
					hourLP.addRule(RelativeLayout.ALIGN_PARENT_TOP);
					hourItem.setLayoutParams(hourLP);

					topLP.addRule(RelativeLayout.ALIGN_PARENT_TOP);
					topLP.addRule(RelativeLayout.RIGHT_OF, hourItem.getId());
					topSection.setLayoutParams(topLP);

					bottomLP.addRule(RelativeLayout.BELOW, topSection.getId());
					bottomLP.addRule(RelativeLayout.RIGHT_OF, hourItem.getId());
					bottomSection.setLayoutParams(bottomLP);
				}

				// Detach views from the old layout
				rl.removeAllViews();

				// Attach to the new one
				mScreen.addView(hourItem);
				mScreen.addView(topSection);
				mScreen.addView(bottomSection);
			}

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
