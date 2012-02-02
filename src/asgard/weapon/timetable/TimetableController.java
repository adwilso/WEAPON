package asgard.weapon.timetable;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import asgard.weapon.ConditionCodes;

/**
 * 
 * @author Benjamin
 * 
 *         Controller for all timetable interactions. Creates new threads to
 *         update Models and creates the bridge between the Views and Models.
 * 
 *         Singleton patter is used to ensure only one controller exists at a
 *         time
 */
public class TimetableController {

	private static TimetableController mController;

	private HandlerThread mHandlerThread; // Thread for Controller handler
	private List<Handler> mOutgoingHandlers; // List of all handlers listening
												// to the Controller
	private Handler mHandler; // Controller's Handler

	private List<Timetable> mTimetable; // Reference to the current timetable

	public static synchronized TimetableController getController() {
		if (mController == null) {
			mController = new TimetableController();
		}
		return mController;
	}

	private TimetableController() {
		mOutgoingHandlers = new ArrayList<Handler>();

		mHandlerThread = new HandlerThread("Controller Handler");
		mHandlerThread.start();

		mHandler = new Handler(mHandlerThread.getLooper()) {
			public void handleMessage(android.os.Message msg) {
				TimetableController.this.handleMessage(msg);
			}
		};

		mTimetable = new ArrayList<Timetable>();
	}

	public Handler getHandler() {
		return mHandler;
	}

	public void addHandler(Handler handler) {
		mOutgoingHandlers.add(handler);
	}

	public void removeHandler(Handler handler) {
		mOutgoingHandlers.remove(handler);
	}

	private void handleMessage(Message msg) {
		// Deal with message by calling appropriate functions
		switch (msg.what) {
		case ConditionCodes.V_SAVE_TIMETABLE:
			saveTimetable(msg);
			break;
		case ConditionCodes.V_LOAD_TIMETABLE:
			loadTimetable(msg);
			break;
		case ConditionCodes.V_NEW_TIMETABLE:
			createTimetable(msg);
			break;
		case ConditionCodes.V_TEST_NULL:
			if (mTimetable == null) {
				msg.obj = "Timetable list is null :(";
			} else
				msg.obj = "Timetable list is not null :)";
			msg.what = ConditionCodes.C_TEST_NULL;
			break;
		case ConditionCodes.V_CREATE_TIMETABLE_SUBMIT:
			mTimetable.add((Timetable)msg.obj);
		}

		// Post the outcome message to all attached handlers
		notifyHandlers(msg.what, msg.arg1, msg.arg2, msg.obj);
	}

	// Makes a TimetableCreationForm object (started in a new activity)
	private void createTimetable(Message msg) {

		if (msg.obj instanceof TimetableMainView) {
			Intent intent = new Intent((Activity) msg.obj,
					TimetableCreationForm.class);
			((Activity) msg.obj).startActivity(intent);
		}
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

	private void loadTimetable(Message msg) {
		final Context CONTEXT = (Context) msg.obj;
		new Thread(new Runnable() {
			@Override
			public void run() {
				mTimetable = Timetable.load(CONTEXT);
			}
		}).start();
		msg.what = ConditionCodes.C_TIMETABLE_LOADED;
	}

	private void saveTimetable(Message msg) {
		final Context CONTEXT = (Context) msg.obj;
		new Thread(new Runnable() {
			@Override
			public void run() {
				Timetable.save(CONTEXT, mTimetable);
			}
		}).start();
		msg.what = ConditionCodes.C_TIMETABLE_SAVED;
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