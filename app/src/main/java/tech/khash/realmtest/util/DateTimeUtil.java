package tech.khash.realmtest.util;

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
}
