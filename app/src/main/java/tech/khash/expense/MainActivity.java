package tech.khash.expense;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

import io.realm.Realm;
import tech.khash.expense.base.BaseActivity;
import tech.khash.expense.model.Constants;
import tech.khash.expense.model.ExpenseEntity;
import tech.khash.expense.util.CalculateUtil;
import tech.khash.expense.util.CommonUtil;
import tech.khash.expense.util.DateTimeUtil;
import tech.khash.expense.util.DialogUtil;
import tech.khash.expense.util.RealmUtil;
import tech.khash.expense.util.ViewUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener,
        SpeedDialView.OnActionSelectedListener, DialogUtil.ShowDeleteConfirmationDialog.DeleteDialogListener {

    private SpeedDialView fab;
    private TextView weekTitleText,foodText, alcoholText, weedText, gasText, accommText, gearText,
            otherText, totalText;
    private LinearLayout foodContainer, alcoholContainer, weedContainer, gasContainer,
            accommContainer, gearContainer, otherContainer;
    private View overlayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
        setupFab();
        updateView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateView();
    }

    private void initViews() {
        fab = findViewById(R.id.fab);
        overlayView = findViewById(R.id.fab_overlay);

        weekTitleText = findViewById(R.id.text_title_date);
        foodText = findViewById(R.id.text_food);
        alcoholText = findViewById(R.id.text_alcohol);
        weedText = findViewById(R.id.text_weed);
        gasText = findViewById(R.id.text_gas);
        accommText = findViewById(R.id.text_accommodation);
        gearText = findViewById(R.id.text_gear);
        otherText = findViewById(R.id.text_other);
        totalText = findViewById(R.id.text_total);


        foodContainer = findViewById(R.id.container_food);
        alcoholContainer = findViewById(R.id.container_alcohol);
        weedContainer = findViewById(R.id.container_weed);
        gasContainer = findViewById(R.id.container_gas);
        accommContainer = findViewById(R.id.container_accommodation);
        gearContainer = findViewById(R.id.container_gear);
        otherContainer = findViewById(R.id.container_other);
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
                    ViewUtil.showFadeView(overlayView, 300, 0.5f);
                } else {
                    ViewUtil.hideFadeView(overlayView, 300);
                }
            }
        });
    }

    private void updateView() {
        int thisWeek = DateTimeUtil.getWeekOfTheYear();
        String mondayStr = DateTimeUtil.getMondayDateTimeStr();

        String title = getString(R.string.main_report_title_date, thisWeek, mondayStr);
        Spanned styledTitle = HtmlCompat.fromHtml(title, HtmlCompat.FROM_HTML_MODE_LEGACY);
        weekTitleText.setText(styledTitle);

        int food = CalculateUtil.getThisWeekTotalTyped(ExpenseEntity.FOOD);
        if (food > 0) {
            foodContainer.setVisibility(View.VISIBLE);
            foodText.setText(String.valueOf(food));
        }

        int alcohol = CalculateUtil.getThisWeekTotalTyped(ExpenseEntity.ALCOHOL);
        if (alcohol > 0) {
            alcoholContainer.setVisibility(View.VISIBLE);
            alcoholText.setText(String.valueOf(alcohol));
        }

        int weed = CalculateUtil.getThisWeekTotalTyped(ExpenseEntity.WEED);
        if (weed > 0) {
            weedContainer.setVisibility(View.VISIBLE);
            weedText.setText(String.valueOf(weed));
        }

        int gas = CalculateUtil.getThisWeekTotalTyped(ExpenseEntity.GAS);
        if (gas > 0) {
            gasContainer.setVisibility(View.VISIBLE);
            gasText.setText(String.valueOf(gas));
        }

        int accommodation = CalculateUtil.getThisWeekTotalTyped(ExpenseEntity.ACCOMMODATION);
        if (accommodation > 0) {
            accommContainer.setVisibility(View.VISIBLE);
            accommText.setText(String.valueOf(accommodation));
        }

        int other = CalculateUtil.getThisWeekTotalTyped(ExpenseEntity.OTHER);
        if (other > 0) {
            otherContainer.setVisibility(View.VISIBLE);
            otherText.setText(String.valueOf(other));
        }

        int gear = CalculateUtil.getThisWeekTotalTyped(ExpenseEntity.GEAR);
        if (gear > 0) {
            gearContainer.setVisibility(View.VISIBLE);
            gearText.setText(String.valueOf(gear));
        }

        int total = CalculateUtil.getThisWeekTotal();
        totalText.setText(String.valueOf(total));
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_overlay:
                closeFab();
                break;
            default:
                break;
        }
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
        startActivityForResult(intent,Constants.REQUEST_CODE_ADD_NEW_EXPENSE);
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_ADD_NEW_EXPENSE && resultCode == RESULT_OK) {
            updateView();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_list:
                Intent intent = new Intent(MainActivity.this, ExpenseListActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_delete_all:
                showDeleteConfirmationDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDeleteConfirmationDialog() {
        DialogUtil.ShowDeleteConfirmationDialog dialog =
                new DialogUtil.ShowDeleteConfirmationDialog(this, this);
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
}
