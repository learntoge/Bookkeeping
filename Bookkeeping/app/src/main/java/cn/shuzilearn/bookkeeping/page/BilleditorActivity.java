package cn.shuzilearn.bookkeeping.page;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import cn.shuzilearn.bookkeeping.BookkeepingActivity;
import cn.shuzilearn.bookkeeping.R;
import cn.shuzilearn.bookkeeping.application.Myapplication;
import cn.shuzilearn.bookkeeping.dao.billDao;
import cn.shuzilearn.bookkeeping.filter.AmountInputFilter;
import cn.shuzilearn.bookkeeping.pojo.bill;
import cn.shuzilearn.bookkeeping.utils.ToastUtil;

import java.time.LocalDate;
import java.util.Objects;

public class BilleditorActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText amount;
    private EditText remark;
    private Button amountTime;
    private Button classical;
    private EditText classicalText;
    private EditText amountTimeText;
    private billDao billDao;
    private LinearLayout linearLayout;
    //    private Intent intent2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_billeditor);

        findViewById(R.id.save1).setOnClickListener(this);
        findViewById(R.id.goback).setOnClickListener(this);

        amount = findViewById(R.id.amount);
        classical = findViewById(R.id.classical);
        classicalText = findViewById(R.id.classicalText);
        amountTime = findViewById(R.id.amountTime);
        amountTimeText = findViewById(R.id.amountTimeText);
        remark = findViewById(R.id.remark);
        linearLayout = findViewById(R.id.Editorbook);
        amount.setFilters(new InputFilter[]{new AmountInputFilter(10, 2)});
        classical.setOnClickListener(this);
        amountTime.setOnClickListener(this);

        billDao = Myapplication.getInstance().getDatabase().billdao();

        // 修改时渲染传递的数据
        Intent intent = getIntent();
        int id = intent.getIntExtra("id1", -1);
        if (id != -1) {
            double amount1 = intent.getDoubleExtra("amount1", 0.0);
            String classify1 = intent.getStringExtra("classify1");
            String time1 = intent.getStringExtra("time1");
            String remark1 = intent.getStringExtra("remark1");

            // ToastUtil.showToast_s(this, );
            amount.setText(String.valueOf(amount1));
            remark.setText(remark1);
            classicalText.setText(classify1);
            amountTimeText.setText(time1);
            linearLayout.setTag(id);
            // intent2 = new Intent(this, BookkeepingActivity.class);


        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.goback) {
            finish();
        }
        if (v.getId() == R.id.classical) {
            // 获取布局
            LayoutInflater inflater = getLayoutInflater();
            View group = inflater.inflate(R.layout.class_alter, null);


            // 渲染数据
            RadioGroup radioGroup = group.findViewById(R.id.classButton);
            xuanranButton(radioGroup);

            // 创建弹窗
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("请选择类别");
            builder.setView(group);
            builder.setPositiveButton("确认", (a, b) -> {
                RadioButton chooseBtn = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                classicalText.setText(chooseBtn.getText());
            });
            builder.setNegativeButton("取消", null);
            builder.show();


        }
        if (v.getId() == R.id.amountTime) {
            LocalDate now = LocalDate.now();
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    // 这里可以处理选择的日期
                    String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    amountTimeText.setText(selectedDate);

                }
            }, now.getYear(), now.getMonthValue() - 1, now.getDayOfMonth());

            // 显示日期选择对话框
            datePickerDialog.show();
        }

        if (v.getId() == R.id.save1) {
            double money = Double.parseDouble(amount.getText().toString());
            String className = classicalText.getText().toString();
            String date = amountTimeText.getText().toString();
            String remarkText = remark.getText().toString();

            if ((String.valueOf(money)).isEmpty() || className.isEmpty() || date.isEmpty()) {
                ToastUtil.showToast_s(this, "请填写包含(*)的必要信息");
            } else {
                bill bill = new bill();
                Object id1 = linearLayout.getTag();
                if (id1 != null) {
                    bill.setId(Integer.parseInt(id1.toString()));
                }
                bill.setAmount(money);
                bill.setClassify(className);
                bill.setTime(date);
                if (!remarkText.isEmpty()) {
                    bill.setRemark(remarkText);
                }
                if (id1 != null) {
                    billDao.update(bill);
                    ToastUtil.showToast_s(this, "账单修改成功！");
                    //                    startActivity(intent2);
                    finish();
                } else {
                    billDao.insert(bill);
                    ToastUtil.showToast_s(this, "账单保存成功！");
                    //                    startActivity(intent2);
                    finish();
                }


            }
        }
    }

    private void xuanranButton(RadioGroup radioGroup) {
        // 动态生成 RadioButton
        String[] options = new String[]{"餐饮", "水果", "服装", "零食", "饮料", "交通", "娱乐", "生活缴费", "其他",};
        for (int i = 0; i < options.length; i++) {
            RadioButton radioButton = new RadioButton(this);
            if (i == 0) {
                radioButton.setChecked(true);
            }
            radioButton.setId(View.generateViewId()); // 为每个 RadioButton 生成唯一 ID
            radioButton.setText(options[i]);
            radioButton.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT));
            radioGroup.addView(radioButton);
        }
    }
}