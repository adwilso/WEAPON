package asgard.weapon.timetable;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @author Benjamin
 *
 */
public class Course implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6834088449384071509L;
	private String mCourseCode;
	
	public String getmCourseCode() {
		return mCourseCode;
	}
	public void setmCourseCode(String mCourseCode) {
		this.mCourseCode = mCourseCode;
	}
	public List<Session> getmSessions() {
		return mSessions;
	}
	public void setmSessions(List<Session> mSessions) {
		this.mSessions = mSessions;
	}
	private List<Session> mSessions;
	
	
}
