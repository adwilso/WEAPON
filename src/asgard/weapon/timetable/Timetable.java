package asgard.weapon.timetable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import android.content.Context;

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

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public void addSession(Session session) {
		mSessions.add(session);
	}

	public Session getSession(int index) {
		return mSessions.get(index);
	}

	/*
	 * Attempts to load the first timetable from memory
	 */
	public static Timetable load(Context context) {
		try {
			FileInputStream fIn = context.openFileInput(FILENAME);
			// Read object with ObjectInputStream
			ObjectInputStream objIn = new ObjectInputStream(fIn);
			// Read object in from disk
			Object time = objIn.readObject();

			if (time instanceof Timetable) {
				Timetable timetable = (Timetable) time;
				return timetable;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Attempts to return the timetable with the specified name from memory
	 */
	public static Timetable load(Context context, String timetableName) {
		return null;
	}

	/*
	 * Attempts to save the timetable to a file
	 */
	public void save(Context context) {
		synchronized (this) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// mStatusTextView.setText("Saving timetable");
			try {
				FileOutputStream fOut = context.openFileOutput(FILENAME,
						Context.MODE_PRIVATE);
				// Write object with ObjectInputStream
				ObjectOutputStream objOut = new ObjectOutputStream(fOut);
				// Write object out to disk
				objOut.writeObject(this);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
