package cn.shuzilearn.bookkeeping.page;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import cn.shuzilearn.bookkeeping.R;
import cn.shuzilearn.bookkeeping.dao.noteDao;
import cn.shuzilearn.bookkeeping.pojo.note;
import cn.shuzilearn.bookkeeping.utils.ToastUtil;
import cn.shuzilearn.bookkeeping.application.Myapplication;

import java.time.LocalDate;
import java.util.Objects;


public class EditnoteActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText text_view;
    private EditText title_view;
    private ImageView back;
    private ImageView save;
    private noteDao notedao;
    private int id;
    private String title;
    private String content;
    private String createTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editnote);
        Objects.requireNonNull(getSupportActionBar()).hide();

        title_view = findViewById(R.id.note_title);
        text_view = findViewById(R.id.note_text);
        back = findViewById(R.id.goback);
        save = findViewById(R.id.savetext);
        back.setOnClickListener(this);
        save.setOnClickListener(this);
        notedao = Myapplication.getInstance().getDatabase().notedao();

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getIntExtra("id",-1);
            title = intent.getStringExtra("title");
            content = intent.getStringExtra("content");
            createTime = intent.getStringExtra("jointime");
//            title_view.setId(Integer.parseInt(id));
            title_view.setTag(id);
            title_view.setText(title);
            text_view.setText(content);
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.goback){
            finish();
        }
        if (v.getId()==R.id.savetext){
            if (id==-1){
                ToastUtil.showToast_s(this,"保存成功！");
                LocalDate date = LocalDate.now();
                notedao.insert(new note(title_view.getText().toString(),text_view.getText().toString(),date.toString()));
//                finish();
            }
            else {
                ToastUtil.showToast_s(this,"修改成功！");
                note n = new note();
                n.setId(id);
                n.setTitle(title_view.getText().toString());
                n.setContent(text_view.getText().toString());
                n.setCreateTime(createTime);
                notedao.update(n);
//                finish();

            }


        }
    }
}