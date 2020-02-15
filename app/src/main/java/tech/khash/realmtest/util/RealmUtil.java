package tech.khash.realmtest.util;

import androidx.annotation.NonNull;

import org.joda.time.DateTime;

import java.util.ArrayList;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.RealmSchema;
import tech.khash.realmtest.model.Constants;
import tech.khash.realmtest.model.ExpenseEntity;

public class RealmUtil {

    public static void setDefaultRealmConfiguration() {
        RealmConfiguration defaultConfiguration = new RealmConfiguration.Builder()
                .name("expense.realm")
                .schemaVersion(Constants.REALM.REALM_VERSION)
                .migration(new RealmExpenseMigration())
                .build();
        Realm.setDefaultConfiguration(defaultConfiguration);
    }

    /*
        ------------------------------  INSERT/UPDATE/PATCH  -------------------------------
    */
    public static void insertUpdateExpenseToRealm(final ExpenseEntity entity,
                                                  Realm.Transaction.OnSuccess successListener,
                                                  Realm.Transaction.OnError errorListener) {
        if (entity == null)
            return;

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(entity);
            }
        }, successListener, errorListener);
    }

    /*
    ------------------------------  DELETE  ----------------------------------------
    */
    public static void deleteEntireDatabase(Realm.Transaction.OnSuccess successListener,
                                            Realm.Transaction.OnError errorListener) {



        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

            }
        }, successListener, errorListener);

        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
        realm.close();
    }

    /*
        ------------------------------  QUERY  -------------------------------
    */
    public static ArrayList<ExpenseEntity> getThisWeekExpensesAll () {
        int week = DateTimeUtil.getThisWeekInt();
        Realm realm = Realm.getDefaultInstance();

        RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
        query.equalTo(ExpenseEntity.WEEK_OF_THE_YEAR, week);
        RealmResults<ExpenseEntity> results = query.findAll();

        ArrayList<ExpenseEntity> expenses = new ArrayList<>();
        expenses.addAll(results);

        return expenses;
    }

    public static ArrayList<ExpenseEntity> getThisWeekExpensesTyped (@NonNull @ExpenseEntity.ExpenseTypeInt int type) {
        int week = DateTimeUtil.getThisWeekInt();

        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
        query.equalTo(ExpenseEntity.WEEK_OF_THE_YEAR, week);
        query.equalTo(ExpenseEntity.TYPE, type);
        RealmResults<ExpenseEntity> results = query.findAll();

        ArrayList<ExpenseEntity> expenses = new ArrayList<>();
        expenses.addAll(results);

        return expenses;
    }

    public static ArrayList<ExpenseEntity> getWeekExpensesAll (@NonNull int week) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
        query.equalTo(ExpenseEntity.WEEK_OF_THE_YEAR, week);
        RealmResults<ExpenseEntity> results = query.findAll();

        ArrayList<ExpenseEntity> expenses = new ArrayList<>();
        expenses.addAll(results);

        return expenses;
    }

    public static ArrayList<ExpenseEntity> getWeekExpensesTyped (@NonNull int week, @NonNull @ExpenseEntity.ExpenseTypeInt int type) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
        query.equalTo(ExpenseEntity.WEEK_OF_THE_YEAR, week);
        query.equalTo(ExpenseEntity.TYPE, type);
        RealmResults<ExpenseEntity> results = query.findAll();

        ArrayList<ExpenseEntity> expenses = new ArrayList<>();
        expenses.addAll(results);

        return expenses;
    }

    public static ArrayList<ExpenseEntity> getThisMonthExpensesAll () {
        int month = DateTimeUtil.getThisMonthInt();

        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
        query.equalTo(ExpenseEntity.MONTH_OF_THE_YEAR, month);
        RealmResults<ExpenseEntity> results = query.findAll();

        ArrayList<ExpenseEntity> expenses = new ArrayList<>();
        expenses.addAll(results);

        return expenses;
    }

    public static ArrayList<ExpenseEntity> getThisMonthExpensesTyped (@NonNull @ExpenseEntity.ExpenseTypeInt int type) {
        int month = DateTimeUtil.getThisMonthInt();

        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
        query.equalTo(ExpenseEntity.MONTH_OF_THE_YEAR, month);
        query.equalTo(ExpenseEntity.TYPE, type);
        RealmResults<ExpenseEntity> results = query.findAll();

        ArrayList<ExpenseEntity> expenses = new ArrayList<>();
        expenses.addAll(results);

        return expenses;
    }

    public static ArrayList<ExpenseEntity> getMonthExpensesAll (@NonNull int month) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
        query.equalTo(ExpenseEntity.MONTH_OF_THE_YEAR, month);
        RealmResults<ExpenseEntity> results = query.findAll();

        ArrayList<ExpenseEntity> expenses = new ArrayList<>();
        expenses.addAll(results);

        return expenses;
    }

    public static ArrayList<ExpenseEntity> getMonthExpensesTyped (@NonNull int month, @NonNull @ExpenseEntity.ExpenseTypeInt int type) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
        query.equalTo(ExpenseEntity.MONTH_OF_THE_YEAR, month);
        query.equalTo(ExpenseEntity.TYPE, type);
        RealmResults<ExpenseEntity> results = query.findAll();

        ArrayList<ExpenseEntity> expenses = new ArrayList<>();
        expenses.addAll(results);

        return expenses;
    }

    public static ArrayList<ExpenseEntity> getThisYearExpensesAll () {
        int year = DateTimeUtil.getThisYearInt();

        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
        query.equalTo(ExpenseEntity.YEAR, year);
        RealmResults<ExpenseEntity> results = query.findAll();

        ArrayList<ExpenseEntity> expenses = new ArrayList<>();
        expenses.addAll(results);

        return expenses;
    }

    public static ArrayList<ExpenseEntity> getThisYearExpensesTyped (@NonNull @ExpenseEntity.ExpenseTypeInt int type) {
        int year = DateTimeUtil.getThisYearInt();

        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
        query.equalTo(ExpenseEntity.YEAR, year);
        query.equalTo(ExpenseEntity.TYPE, type);
        RealmResults<ExpenseEntity> results = query.findAll();

        ArrayList<ExpenseEntity> expenses = new ArrayList<>();
        expenses.addAll(results);

        return expenses;
    }

    public static ArrayList<ExpenseEntity> getYearExpensesAll (@NonNull int year) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
        query.equalTo(ExpenseEntity.YEAR, year);
        RealmResults<ExpenseEntity> results = query.findAll();

        ArrayList<ExpenseEntity> expenses = new ArrayList<>();
        expenses.addAll(results);

        return expenses;
    }

    public static ArrayList<ExpenseEntity> getYearExpensesTyped (@NonNull int year, @NonNull @ExpenseEntity.ExpenseTypeInt int type) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
        query.equalTo(ExpenseEntity.YEAR, year);
        query.equalTo(ExpenseEntity.TYPE, type);
        RealmResults<ExpenseEntity> results = query.findAll();

        ArrayList<ExpenseEntity> expenses = new ArrayList<>();
        expenses.addAll(results);

        return expenses;
    }

    public interface RealmSuccessListener {
        void onSuccess();
        void onFailure();
    }

    /*
        ------------------------------  MIGRATION  -------------------------------
    */
    private static final class RealmExpenseMigration implements RealmMigration {
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

            // Access the Realm schema in order to create, modify or delete classes and their fields.
            RealmSchema schema = realm.getSchema();

            //migrate from 1 to 2 (added year to the table)
            if (oldVersion == 1) {
                schema.get("ExpenseEntity")
                        .addField(ExpenseEntity.YEAR, int.class)
                        .transform(new RealmObjectSchema.Function() {
                            @Override
                            public void apply(DynamicRealmObject obj) {
                                //add year to the old ones
                                long epoch = obj.get(ExpenseEntity.EPOCH);
                                DateTime dateTime = new DateTime(epoch);
                                int year = dateTime.getYear();
                                obj.set(ExpenseEntity.YEAR, year);
                            }
                        });
                oldVersion++;
            }
        }
    }
}
