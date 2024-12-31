package cn.shuzilearn.bookkeeping.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentActivity;
import cn.shuzilearn.bookkeeping.R;
import cn.shuzilearn.bookkeeping.page.*;
import com.github.mikephil.charting.BuildConfig;

public class UserFragment extends Fragment implements View.OnClickListener {

    private TextView tologin;
    private FragmentActivity activity;
    private SharedPreferences sp;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        sp = getActivity().getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        TextView username = view.findViewById(R.id.username);
        username.setText(sp.getString("username", "用户未登录！"));
        TextView phone = view.findViewById(R.id.phone);
        phone.setText(sp.getString("phone", ""));
        tologin = view.findViewById(R.id.Tologin);
        tologin.setOnClickListener(this);
        if (sp.getBoolean("Loginstate", true)) {
            tologin.setText("注销");
        }
        else {
            tologin.setText("登录");

        }

        // 协议跳转
        view.findViewById(R.id.Protocol1).setOnClickListener(this);
        view.findViewById(R.id.Protocol2).setOnClickListener(this);

        // 作者介绍
        TextView aboutDeveloper =  view.findViewById(R.id.aboutDeveloper);
        aboutDeveloper.setOnClickListener(this);

        // 软件介绍
        TextView aboutSoft = view.findViewById(R.id.aboutSoft);
        aboutSoft.setOnClickListener(this);


        // 设置版本号
        TextView version =  view.findViewById(R.id.version);
        // 获取版本号
        version.setText("软件版本号："+getActivity().getString(R.string.AppVersion));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshUI();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.Tologin) {
            if (tologin.getText().equals("登录")) {
                Intent intent = new Intent(getActivity(), loginPageActivity.class);
                startActivity(intent);



            } else if (tologin.getText().equals("注销")) {
                SharedPreferences.Editor editor = sp.edit();
                editor.remove("username");
                editor.remove("phone");
                editor.apply();
                sp.edit().putBoolean("Loginstate", false).apply();

                refreshUI();
            }
        }

        // 协议跳转逻辑
        if (v.getId()==R.id.Protocol1){
            Intent intent = new Intent(getActivity(), Protocol1Activity.class);
            startActivity(intent);

        }
        if (v.getId()==R.id.Protocol2){
            Intent intent = new Intent(getActivity(), Protocol2Activity.class);
            startActivity(intent);
        }

        // 关于作者
        if (v.getId()==R.id.aboutDeveloper){
            Intent intent = new Intent(getActivity(), AboutdevelopersActivity.class);
            startActivity(intent);

        }
        //关于软件
        if (v.getId()==R.id.aboutSoft){
            Intent intent = new Intent(getActivity(), AboutsoftwareActivity.class);
            startActivity(intent);

        }
    }

    // 添加刷新UI的方法
    public void refreshUI() {
        if (getView() != null) {
            SharedPreferences sp1 = getActivity().getSharedPreferences("isLogin", Context.MODE_PRIVATE);
            TextView username = getView().findViewById(R.id.username);
            TextView phone = getView().findViewById(R.id.phone);
            TextView button =  getView().findViewById(R.id.Tologin);
            button.setText(sp1.getBoolean("Loginstate",false)?"注销":"登录");
            username.setText(sp1.getString("username", "用户未登录！"));
            phone.setText(sp1.getString("phone", "请先登录"));
        }
    }
}