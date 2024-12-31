package cn.shuzilearn.bookkeeping.page;

import android.os.Bundle;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import cn.shuzilearn.bookkeeping.R;
import cn.shuzilearn.bookkeeping.utils.AlertDialogUtil;

public class AboutsoftwareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_aboutsoftware);

        // 获取 WebView 控件
        WebView webView = findViewById(R.id.aboutSoft);

        // 启用 JavaScript 支持（如果需要）
        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);

        // 加载 assets 目录下的 HTML 文件
        webView.loadUrl("file:///android_asset/aboutapp.html");

        findViewById(R.id.goback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}