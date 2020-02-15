package tech.khash.realmtest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import tech.khash.realmtest.R;
import tech.khash.realmtest.model.Constants;

public class ExpenseTypeAdapter extends RecyclerView.Adapter<ExpenseTypeAdapter.ExpenseTypeViewHolder> {
    private final String[] expenses = new String[7];
    private LayoutInflater inflater;
    public ListClickListener listClickListener;

    public ExpenseTypeAdapter(@NonNull Context context, @NonNull ListClickListener listClickListener) {
        this.listClickListener = listClickListener;
        inflater = LayoutInflater.from(context);
        expenses[0] = Constants.FOOD;
        expenses[1] = Constants.ALCOHOL;
        expenses[2] = Constants.WEED;
        expenses[3] = Constants.GAS;
        expenses[4] = Constants.ACCOMMODATION;
        expenses[5] = Constants.OTHER;
        expenses[6] = Constants.GEAR;
    }

    @NonNull
    @Override
    public ExpenseTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = inflater.inflate(R.layout.expense_type_list_item, parent, false);
        return new ExpenseTypeViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseTypeViewHolder holder, int position) {
        String expenseTypeStr = expenses[position];
        holder.typeText.setText(expenseTypeStr);
    }

    @Override
    public int getItemCount() {
        return expenses.length;
    }

    public interface ListClickListener {
        void onListItemClick(int position);
    }

    class ExpenseTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView typeText;

        private ExpenseTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            typeText = itemView.findViewById(R.id.text_expense_item);
            typeText.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            listClickListener.onListItemClick(position);
        }
    }
}
