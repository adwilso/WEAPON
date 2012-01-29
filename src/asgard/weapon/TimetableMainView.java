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
	private TextView mTimetableName;

	private Handler mHandler;

	private TimetableMainController mTimetableController;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Do initial UI preperation
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

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.load_button) {
			/*
			 * try { // Read to disk with FileInputStream FileInputStream fIn =
			 * openFileInput(FILENAME); // Read object with ObjectInputStream
			 * ObjectInputStream objIn = new ObjectInputStream(fIn); // Read
			 * object in from disk Object time = objIn.readObject();
			 * 
			 * if (time instanceof Timetable) { Timetable timetable =
			 * (Timetable) time;
			 * 
			 * mStatusTextView.setText("Loaded successfully");
			 * mTimetableName.setText(timetable.getName()); }
			 * 
			 * } catch (Exception e) { e.printStackTrace(); }
			 */
		} else if (v.getId() == R.id.save_button) {
			/*
			 * try { // Write to disk with FileOutputStream FileOutputStream
			 * fOut = openFileOutput(FILENAME, Context.MODE_PRIVATE); // Write
			 * object with ObjectInputStream ObjectOutputStream objOut = new
			 * ObjectOutputStream(fOut); // Write object out to disk
			 * objOut.writeObject(mTimetable);
			 * 
			 * mStatusTextView.setText("Saved succesfully!");
			 * mTimetableName.setText("none"); } catch (Exception e) {
			 * e.printStackTrace(); }
			 */
		}
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		return false;
	}
}