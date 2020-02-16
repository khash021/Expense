package tech.khash.expense.util;

import androidx.annotation.NonNull;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static int getWeekOfTheYear() {
        DateTime todayDateTime = new DateTime();
        return todayDateTime.getWeekOfWeekyear();
    }

    public static String getMondayDateTimeStr() {
        DateTime todayDateTime = new DateTime();
        DateTime mondayDateTime = todayDateTime.withDayOfWeek(DateTimeConstants.MONDAY);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("EEE, MMM.dd.YYYY");

        return formatter.print(mondayDateTime);
    }

    public static int getThisWeekInt() {
        DateTime todayDateTime = new DateTime();
        return todayDateTime.withDayOfWeek(DateTimeConstants.MONDAY).getWeekOfWeekyear();
    }

    public static int getThisMonthInt() {
        DateTime todayDateTime = new DateTime();
        return todayDateTime.withDayOfWeek(DateTimeConstants.MONDAY).getMonthOfYear();
    }

    public static int getThisYearInt() {
        DateTime todayDateTime = new DateTime();
        return todayDateTime.withDayOfWeek(DateTimeConstants.MONDAY).getYear();
    }

    public static long getEpochBeginningOfToday() {
        DateTime now = new DateTime();
        DateTime todayStart = now.withTimeAtStartOfDay();
        return todayStart.getMillis();
    }

    public static long getEpochEndOfToday() {
        DateTime now = new DateTime();
        DateTime tomorrowStart = now.plusDays(1).withTimeAtStartOfDay();
        return tomorrowStart.getMillis();
    }

    public static long getStartDayEpochFromDateTime(@NonNull DateTime dateTime) {
        DateTime start = dateTime.withTimeAtStartOfDay();
        return start.getMillis();
    }

    public static long getEndDayEpochEndFromDateTime(@NonNull DateTime dateTime) {
        DateTime end = dateTime.plusDays(1).withTimeAtStartOfDay();
        return end.getMillis();
    }
}
