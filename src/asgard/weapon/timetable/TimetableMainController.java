package asgard.weapon.timetable;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import asgard.weapon.ConditionCodes;

/**
 * 
 * @author Benjamin
 * 
 *         Controller for all timetable interactions. Creates new threads to
 *         update Models and creates the bridge between the Views and Models
 */
public class TimetableMainController {

	private HandlerThread mHandlerThread; // Thread for Controller handler
	private List<Handler> mOutgoingHandlers; // List of all handlers listening
												// to the Controller
	private Handler mHandler; // Controller's Handler

	private Timetable mTimetable; // Reference to the current timetable

	public TimetableMainController(Handler handler) {
		mOutgoingHandlers = new ArrayList<Handler>();
		mOutgoingHandlers.add(handler);

		mHandlerThread = new HandlerThread("Controller Handler");
		mHandlerThread.start();

		mTimetable = new Timetable("duh");
		
		mHandler = new Handler(mHandlerThread.getLooper()) {
			public void handleMessage(android.os.Message msg) {
				TimetableMainController.this.handleMessage(msg);
			}
		};
	}

	private void handleMessage(Message msg) {
		// Do message modifications
		switch (msg.what) {
		case ConditionCodes.V_SAVE_TIMETABLE:
			saveTimetable(msg);
			break;
		case ConditionCodes.V_LOAD_TIMETABLE:
			loadTimetable(msg);		
			break;
		}

		// Post the outcome message to all attached handlers
		notifyHandlers(msg.what, msg.arg1, msg.arg2, msg.obj);
	}

	private void loadTimetable(Message msg) {
		mTimetable.load((Context)msg.obj);
		msg.what = ConditionCodes.C_TIMETABLE_LOADED;
	}

	private void saveTimetable(Message msg) {
		mTimetable.save((Context)msg.obj);
		msg.what = ConditionCodes.C_TIMETABLE_SAVED;
	}

	public Handler getHandler() {
		return mHandler;
	}

	public void notifyHandlers(int what, int arg1, int arg2, Object obj) {
		// Ensure the handler list isn't empty
		if (!mOutgoingHandlers.isEmpty()) {
			// Go over list and send message to all listening views
			for (Handler handler : mOutgoingHandlers) {
				Message message = handler.obtainMessage(what, arg1, arg2, obj);
				message.sendToTarget();
			}
		}
	}

	public void submitCourseForm() {

	}

	public void compareTimetablePressed() {

	}

	public void compareTimetable() {

	}

	public void editCourse() {

	}

	public void displayGetTimetableView() {

	}

	public void createManageView() {

	}

	public void closeManageView() {

	}

	public void editTimetable() {

	}

	public void deleteTimetable() {

	}

	public void publishTimetablePressed() {

	}

	public void uploadStatus() {

	}

	public void listTimetable() {

	}

	public void changeDisplayedTimetable(String name) {

	}

	public void closeTimetableSelectView() {

	}

	public void closeTimetableMainView() {

	}

}