package tech.khash.expense.util;

import android.content.Context;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Collection;
import java.util.HashMap;

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

    public static boolean isHashMapEmpty(HashMap<?, ?> hashMap) {
        return hashMap == null || hashMap.size() <= 0;
    }

    public static String getCommaSeparatedIntStr(int number) {
        NumberFormat format = NumberFormat.getIntegerInstance();
        return format.format(number);
    }
    //TODO: make a method to add + and - sign to amounts
}
