package asgard.weapon.timetable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

/**
 * 
 * @author Benjamin
 * 
 *         Controller for all timetable interactions. Creates new threads to
 *         update Models and creates the bridge between the Views and Models
 */
public class TimetableMainController {

	private HandlerThread mHandlerThread;
	private Handler mHandler;
	private List<Handler> mViewHandlers;

	public TimetableMainController(Handler handler) {
		mViewHandlers = new ArrayList<Handler>();
		mViewHandlers.add(handler);

		mHandlerThread = new HandlerThread("Controller Handler");
		mHandlerThread.start();

		mHandler = new Handler(mHandlerThread.getLooper()) {
			public void handleMessage(android.os.Message msg) {
				TimetableMainController.this.handleMessage(msg);
			}
		};
	}

	protected void handleMessage(Message msg) {
		// TODO Auto-generated method stub

		notifyHandlers(msg.what, msg.arg1, msg.arg2, msg.obj);
	}

	public void notifyHandlers(int what, int arg1, int arg2, Object obj) {

	}

	public void saveTimetable(Timetable timetable, FileOutputStream fOut) {
		timetable.save(fOut);
	}

	public void loadTimetable(Timetable timetable, String timetableName,
			FileInputStream fIn) {
		timetable.load(timetableName, fIn);

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