package com.mrtrying.widget.expandabletext.example;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mrtrying.widget.expandabletext.ExpandLayout;

public class SecondActivity extends AppCompatActivity {

    private ExpandLayout expandLayout;
    private Button btnOpen;
    private Button btnClose;
    private Button btn_add;
    private LinearLayout llContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initView();
    }

    private void initView() {
        expandLayout = (ExpandLayout) findViewById(R.id.expand_layout);
        expandLayout.initExpand(false);
        btnOpen = (Button) findViewById(R.id.btn_open);
        btn_add = (Button) findViewById(R.id.btn_add);
        btnClose = (Button) findViewById(R.id.btn_close);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandLayout.toggleExpand();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandLayout.toggleExpand();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = new TextView(SecondActivity.this);
                textView.setText("测试");
                textView.setTextSize(18);
                llContent.addView(textView);
            }
        });
        llContent = (LinearLayout) findViewById(R.id.ll_content);
    }
}
