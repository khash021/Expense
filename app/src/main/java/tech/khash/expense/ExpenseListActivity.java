package tech.khash.expense;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import tech.khash.expense.adapter.ExpenseEntityRecyclerAdapter;
import tech.khash.expense.base.BaseActivity;
import tech.khash.expense.model.Constants;
import tech.khash.expense.model.ExpenseEntity;
import tech.khash.expense.util.CommonUtil;
import tech.khash.expense.util.DialogUtil;

public class ExpenseListActivity extends BaseActivity implements ExpenseEntityRecyclerAdapter.ListClickListener {

    private ArrayList<ExpenseEntity> expenseEntities;
    private ExpenseEntityRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        getExpenseEntities();

        //reverse list so the newest is at top
        Collections.reverse(expenseEntities);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new ExpenseEntityRecyclerAdapter(this,
                expenseEntities, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,
                false));
        DividerItemDecoration decoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(decoration);
    }

    private void getExpenseEntities() {
        expenseEntities = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
        RealmResults<ExpenseEntity> results = query.findAll();
        expenseEntities.addAll(results);
    }

    @Override
    public void onExpenseListClick(int position) {
        try {
            ExpenseEntity expenseEntity = expenseEntities.get(position);
            long epoch = expenseEntity.getEpoch();
            //TODO: go to edit
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onExpenseListLongClick(int position) {
        try {
            ExpenseEntity expenseEntity = expenseEntities.get(position);
            final long epoch = expenseEntity.getEpoch();
            DialogUtil.showExpenseDeleteEditDialog(this, new DialogUtil.ExpenseListDialogListener() {
                @Override
                public void onEditSelected() {
                    Intent editIntent = new Intent(ExpenseListActivity.this, AddNewExpenseActivity.class);
                    editIntent.putExtra(Constants.EDIT_EXPENSE_EXTRA_EPOCH, epoch);
                    startActivity(editIntent);
                }

                @Override
                public void onDeleteSelected() {
                    DialogUtil.showSingleExpenseDeleteConfirmationDialog(ExpenseListActivity.this,
                            new DialogUtil.DeleteDialogListener() {
                        @Override
                        public void onDeleteSelected() {
                            //TODO: delete
                            CommonUtil.showToastShort(getApplicationContext(), "Delete");
                        }

                        @Override
                        public void onCancelSelected() {
                            CommonUtil.showToastShort(getApplicationContext(), "Cancel");
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
