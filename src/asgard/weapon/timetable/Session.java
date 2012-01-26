package asgard.weapon.timetable;

import java.io.Serializable;

/**
 * 
 * @author Benjamin
 *
 */
public class Session implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1101524987457494622L;
	private int mTime;
	private int mDate;
	private String mDescription;
	
	public int getmTime() {
		return mTime;
	}
	public void setmTime(int mTime) {
		this.mTime = mTime;
	}
	public int getmDate() {
		return mDate;
	}
	public void setmDate(int mDate) {
		this.mDate = mDate;
	}
	public String getmDescription() {
		return mDescription;
	}
	public void setmDescription(String mDescription) {
		this.mDescription = mDescription;
	}
}
