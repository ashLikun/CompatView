package com.ashlikun.compatview.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.app = getApplication();
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
    }
}
