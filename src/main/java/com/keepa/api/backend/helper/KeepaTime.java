package com.keepa.api.backend.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Keepa Time - Unix Time Converter Helper Class
 */
public class KeepaTime {
	public static long keepaStartHour = 0;
	private static long keepaStartMinute = 0;

	static {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			keepaStartHour = (int) Math.floor(sdf.parse("2011-01-01 00:00:00").getTime() / (1000 * 60 * 60));
			keepaStartMinute = keepaStartHour * 60;
		} catch (ParseException e) {
			e.printStackTrace();
		}
    }

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
}
