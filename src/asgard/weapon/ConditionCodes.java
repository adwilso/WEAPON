package asgard.weapon;

/**
 * 
 * @author Benjamin
 * 
 *         Condition codes used for messages. All codes have the format
 *         <type>_<action> where type (either M, V or C) is the class
 *         type that initiates the message
 */

public interface ConditionCodes {
	public final int V_DO_NOTHING = -1;
	
	// Basic operations
	public final int V_SAVE_TIMETABLE = 0;	
	public final int V_LOAD_TIMETABLE = 1;
	public final int V_DELETE_TIMETABLE = 2;
	public final int V_CREATE_TIMETABLE = 3;
	public final int V_MODIFY_TIMETABLE = 4;
	public final int V_DISPLAY_TIMETABLE = 5;
	public final int V_SELECT_TIMETABLE = 6;
		
	public final int V_CLOSE_NEW_TIMETABLE = 12;
	
	public final int V_LAUNCH_TIMETABLE_CREATION_FORM = 20;
	public final int V_LAUNCH_COURSE_CREATION_FORM = 21;
	public final int V_ADD_SESSION = 22;
	public final int V_ADD_COURSES = 23;
	
	public final int V_GET_TIMETABLE = 30;
	public final int V_GET_COURSES = 31;
	public final int V_GET_SESSION = 32;
	public final int V_SET_SESSION = 33;
	public final int V_GET_ALL_TIMETABLE = 34;
	public final int V_TIMETABLE_SELECTED = 35;
	public final int V_DELETE_SELECTED = 36;
	public final int V_DELETE_LAST = 37;
	public final int V_DELETE_SESSION = 38;
	public final int V_EDIT_SESSION = 39;
	
	public final int V_LAUNCH_SESSION_FORM = 40;
	public final int V_SET_TIME_DATE = 41;
	
	public final int C_TIMETABLE_SAVING = 100;
	public final int C_TIMETABLE_SAVED = 101;
	public final int C_TIMETABLE_NOT_SAVED = 102;
	public final int C_SEND_ALL_TIMETABLE = 103;
	public final int C_TIMETABLE_LOADED = 104;
	public final int C_TIMETABLE_NOT_LOADED = 105;
	
	public final int C_TIMETABLE_DISPLAYED = 111;
	
	public final int C_TIMETABLE_CREATED = 106;
	
	public final int C_TIMETABLE_CLOSED = 107;
	
	public final int C_GET_ACTIVITY = 108;

	public final int C_TEST_NULL = 109;
	public final int C_TIMETABLE_DELETED = 110;

	public final int C_TIMETABLE_RETRIEVED = 115;

	public final int C_COURSES_RETRIEVED = 116;
	
	public final int C_SESSION_ADDED = 130;
	public final int C_SESSION_NOT_ADDED = 131;
	
	public final int C_SESSION_SET = 132;
	
	public final int C_SESSION_RECIEVED = 140;
	public final int C_SESSION_DELETED = 141;
	public final int C_TIME_DATE_SET = 142;
	public final int C_SESSION_UPDATED = 143;
}
