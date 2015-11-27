package io.alacroix.utils;

import java.util.concurrent.TimeUnit;

/**
 * @author Adrien Lacroix
 * @version 0.1.0
 */
public abstract class TimeUtils {

	/**
	 * Return a string with format %02d:%O2d
	 * for minutes and seconds
	 * @param milliseconds
	 * @return
	 */
	public static String beautifyMilliseconds(int milliseconds) {
		return String.format("%02d:%02d",
				TimeUnit.MILLISECONDS.toSeconds(milliseconds) / 60,
				TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60);
	}

}
