package cn.shuzilearn.bookkeeping.page;

import android.os.Bundle;

import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import cn.shuzilearn.bookkeeping.R;
import cn.shuzilearn.bookkeeping.utils.AlertDialogUtil;

public class AboutdevelopersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       getSupportActionBar().hide();

        setContentView(R.layout.activity_aboutdevelopers);
        findViewById(R.id.goback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}