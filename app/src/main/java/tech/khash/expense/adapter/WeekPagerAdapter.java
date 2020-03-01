package tech.khash.expense.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import tech.khash.expense.fragment.WeekFragment;
import tech.khash.expense.model.WeekEntity;

public class WeekPagerAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private ArrayList<WeekEntity> weeks;

    public WeekPagerAdapter(@NonNull FragmentManager fm, @NonNull Context context, @NonNull ArrayList<WeekEntity> weeks) {
        super(fm);
        this.context = context;
        this.weeks = weeks;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return WeekFragment.newInstance(context, weeks.get(position));
    }

    @Override
    public int getCount() {
        return weeks.size();
    }
}
