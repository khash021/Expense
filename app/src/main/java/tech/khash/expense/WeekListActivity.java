package tech.khash.expense;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import tech.khash.expense.adapter.WeekPagerAdapter;
import tech.khash.expense.base.BaseActivity;
import tech.khash.expense.model.WeekEntity;
import tech.khash.expense.util.WeekUtil;

public class WeekListActivity extends BaseActivity {

    //TODO: onclick show the list of expenses in a list dialog

    private ViewPager viewPager;
    private ArrayList<WeekEntity> weeks;
    private WeekPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_list_layout);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        weeks = WeekUtil.getAllWeeksAscending(this);

        viewPager = findViewById(R.id.view_pager);
        pagerAdapter = new WeekPagerAdapter(getSupportFragmentManager(), this, weeks);
        viewPager.setAdapter(pagerAdapter);

    }
}
