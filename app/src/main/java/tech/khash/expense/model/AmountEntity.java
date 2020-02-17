package tech.khash.expense.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class AmountEntity {

    private int food, alcohol, weed, gas, accommodation, other, gear;
    private int total;
    private int week, month, year;
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
                case ExpenseEntity.ALCOHOL:
                    alcohol += entity.getAmount();
                case ExpenseEntity.WEED:
                    weed += entity.getAmount();
                case ExpenseEntity.GAS:
                    gas += entity.getAmount();
                case ExpenseEntity.ACCOMMODATION:
                    accommodation += entity.getAmount();
                case ExpenseEntity.OTHER:
                    other += entity.getAmount();
                case ExpenseEntity.GEAR:
                    gear += entity.getAmount();
                default:
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
}
