package tech.khash.expense.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        holder.typeText.setText(expenseEntity.getExpenseTypeStr());
        holder.dateText.setText(expenseEntity.getTodayDateTimeStr());
        holder.monthText.setText(String.valueOf(expenseEntity.getMonthOfTheYear()));
        holder.weekText.setText(String.valueOf(expenseEntity.getWeekOfTheYear()));
        holder.commentText.setText(expenseEntity.getComment());
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

        final TextView typeText, amountText, weekText, monthText, dateText, commentText;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);

            typeText = itemView.findViewById(R.id.text_type);
            amountText = itemView.findViewById(R.id.text_amount);
            weekText = itemView.findViewById(R.id.text_week);
            monthText = itemView.findViewById(R.id.text_month);
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
