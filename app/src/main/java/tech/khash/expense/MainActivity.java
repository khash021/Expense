package tech.khash.expense;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Realm;
import tech.khash.expense.base.BaseActivity;
import tech.khash.expense.model.AmountEntity;
import tech.khash.expense.model.Constants;
import tech.khash.expense.model.ExpenseEntity;
import tech.khash.expense.util.CommonUtil;
import tech.khash.expense.util.DateTimeUtil;
import tech.khash.expense.util.DialogUtil;
import tech.khash.expense.util.RealmUtil;
import tech.khash.expense.util.SharedPreferencesUtil;
import tech.khash.expense.util.ViewUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener,
        SpeedDialView.OnActionSelectedListener, DialogUtil.ShowDeleteDatabaseConfirmationDialog.DeleteDialogListener {

    private SpeedDialView fab;
    private TextView weekTitleText, foodText, alcoholText, weedText, gasText, accommText, gearText,
            otherText, totalText, toolbarText, remainingText, allowanceText;
    private LinearLayout foodContainer, alcoholContainer, weedContainer, gasContainer,
            accommContainer, gearContainer, otherContainer;
    private View overlayView;
    private ImageButton imageButtonClose;
    private ProgressBar progressBarBudget, progressBarLoading;
    private ConstraintLayout mainLayout;
    private boolean isReportMenu = false;
    private HashMap<String, AmountEntity> amountEntityHashMap;

    private int weeklyBudget, weeklyAllowance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
        setupFab();
        updateWeeklyLimit();
        updateView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateWeeklyLimit();
        updateView();
    }

    private void initViews() {
        mainLayout = findViewById(R.id.container_main);
        mainLayout.setOnClickListener(this);

        fab = findViewById(R.id.fab);
        overlayView = findViewById(R.id.fab_overlay);

        imageButtonClose = findViewById(R.id.image_close);
        imageButtonClose.setOnClickListener(this);

        weekTitleText = findViewById(R.id.text_title_date);
        foodText = findViewById(R.id.text_food);
        alcoholText = findViewById(R.id.text_alcohol);
        weedText = findViewById(R.id.text_weed);
        gasText = findViewById(R.id.text_gas);
        accommText = findViewById(R.id.text_accommodation);
        gearText = findViewById(R.id.text_gear);
        otherText = findViewById(R.id.text_other);
        totalText = findViewById(R.id.text_total);
        toolbarText = findViewById(R.id.text_toolbar_report_title);
        remainingText = findViewById(R.id.text_remaining);
        allowanceText = findViewById(R.id.text_allowance);

        foodContainer = findViewById(R.id.container_food);
        alcoholContainer = findViewById(R.id.container_alcohol);
        weedContainer = findViewById(R.id.container_weed);
        gasContainer = findViewById(R.id.container_gas);
        accommContainer = findViewById(R.id.container_accommodation);
        gearContainer = findViewById(R.id.container_gear);
        otherContainer = findViewById(R.id.container_other);

        progressBarBudget = findViewById(R.id.progress_bar_budget);
        progressBarLoading = findViewById(R.id.progress_bar_loading);
    }

    private void setupFab() {
        fab.clearActionItems();

        fab.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_create_other, R.drawable.ic_other_white)
                        .setFabBackgroundColor(ContextCompat.getColor(this, R.color.colorAccentQuinary))
                        .setLabel(getString(R.string.expense_type_other))
                        .setLabelClickable(true).create());

        fab.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_create_gear, R.drawable.ic_gear_white)
                        .setFabBackgroundColor(ContextCompat.getColor(this, R.color.colorAccentQuinary))
                        .setLabel(getString(R.string.expense_type_gear))
                        .setLabelClickable(true).create());

        fab.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_create_accommodation, R.drawable.ic_accomm_white)
                        .setFabBackgroundColor(ContextCompat.getColor(this, R.color.colorAccentQuinary))
                        .setLabel(getString(R.string.expense_type_accommodation))
                        .setLabelClickable(true).create());

        fab.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_create_gas, R.drawable.ic_gas_white)
                        .setFabBackgroundColor(ContextCompat.getColor(this, R.color.colorAccentQuinary))
                        .setLabel(getString(R.string.expense_type_gas))
                        .setLabelClickable(true).create());
        fab.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_create_weed, R.drawable.ic_weed_white)
                        .setFabBackgroundColor(ContextCompat.getColor(this, R.color.colorAccentQuinary))
                        .setLabel(getString(R.string.expense_type_weed))
                        .setLabelClickable(true).create());

        fab.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_create_alcohol, R.drawable.ic_alcohol_white)
                        .setFabBackgroundColor(ContextCompat.getColor(this, R.color.colorAccentQuinary))
                        .setLabel(getString(R.string.expense_type_alcohol))
                        .setLabelClickable(true).create());

        fab.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_create_food, R.drawable.ic_food_white)
                        .setFabBackgroundColor(ContextCompat.getColor(this, R.color.colorAccentQuinary))
                        .setLabel(getString(R.string.expense_type_food))
                        //.setLabelColor(ContextCompat.getColor(getContext(), R.color.default_text_color))
                        //.setLabelBackgroundColor(ContextCompat.getColor(getContext(), R.color.white))
                        .setLabelClickable(true).create());

        overlayView.setOnClickListener(this);
        fab.setOnActionSelectedListener(this);
        fab.setOnChangeListener(new SpeedDialView.OnChangeListener() {
            @Override
            public boolean onMainActionSelected() {
                return false;
            }

            @Override
            public void onToggleChanged(boolean isOpen) {
                if (isOpen) {
                    ViewUtil.fadeInView(overlayView, 300, 0.5f);
                } else {
                    ViewUtil.fadeOutView(overlayView, 300);
                }
            }
        });
    }

    private void updateView() {
        progressBarLoading.setVisibility(View.VISIBLE);
        clearReportTexts();
        if (CommonUtil.isHashMapEmpty(amountEntityHashMap))
            amountEntityHashMap = new HashMap<>();

        if (!isReportMenu)
            showThisWeekData();
        else
            showLastWeekReport();
        progressBarLoading.setVisibility(View.GONE);
    }

    private void clearReportTexts() {
        foodContainer.setVisibility(View.GONE);
        alcoholContainer.setVisibility(View.GONE);
        weedContainer.setVisibility(View.GONE);
        gasContainer.setVisibility(View.GONE);
        accommContainer.setVisibility(View.GONE);
        otherContainer.setVisibility(View.GONE);
        gearContainer.setVisibility(View.GONE);
    }

    private void showThisWeekData() {
        AmountEntity thisWeekAmountEntity;
        if (amountEntityHashMap.containsKey(Constants.THIS_WEEK_EXPENSE_KEY)) {
            thisWeekAmountEntity = amountEntityHashMap.get(Constants.THIS_WEEK_EXPENSE_KEY);
        } else {
            ArrayList<ExpenseEntity> expenseEntities = RealmUtil.getThisWeekExpensesAll();
            int thisWeek = DateTimeUtil.getWeekOfTheYear();
            String mondayStr = DateTimeUtil.getMondayDateTimeStr();

            thisWeekAmountEntity = new AmountEntity(expenseEntities, thisWeek, mondayStr);
            if (thisWeekAmountEntity == null)
                return;
            else
                amountEntityHashMap.put(Constants.THIS_WEEK_EXPENSE_KEY, thisWeekAmountEntity);
        }

        String title = getString(R.string.main_report_title_date, thisWeekAmountEntity.getWeek(),
                thisWeekAmountEntity.getMondayStr());
        Spanned styledTitle = HtmlCompat.fromHtml(title, HtmlCompat.FROM_HTML_MODE_LEGACY);
        weekTitleText.setText(styledTitle);

        int food = thisWeekAmountEntity.getFood();
        if (food > 0) {
            foodContainer.setVisibility(View.VISIBLE);
            foodText.setText(String.valueOf(food));
        }

        int alcohol = thisWeekAmountEntity.getAlcohol();
        if (alcohol > 0) {
            alcoholContainer.setVisibility(View.VISIBLE);
            alcoholText.setText(String.valueOf(alcohol));
        }

        int weed = thisWeekAmountEntity.getWeed();
        if (weed > 0) {
            weedContainer.setVisibility(View.VISIBLE);
            weedText.setText(String.valueOf(weed));
        }

        int gas = thisWeekAmountEntity.getGas();
        if (gas > 0) {
            gasContainer.setVisibility(View.VISIBLE);
            gasText.setText(String.valueOf(gas));
        }

        int accommodation = thisWeekAmountEntity.getAccommodation();
        if (accommodation > 0) {
            accommContainer.setVisibility(View.VISIBLE);
            accommText.setText(String.valueOf(accommodation));
        }

        int other = thisWeekAmountEntity.getOther();
        if (other > 0) {
            otherContainer.setVisibility(View.VISIBLE);
            otherText.setText(String.valueOf(other));
        }

        int gear = thisWeekAmountEntity.getGear();
        if (gear > 0) {
            gearContainer.setVisibility(View.VISIBLE);
            gearText.setText(String.valueOf(gear));
        }

        int total = thisWeekAmountEntity.getTotal();
        totalText.setText(String.valueOf(total));

        showWeeklyRemaining(thisWeekAmountEntity);

        updateProgressBar(total);
    }

    private void showLastWeekReport() {
        AmountEntity lastWeekAmountEntity;
        if (amountEntityHashMap.containsKey(Constants.LAST_WEEK_EXPENSE_KEY)) {
            lastWeekAmountEntity = amountEntityHashMap.get(Constants.LAST_WEEK_EXPENSE_KEY);
        } else {
            int lastWeek = DateTimeUtil.getWeekOfTheYear() - 1;
            ArrayList<ExpenseEntity> expenseEntities = RealmUtil.getWeekExpensesAll(lastWeek);
            long epoch = expenseEntities.get(0).getEpoch();
            DateTime mondayDateTime = new DateTime(epoch);
            String mondayStr = DateTimeUtil.getMondayDateTimeStr(mondayDateTime);

            lastWeekAmountEntity = new AmountEntity(expenseEntities, lastWeek, mondayStr);
            if (lastWeekAmountEntity == null)
                return;
            else
                amountEntityHashMap.put(Constants.LAST_WEEK_EXPENSE_KEY, lastWeekAmountEntity);
        }

        String title = getString(R.string.main_report_title_date, lastWeekAmountEntity.getWeek(),
                lastWeekAmountEntity.getMondayStr());
        Spanned styledTitle = HtmlCompat.fromHtml(title, HtmlCompat.FROM_HTML_MODE_LEGACY);
        weekTitleText.setText(styledTitle);

        int food = lastWeekAmountEntity.getFood();
        if (food > 0) {
            foodContainer.setVisibility(View.VISIBLE);
            foodText.setText(String.valueOf(food));
        }

        int alcohol = lastWeekAmountEntity.getAlcohol();
        if (alcohol > 0) {
            alcoholContainer.setVisibility(View.VISIBLE);
            alcoholText.setText(String.valueOf(alcohol));
        }

        int weed = lastWeekAmountEntity.getWeed();
        if (weed > 0) {
            weedContainer.setVisibility(View.VISIBLE);
            weedText.setText(String.valueOf(weed));
        }

        int gas = lastWeekAmountEntity.getGas();
        if (gas > 0) {
            gasContainer.setVisibility(View.VISIBLE);
            gasText.setText(String.valueOf(gas));
        }

        int accommodation = lastWeekAmountEntity.getAccommodation();
        if (accommodation > 0) {
            accommContainer.setVisibility(View.VISIBLE);
            accommText.setText(String.valueOf(accommodation));
        }

        int other = lastWeekAmountEntity.getOther();
        if (other > 0) {
            otherContainer.setVisibility(View.VISIBLE);
            otherText.setText(String.valueOf(other));
        }

        int gear = lastWeekAmountEntity.getGear();
        if (gear > 0) {
            gearContainer.setVisibility(View.VISIBLE);
            gearText.setText(String.valueOf(gear));
        }

        int total = lastWeekAmountEntity.getTotal();
        totalText.setText(String.valueOf(total));

        showWeeklyRemaining(lastWeekAmountEntity);

        updateProgressBar(total);
    }

    private void updateProgressBar(int total) {
        int percentTotal = getPercentTotal(total);
        progressBarBudget.setProgressDrawable(ContextCompat.getDrawable(this,
                R.drawable.progress_bar));
        if (percentTotal < 100) {
            progressBarBudget.setProgressDrawable(ContextCompat.getDrawable(this,
                    R.drawable.progress_bar));
            progressBarBudget.setProgress(percentTotal, true);
            progressBarBudget.setSecondaryProgress(100);
        } else {
            progressBarBudget.setProgressDrawable(ContextCompat.getDrawable(this,
                    R.drawable.progress_bar_red));
            progressBarBudget.setProgress(100, true);
        }
    }

    private int getPercentTotal(int total) {
        float perc = (float) (total * 100) / weeklyBudget;
        int percInt = Math.round(perc);
        if (percInt >= 100)
            return 100;
        else
            return percInt;
    }

    private void showWeeklyRemaining(@NonNull AmountEntity entity) {
        int remaining = entity.getBudgetRemaining(this);
        updateTextViewWithColor(remainingText, remaining);

        int allowance = entity.getAllowanceRemaining(this);
        updateTextViewWithColor(allowanceText, allowance);
    }

    private void updateTextViewWithColor(@NonNull TextView textView, @NonNull int remaining) {
        if (remaining < 0) {
            textView.setText("- " + remaining);
            textView.setTextColor(getColor(R.color.red));
        } else if (remaining > 0) {
            textView.setText("+ " + remaining);
            textView.setTextColor(getColor(R.color.colorAccentTertiary));
        } else {
            textView.setText(String.valueOf(remaining));
            textView.setTextColor(getColor(R.color.colorAccentQuinary));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isReportMenu) {
            getMenuInflater().inflate(R.menu.menu_report, menu);
            imageButtonClose.setVisibility(View.VISIBLE);
            toolbarText.setVisibility(View.VISIBLE);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } else {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            imageButtonClose.setVisibility(View.GONE);
            toolbarText.setVisibility(View.GONE);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivityForResult(settingsIntent, Constants.REQUEST_CODE_SETTINMGS);
                return true;
            case R.id.action_list:
                Intent listIntent = new Intent(MainActivity.this, ExpenseListActivity.class);
                startActivity(listIntent);
                return true;
            case R.id.action_delete_all:
                showDeleteConfirmationDialog();
                return true;
            case R.id.action_chart:
                Intent chartIntent = new Intent(MainActivity.this, ChartActivity.class);
                startActivity(chartIntent);
                return true;
            case R.id.action_report_last_week:
                isReportMenu = true;
                invalidateOptionsMenu();
                updateView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_overlay:
                closeFab();
                break;
            case R.id.image_close:
                closeReportView();
                break;
            case R.id.container_main:
                if (isReportMenu)
                    closeReportView();
                break;
            default:
                break;
        }
    }

    private void closeReportView() {
        isReportMenu = false;
        invalidateOptionsMenu();
        updateView();
    }

    @Override
    public boolean onActionSelected(SpeedDialActionItem actionItem) {
        Intent intent = new Intent(this, AddNewExpenseActivity.class);
        switch (actionItem.getId()) {
            case R.id.fab_create_food:
                intent.putExtra(Constants.ADD_NEW_EXPENSE_EXTRA_TYPE, ExpenseEntity.FOOD);
                break;
            case R.id.fab_create_alcohol:
                intent.putExtra(Constants.ADD_NEW_EXPENSE_EXTRA_TYPE, ExpenseEntity.ALCOHOL);
                break;
            case R.id.fab_create_weed:
                intent.putExtra(Constants.ADD_NEW_EXPENSE_EXTRA_TYPE, ExpenseEntity.WEED);
                break;
            case R.id.fab_create_gas:
                intent.putExtra(Constants.ADD_NEW_EXPENSE_EXTRA_TYPE, ExpenseEntity.GAS);
                break;
            case R.id.fab_create_accommodation:
                intent.putExtra(Constants.ADD_NEW_EXPENSE_EXTRA_TYPE, ExpenseEntity.ACCOMMODATION);
                break;
            case R.id.fab_create_gear:
                intent.putExtra(Constants.ADD_NEW_EXPENSE_EXTRA_TYPE, ExpenseEntity.GEAR);
                break;
            case R.id.fab_create_other:
                intent.putExtra(Constants.ADD_NEW_EXPENSE_EXTRA_TYPE, ExpenseEntity.OTHER);
                break;
        }
        startActivityForResult(intent, Constants.REQUEST_CODE_ADD_NEW_EXPENSE);
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_ADD_NEW_EXPENSE && resultCode == RESULT_OK) {
            if (amountEntityHashMap.containsKey(Constants.THIS_WEEK_EXPENSE_KEY))
                amountEntityHashMap.remove(Constants.THIS_WEEK_EXPENSE_KEY);
            updateView();
        } else if (requestCode == Constants.REQUEST_CODE_SETTINMGS) {
            updateWeeklyLimit();
            updateView();
        }
    }

    private void showDeleteConfirmationDialog() {
        DialogUtil.ShowDeleteDatabaseConfirmationDialog dialog =
                new DialogUtil.ShowDeleteDatabaseConfirmationDialog(this, this);
    }

    private void deleteAll() {
        RealmUtil.deleteEntireDatabase(new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                CommonUtil.showToastShort(MainActivity.this,
                        getApplicationContext().getString(R.string.delete_all_success_message));
                updateView();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                CommonUtil.showToastShort(MainActivity.this,
                        getApplicationContext().getString(R.string.submitted_failure));
            }
        });
    }

    @Override
    public void onDeleteSelected() {
        deleteAll();
    }

    @Override
    public void onCancelSelected() {
        CommonUtil.showToastShort(this, "CANCEL");
    }

    private void showFab() {
        if (fab != null)
            fab.show();
    }

    private void hideFab() {
        if (fab != null)
            fab.hide();
    }

    private void closeFab() {
        if (fab != null && fab.isOpen())
            fab.close();
    }

    @Override
    public void onBackPressed() {
        if (isReportMenu) {
            closeReportView();
            return;
        }
        super.onBackPressed();
    }

    private void updateWeeklyLimit() {
        weeklyBudget = SharedPreferencesUtil.getWeeklyBudget(this);
        weeklyAllowance = SharedPreferencesUtil.getWeeklyAllowance(this);
    }
}
