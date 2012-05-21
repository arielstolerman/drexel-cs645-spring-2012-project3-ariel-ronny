package main;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Utility methods.
 * 
 * @author Ariel Stolerman
 *
 */
public class Utils {
	
	private static SimpleDateFormat tf = new SimpleDateFormat("HH-mm-ss");
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private static Calendar cal;

	/**
	 * @return The current date.
	 */
	public static String date() {
		cal = Calendar.getInstance();
		return df.format(cal.getTime());
	}
	
	/**
	 * @return The current time.
	 */
	public static String time() {
		cal = Calendar.getInstance();
		return tf.format(cal.getTime());
	}
	
	/**
	 * @return A path for a new log with the date and time of creation.
	 */
	public static String getLogPath() {
		return "log_" + Utils.date() +
				"_" + Utils.time() + ".txt";
	}
	
}
