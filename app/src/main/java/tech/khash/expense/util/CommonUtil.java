package tech.khash.expense.util;

import android.content.Context;
import android.widget.Toast;

import java.util.Collection;

public class CommonUtil {

    public static void showToastShort(Context context, String message) {
        if (message == null)
            return;
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static String getEmptyString () {
        return "";
    }

    public static boolean isEmptyList(Collection<?> list) {
        return list == null || list.isEmpty();
    }
}
