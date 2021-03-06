package tech.khash.expense;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import java.util.HashMap;

import io.realm.Realm;
import tech.khash.expense.base.BaseActivity;
import tech.khash.expense.model.Constants;
import tech.khash.expense.model.ExpenseEntity;
import tech.khash.expense.util.CommonUtil;
import tech.khash.expense.util.DialogUtil;
import tech.khash.expense.util.RealmUtil;

public class AddNewExpenseActivity extends BaseActivity implements View.OnClickListener,
        DialogUtil.ExpenseTypeDialog.ExpenseTypeDialogListener {

    private Button buttonSubmit;
    private EditText editAmount, editComment;
    private ImageView imageExpenseType;
    private int currentType = -1;
    private ActionBar actionBar;
    private boolean isEditMode = false;
    private ExpenseEntity editEntity;
    private View expenseOverlay;
    private HashMap<String, Object> initialValuesMap;

    //TODO: make button and actionbar reactive to be disabled and enabled dynamically

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_expense);
        initializeViews();

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.ADD_NEW_EXPENSE_EXTRA_TYPE)) {
            int type = intent.getIntExtra(Constants.ADD_NEW_EXPENSE_EXTRA_TYPE, -1);
            setExpenseType(type);
        } else if (intent != null && intent.hasExtra(Constants.ADD_NEW_EXPENSE_APP_SHORTCUT) &&
                intent.getBooleanExtra(Constants.ADD_NEW_EXPENSE_APP_SHORTCUT, false)) {
            showAppShortcutExpenseTypeOverlay();
        } else if (intent != null && intent.hasExtra(Constants.EDIT_EXPENSE_EXTRA_EPOCH)) {
            isEditMode = true;
            long epoch = intent.getLongExtra(Constants.EDIT_EXPENSE_EXTRA_EPOCH, -1);
            if (epoch != -1) {
                editEntity = RealmUtil.getExpenseEntityEpoch(epoch);
                currentType = editEntity.getType();
            }
        }
        actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        //setupPage(currentType);

        if (isEditMode)
            setupEditMode();
        else
            saveInitialValues();
    }

    private void showAppShortcutExpenseTypeOverlay() {
        expenseOverlay.setVisibility(View.VISIBLE);
        findViewById(R.id.overlay_food_container).setOnClickListener(
                v -> setExpenseType(ExpenseEntity.FOOD));
        findViewById(R.id.overlay_alcohol_container).setOnClickListener(
                v -> setExpenseType(ExpenseEntity.ALCOHOL));
        findViewById(R.id.overlay_weed_container).setOnClickListener(
                v -> setExpenseType(ExpenseEntity.WEED));
        findViewById(R.id.overlay_gas_container).setOnClickListener(
                v -> setExpenseType(ExpenseEntity.GAS));
        findViewById(R.id.overlay_accom_container).setOnClickListener(
                v -> setExpenseType(ExpenseEntity.ACCOMMODATION));
        findViewById(R.id.overlay_gear_container).setOnClickListener(
                v -> setExpenseType(ExpenseEntity.GEAR));
        findViewById(R.id.overlay_other_container).setOnClickListener(
                v -> setExpenseType(ExpenseEntity.OTHER));

        showFullScreen();
    }

    private void showFullScreen() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE);

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
    }

    private void removeFullScreen() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

        if (getSupportActionBar() != null)
            getSupportActionBar().show();
    }

    private void initializeViews() {
        expenseOverlay = findViewById(R.id.expense_overlay);
        imageExpenseType = findViewById(R.id.image_expense_type);
        imageExpenseType.setOnClickListener(this);

        buttonSubmit = findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(this);

        editAmount = findViewById(R.id.edit_amount);
        editComment = findViewById(R.id.edit_comment);

        View.OnKeyListener keyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    hideSoftKeyboard();
                    ((EditText) v).setCursorVisible(false);
                    return true;
                }
                return false;
            }
        };

        View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    ((EditText) v).setCursorVisible(true);
                else
                    ((EditText) v).setCursorVisible(false);
            }
        };

        editAmount.setOnKeyListener(keyListener);
        editAmount.setOnFocusChangeListener(focusChangeListener);

        editComment.setOnKeyListener(keyListener);
        editComment.setOnFocusChangeListener(focusChangeListener);
    }

    private void setupPage(int currentType) {
        imageExpenseType.setVisibility(View.VISIBLE);
        switch (currentType) {
            case ExpenseEntity.FOOD:
                if (actionBar != null)
                    actionBar.setTitle(!isEditMode ? R.string.add_new_expense_food : R.string.edit_expense_food);
                imageExpenseType.setImageResource(R.drawable.food_header);
                return;
            case ExpenseEntity.ALCOHOL:
                if (actionBar != null)
                    actionBar.setTitle(!isEditMode ? R.string.add_new_expense_alcohol : R.string.edit_expense_alcohol);
                imageExpenseType.setImageResource(R.drawable.alcohol_header);
                return;
            case ExpenseEntity.WEED:
                if (actionBar != null)
                    actionBar.setTitle(!isEditMode ? R.string.add_new_expense_weed : R.string.edit_expense_weed);
                imageExpenseType.setImageResource(R.drawable.weed_header);
                return;
            case ExpenseEntity.GAS:
                if (actionBar != null)
                    actionBar.setTitle(!isEditMode ? R.string.add_new_expense_gas : R.string.edit_expense_gas);
                imageExpenseType.setImageResource(R.drawable.gas_header);
                return;
            case ExpenseEntity.ACCOMMODATION:
                if (actionBar != null)
                    actionBar.setTitle(!isEditMode ? R.string.add_new_expense_accommodation : R.string.edit_expense_accommodation);
                imageExpenseType.setImageResource(R.drawable.accomm_header);
                return;
            case ExpenseEntity.GEAR:
                if (actionBar != null)
                    actionBar.setTitle(!isEditMode ? R.string.add_new_expense_gear : R.string.edit_expense_gear);
                imageExpenseType.setImageResource(R.drawable.gear_header);
                return;
            case ExpenseEntity.OTHER:
                if (actionBar != null)
                    actionBar.setTitle(!isEditMode ? R.string.add_new_expense_other : R.string.edit_expense_other);
                imageExpenseType.setImageResource(R.drawable.other_header);
                return;
            default:
                imageExpenseType.setVisibility(View.GONE);
                return;
        }
    }

    private void setupEditMode() {
        int amount = editEntity.getAmount();
        editAmount.setText(String.valueOf(amount));

        String comment = editEntity.getComment();
        editComment.setText(comment);

        selectEditTextEnd(editAmount);

        saveInitialValues();
    }

    private void saveInitialValues() {
        if (initialValuesMap != null)
            return;
        initialValuesMap = new HashMap<>();
        initialValuesMap.put(Constants.INITIAL_VALUES_TYPE, currentType);
        initialValuesMap.put(Constants.INITIAL_VALUES_COMMENT, editComment.getText().toString());

        String amountStr = editAmount.getText().toString();
        int amount;
        if (TextUtils.isEmpty(amountStr))
            amount = -1;
        else
            amount = Integer.valueOf(amountStr);

        initialValuesMap.put(Constants.INITIAL_VALUES_AMOUNT, amount);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_submit:
                if (checkFields())
                    submitExpense();
                break;
            case R.id.image_expense_type:
                showSwitchTypeDialog();
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
                return true;
            case android.R.id.home:
                exitWithConfirmation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void exitWithConfirmation() {
        if (!hasUnsavedChanged())
            finish();
        else
            showExitConfirmationDialog();
    }

    private boolean hasUnsavedChanged() {
        if (initialValuesMap == null)
            return false;
        String amountStr = editAmount.getText().toString();
        int currentAmount;
        if (TextUtils.isEmpty(amountStr))
            currentAmount = -1;
        else
            currentAmount = Integer.valueOf(amountStr);

        String currentComment = editComment.getText().toString();

        int prevAmount = (int) initialValuesMap.get(Constants.INITIAL_VALUES_AMOUNT);
        String prevComment = (String) initialValuesMap.get(Constants.INITIAL_VALUES_COMMENT);
        int prevType = (int) initialValuesMap.get(Constants.INITIAL_VALUES_TYPE);

        if (prevAmount != currentAmount)
            return true;

        if (prevType != currentType)
            return true;

        if (!prevComment.equalsIgnoreCase(currentComment))
            return true;

        return false;
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
            CommonUtil.showToastShort(this, getString(R.string.please_complete_all_fields));
            return;
        }

        if (!isEditMode) {
            ExpenseEntity expenseEntity = new ExpenseEntity(expenseType, amount, comment);
            RealmUtil.insertUpdateExpenseToRealm(expenseEntity, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    CommonUtil.showToastShort(AddNewExpenseActivity.this,
                            getString(R.string.submitted_successfully));
                    setResult(RESULT_OK);
                    finish();
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    CommonUtil.showToastShort(AddNewExpenseActivity.this,
                            getString(R.string.submission_failure));
                }
            });
        } else {
            RealmUtil.updateExpenseEntity(editEntity.getEpoch(), amount, currentType, comment,
                    new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            Intent returnIntent = new Intent(AddNewExpenseActivity.this, MainActivity.class);
                            startActivity(returnIntent);
                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            CommonUtil.showToastShort(AddNewExpenseActivity.this,
                                    getString(R.string.submission_failure));
                        }
                    });
        }
    }

    private void setExpenseType(int type) {
        if (type == -1)
            return;
        if (expenseOverlay.getVisibility() != View.GONE)
            expenseOverlay.setVisibility(View.GONE);
        switch (type) {
            case ExpenseEntity.FOOD:
                currentType = ExpenseEntity.FOOD;
                break;
            case ExpenseEntity.ALCOHOL:
                currentType = ExpenseEntity.ALCOHOL;
                break;
            case ExpenseEntity.WEED:
                currentType = ExpenseEntity.WEED;
                break;
            case ExpenseEntity.GAS:
                currentType = ExpenseEntity.GAS;
                break;
            case ExpenseEntity.ACCOMMODATION:
                currentType = ExpenseEntity.ACCOMMODATION;
                break;
            case ExpenseEntity.GEAR:
                currentType = ExpenseEntity.GEAR;
                break;
            case ExpenseEntity.OTHER:
                currentType = ExpenseEntity.OTHER;
                break;
            default:
                currentType = -1;
                return;
        }
        removeFullScreen();
        setupPage(currentType);
        editAmount.requestFocus();
        showSoftKeyboard();
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
        editAmount.requestFocus();
        showSoftKeyboard();
        setupPage(currentType);
    }

    @Override
    public void onBackPressed() {
        exitWithConfirmation();
    }

    private void showExitConfirmationDialog() {
        DialogUtil.showUnsavedChangesDialog(this, new DialogUtil.UnsavedChangesDialogListener() {
            @Override
            public void onDiscard() {
                finish();
            }
        });
    }

    @Override
    public void onDismiss() {
    }
}
