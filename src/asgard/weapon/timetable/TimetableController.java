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
 * @author Benjamin, Jarrett
 * 
 *         Controller for all timetable interactions. Creates new threads to
 *         update Models and creates the bridge between the Views and Models.
 * 
 *         Singleton pattern is used to ensure only one controller exists at a
 *         time
 * 
 *         A little explanation of what's going on here: The Handler mechanism
 *         provides a simple way for classes running in different threads to
 *         communicate with each other. It uses a message queue to pass Runnable
 *         and Message objects to the controller.
 * 
 *         Although the UI and controller are running in the same thread, using
 *         messages allows me to write functions regardless of how the views are
 *         finally designed. In other words, any changes to the view (besides
 *         message codes) will have NO EFFECT on the code of the Controller or
 *         Models!
 * 
 *         I have also designed it so any number of handler threads can register
 *         to the controller and any message sent by the controller will be
 *         passed to all of them. Even if a view in a different thread isn't
 *         visible (or isn't even in the timetable module) it can still receive
 *         messages and update its fields accordingly.
 * 
 *         If at any time the controller is needed, it can be called used in the
 *         following way:
 * 
 *         TimetableController.getInstance().addHandler(Handler
 *         theHandlerOfYourThread); Message msg = new
 *         Message(ConditionCodes.THE_MESSAGE_NAME);
 *         TimetableController.getInstance().getHandler().sendMessage(msg);
 * 
 */
public class TimetableController {

	private static TimetableController mController;

	private HandlerThread mHandlerThread; // Thread for Controller handler
	private List<Handler> mOutgoingHandlers; // List of all handlers listening
												// to the Controller
	private Handler mHandler; // Controller's Handler

	private List<Timetable> mTimetables; // Reference to the current timetable

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

		mTimetables = new ArrayList<Timetable>();
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
		// Deal with messages by calling appropriate functions
		switch (msg.what) {

		case ConditionCodes.V_SAVE_TIMETABLE:
			saveTimetable(msg);
			msg.what = ConditionCodes.C_TIMETABLE_SAVING;
			break;

		case ConditionCodes.V_LOAD_TIMETABLE:
			loadTimetable(msg);
			msg.what = ConditionCodes.C_TIMETABLE_LOADING;
			break;

		case ConditionCodes.V_LAUNCH_CREATION_ACTIVITY:
			launchCreateTimetableActivity(msg);
			break;

		case ConditionCodes.V_DELETE_TIMETABLE:
			deleteTimetable(msg);
			msg.what = ConditionCodes.C_TIMETABLE_DELETED;
			break;

		case ConditionCodes.V_TEST_NULL:
			if (mTimetables == null) {
				msg.obj = "Timetable list is null :(";
			} else
				msg.obj = "Timetable list is not null :)";
			msg.what = ConditionCodes.C_TEST_NULL;
			break;

		case ConditionCodes.V_CREATE_TIMETABLE:
			mTimetables.add((Timetable) msg.obj);
			msg.what = ConditionCodes.C_TIMETABLE_CREATED;
			break;
		}

		// Post the outcome message to all attached handlers
		notifyHandlers(msg.what, msg.arg1, msg.arg2, msg.obj);
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
				// Load the timetable and send a message when done
				mTimetables = Timetable.load(CONTEXT);
				
				//If it isn't null, pass to the view
				if (mTimetables != null) {
					for (int i = 0; i < mTimetables.size(); i++) {
						Message message = mHandler.obtainMessage();
						message.what = ConditionCodes.C_TIMETABLE_LOADED;
						message.obj = mTimetables.get(i).getName();
						mHandler.sendMessage(message);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				// If it is null, make a new one
				else {
					mTimetables = new ArrayList<Timetable>();
				}
			}
		}).start();
		msg.what = ConditionCodes.C_TIMETABLE_LOADING;
	}

	private void deleteTimetable(Message msg) {
		Timetable.delete((Context) msg.obj);
		msg.what = ConditionCodes.C_TIMETABLE_DELETED;
	}

	private void saveTimetable(Message msg) {
		final Context CONTEXT = (Context) msg.obj;
		new Thread(new Runnable() {
			@Override
			public void run() {
				Timetable.save(CONTEXT, mTimetables);
				Message message = mHandler.obtainMessage();
				message.what = ConditionCodes.C_TIMETABLE_SAVED;
			}
		}).start();
	}
	
	// Makes a TimetableCreationForm object (started in a new activity)
		private void launchCreateTimetableActivity(Message msg) {

			if (msg.obj instanceof TimetableMainView) {
				Intent intent = new Intent((Activity) msg.obj,
						TimetableCreationForm.class);
				((Activity) msg.obj).startActivity(intent);
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