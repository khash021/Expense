package tech.khash.expense.util;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;
import tech.khash.expense.model.ExpenseEntity;

public class RealmExpenseMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        // Access the Realm schema in order to create, modify or delete classes and their fields.
        RealmSchema schema = realm.getSchema();

        //migrate from 1 to 2 (added year to the table)
        if (oldVersion == 1) {
            RealmObjectSchema expenseSchema = schema.get("ExpenseEntity");

            //Add year
            expenseSchema
                    .addField(ExpenseEntity.YEAR, Integer.class)
                    .transform(new RealmObjectSchema.Function() {
                        @Override
                        public void apply(DynamicRealmObject obj) {
                            obj.set("fullName", obj.getString("firstName") + " " + obj.getString("lastName"));
                        }
                    })
                    .removeField("firstName")
                    .removeField("lastName");
            oldVersion++;
        }
    }
}
