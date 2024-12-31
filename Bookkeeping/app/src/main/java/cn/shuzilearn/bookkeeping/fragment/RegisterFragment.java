package cn.shuzilearn.bookkeeping.fragment;

import android.annotation.SuppressLint;
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
import cn.shuzilearn.bookkeeping.utils.AlertDialogUtil;
import cn.shuzilearn.bookkeeping.utils.CaptchaUtil;
import cn.shuzilearn.bookkeeping.utils.PhoneNumberUtil;
import cn.shuzilearn.bookkeeping.utils.ToastUtil;

import java.time.LocalDate;


public class RegisterFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    private EditText phone;
    private EditText code;
    private EditText password;
    private EditText enterpassword;
    private CheckBox yinsi1;
    private CheckBox yinsi2;
    private Button getcode;
    private Button resbtn;
    private int intcode;
    private userDao userdao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userdao = Myapplication.getInstance().getDatabase().userdao();
//        database = new myapplication().getBookkeepDataBase();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        // 获取界面中的按钮输入框等控件
        phone = view.findViewById(R.id.phone);
        code = view.findViewById(R.id.code);
        password = view.findViewById(R.id.password);
        enterpassword = view.findViewById(R.id.enterpassword);
        yinsi1 = view.findViewById(R.id.yinsi1);
        yinsi2 = view.findViewById(R.id.yinsi2);
        getcode = view.findViewById(R.id.getcode);
        resbtn = view.findViewById(R.id.resbtn);

        getcode.setOnClickListener(this);
        resbtn.setOnClickListener(this);
        checkedchoose();
        yinsi1.setOnCheckedChangeListener(this);
        yinsi2.setOnCheckedChangeListener(this);

        // 返回view
        return view;
    }


    // 点击方法重写
    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        // 点击获取验证码按钮执行以下逻辑
        if (v.getId() == R.id.getcode) {
            intcode = CaptchaUtil.getCaptchaCode();
            AlertDialogUtil.showAlertDialog(getActivity(),"你的验证码",String.valueOf(intcode));
        }

        // 点击注册时的逻辑
        if (v.getId() == R.id.resbtn){
            // 读取用户输入的信息
            String inphone =phone.getText().toString();
            String incode =code.getText().toString();
            String inpassword =password.getText().toString();
            String inenterpassword =enterpassword.getText().toString();
            // 检查手机号及其合法性
            if (inphone.isEmpty()){
                ToastUtil.showToast_s(getActivity(),"请输入手机号");
                return;
            }

            // 检查用户输入的验证码的合法性
            if (incode.isEmpty()){
                ToastUtil.showToast_s(getActivity(),"请输入验证码");
                return;
            }
            if (Integer.parseInt(incode)!=intcode){
                ToastUtil.showToast_s(getActivity(),"验证码错误");
                return;
            }

            // 检查密码及两次密码的合法性
            if (inpassword.isEmpty()){
                ToastUtil.showToast_s(getActivity(),"请输入密码");
                return;
            }
            if (inenterpassword.isEmpty()){
                ToastUtil.showToast_s(getActivity(),"请输入确认密码");
                return;

            }

            // 检查用户信息的合法性
            if (!inpassword.equals(inenterpassword)){
                ToastUtil.showToast_s(getActivity(),"两次密码不一致");
                return;
            }

            // 先使用正则表达式检查手机号格式是否合法
            if (!PhoneNumberUtil.isValidPhoneNumber(inphone)){
                ToastUtil.showToast_s(getActivity(),"手机号不合法");
                return;
            }

            // 检查用户的手机号是否已经被注册
            if (userdao.findByPhone(inphone)==null){
                String jointime = LocalDate.now().toString();
                userdao.insert(new user(inphone,inenterpassword,jointime));
                if (userdao.findByPhone(inphone)!=null){
                    ToastUtil.showToast_s(getActivity(),"注册成功，快去登录吧！！");
                }
                else {
                    ToastUtil.showToast_s(getActivity(),"注册失败，请联系管理员！！");
                }

            }
            else {
                ToastUtil.showToast_s(getActivity(),"该手机号已经被注册！");
            }


        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.yinsi1 || buttonView.getId() == R.id.yinsi2) {
            checkedchoose();
        }
    }

    // 检查协议是否被选择，未全部选择时，禁止注册
    private void checkedchoose() {
        if (!yinsi1.isChecked() || !yinsi2.isChecked()) {
            resbtn.setEnabled(false);
        } else {
            resbtn.setEnabled(true);
        }
    }

}