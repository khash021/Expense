package tech.khash.expense.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tech.khash.expense.R;
import tech.khash.expense.model.ExpenseEntity;

public class ExpenseEntityRecyclerAdapter extends RecyclerView.Adapter<ExpenseEntityRecyclerAdapter.ExpenseViewHolder> {

    //list of data
    private final ArrayList<ExpenseEntity> expenses;
    private LayoutInflater inflater;


    private ListClickListener listClickListener;

    public interface ListClickListener {
        void onExpenseListClick(int position);

        void onExpenseListLongClick(int position);
    }

    public ExpenseEntityRecyclerAdapter(Context context, ArrayList<ExpenseEntity> expenses,
                                        ListClickListener clickListener) {
        this.listClickListener = clickListener;
        this.expenses = expenses;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = inflater.inflate(R.layout.expense_list_item, parent, false);
        return new ExpenseViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        ExpenseEntity expenseEntity = expenses.get(position);

        holder.amountText.setText(String.valueOf(expenseEntity.getAmount()));
        holder.dateText.setText(expenseEntity.getTodayDateTimeStr());
        holder.weekText.setText(String.valueOf(expenseEntity.getWeekOfTheYear()));
        String comment = expenseEntity.getComment();
        if (!TextUtils.isEmpty(comment)) {
            holder.commentText.setVisibility(View.VISIBLE);
            holder.commentText.setText(expenseEntity.getComment());
        }

        switch (expenseEntity.getType()) {
            case ExpenseEntity.FOOD:
                holder.imageView.setImageResource(R.drawable.food_header);
                return;
            case ExpenseEntity.ALCOHOL:
                holder.imageView.setImageResource(R.drawable.alcohol_header);
                return;
            case ExpenseEntity.WEED:
                holder.imageView.setImageResource(R.drawable.weed_header);
                return;
            case ExpenseEntity.GAS:
                holder.imageView.setImageResource(R.drawable.gas_header);
                return;
            case ExpenseEntity.ACCOMMODATION:
                holder.imageView.setImageResource(R.drawable.accomm_header);
                return;
            case ExpenseEntity.GEAR:
                holder.imageView.setImageResource(R.drawable.gear_header);
                return;
            case ExpenseEntity.OTHER:
                holder.imageView.setImageResource(R.drawable.other_header);
                return;
            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        if (expenses == null)
            return 0;
        else
            return expenses.size();
    }

    class ExpenseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {

        final TextView amountText, weekText, dateText, commentText;
        final ImageView imageView;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            amountText = itemView.findViewById(R.id.text_amount);
            weekText = itemView.findViewById(R.id.text_week);
            dateText = itemView.findViewById(R.id.text_date);
            commentText = itemView.findViewById(R.id.text_comment);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            listClickListener.onExpenseListClick(position);
        }

        @Override
        public boolean onLongClick(View v) {
            int position = getLayoutPosition();
            listClickListener.onExpenseListLongClick(position);
            return true;
        }
    }
}
