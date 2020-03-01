package tech.khash.expense;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

import tech.khash.expense.base.BaseActivity;
import tech.khash.expense.model.WeekEntity;
import tech.khash.expense.model.ExpenseEntity;
import tech.khash.expense.util.RealmUtil;

public class ChartActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        PieChart chart = findViewById(R.id.chart);

        ArrayList<ExpenseEntity> expenseEntities = RealmUtil.getThisWeekExpensesAll();
        WeekEntity amountEntity = new WeekEntity(expenseEntities);
        ArrayList<PieEntry> entries = new ArrayList<>();

//        PieEntry foodEntry = new PieEntry(amountEntity.getFood(),
//                getString(R.string.expense_type_food), getDrawable(R.drawable.ic_food_white));
        PieEntry foodEntry = new PieEntry(amountEntity.getFood(), getDrawable(R.drawable.ic_food_white));
        entries.add(foodEntry);

//        PieEntry alcoholEntry = new PieEntry(amountEntity.getFood(),
//                getString(R.string.expense_type_alcohol), getDrawable(R.drawable.ic_alcohol_white));
//        entries.add(alcoholEntry);
//
//        PieEntry weedEntry = new PieEntry(amountEntity.getFood(),
//                getString(R.string.expense_type_weed), getDrawable(R.drawable.ic_weed_white));
//        entries.add(weedEntry);

//        PieEntry gasEntry = new PieEntry(amountEntity.getFood(),
//                getString(R.string.expense_type_gas), getDrawable(R.drawable.ic_gas_white));
        PieEntry gasEntry = new PieEntry(amountEntity.getFood(), getDrawable(R.drawable.ic_gas_white));
        entries.add(gasEntry);

//        PieEntry accommEntry = new PieEntry(amountEntity.getFood(),
//                getString(R.string.expense_type_accommodation), getDrawable(R.drawable.ic_accomm_white));
//        entries.add(accommEntry);
//
//        PieEntry otherEntry = new PieEntry(amountEntity.getFood(),
//                getString(R.string.expense_type_other), getDrawable(R.drawable.ic_other_white));
//        entries.add(otherEntry);

//        PieEntry gearEntry = new PieEntry(amountEntity.getFood(),
//                getString(R.string.expense_type_gear), getDrawable(R.drawable.ic_gear_white));
        PieEntry gearEntry = new PieEntry(amountEntity.getFood(), getDrawable(R.drawable.ic_gear_white));
        entries.add(gearEntry);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getColor(R.color.red));
        //colors.add(R.color.red);
        colors.add(getColor(R.color.green));
        //colors.add(R.color.colorAccentOctonary);
        colors.add(getColor(R.color.blue_argb));
        //colors.add(R.color.background_light);
        //colors.add(R.color.blue_argb);

        chart.setUsePercentValues(false);


        PieDataSet dataSet = new PieDataSet(entries, getString(R.string.this_week_chart));

        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        chart.animateY(1000);
        chart.setData(pieData);

    }
}
