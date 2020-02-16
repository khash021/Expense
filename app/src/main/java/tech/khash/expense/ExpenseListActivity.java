package tech.khash.expense;

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
import tech.khash.expense.base.BaseApp;
import tech.khash.expense.model.ExpenseEntity;

public class ExpenseListActivity extends BaseApp {

    private ArrayList<ExpenseEntity> expenseEntities;

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
        ExpenseEntityRecyclerAdapter adapter = new ExpenseEntityRecyclerAdapter(this,
                expenseEntities, null);
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
}