package asgard.weapon.timetable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import android.app.Activity;
import android.content.Context;
/**
 * 
 * @author Benjamin
 *
 */
public class Timetable implements Serializable {
	
	private final String FILENAME = "timetable.data";
	
	private static final long serialVersionUID = -8496468328964194932L;
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
	
	public Timetable load(String timetableName, FileInputStream fIn) {
		try {
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
	
	public void save(FileOutputStream fOut) {
		
		//mStatusTextView.setText("Saving timetable");
		try {
			// Write object with ObjectInputStream
			ObjectOutputStream objOut = new ObjectOutputStream(fOut);
			// Write object out to disk
			objOut.writeObject(this);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
