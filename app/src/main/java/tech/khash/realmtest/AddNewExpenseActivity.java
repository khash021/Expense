package tech.khash.realmtest;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import io.realm.Realm;
import tech.khash.realmtest.base.BaseApp;
import tech.khash.realmtest.model.Constants;
import tech.khash.realmtest.model.ExpenseEntity;
import tech.khash.realmtest.util.CommonUtil;
import tech.khash.realmtest.util.DialogUtil;
import tech.khash.realmtest.util.RealmUtil;

public class AddNewExpenseActivity extends BaseApp implements View.OnClickListener,
        AdapterView.OnItemSelectedListener, DialogUtil.ExpenseTypeDialog.ExpenseTypeDialogListener {

    private Button buttonSubmit;
    private EditText editAmount, editComment;
    private LinearLayout typeContainer;
    private Spinner spinner;
    private int currentType = -1;
    private ActionBar actionBar;

    //TODO: make button and actionbar reactive to be disabled and enabled dynamically

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_expense);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.ADD_NEW_EXPENSE_EXTRA_TYPE)) {
            int type = intent.getIntExtra(Constants.ADD_NEW_EXPENSE_EXTRA_TYPE, -1);
            setExpenseType(type);
        }
        actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        initializeViews();
        setupPage(currentType);
    }

    private void initializeViews() {
        typeContainer = findViewById(R.id.container_type);

        buttonSubmit = findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(this);

        editAmount = findViewById(R.id.edit_amount);
        editComment = findViewById(R.id.edit_comment);

        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        editAmount.requestFocus();
        showSoftKeyboard();
    }

    private void setupPage(int currentType) {
        if (typeContainer != null)
            typeContainer.setVisibility(View.GONE);
        switch (currentType) {
            case ExpenseEntity.FOOD:
                if (actionBar != null)
                    actionBar.setTitle(R.string.add_new_expense_food);
                //TODO:
                return;
            case ExpenseEntity.ALCOHOL:
                if (actionBar != null)
                    actionBar.setTitle(R.string.add_new_expense_alcohol);
                //TODO:
                return;
            case ExpenseEntity.WEED:
                if (actionBar != null)
                    actionBar.setTitle(R.string.add_new_expense_weed);
                //TODO:
                return;
            case ExpenseEntity.GAS:
                if (actionBar != null)
                    actionBar.setTitle(R.string.add_new_expense_gas);
                //TODO:
                return;
            case ExpenseEntity.ACCOMMODATION:
                if (actionBar != null)
                    actionBar.setTitle(R.string.add_new_expense_accommodation);
                //TODO:
                return;
            case ExpenseEntity.GEAR:
                if (actionBar != null)
                    actionBar.setTitle(R.string.add_new_expense_gear);
                //TODO:
                return;
            case ExpenseEntity.OTHER:
                if (actionBar != null)
                    actionBar.setTitle(R.string.add_new_expense_other);
                //TODO:
                return;
            default:
                if (typeContainer != null)
                    typeContainer.setVisibility(View.VISIBLE);
                return;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_submit:
                if (checkFields())
                    submitExpense();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_expense, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_submit_expense:
                if (checkFields())
                    submitExpense();
                return true;
            case R.id.action_switch_type:
                showSwitchTypeDialog();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSwitchTypeDialog() {
        DialogUtil.ExpenseTypeDialog dialog = new DialogUtil.ExpenseTypeDialog(this,
                this);
        dialog.show(getSupportFragmentManager(), "Expense Type Dialog");
    }

    private boolean checkFields() {
        String error = null;

        if (currentType == -1) {
            error = "Expense type is required\n";
        }

        if (TextUtils.isEmpty(editAmount.getText().toString()) || editAmount.getText().toString().trim().equalsIgnoreCase("0")) {
            error += "Amount is required and cannot be 0";
        }

        if (error == null)
            return true;
        else {
            CommonUtil.showToastShort(this, error);
            return false;
        }
    }

    private void submitExpense() {
        int amount = Integer.valueOf(editAmount.getText().toString());
        String comment = editComment.getText().toString().trim();
        int expenseType = getExpenseType(currentType);
        if (amount == 0 || currentType == -1) {
            CommonUtil.showToastShort(this, getResources().getString(R.string.please_complete_all_fields));
            return;
        }

        ExpenseEntity expenseEntity = new ExpenseEntity(expenseType, amount, comment);
        RealmUtil.insertUpdateExpenseToRealm(expenseEntity, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                CommonUtil.showToastShort(AddNewExpenseActivity.this,
                        getResources().getString(R.string.submitted_successfully));
                setResult(RESULT_OK);
                hideSoftKeyboard();
                finish();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                CommonUtil.showToastShort(AddNewExpenseActivity.this,
                        getResources().getString(R.string.submitted_failure));
            }
        });
    }

    private void setExpenseType(int type) {
        if (type == -1)
            return;
        switch (type) {
            case ExpenseEntity.FOOD:
                currentType = ExpenseEntity.FOOD;
                return;
            case ExpenseEntity.ALCOHOL:
                currentType = ExpenseEntity.ALCOHOL;
                return;
            case ExpenseEntity.WEED:
                currentType = ExpenseEntity.WEED;
                return;
            case ExpenseEntity.GAS:
                currentType = ExpenseEntity.GAS;
                return;
            case ExpenseEntity.ACCOMMODATION:
                currentType = ExpenseEntity.ACCOMMODATION;
                return;
            case ExpenseEntity.GEAR:
                currentType = ExpenseEntity.GEAR;
                return;
            case ExpenseEntity.OTHER:
                currentType = ExpenseEntity.OTHER;
                return;
            default:
                currentType = -1;
                return;
        }
    }

    private int getExpenseType(int type) {
        switch (type) {
            case -1:
            default:
                return -1;
            case 1:
                return ExpenseEntity.FOOD;
            case 2:
                return ExpenseEntity.ALCOHOL;
            case 3:
                return ExpenseEntity.WEED;
            case 4:
                return ExpenseEntity.GAS;
            case 5:
                return ExpenseEntity.ACCOMMODATION;
            case 6:
                return ExpenseEntity.OTHER;
            case 7:
                return ExpenseEntity.GEAR;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
            default:
                currentType = -1;
                break;
            case 1:
                currentType = ExpenseEntity.FOOD;
                break;
            case 2:
                currentType = ExpenseEntity.ALCOHOL;
                break;
            case 3:
                currentType = ExpenseEntity.WEED;
                break;
            case 4:
                currentType = ExpenseEntity.GAS;
                break;
            case 5:
                currentType = ExpenseEntity.ACCOMMODATION;
                break;
            case 6:
                currentType = ExpenseEntity.OTHER;
                break;
            case 7:
                currentType = ExpenseEntity.GEAR;
                break;
        }
    }

    @Override
    public void onExpenseSelected(int expenseType) {
        switch (expenseType) {
            case 0:
            default:
                currentType = -1;
                break;
            case 1:
                currentType = ExpenseEntity.FOOD;
                break;
            case 2:
                currentType = ExpenseEntity.ALCOHOL;
                break;
            case 3:
                currentType = ExpenseEntity.WEED;
                break;
            case 4:
                currentType = ExpenseEntity.GAS;
                break;
            case 5:
                currentType = ExpenseEntity.ACCOMMODATION;
                break;
            case 6:
                currentType = ExpenseEntity.OTHER;
                break;
            case 7:
                currentType = ExpenseEntity.GEAR;
                break;
        }
        setupPage(currentType);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onDismiss() {
    }
}
