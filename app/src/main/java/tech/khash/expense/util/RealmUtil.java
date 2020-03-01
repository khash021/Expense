package tech.khash.expense.util;

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
import io.realm.Sort;
import tech.khash.expense.model.Constants;
import tech.khash.expense.model.ExpenseEntity;

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
    public static void insertUpdateExpenseToRealm(@NonNull final ExpenseEntity entity,
                                                  Realm.Transaction.OnSuccess successListener,
                                                  Realm.Transaction.OnError errorListener) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(entity);
            }
        }, successListener, errorListener);
    }

    public static void updateExpenseEntity (@NonNull final long epoch, @NonNull final int amount,
                                            @NonNull final int type, final String comment,
                                            Realm.Transaction.OnSuccess successListener,
                                            final Realm.Transaction.OnError errorListener) {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
                query.equalTo(ExpenseEntity.EPOCH, epoch);
                ExpenseEntity expenseEntity = query.findFirst();
                if (expenseEntity == null) {
                    if (errorListener != null)
                        errorListener.onError(new Throwable("Entity not found"));
                } else {
                    expenseEntity.setType(type);
                    expenseEntity.setAmount(amount);
                    expenseEntity.setComment(comment);
                }
            }
        }, successListener, errorListener);
    }

    public static void updateExpenseEntityAmount(@NonNull final long epoch, @NonNull final int amount,
                                                 Realm.Transaction.OnSuccess successListener,
                                                 final Realm.Transaction.OnError errorListener) {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
                query.equalTo(ExpenseEntity.EPOCH, epoch);
                ExpenseEntity expenseEntity = query.findFirst();
                if (expenseEntity == null) {
                    if (errorListener != null)
                        errorListener.onError(null);
                } else
                    expenseEntity.setAmount(amount);
            }
        }, successListener, errorListener);
    }

    public static void updateExpenseEntityComment(@NonNull final long epoch, @NonNull final String comment,
                                                  Realm.Transaction.OnSuccess successListener,
                                                  final Realm.Transaction.OnError errorListener) {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
                query.equalTo(ExpenseEntity.EPOCH, epoch);
                ExpenseEntity expenseEntity = query.findFirst();
                if (expenseEntity == null) {
                    if (errorListener != null)
                        errorListener.onError(null);
                } else {
                    expenseEntity.setComment(comment);
                }
            }
        }, successListener, errorListener);
    }

    public static void updateExpenseEntityType(@NonNull final long epoch,
                                               @ExpenseEntity.ExpenseTypeInt final int type,
                                               Realm.Transaction.OnSuccess successListener,
                                               final Realm.Transaction.OnError errorListener) {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
                query.equalTo(ExpenseEntity.EPOCH, epoch);
                ExpenseEntity expenseEntity = query.findFirst();
                if (expenseEntity == null) {
                    if (errorListener != null)
                        errorListener.onError(null);
                } else {
                    expenseEntity.setType(type);
                }
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

    public static void deleteSingleExpenseEpoch(@NonNull final long epoch,
                                                Realm.Transaction.OnSuccess successListener,
                                                final Realm.Transaction.OnError errorListener) {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
                query.equalTo(ExpenseEntity.EPOCH, epoch);
                RealmResults<ExpenseEntity> results = query.findAll();
                if (CommonUtil.isEmptyList(results))
                    errorListener.onError(null);
                else {
                    results.deleteAllFromRealm();
                }
            }
        }, successListener, errorListener);
    }

    /*
        ------------------------------  QUERY  -------------------------------
    */
    public static ExpenseEntity getExpenseEntityEpoch(long epoch) {
        Realm realm = Realm.getDefaultInstance();

        RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
        query.equalTo(ExpenseEntity.EPOCH, epoch);
        RealmResults<ExpenseEntity> results = query.findAll();
        if (CommonUtil.isEmptyList(results))
            return null;
        else {
            return results.get(0);
        }
    }

    public static ArrayList<ExpenseEntity> getThisWeekExpensesAll() {
        int week = DateTimeUtil.getThisWeekInt();
        Realm realm = Realm.getDefaultInstance();

        RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
        query.equalTo(ExpenseEntity.WEEK_OF_THE_YEAR, week);
        RealmResults<ExpenseEntity> results = query.findAll();

        ArrayList<ExpenseEntity> expenses = new ArrayList<>();
        expenses.addAll(results);

        return expenses;
    }

    public static ArrayList<ExpenseEntity> getThisWeekExpensesTyped(@NonNull @ExpenseEntity.ExpenseTypeInt int type) {
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

    public static ArrayList<ExpenseEntity> getWeekExpensesAll(@NonNull int week) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
        query.equalTo(ExpenseEntity.WEEK_OF_THE_YEAR, week);
        RealmResults<ExpenseEntity> results = query.findAll();

        ArrayList<ExpenseEntity> expenses = new ArrayList<>();
        expenses.addAll(results);

        return expenses;
    }

    public static ArrayList<ExpenseEntity> getWeekExpensesTyped(@NonNull int week, @NonNull @ExpenseEntity.ExpenseTypeInt int type) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
        query.equalTo(ExpenseEntity.WEEK_OF_THE_YEAR, week);
        query.equalTo(ExpenseEntity.TYPE, type);
        RealmResults<ExpenseEntity> results = query.findAll();

        ArrayList<ExpenseEntity> expenses = new ArrayList<>();
        expenses.addAll(results);

        return expenses;
    }

    public static ArrayList<ExpenseEntity> getThisMonthExpensesAll() {
        int month = DateTimeUtil.getThisMonthInt();

        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
        query.equalTo(ExpenseEntity.MONTH_OF_THE_YEAR, month);
        RealmResults<ExpenseEntity> results = query.findAll();

        ArrayList<ExpenseEntity> expenses = new ArrayList<>();
        expenses.addAll(results);

        return expenses;
    }

    public static ArrayList<ExpenseEntity> getThisMonthExpensesTyped(@NonNull @ExpenseEntity.ExpenseTypeInt int type) {
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

    public static ArrayList<ExpenseEntity> getMonthExpensesAll(@NonNull int month) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
        query.equalTo(ExpenseEntity.MONTH_OF_THE_YEAR, month);
        RealmResults<ExpenseEntity> results = query.findAll();

        ArrayList<ExpenseEntity> expenses = new ArrayList<>();
        expenses.addAll(results);

        return expenses;
    }

    public static ArrayList<ExpenseEntity> getMonthExpensesTyped(@NonNull int month, @NonNull @ExpenseEntity.ExpenseTypeInt int type) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
        query.equalTo(ExpenseEntity.MONTH_OF_THE_YEAR, month);
        query.equalTo(ExpenseEntity.TYPE, type);
        RealmResults<ExpenseEntity> results = query.findAll();

        ArrayList<ExpenseEntity> expenses = new ArrayList<>();
        expenses.addAll(results);

        return expenses;
    }

    public static ArrayList<ExpenseEntity> getThisYearExpensesAll() {
        int year = DateTimeUtil.getThisYearInt();

        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
        query.equalTo(ExpenseEntity.YEAR, year);
        RealmResults<ExpenseEntity> results = query.findAll();

        ArrayList<ExpenseEntity> expenses = new ArrayList<>();
        expenses.addAll(results);

        return expenses;
    }

    public static ArrayList<ExpenseEntity> getThisYearExpensesTyped(@NonNull @ExpenseEntity.ExpenseTypeInt int type) {
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

    public static ArrayList<ExpenseEntity> getYearExpensesAll(@NonNull int year) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
        query.equalTo(ExpenseEntity.YEAR, year);
        RealmResults<ExpenseEntity> results = query.findAll();

        ArrayList<ExpenseEntity> expenses = new ArrayList<>();
        expenses.addAll(results);

        return expenses;
    }

    public static ArrayList<ExpenseEntity> getYearExpensesTyped(@NonNull int year, @NonNull @ExpenseEntity.ExpenseTypeInt int type) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
        query.equalTo(ExpenseEntity.YEAR, year);
        query.equalTo(ExpenseEntity.TYPE, type);
        RealmResults<ExpenseEntity> results = query.findAll();

        ArrayList<ExpenseEntity> expenses = new ArrayList<>();
        expenses.addAll(results);

        return expenses;
    }

    public static ArrayList<ExpenseEntity> getAllExpensesAscending() {
        Realm realm = Realm.getDefaultInstance();

        RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
        query.sort(ExpenseEntity.EPOCH, Sort.ASCENDING);
        RealmResults<ExpenseEntity> results = query.findAll();

        ArrayList<ExpenseEntity> expenses = new ArrayList<>();
        expenses.addAll(results);

        return expenses;
    }

    public static ArrayList<ExpenseEntity> getAllExpensesDescending() {
        Realm realm = Realm.getDefaultInstance();

        RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
        query.sort(ExpenseEntity.EPOCH, Sort.DESCENDING);
        RealmResults<ExpenseEntity> results = query.findAll();

        ArrayList<ExpenseEntity> expenses = new ArrayList<>();
        expenses.addAll(results);

        return expenses;
    }

    public static int getFirstWeekCountInt() {
        Realm realm = Realm.getDefaultInstance();

        RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
        query.sort(ExpenseEntity.EPOCH, Sort.ASCENDING);
        query.limit(1);
        RealmResults<ExpenseEntity> results = query.findAll();
        return results.get(0).getWeekOfTheYear();
    }

    public static int getLastWeekCountInt() {
        Realm realm = Realm.getDefaultInstance();

        RealmQuery<ExpenseEntity> query = realm.where(ExpenseEntity.class);
        query.sort(ExpenseEntity.EPOCH, Sort.DESCENDING);
        query.limit(1);
        RealmResults<ExpenseEntity> results = query.findAll();
        return results.get(0).getWeekOfTheYear();
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
