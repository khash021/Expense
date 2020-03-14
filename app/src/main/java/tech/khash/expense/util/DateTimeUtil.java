package tech.khash.expense.util;

import androidx.annotation.NonNull;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import tech.khash.expense.model.Constants;

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

    public static String getMondayDateTimeStr(DateTime dateTime) {
        DateTime mondayDateTime = dateTime.withDayOfWeek(DateTimeConstants.MONDAY);
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

    public static String getThisMonthStr() {
        int thisMonth = getThisMonthInt();
        return getMonthStr(thisMonth);
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

    public static int[] getThisSeasonInts() {
        int month = getThisMonthInt();
        switch (month) {
            case 1:
            case 2:
            case 3:
                return new int[]{1, 2, 3};
            case 4:
            case 5:
            case 6:
                return new int[]{4, 5, 6};
            case 7:
            case 8:
            case 9:
                return new int[]{7, 8, 9};
            case 10:
            case 11:
            case 12:
                return new int[]{10, 11, 12};
            default:
                return null;
        }
    }

    private static String getMonthStr(@NonNull int thisMonth) {
        if (thisMonth < 1 || thisMonth > 12)
            return null;
        switch (thisMonth) {
            case 1:
                return Constants.JANUARY;
            case 2:
                return Constants.FEBRUARY;
            case 3:
                return Constants.MARCH;
            case 4:
                return Constants.APRIL;
            case 5:
                return Constants.MARCH;
            case 6:
                return Constants.JUNE;
            case 7:
                return Constants.JULY;
            case 8:
                return Constants.AUGUST;
            case 9:
                return Constants.SEPTEMBER;
            case 10:
                return Constants.OCTOBER;
            case 11:
                return Constants.NOVEMBER;
            case 12:
                return Constants.DECEMBER;
            default:
                return null;

        }
    }
}
