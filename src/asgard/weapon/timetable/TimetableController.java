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
	private Timetable mCurrentTimetable;

	private int mClickedTime;
	private int mClickedWeekday;

	private Session mSession;

	public static synchronized TimetableController getController(Context context) {
		if (mController == null) {
			mController = new TimetableController(context);
		}
		return mController;
	}

	// Loads a TimetableController, loading any Timetables into memory
	private TimetableController(Context context) {
		mOutgoingHandlers = new ArrayList<Handler>();

		mHandlerThread = new HandlerThread("Controller Handler");
		mHandlerThread.start();

		mHandler = new Handler(mHandlerThread.getLooper()) {
			@Override
			public void handleMessage(android.os.Message msg) {
				TimetableController.this.handleMessage(msg);
			}
		};

		mTimetables = new ArrayList<Timetable>(10);

		// Load the timetable, set head of list to current timetable
		loadTimetable(context);

		// If nothing was returned by load, make a new empty timetable
		if (mTimetables.size() > 0)
			mCurrentTimetable = mTimetables.get(0);
		else
			mCurrentTimetable = new Timetable("Default");
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

	@SuppressWarnings("unchecked")
	private void handleMessage(Message msg) {
		// Deal with messages by calling appropriate functions
		switch (msg.what) {

		case ConditionCodes.V_SAVE_TIMETABLE:
			saveTimetable(msg);
			msg.what = ConditionCodes.C_TIMETABLE_SAVING;
			break;

		case ConditionCodes.V_LOAD_TIMETABLE:
			loadTimetable((Context) msg.obj);
			break;

		case ConditionCodes.V_LAUNCH_TIMETABLE_CREATION_FORM:
			launchCreateTimetableActivity(msg);
			break;

		case ConditionCodes.V_CREATE_TIMETABLE:
			mTimetables.add((Timetable) msg.obj);
			mCurrentTimetable = (Timetable) msg.obj;
			msg.what = ConditionCodes.C_TIMETABLE_CREATED;
			break;

		case ConditionCodes.V_SET_TIME_DATE:
			mClickedTime = msg.arg1;
			mClickedWeekday = msg.arg2;
			mSession = null;

			msg.what = ConditionCodes.C_TIME_DATE_SET;
			break;

		case ConditionCodes.V_LAUNCH_COURSE_CREATION_FORM:

			launchCreationForm(msg);
			break;

		case ConditionCodes.V_GET_TIMETABLE:

			msg.obj = mCurrentTimetable;
			msg.what = ConditionCodes.C_TIMETABLE_RETRIEVED;
			break;

		case ConditionCodes.V_GET_COURSES:
			msg.obj = mCurrentTimetable.getCourses();
			msg.arg1 = mClickedTime;
			msg.arg2 = mClickedWeekday;
			msg.what = ConditionCodes.C_COURSES_RETRIEVED;
			break;

		case ConditionCodes.V_GET_SESSION:
			msg.obj = mSession;
			msg.what = ConditionCodes.C_SESSION_RECIEVED;
			msg.arg1 = mClickedTime;
			msg.arg2 = mClickedWeekday;
			break;

		case ConditionCodes.V_SET_SESSION:
			mSession = (Session) msg.obj;
			msg.what = ConditionCodes.C_SESSION_SET;
			break;

		case ConditionCodes.V_DELETE_SESSION:
			List<Course> listOfCourses = mCurrentTimetable.getCourses();
			for (int i = 0; i < listOfCourses.size(); i++) {
				if (listOfCourses.get(i).removeSession((Session) msg.obj)) {
					msg.what = ConditionCodes.C_SESSION_DELETED;
					msg.obj = mCurrentTimetable;
					i = listOfCourses.size();
				}
			}
			break;

		case ConditionCodes.V_LAUNCH_SESSION_FORM:
			launchSessionForm(msg);
			break;

		case ConditionCodes.V_SELECT_TIMETABLE:
			listTimetables(msg);
			break;

		case ConditionCodes.V_TIMETABLE_SELECTED:
			mCurrentTimetable = mTimetables.get(msg.arg1);
			msg.obj = mCurrentTimetable;
			msg.what = ConditionCodes.C_TIMETABLE_RETRIEVED;
			break;
		case ConditionCodes.V_DELETE_SELECTED:
			if (mCurrentTimetable == mTimetables.get(msg.arg1)) {
				mTimetables.remove(msg.arg1);

				try {
					mCurrentTimetable = mTimetables.get(0);
				} catch (Exception e) {

				}

			} else {
				mTimetables.remove(msg.arg1);
			}

			deleteTimetable(msg);

			 if (mTimetables.size() == 0){
			 mTimetables.add(new Timetable ("My Timetable"));
			 mCurrentTimetable = mTimetables.get(0);
			 }

			saveTimetable(msg);

			msg.what = ConditionCodes.C_TIMETABLE_RETRIEVED;
			msg.obj = mCurrentTimetable;
			break;

		case ConditionCodes.V_EDIT_SESSION:
			mSession = (Session) msg.obj;
			mClickedWeekday = mSession.getDate();
			mClickedTime = mSession.getHour() * 2
					+ (mSession.getMinute() == 0 ? 0 : 1);

			msg.what = ConditionCodes.C_SESSION_UPDATED;
			break;

		case ConditionCodes.V_DELETE_LAST:
			mTimetables.remove(mTimetables.size() - 1);
			try {
				mCurrentTimetable = mTimetables.get(0);
			} catch (Exception e) {

			}
			break;

		case ConditionCodes.V_GET_ALL_TIMETABLE:
			msg.obj = mTimetables;
			msg.what = ConditionCodes.C_SEND_ALL_TIMETABLE;
			break;

		case ConditionCodes.V_ADD_COURSES:
			try {
				mCurrentTimetable.setCourses((List<Course>) msg.obj);
				msg.what = ConditionCodes.C_SESSION_ADDED;
			} catch (Exception e) {
				msg.what = ConditionCodes.C_SESSION_NOT_ADDED;
			}
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

	private void loadTimetable(Context context) {
		final Context CONTEXT = context;

		new Thread(new Runnable() {
			@Override
			public void run() {
				// Load the timetable and send a message when done
				mTimetables = Timetable.load(CONTEXT);
				List<Timetable> tempTimetables = mTimetables;

				// If it is null, make a new one
				if (mTimetables == null || mTimetables.size() == 0) {
					mTimetables = new ArrayList<Timetable>();
					mTimetables.add(new Timetable("My Timetable"));
					mCurrentTimetable = mTimetables.get(0);
				}
				Message message = mHandler.obtainMessage();
				message.what = ConditionCodes.C_TIMETABLE_LOADED;
				message.sendToTarget();
			}
		}).start();
	}

	private void listTimetables(Message msg) {

		if (msg.obj instanceof TimetableMainView) {
			Intent intent = new Intent((Activity) msg.obj,
					TimetableSelectionForm.class);
			((Activity) msg.obj).startActivity(intent);
		}
	}

	private void saveTimetable(Message msg) {
		final Context CONTEXT = (Context) msg.obj;
		new Thread(new Runnable() {
			@Override
			public void run() {
				Timetable.save(CONTEXT, mTimetables);
				Message message = mHandler.obtainMessage();
				message.what = ConditionCodes.C_TIMETABLE_SAVED;
				message.sendToTarget();
			}
		}).start();
	}

	private void deleteTimetable(Message msg) {
		final Context CONTEXT = (Context) msg.obj;
		new Thread(new Runnable() {
			@Override
			public void run() {
				Timetable.delete(CONTEXT);
				Message message = mHandler.obtainMessage();
				message.what = ConditionCodes.C_TIMETABLE_DELETED;
				message.sendToTarget();
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

	public void launchCreationForm(Message msg) {

		if (msg.obj instanceof Activity) {
			Intent intent = new Intent((Activity) msg.obj,
					SessionCreationForm.class);
			((Activity) msg.obj).startActivity(intent);

		}
	}

	private void launchSessionForm(Message msg) {
		if (msg.obj instanceof Activity) {
			Intent intent = new Intent((Activity) msg.obj, SessionForm.class);
			((Activity) msg.obj).startActivity(intent);
		}
	}

	public void compareTimetable() {

	}

	public void editTimetable() {

	}

	public void publishTimetablePressed() {

	}

	public void uploadStatus() {

	}

	public void closeTimetableSelectView() {

	}

}