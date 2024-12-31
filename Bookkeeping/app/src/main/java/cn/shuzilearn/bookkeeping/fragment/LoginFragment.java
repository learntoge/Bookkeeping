package cn.shuzilearn.bookkeeping.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.shuzilearn.bookkeeping.R;
import cn.shuzilearn.bookkeeping.application.Myapplication;
import cn.shuzilearn.bookkeeping.dao.userDao;
import cn.shuzilearn.bookkeeping.pojo.user;
import cn.shuzilearn.bookkeeping.utils.PhoneNumberUtil;
import cn.shuzilearn.bookkeeping.utils.ToastUtil;


public class LoginFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    private EditText phone;
    private EditText password;
    private CheckBox savepwd;
    private CheckBox xieyi1;
    private CheckBox xieyi2;
    private Button login;
    private userDao userdao;
    private SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userdao = Myapplication.getInstance().getDatabase().userdao();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        phone = view.findViewById(R.id.phone);
        password = view.findViewById(R.id.password);
        savepwd = view.findViewById(R.id.save_password);
        xieyi1 = view.findViewById(R.id.yinsi1);
        xieyi2 = view.findViewById(R.id.yinsi2);
        login = view.findViewById(R.id.login);
        login.setOnClickListener(this);
        checkedchoose();
        xieyi1.setOnCheckedChangeListener(this);
        xieyi2.setOnCheckedChangeListener(this);
        preferences = getActivity().getSharedPreferences("saveData", Context.MODE_PRIVATE);
        if (preferences != null) {
            phone.setText(preferences.getString("phone", ""));
            password.setText(preferences.getString("passwd", ""));

        }
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login) {
            String inphone = phone.getText().toString();
            String inpwd = password.getText().toString();
            if (inphone.isEmpty() || inpwd.isEmpty()) {
                ToastUtil.showToast_s(getActivity(), "手机号或密码不能为空");
                return;
            }
            if (!PhoneNumberUtil.isValidPhoneNumber(inphone)) {
                ToastUtil.showToast_s(getActivity(), "手机号格式不正确");
                return;
            }
            if (userdao.findByPhone(inphone) == null) {
                ToastUtil.showToast_s(getActivity(), "手机号未注册，请先注册！");
                return;
            }

            if (savepwd.isChecked()) {
                // 获取编辑器
                SharedPreferences.Editor editor = preferences.edit();
                // 存入key -- value
                editor.putString("phone", inphone);
                editor.putString("passwd", inpwd);
                editor.apply();
            } else {
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("phone");  // 移除 phone
                editor.remove("passwd"); // 移除 passwd
                editor.apply();
            }

            ToastUtil.showToast_s(getActivity(), "登录成功！");

            user u = userdao.findByPhone(inphone);
            SharedPreferences.Editor logState = getActivity().getSharedPreferences("isLogin", Context.MODE_PRIVATE).edit();
            logState.putBoolean("Loginstate", true);
            logState.putString("phone", u.getPhone());
            logState.putString("username", u.getNickname() == null ? "用户" + u.getPhone() : u.getNickname());
            logState.apply();
            getActivity().setResult(1);
            getActivity().finish();
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == xieyi1.getId() || buttonView.getId() == xieyi2.getId()) {
            checkedchoose();
        }
    }

    // 检查协议是否被选择，未全部选择时，禁止登录
    private void checkedchoose() {
        if (!xieyi1.isChecked() || !xieyi2.isChecked()) {
            login.setEnabled(false);
        } else {
            login.setEnabled(true);
        }
    }
}