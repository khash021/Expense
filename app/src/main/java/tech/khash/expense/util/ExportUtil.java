package tech.khash.expense.util;

import android.app.Activity;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import tech.khash.expense.model.ExpenseEntity;

public class ExportUtil {

    public static Uri getNewExpenseCsvUri(@NonNull ArrayList<ExpenseEntity> expenses) {
        return null;
    }

    private static File createCsvFile(Activity activity, String name, ArrayList<ExpenseEntity> expenses) throws IOException {
        File file = new File(activity.getApplicationContext().getFilesDir(), name);
        FileWriter fw = new FileWriter(file.getAbsolutePath());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("Epoch, Date, Type, Amount, Week, Month, Year, " + "Comment, \n");
        for (ExpenseEntity expenseEntity : expenses) {
            String s = createLine(expenseEntity);
            bw.write(s);
        }
        bw.close();
        return file;
    }

    private static String createLine(ExpenseEntity expenseEntity) {
        String epoch = String.valueOf(expenseEntity.getEpoch());
        String date = expenseEntity.getTodayDateTimeStr();
        String type = expenseEntity.getExpenseTypeStr();
        String amount = String.valueOf(expenseEntity.getAmount());
        String week = String.valueOf(expenseEntity.getWeekOfTheYear());
        String month = String.valueOf(expenseEntity.getMonthOfTheYear());
        String year = String.valueOf(expenseEntity.getYear());
        String comment = expenseEntity.getComment();

        String line = epoch + "," + date + "," + type + "," + amount + "," + week + "," + month +
                "," + year + "," + comment;
        return line;
    }

}
