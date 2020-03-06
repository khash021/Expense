package tech.khash.expense.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;

import tech.khash.expense.R;
import tech.khash.expense.model.ExpenseEntity;
import tech.khash.expense.model.WeekEntity;
import tech.khash.expense.util.DialogUtil;
import tech.khash.expense.util.RealmUtil;
import tech.khash.expense.util.SharedPreferencesUtil;

public class WeekFragment extends Fragment {

    //TODO: add click listener

    private Context context;
    private WeekEntity week;
    private View v;

    public static Fragment newInstance(@NonNull Context context, @NonNull WeekEntity weekEntity) {
        WeekFragment fragment = new WeekFragment();
        fragment.context = context;
        fragment.week = weekEntity;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.content_main, container, false);

        initializeUpdateViews();

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetailedReport();
            }
        });
        return v;
    }

    private void showDetailedReport() {
        ArrayList<ExpenseEntity> expenseEntities = RealmUtil.getWeekExpensesAll(week.getWeek());
        //we want to show the week expense report in ascending order (from Monday to Sunday)
        Collections.reverse(expenseEntities);
        DialogUtil.ExpenseListDialog dialog = new DialogUtil.ExpenseListDialog(context, expenseEntities);
        dialog.show(getFragmentManager(), "Expense List Dialog");
    }

    private void initializeUpdateViews() {

        TextView weekTitleText = v.findViewById(R.id.text_title_date);

        TextView foodText = v.findViewById(R.id.text_food);
        TextView alcoholText = v.findViewById(R.id.text_alcohol);
        TextView weedText = v.findViewById(R.id.text_weed);
        TextView gasText = v.findViewById(R.id.text_gas);
        TextView accommText = v.findViewById(R.id.text_accommodation);
        TextView gearText = v.findViewById(R.id.text_gear);
        TextView otherText = v.findViewById(R.id.text_other);

        TextView totalText = v.findViewById(R.id.text_total);
        TextView remainingText = v.findViewById(R.id.text_remaining);
        TextView allowanceText = v.findViewById(R.id.text_allowance);

        LinearLayout foodContainer = v.findViewById(R.id.container_food);
        LinearLayout alcoholContainer = v.findViewById(R.id.container_alcohol);
        LinearLayout weedContainer = v.findViewById(R.id.container_weed);
        LinearLayout gasContainer = v.findViewById(R.id.container_gas);
        LinearLayout accommContainer = v.findViewById(R.id.container_accommodation);
        LinearLayout gearContainer = v.findViewById(R.id.container_gear);
        LinearLayout otherContainer = v.findViewById(R.id.container_other);

        ProgressBar progressBarBudget = v.findViewById(R.id.progress_bar_budget);

        String title = getString(R.string.main_report_title_date, week.getWeek(),
                week.getMondayStr());
        Spanned styledTitle = HtmlCompat.fromHtml(title, HtmlCompat.FROM_HTML_MODE_LEGACY);
        weekTitleText.setText(styledTitle);

        int food = week.getFood();
        if (food > 0) {
            foodContainer.setVisibility(View.VISIBLE);
            foodText.setText(String.valueOf(food));
        }

        int alcohol = week.getAlcohol();
        if (alcohol > 0) {
            alcoholContainer.setVisibility(View.VISIBLE);
            alcoholText.setText(String.valueOf(alcohol));
        }

        int weed = week.getWeed();
        if (weed > 0) {
            weedContainer.setVisibility(View.VISIBLE);
            weedText.setText(String.valueOf(weed));
        }

        int gas = week.getGas();
        if (gas > 0) {
            gasContainer.setVisibility(View.VISIBLE);
            gasText.setText(String.valueOf(gas));
        }

        int accommodation = week.getAccommodation();
        if (accommodation > 0) {
            accommContainer.setVisibility(View.VISIBLE);
            accommText.setText(String.valueOf(accommodation));
        }

        int other = week.getOther();
        if (other > 0) {
            otherContainer.setVisibility(View.VISIBLE);
            otherText.setText(String.valueOf(other));
        }

        int gear = week.getGear();
        if (gear > 0) {
            gearContainer.setVisibility(View.VISIBLE);
            gearText.setText(String.valueOf(gear));
        }

        int total = week.getTotal();
        totalText.setText(String.valueOf(total));

        int remaining = week.getBudgetRemaining(context);
        updateTextViewWithColor(remainingText, remaining);

        int allowance = week.getAllowanceRemaining(context);
        updateTextViewWithColor(allowanceText, allowance);

        updateProgressBar(progressBarBudget, total);

    }

    private void updateTextViewWithColor(@NonNull TextView textView, @NonNull int remaining) {
        if (remaining < 0) {
            textView.setText(String.valueOf(remaining));
            textView.setTextColor(context.getColor(R.color.red));
        } else if (remaining > 0) {
            textView.setText("+ " + remaining);
            textView.setTextColor(context.getColor(R.color.colorAccentTertiary));
        } else {
            textView.setText(String.valueOf(remaining));
            textView.setTextColor(context.getColor(R.color.colorAccentQuinary));
        }
    }

    private void updateProgressBar(@NonNull ProgressBar progressBarBudget, int total) {
        int percentTotal = getPercentTotal(total);
        progressBarBudget.setProgressDrawable(ContextCompat.getDrawable(context,
                R.drawable.progress_bar));
        if (percentTotal < 100) {
            progressBarBudget.setProgressDrawable(ContextCompat.getDrawable(context,
                    R.drawable.progress_bar));
            progressBarBudget.setProgress(percentTotal, true);
            progressBarBudget.setSecondaryProgress(100);
        } else {
            progressBarBudget.setProgressDrawable(ContextCompat.getDrawable(context,
                    R.drawable.progress_bar_red));
            progressBarBudget.setProgress(100, true);
        }
    }

    private int getPercentTotal(int total) {
        int weeklyBudget = SharedPreferencesUtil.getWeeklyBudget(context);
        float perc = (float) (total * 100) / weeklyBudget;
        int percInt = Math.round(perc);
        if (percInt >= 100)
            return 100;
        else
            return percInt;
    }
}
