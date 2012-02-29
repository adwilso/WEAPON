package asgard.weapon.timetable;

import java.io.Serializable;

/**
 * 
 * @author Benjamin
 *
 */
public class Session implements Serializable{

	private static final long serialVersionUID = 1101524987457494622L;
	private int mStartHour;
	private int mStartMin;
	private float mDuration;
	private int mDay;
	private String mDescription;
	private String mLocation;
	
	public Session(){
	
	}
	
	public Session(int startHour, int startMin, float duration, int day, String description, String location){
		setTime(startHour, startMin, duration);
		setDay(day);
		setDescription(description);
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
}
