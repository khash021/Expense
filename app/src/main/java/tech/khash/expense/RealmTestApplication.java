package tech.khash.expense;

import android.app.Application;

import io.realm.Realm;
import tech.khash.expense.util.RealmUtil;

public class RealmTestApplication extends Application {

    public RealmTestApplication() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initializeRealm();
    }

    private void initializeRealm() {
        Realm.init(this);
        RealmUtil.setDefaultRealmConfiguration();
    }
}
