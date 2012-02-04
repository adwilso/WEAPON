package asgard.weapon.timetable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import android.content.Context;
import android.util.Log;

/**
 * 
 * @author Benjamin
 * 
 */
public class Timetable implements Serializable {

	private static final long serialVersionUID = -8496468328964194932L;
	private static final String FILENAME = "timetable.data";

	protected String mName;
	protected List<Session> mSessions;

	public Timetable(String name) {
		mName = name;
	}

	public synchronized String getName() {
		return mName;
	}

	public synchronized void setName(String name) {
		mName = name;
	}

	public synchronized void addSession(Session session) {
		mSessions.add(session);
	}

	public synchronized Session getSession(int index) {
		return mSessions.get(index);
	}

	/*
	 * Attempts to load all the timetables from memory
	 */
	public static synchronized List<Timetable> load(Context context) {
		try {
			FileInputStream fIn = context.openFileInput(FILENAME);
			// Read object with ObjectInputStream
			ObjectInputStream objIn = new ObjectInputStream(fIn);
			// Read object in from disk
			Object time = objIn.readObject();

			if (time instanceof List<?>) {
				@SuppressWarnings("unchecked")
				List<Timetable> timetable = (List<Timetable>) time;
				return timetable;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Attempts to save the timetable to a file. Note that nothing else can be
	 * done to the timetable when this is happening!
	 */
	public static synchronized void save(Context context, List<Timetable> list) {

		Log.d("Timetable", "Done saving timetable");
		try {
			FileOutputStream fOut = context.openFileOutput(FILENAME,
					Context.MODE_PRIVATE);
			// Write object with ObjectInputStream
			ObjectOutputStream objOut = new ObjectOutputStream(fOut);
			// Write object out to disk
			objOut.writeObject(list);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static synchronized void delete(Context context) {
		context.deleteFile(FILENAME);
	}
}
