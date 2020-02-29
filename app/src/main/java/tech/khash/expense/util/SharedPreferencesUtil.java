package tech.khash.expense.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import tech.khash.expense.R;

public class SharedPreferencesUtil {

    public static int getWeeklyBudget(@NonNull Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String s = sharedPreferences.getString(context.getString(R.string.settings_weekly_budget_key), "350");
        return Integer.valueOf(s);
    }

    public static int getWeeklyAllowance(@NonNull Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String s = sharedPreferences.getString(context.getString(R.string.settings_weekly_allowance_key), "450");
        return Integer.valueOf(s);
    }
}
