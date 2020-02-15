package tech.khash.realmtest.model;

import android.text.TextUtils;

import androidx.annotation.IntDef;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import tech.khash.realmtest.util.CommonUtil;

public class ExpenseEntity extends RealmObject {

    @IntDef({FOOD, GEAR, ALCOHOL, WEED, GAS, ACCOMMODATION, OTHER})
    public @interface  ExpenseTypeInt {
    }

    public static final @ExpenseTypeInt
    int FOOD = 1;
    public static final @ExpenseTypeInt
    int ALCOHOL = 2;
    public static final @ExpenseTypeInt
    int WEED = 3;
    public static final @ExpenseTypeInt
    int GAS = 4;
    public static final @ExpenseTypeInt
    int ACCOMMODATION = 5;
    public static final @ExpenseTypeInt
    int OTHER = 6;
    public static final @ExpenseTypeInt
    int GEAR = 7;

    @PrimaryKey
    private long epoch;

    private int amount;
    private int type;
    private int weekOfTheYear;
    private int monthOfTheYear;
    private int year;

    private String comment;
    private String todayDateTimeStr;
    private String mondayDateTimeStr;

    public static final String EPOCH = "epoch";
    public static final String AMOUNT = "amount";
    public static final String TYPE = "type";
    public static final String WEEK_OF_THE_YEAR = "weekOfTheYear";
    public static final String MONTH_OF_THE_YEAR = "monthOfTheYear";
    public static final String YEAR = "year";
    public static final String COMMENT = "comment";
    public static final String TODAY_DATE_STR = "todayDateTimeStr";
    public static final String MONDAY_DATE_STR = "mondayDateTimeStr";

    public ExpenseEntity() {
    }

    public ExpenseEntity(@ExpenseTypeInt int type, int amount) {
        this.type = type;
        this.amount = amount;
        epoch = System.currentTimeMillis();

        DateTime todayDateTime = new DateTime();
        DateTime mondayDateTime = todayDateTime.withDayOfWeek(DateTimeConstants.MONDAY);

        weekOfTheYear = mondayDateTime.getWeekOfWeekyear();
        monthOfTheYear = todayDateTime.getMonthOfYear();
        year = todayDateTime.getYear();

        DateTimeFormatter formatter = DateTimeFormat.forPattern("EEE, MMM.dd.YYYY");
        todayDateTimeStr = formatter.print(todayDateTime);
        mondayDateTimeStr = formatter.print(mondayDateTime);
    }

    public ExpenseEntity(@ExpenseTypeInt int type, int amount, String comment) {
        this.type = type;
        this.amount = amount;
        this.comment = comment;
        epoch = System.currentTimeMillis();

        DateTime todayDateTime = new DateTime();
        DateTime mondayDateTime = todayDateTime.withDayOfWeek(DateTimeConstants.MONDAY);

        weekOfTheYear = mondayDateTime.getWeekOfWeekyear();
        monthOfTheYear = todayDateTime.getMonthOfYear();
        year = todayDateTime.getYear();

        DateTimeFormatter formatter = DateTimeFormat.forPattern("EEE, MMM.dd.YYYY");
        todayDateTimeStr = formatter.print(todayDateTime);
        mondayDateTimeStr = formatter.print(mondayDateTime);
    }

    public ExpenseEntity convertToRealmObject() {
        ExpenseEntity realmEntity = new ExpenseEntity();
        realmEntity.epoch = this.epoch;
        realmEntity.amount = this.amount;
        realmEntity.type = this.type;
        realmEntity.weekOfTheYear = this.weekOfTheYear;
        realmEntity.monthOfTheYear = this.monthOfTheYear;
        realmEntity.comment = this.comment;
        realmEntity.todayDateTimeStr = this.todayDateTimeStr;
        realmEntity.mondayDateTimeStr = this.mondayDateTimeStr;
        return realmEntity;
    }

    /*
        ------------------------------  SETTERS  -------------------------------
    */

    public void setEpoch(long epoch) {
        this.epoch = epoch;
        DateTime todayDateTime = new DateTime(epoch);
        DateTime mondayDateTime = todayDateTime.withDayOfWeek(DateTimeConstants.MONDAY);

        weekOfTheYear = mondayDateTime.getWeekOfWeekyear();
        monthOfTheYear = todayDateTime.getMonthOfYear();
        year = todayDateTime.getYear();

        DateTimeFormatter formatter = DateTimeFormat.forPattern("EEE, MMM.dd.YYYY");
        todayDateTimeStr = formatter.print(todayDateTime);
        mondayDateTimeStr = formatter.print(mondayDateTime);
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /*
        ------------------------------  GETTERS  -------------------------------
    */

    public long getEpoch() {
        return epoch;
    }

    public int getAmount() {
        return amount;
    }

    public int getType() {
        return type;
    }

    public int getWeekOfTheYear() {
        return weekOfTheYear;
    }

    public int getMonthOfTheYear() {
        return monthOfTheYear;
    }

    public int getYear() {
        return year;
    }

    public String getTodayDateTimeStr() {
        return todayDateTimeStr;
    }

    public String getMondayDateTimeStr() {
        return mondayDateTimeStr;
    }

    public String getExpenseTypeStr() {
        switch (type) {
            case FOOD:
                return Constants.FOOD;
            case ALCOHOL:
                return Constants.ALCOHOL;
            case WEED:
                return Constants.WEED;
            case GAS:
                return Constants.GAS;
            case ACCOMMODATION:
                return Constants.ACCOMMODATION;
            case OTHER:
                return Constants.OTHER;
            case GEAR:
                return Constants.GEAR;
            default:
                return CommonUtil.getEmptyString();

        }
    }

    public String getComment() {
        return !TextUtils.isEmpty(comment) ? comment : CommonUtil.getEmptyString();
    }

    public static int[] getExpenseTypes() {
        return new int[]{FOOD, GEAR, ALCOHOL, WEED, GAS, ACCOMMODATION, OTHER};
    }
}
