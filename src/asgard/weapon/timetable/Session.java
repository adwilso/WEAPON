package asgard.weapon.timetable;

import java.io.Serializable;

/**
 * 
 * @author Benjamin
 *
 */
public class Session implements Serializable{

	private static final long serialVersionUID = 1101524987457494622L;
	private int mTime;
	private int mDate;
	private String mDescription;
	
	public Session(){
	
	}
	
	public Session(int time, int date, String description){
		setTime(time);
		setDate(date);
		setDescription(description);
	}
	
	public int getTime() {
		return mTime;
	}
	public void setTime(int mTime) {
		this.mTime = mTime;
	}
	public int getDate() {
		return mDate;
	}
	public void setDate(int mDate) {
		this.mDate = mDate;
	}
	public String getDescription() {
		return mDescription;
	}
	public void setDescription(String mDescription) {
		this.mDescription = mDescription;
	}
}
