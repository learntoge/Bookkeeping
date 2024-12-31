package cn.shuzilearn.bookkeeping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import cn.shuzilearn.bookkeeping.fragment.*;
import cn.shuzilearn.bookkeeping.page.BilleditorActivity;
import cn.shuzilearn.bookkeeping.page.EditnoteActivity;
import cn.shuzilearn.bookkeeping.page.loginPageActivity;
import cn.shuzilearn.bookkeeping.utils.ToastUtil;

import java.util.Objects;

public class BookkeepingActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout home;
    private LinearLayout fenxi;
    private LinearLayout jishiben;
    private LinearLayout user;
    private Fragment currentFragment;
    private TextView title_header;
    private ImageView addbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookkeeping);
        Objects.requireNonNull(getSupportActionBar()).hide();
        home = findViewById(R.id.home);
        fenxi = findViewById(R.id.fenxi);
        jishiben = findViewById(R.id.jishiben);
        user = findViewById(R.id.user);
        addbtn = findViewById(R.id.addbutton);
        title_header = findViewById(R.id.header_title);

        home.setOnClickListener(this);
        fenxi.setOnClickListener(this);
        jishiben.setOnClickListener(this);
        user.setOnClickListener(this);
        addbtn.setOnClickListener(this);

        replacefm(new HomeFragment());
        currentFragment = getSupportFragmentManager().findFragmentById(R.id.showFragment);
        SharedPreferences sharedPreferences = getSharedPreferences("isLogin", MODE_PRIVATE);

            if (!sharedPreferences.getBoolean("Loginstate", false)){
                ToastUtil.showToast_s(this,"账户未登录");

                Intent intent = new Intent(BookkeepingActivity.this, loginPageActivity.class);
                ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        o -> {
                            if (o!=null){
                                if(o.getResultCode()==1){
                                    replacefm(new UserFragment());
                                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.showFragment);
                                    if (fragment instanceof UserFragment) {
                                        ((UserFragment) fragment).refreshUI(); // 调用刷新方法
                                    }
                                }
                            }
                        }
                );

                resultLauncher.launch(intent);
            }




    }

    @Override
    public void onClick(View v) {
        currentFragment = getSupportFragmentManager().findFragmentById(R.id.showFragment);

        if (v.getId() == R.id.home){
            title_header.setText("省钱记");
            replacefm(new HomeFragment());

        }
        if (v.getId() == R.id.fenxi){
            title_header.setText("消费分析");

            replacefm(new AnalyseFragment());
//            ToastUtil.showToast_s(this,"消费分析功能暂未完成开发哦！");

        }
        if (v.getId() == R.id.jishiben){
            title_header.setText("记事本");
            replacefm(new NotepadFragment());

        }
        if (v.getId() == R.id.user){
            title_header.setText("个人中心");

            replacefm(new UserFragment());
        }
        if (v.getId()==R.id.addbutton){
            if (currentFragment instanceof NotepadFragment) {
                Intent intent = new Intent(BookkeepingActivity.this, EditnoteActivity.class);
                startActivity(intent);

            }
            else if (currentFragment instanceof HomeFragment) {
                Intent intent = new Intent(BookkeepingActivity.this, BilleditorActivity.class);
                startActivity(intent);
            }
            else {
                ToastUtil.showToast_s(this,"该功能暂未完成开发哦！");
            }

        }

    }

    // 切换碎片的函数
    private void replacefm(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.showFragment, fragment);
        transaction.commit();
    }


}