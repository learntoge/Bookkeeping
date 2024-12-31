package cn.shuzilearn.bookkeeping.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    public static void showToast_s(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
    public static void showToast_l(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }
}
