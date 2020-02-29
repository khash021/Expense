package tech.khash.expense.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tech.khash.expense.R;
import tech.khash.expense.model.AmountEntity;
import tech.khash.expense.util.CommonUtil;

public class WeekRecyclerAdapter extends RecyclerView.Adapter<WeekRecyclerAdapter.WeekViewHolder>{

    private ArrayList<AmountEntity> weeks;
    private LayoutInflater inflater;

    public WeekRecyclerAdapter(Context context, ArrayList<AmountEntity> weeks) {
        inflater =  LayoutInflater.from(context);
        this.weeks = weeks;
    }
    @NonNull
    @Override
    public WeekRecyclerAdapter.WeekViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = inflater.inflate(R.layout.content_main, parent, false);
        return new WeekViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekRecyclerAdapter.WeekViewHolder holder, int position) {
        AmountEntity week = weeks.get(position);



    }

    @Override
    public int getItemCount() {
        if (CommonUtil.isEmptyList(weeks))
            return 0;
        else
            return weeks.size();
    }

    class WeekViewHolder extends RecyclerView.ViewHolder {
        private TextView weekTitleText, foodText, alcoholText, weedText, gasText, accommText, gearText,
                otherText, totalText, toolbarText, remainingText;
        private LinearLayout foodContainer, alcoholContainer, weedContainer, gasContainer,
                accommContainer, gearContainer, otherContainer;
        private ProgressBar progressBarBudget;

        public WeekViewHolder (@NonNull View v) {
            super(v);

            weekTitleText = v.findViewById(R.id.text_title_date);
            foodText = v.findViewById(R.id.text_food);
            alcoholText = v.findViewById(R.id.text_alcohol);
            weedText = v.findViewById(R.id.text_weed);
            gasText = v.findViewById(R.id.text_gas);
            accommText = v.findViewById(R.id.text_accommodation);
            gearText = v.findViewById(R.id.text_gear);
            otherText = v.findViewById(R.id.text_other);
            totalText = v.findViewById(R.id.text_total);
            toolbarText = v.findViewById(R.id.text_toolbar_report_title);
            remainingText = v.findViewById(R.id.text_remaining);

            foodContainer = v.findViewById(R.id.container_food);
            alcoholContainer = v.findViewById(R.id.container_alcohol);
            weedContainer = v.findViewById(R.id.container_weed);
            gasContainer = v.findViewById(R.id.container_gas);
            accommContainer = v.findViewById(R.id.container_accommodation);
            gearContainer = v.findViewById(R.id.container_gear);
            otherContainer = v.findViewById(R.id.container_other);

            progressBarBudget = v.findViewById(R.id.progress_bar_budget);
        }
    }
}
