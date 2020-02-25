package tech.khash.expense.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import tech.khash.expense.util.CommonUtil;

public class AmountEntity {

    private int food, alcohol, weed, gas, accommodation, other, gear;
    private int total;
    private Integer week, month, year;
    private String mondayStr;
    private ArrayList<ExpenseEntity> expenses;
    //TODO: add different constructors for various periods

    public AmountEntity(@NonNull ArrayList<ExpenseEntity> expenses) {
        this.expenses = expenses;

        food = 0;
        alcohol = 0;
        weed = 0;
        gas = 0;
        accommodation = 0;
        other = 0;
        gear = 0;

        for (ExpenseEntity entity : expenses) {
            switch (entity.getType()) {
                case ExpenseEntity.FOOD:
                    food += entity.getAmount();
                    break;
                case ExpenseEntity.ALCOHOL:
                    alcohol += entity.getAmount();
                    break;
                case ExpenseEntity.WEED:
                    weed += entity.getAmount();
                    break;
                case ExpenseEntity.GAS:
                    gas += entity.getAmount();
                    break;
                case ExpenseEntity.ACCOMMODATION:
                    accommodation += entity.getAmount();
                    break;
                case ExpenseEntity.OTHER:
                    other += entity.getAmount();
                    break;
                case ExpenseEntity.GEAR:
                    gear += entity.getAmount();
                    break;
                default:
                    break;
            }
        }
        total = food + alcohol + weed + gas + accommodation + other + gear;
    }

    public AmountEntity(@NonNull ArrayList<ExpenseEntity> expenses, int week, String monday) {
        this.expenses = expenses;
        this.week = week;
        mondayStr = monday;

        food = 0;
        alcohol = 0;
        weed = 0;
        gas = 0;
        accommodation = 0;
        other = 0;
        gear = 0;

        for (ExpenseEntity entity : expenses) {
            switch (entity.getType()) {
                case ExpenseEntity.FOOD:
                    food += entity.getAmount();
                    break;
                case ExpenseEntity.ALCOHOL:
                    alcohol += entity.getAmount();
                    break;
                case ExpenseEntity.WEED:
                    weed += entity.getAmount();
                    break;
                case ExpenseEntity.GAS:
                    gas += entity.getAmount();
                    break;
                case ExpenseEntity.ACCOMMODATION:
                    accommodation += entity.getAmount();
                    break;
                case ExpenseEntity.OTHER:
                    other += entity.getAmount();
                    break;
                case ExpenseEntity.GEAR:
                    gear += entity.getAmount();
                    break;
                default:
                    break;
            }
        }
        total = food + alcohol + weed + gas + accommodation + other + gear;
    }

    public int getFood() {
        return food;
    }

    public int getAlcohol() {
        return alcohol;
    }

    public int getWeed() {
        return weed;
    }

    public int getGas() {
        return gas;
    }

    public int getAccommodation() {
        return accommodation;
    }

    public int getOther() {
        return other;
    }

    public int getGear() {
        return gear;
    }

    public int getTotal() {
        return total;
    }

    public int getRemaining(@NonNull int weeklyBudget) {
        return weeklyBudget - total;
    }

    public Integer getWeek() {
        if (week == null)
            return -1;
        else
            return week;
    }

    public Integer getMonth() {
        if (month == null)
            return -1;
        else
            return month;
    }

    public Integer getYear() {
        if (year == null)
            return -1;
        else
            return year;
    }

    public String getMondayStr() {
        if (mondayStr == null)
            return CommonUtil.getEmptyString();
        else
            return mondayStr;
    }
}
