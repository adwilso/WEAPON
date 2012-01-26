package asgard.weapon.timetable;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @author Benjamin
 *
 */
public class Timetable implements Serializable {
	private static final long serialVersionUID = -8496468328964194932L;
	protected String mName;
	protected List<Session> mSessions;
	
	public String getName() {
		return mName;
	}
	
	public void setName() {
		
	}
}
