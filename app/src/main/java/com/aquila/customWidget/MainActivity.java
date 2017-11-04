package com.aquila.customWidget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aquila.customWidget.log.CLog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CLog.debug();
        CLog.v();
        CLog.d();
        CLog.i();
        CLog.e();
        CLog.w();
    }
}
