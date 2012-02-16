package asgard.weapon.timetable;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @author Benjamin
 *
 */
public class Course implements Serializable {
	
	private List<Session> mSessions;
	
	private static final long serialVersionUID = 6834088449384071509L;
	private String mCourseCode;
	
	public Course (String code) {
		mCourseCode = code;
	}
	
	public String getCourseCode() {
		return mCourseCode;
	}
	public void setCourseCode(String mCourseCode) {
		this.mCourseCode = mCourseCode;
	}
	public List<Session> getSessions() {
		return mSessions;
	}
	public void setSessions(List<Session> mSessions) {
		this.mSessions = mSessions;
	}
	
}
