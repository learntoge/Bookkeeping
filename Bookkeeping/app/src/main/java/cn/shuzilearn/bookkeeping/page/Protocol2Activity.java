package cn.shuzilearn.bookkeeping.page;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import cn.shuzilearn.bookkeeping.BookkeepingActivity;
import cn.shuzilearn.bookkeeping.R;

import java.util.Objects;

public class Protocol2Activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_protocol2);

        WebView show = findViewById(R.id.Protocol2);
        show.loadUrl("file:///android_asset/protocol2.html");

        ImageView save = findViewById(R.id.savetext);
        save.setVisibility(View.GONE);

        TextView title = findViewById(R.id.title);
        title.setText("《隐私保护策略》");

        findViewById(R.id.goback).setOnClickListener(this);
        findViewById(R.id.goP1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.goback) {
            Intent i = new Intent(this, BookkeepingActivity.class);
            startActivity(i);
            finish();
        }
        if (v.getId() == R.id.goP1) {
            Intent i = new Intent(this, Protocol1Activity.class);
            startActivity(i);
            finish();
        }
    }
}