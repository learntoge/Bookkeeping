package cn.shuzilearn.bookkeeping.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtil {
    // 检查多个权限是否开启，true为开启，false为未完全开启
    public static boolean checkPermission(Activity activity, String[] permission, int requestCode) {
        // 在Android 6.0后采用动态权限管理

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = PackageManager.PERMISSION_GRANTED;
            for (String s : permission) {
                result = ContextCompat.checkSelfPermission(activity, s);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    break;
                }
            }
            if (result != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, permission, requestCode);
                return false;
            }

        }

        return true;
    }

    // 检查结果数组，true为已授权，false为未授权
    public static boolean checkGrant(int[] grantResults) {
        if (grantResults!=null && grantResults.length>0) {
            // 遍历权限数组
            for (int grantResult : grantResults) {
                // 未获取权限
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
            // 已获取权限
            return true;
        }
        return false;
    }
}
