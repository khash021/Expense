package tech.khash.realmtest.util;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import tech.khash.realmtest.model.ExpenseEntity;

public class CalculateUtil {

    /*
        ------------------------ Week -----------------------------------------
    */
    public static int getThisWeekTotal() {
        ArrayList<ExpenseEntity> expenses = RealmUtil.getThisWeekExpensesAll();
        if (CommonUtil.isEmptyList(expenses))
            return -1;

        int total = 0;
        for (ExpenseEntity expenseEntity : expenses) {
            total += expenseEntity.getAmount();
        }
        return total;
    }

    public static int getThisWeekTotalTyped(@NonNull @ExpenseEntity.ExpenseTypeInt int type) {
        ArrayList<ExpenseEntity> expenses = RealmUtil.getThisWeekExpensesTyped(type);
        if (CommonUtil.isEmptyList(expenses))
            return -1;

        int total = 0;
        for (ExpenseEntity expenseEntity : expenses) {
            total += expenseEntity.getAmount();
        }
        return total;
    }

    public static int getWeekTotal(@NonNull int week) {
        ArrayList<ExpenseEntity> expenses = RealmUtil.getWeekExpensesAll(week);
        if (CommonUtil.isEmptyList(expenses))
            return -1;

        int total = 0;
        for (ExpenseEntity expenseEntity : expenses) {
            total += expenseEntity.getAmount();
        }
        return total;
    }

    public static int getWeekTotalTyped(@NonNull int week, @NonNull @ExpenseEntity.ExpenseTypeInt int type) {
        ArrayList<ExpenseEntity> expenses = RealmUtil.getWeekExpensesTyped(week, type);
        if (CommonUtil.isEmptyList(expenses))
            return -1;

        int total = 0;
        for (ExpenseEntity expenseEntity : expenses) {
            total += expenseEntity.getAmount();
        }
        return total;
    }

    /*
        ------------------------ Month -----------------------------------------
    */
    public static int getThisMonthTotal() {
        ArrayList<ExpenseEntity> expenses = RealmUtil.getThisMonthExpensesAll();
        if (CommonUtil.isEmptyList(expenses))
            return -1;

        int total = 0;
        for (ExpenseEntity expenseEntity : expenses) {
            total += expenseEntity.getAmount();
        }
        return total;
    }

    public static int getThisMonthTotalTyped(@NonNull @ExpenseEntity.ExpenseTypeInt int type) {
        ArrayList<ExpenseEntity> expenses = RealmUtil.getThisMonthExpensesTyped(type);
        if (CommonUtil.isEmptyList(expenses))
            return -1;

        int total = 0;
        for (ExpenseEntity expenseEntity : expenses) {
            total += expenseEntity.getAmount();
        }
        return total;
    }

    public static int getMonthTotal(@NonNull int month) {
        ArrayList<ExpenseEntity> expenses = RealmUtil.getMonthExpensesAll(month);
        if (CommonUtil.isEmptyList(expenses))
            return -1;

        int total = 0;
        for (ExpenseEntity expenseEntity : expenses) {
            total += expenseEntity.getAmount();
        }
        return total;
    }

    public static int getMonthTotalTyped(@NonNull int month, @NonNull @ExpenseEntity.ExpenseTypeInt int type) {
        ArrayList<ExpenseEntity> expenses = RealmUtil.getMonthExpensesTyped(month, type);
        if (CommonUtil.isEmptyList(expenses))
            return -1;

        int total = 0;
        for (ExpenseEntity expenseEntity : expenses) {
            total += expenseEntity.getAmount();
        }
        return total;
    }

    /*
        ------------------------ Year -----------------------------------------
    */
    public static int getThisYearTotal() {
        ArrayList<ExpenseEntity> expenses = RealmUtil.getThisYearExpensesAll();
        if (CommonUtil.isEmptyList(expenses))
            return -1;

        int total = 0;
        for (ExpenseEntity expenseEntity : expenses) {
            total += expenseEntity.getAmount();
        }
        return total;
    }

    public static int getThisYearTotalTyped(@NonNull @ExpenseEntity.ExpenseTypeInt int type) {
        ArrayList<ExpenseEntity> expenses = RealmUtil.getThisYearExpensesTyped(type);
        if (CommonUtil.isEmptyList(expenses))
            return -1;

        int total = 0;
        for (ExpenseEntity expenseEntity : expenses) {
            total += expenseEntity.getAmount();
        }
        return total;
    }

    public static int getYearTotal(@NonNull int year) {
        ArrayList<ExpenseEntity> expenses = RealmUtil.getYearExpensesAll(year);
        if (CommonUtil.isEmptyList(expenses))
            return -1;

        int total = 0;
        for (ExpenseEntity expenseEntity : expenses) {
            total += expenseEntity.getAmount();
        }
        return total;
    }

    public static int getYearTotalTyped(@NonNull int year, @NonNull @ExpenseEntity.ExpenseTypeInt int type) {
        ArrayList<ExpenseEntity> expenses = RealmUtil.getYearExpensesTyped(year, type);
        if (CommonUtil.isEmptyList(expenses))
            return -1;

        int total = 0;
        for (ExpenseEntity expenseEntity : expenses) {
            total += expenseEntity.getAmount();
        }
        return total;
    }
}
