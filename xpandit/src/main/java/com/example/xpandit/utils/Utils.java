package com.example.xpandit.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {

	public static Calendar convertToCalendar(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar newDate = Calendar.getInstance();
		newDate.setTime(sdf.parse(date));
		return newDate;

	}
}
