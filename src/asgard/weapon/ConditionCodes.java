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
	
	public final int V_SAVE_TIMETABLE = 0;
	public final int V_TIMETABLE_SAVED = 1;
	public final int V_LOAD_TIMETABLE = 2;
	public final int V_TIMETABLE_LOADED = 3;
	
	public final int V_NEW_TIMETABLE = 4;
	public final int V_MODIFY_TIMETABLE = 5;
	public final int V_NEW_TIMETABLE_CLOSED = 6;
	public final int V_TEST_NULL = 7;
	public final int V_CREATE_TIMETABLE_SUBMIT = 8;

	
	public final int C_TIMETABLE_SAVED = 100;
	public final int C_TIMETABLE_LOADED = 101;
	public final int C_CREATE_TIMETABLE_CLOSED = 102;
	public final int C_GET_ACTIVITY = 104;
	public final int C_TEST_NULL = 105;
}
