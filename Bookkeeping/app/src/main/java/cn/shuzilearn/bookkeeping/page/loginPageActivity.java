package cn.shuzilearn.bookkeeping.page;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import cn.shuzilearn.bookkeeping.R;
import cn.shuzilearn.bookkeeping.fragment.LoginFragment;
import cn.shuzilearn.bookkeeping.fragment.RegisterFragment;
import cn.shuzilearn.bookkeeping.utils.NetworkRequestUtil;
import cn.shuzilearn.bookkeeping.utils.PermissionUtil;
import cn.shuzilearn.bookkeeping.utils.ToastUtil;
import org.jetbrains.annotations.NotNull;

public class loginPageActivity extends AppCompatActivity implements View.OnClickListener {
    // 权限列表
    private static final String[] permissions = new String[]{
            android.Manifest.permission.INTERNET

    };
    // 权限代码
    private static final int REQUEST_CODE_all = 1;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        getSupportActionBar().hide();

        // 初始碎片
        replacefm(new LoginFragment());

        //获取控件
        findViewById(R.id.changelgfm).setOnClickListener(this);
        findViewById(R.id.changerefm).setOnClickListener(this);


        TextView YIYAN = findViewById(R.id.yiyan1);
        TextView FromandWho = findViewById(R.id.from_and_who);

        NetworkRequestUtil.getYIYANAsync(yiyan -> {
            if (yiyan != null) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    YIYAN.setText(yiyan.getHitokoto());
                    FromandWho.setText("——《" + yiyan.getFrom() + "》  " + yiyan.getFromWho());
                });
            }
        });


    }

    // 切换碎片的函数
    private void replacefm(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.LR_fram, fragment);
        transaction.commit();
    }


    // 重写的点击方法
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.changelgfm) {
            replacefm(new LoginFragment());
        } else if (v.getId() == R.id.changerefm) {
            replacefm(new RegisterFragment());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_all) {
            if (PermissionUtil.checkGrant(grantResults)) {
                ToastUtil.showToast_s(this, "全部权限获取成功!");
            } else {
                for (int i = 0; i < permissions.length; i++) {
                    switch (permissions[i]) {
                        case Manifest.permission.INTERNET:
                            ToastUtil.showToast_s(this, "网络权限获取失败!");
                            break;
                    }
                }
            }

        }
    }
}