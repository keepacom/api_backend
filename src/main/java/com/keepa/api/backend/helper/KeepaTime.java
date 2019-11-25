package com.keepa.api.backend.helper;

/**
 * Keepa Time - Unix Time Converter Helper Class
 */
public class KeepaTime {
	public static long keepaStartHour = 359400;
	public static long keepaStartMinute = 21564000;

	public static int nowHours() {
		return unixInMillisToKeepaHour(System.currentTimeMillis());
	}

	public static int nowMinutes() {
		return unixInMillisToKeepaMinutes(System.currentTimeMillis());
	}

	public static int unixInMillisToKeepaMinutes(long unix) {
		return (int)((Math.floor(unix / (60 * 1000))) - keepaStartMinute);
	}

	public static int unixInMillisToKeepaHour(long unix) {
		return (int)((Math.floor(unix / (60 * 60 * 1000))) - keepaStartHour);
	}

	public static long keepaHourToUnixInMillis(int hour) {
		return hour * 60 * 60 * 1000L + keepaStartHour * 60 * 60 * 1000L;
	}

	public static long keepaMinuteToUnixInMillis(int minute) {
		return minute * 60 * 1000L + keepaStartMinute * 60 * 1000L;
	}

	public static long keepaMinuteToUnixInMillis(String minute) {
		return keepaMinuteToUnixInMillis(Integer.parseInt(minute));
	}
}
