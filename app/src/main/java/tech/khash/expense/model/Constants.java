package tech.khash.expense.model;

public class Constants {

    //TODO: create subclasses like public static final class REALM
    //CODES AND KEYS
    public static final int REQUEST_CODE_ADD_NEW_EXPENSE = 100;
    public static final int REQUEST_CODE_EDIT_EXPENSE = 110;
    public static final int REQUEST_CODE_SETTINMGS = 200;

    public static final String ADD_NEW_EXPENSE_EXTRA_TYPE = "add-new-expense-extra-type";
    public static final String EDIT_EXPENSE_EXTRA_EPOCH = "edit-expense-extra-epoch";

    public static final String THIS_WEEK_EXPENSE_KEY = "this-week-expense-key";
    public static final String LAST_WEEK_EXPENSE_KEY = "last-week-expense-key";

    public static final String FOOD = "Food";
    public static final String ALCOHOL = "Alcohol";
    public static final String WEED = "Weed";
    public static final String GAS = "Gas";
    public static final String ACCOMMODATION = "Accommodation";
    public static final String OTHER = "Other";
    public static final String GEAR = "Gear";

    public static final String INITIAL_VALUES_TYPE = "initial-values_type";
    public static final String INITIAL_VALUES_AMOUNT = "initial-values_amount";
    public static final String INITIAL_VALUES_COMMENT = "initial-values_comment";

    public static final class REALM {
        public static int REALM_VERSION = 2;
    }
}
