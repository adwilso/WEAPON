package asgard.weapon.timetable;

import java.io.Serializable;

/**
 * 
 * @author Benjamin
 * 
 */
public class Session implements Serializable {

	private static final long serialVersionUID = 1101524987457494622L;
	private int mStartHour;
	private int mStartMin;
	private float mDuration;
	private int mDay;
	private String mDescription;
	private String mLocation;
	private String mCourse;

	public Session() {

	}

	public Session(int startHour, int startMin, float duration,
			int day, String description, String location, String course) {
		setTime(startHour, startMin, duration);
		setDay(day);
		setDescription(description);
		setLocation(location);
		setCourse(course);
	}

	public int getHour(){
		return mStartHour;
	}
	
	public int getMinute(){
		return mStartMin;
	}
	
	public String getLocation() {
		return mLocation;
	}

	public void setLocation(String location) {
		mLocation = location;
	}

	public String getTime() {
		return mStartHour + ":" + mStartMin + "," + mDuration;
	}

	public void setTime(int startHour, int startMin, float duration) {
		this.mStartHour = startHour;
		this.mStartMin = startMin;
		this.mDuration = duration;
	}

	public int getDate() {
		return mDay;
	}

	public void setDay(int mDay) {
		this.mDay = mDay;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String mDescription) {
		this.mDescription = mDescription;
	}

	public String getCourse() {
		return mCourse;
	}

	public void setCourse(String mCourse) {
		this.mCourse = mCourse;
	}

	public float getDuration() {
		return mDuration;
	}
}
