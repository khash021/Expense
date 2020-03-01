package tech.khash.expense.util;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;

import tech.khash.expense.model.ExpenseEntity;
import tech.khash.expense.model.WeekEntity;

public class WeekUtil {

    public static ArrayList<WeekEntity> getAllWeeksDescending(@NonNull Context context) {
        ArrayList<ExpenseEntity> expenses = RealmUtil.getAllExpensesAscending();

        ArrayList<WeekEntity> weeks = convertEntitiesToWeeks(expenses);

        return weeks;
    }

    public static ArrayList<WeekEntity> getAllWeeksAscending(@NonNull Context context) {
        ArrayList<ExpenseEntity> expenses = RealmUtil.getAllExpensesAscending();

        ArrayList<WeekEntity> weeks = convertEntitiesToWeeks(expenses);
        Collections.reverse(weeks);

        return weeks;
    }

    private static ArrayList<WeekEntity> convertEntitiesToWeeks(@NonNull ArrayList<ExpenseEntity> expenseEntities) {
        int firstWeek = RealmUtil.getFirstWeekCountInt();
        int lastWeek = RealmUtil.getLastWeekCountInt();

        ArrayList<ArrayList<ExpenseEntity>> expensesArrayList = new ArrayList<>();

        //if we only have one week, the result of first - last would be zero, we just simply add one
        for (int i = firstWeek; i < (lastWeek + 1); i++) {
            ArrayList<ExpenseEntity> weekExpense = RealmUtil.getWeekExpensesAll(i);
            expensesArrayList.add(weekExpense);
        }

        ArrayList<WeekEntity> weeks = new ArrayList<>();
        for (ArrayList<ExpenseEntity> expenseEntities1 : expensesArrayList) {
            int week = expenseEntities1.get(0).getWeekOfTheYear();
            String mondayStr = expenseEntities1.get(0).getMondayDateTimeStr();
            WeekEntity weekEntity = new WeekEntity(expenseEntities1, week, mondayStr);
            weeks.add(weekEntity);

        }

        return weeks;
    }
}
