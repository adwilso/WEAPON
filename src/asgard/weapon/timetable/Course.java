package asgard.weapon.timetable;

import java.io.Serializable;
import java.util.ArrayList;
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
		mSessions = new ArrayList<Session>();
	}
	
	public String getCourseCode() {
		return mCourseCode;
	}
	
	public void setCourseCode(String mCourseCode) {
		this.mCourseCode = mCourseCode;
	}
	
	public List<Session> getSessions () {
		return mSessions;
	}
	
	public Session getSession(int location) {
		return mSessions.get(location);
	}
	
	public void addSession(Session session) {
		mSessions.add(session);
	}
	
	public boolean removeSession (Session session){
		return mSessions.remove(session);
	}
	@Override
	public String toString(){
		return mCourseCode;
	}
	
}
