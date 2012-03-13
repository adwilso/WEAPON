package asgard.weapon.timetable;

import java.util.ArrayList;

import asgard.weapon.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import asgard.weapon.ConditionCodes;

/**
 * 
 * @author Jarrett
 * 
 *         An activity to manage creation of new timetables (TEMPORARY)
 * 
 */
public class TimetableSelectionForm extends Activity implements OnClickListener,
		Handler.Callback {

	private TimetableController mController;

	private Handler mHandler;
	
	private ArrayList<Timetable> mTimetables;
	
	private RelativeLayout mView;
	private boolean delete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.timetable_selection_form);

		delete = false;
		mHandler = new Handler(this);

		mController = TimetableController.getController(this);
		mController.addHandler(mHandler);
		
		mController.getHandler().obtainMessage(ConditionCodes.V_GET_ALL_TIMETABLE).sendToTarget();
	}

	@Override
	public void onClick(View v) {
		Handler handler = mController.getHandler();
		Message message = handler.obtainMessage(ConditionCodes.V_DO_NOTHING,
				this);
		
		int position = v.getId()-1;
		
		Message msg = mController.getHandler().obtainMessage();
		
		if (position < 100){
			msg.what = ConditionCodes.V_DELETE_SELECTED;
			msg.obj = this;
		}
		else{
			msg.what = ConditionCodes.V_TIMETABLE_SELECTED;
		}
		position = position % 100;
		msg.arg1 = position;
		msg.sendToTarget();
		finish();
		//test

	}
	
	public void initialize(){
		mView = (RelativeLayout) findViewById(R.id.button_container);
		LayoutInflater li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		
		for (int i = 0; i < mTimetables.size(); i++){
			
			RelativeLayout rl = (RelativeLayout) li.inflate(
					R.layout.button_form, null);
			
			Button temp = (Button) rl.findViewById(R.id.button);
			TextView name = (TextView) rl.findViewById(R.id.text);
			temp.setOnClickListener(this);
			name.setOnClickListener(this);
			name.setText(mTimetables.get(i).getName());
			temp.setId(i+1);
			name.setId(i+101);
			
			//CHANGE THE ONCLICK EVENT POSITION
			
			LayoutParams buttonLP = (LayoutParams) temp.getLayoutParams();
			LayoutParams textLP = (LayoutParams) name.getLayoutParams();
			
			if (i != 0) {
				TextView aboveText = (TextView) findViewById(i+100);
				Button aboveButton = (Button) findViewById(i);
				
				textLP.addRule(RelativeLayout.BELOW, aboveText.getId());
				buttonLP.addRule(RelativeLayout.BELOW, aboveButton.getId());
				buttonLP.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				temp.setLayoutParams(buttonLP);
				name.setLayoutParams(textLP);

			} else {
				buttonLP.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				textLP.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				textLP.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				buttonLP.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				temp.setLayoutParams(buttonLP);
				name.setLayoutParams(textLP);
			}

			

			rl.removeAllViews();
			mView.addView(temp);
			mView.addView(name);
		}		
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		mController.removeHandler(mHandler);
	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what){
		case ConditionCodes.C_SEND_ALL_TIMETABLE:
			mTimetables = (ArrayList<Timetable>) msg.obj;
			if (msg.arg1 != 0){
				delete = true;
			}
			initialize();
			break;
		}
		return false;
	}
}