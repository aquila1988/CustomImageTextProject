package com.aquila.customWidget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.aquia.sp.viewer.SPFileListActivity;
import com.aquila.commutil.CLog;


public class MainActivity extends AppCompatActivity  implements OnClickListener{

    Button clickButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clickButton = (Button) findViewById(R.id.click_button);
        clickButton.setOnClickListener(this);

        CLog.debug();
        CLog.v();
        CLog.d();
        CLog.i();
        CLog.e();
        CLog.w();

    }

    @Override
    public void onClick(View v) {
        if (v == clickButton){
            Intent intent = new Intent(this, SPFileListActivity.class);
            startActivity(intent);
        }
    }
}
