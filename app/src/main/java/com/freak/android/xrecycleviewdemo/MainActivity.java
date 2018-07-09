package com.freak.android.xrecycleviewdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.freak.android.xrecycleviewdemo.linearLayout.LinearLayoutActivity;

public class MainActivity extends AppCompatActivity {
    private Button ll_linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll_linear=findViewById(R.id.ll_linear);
        ll_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent=new Intent();
              intent.setClass(MainActivity.this,LinearLayoutActivity.class);
              startActivity(intent);
            }
        });
    }
}
