package cn.shuzilearn.bookkeeping.utils;

import android.app.AlertDialog;
import android.content.Context;
import cn.shuzilearn.bookkeeping.R;

public class AlertDialogUtil {
    public static void showAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title)
                .setIcon(R.drawable.yanzhengma)
                .setMessage(String.valueOf(message))
                .setPositiveButton("确定", null)
                .show();
    }
}
