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
	public final int V_SAVE_TIMETABLE = 0;
	public final int V_LOAD_TIMETABLE = 1;
	public final int V_NEW_TIMETABLE = 2;
	public final int V_MODIFY_TIMETABLE = 3;
	
	public final int C_TIMETABLE_SAVED = 100;
	public final int C_TIMETABLE_LOADED = 101;
	public final int C_CREATE_MANAGE_VIEW = 102;
}
