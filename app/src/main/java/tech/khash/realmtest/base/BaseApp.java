package tech.khash.realmtest.base;

import android.content.Context;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

public class BaseApp extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hideSoftKeyboard();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            hideSoftKeyboard();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void showSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }

    protected void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) { // verify if the soft keyboard is open
            if (this.getCurrentFocus() != null)
                imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
    }
}