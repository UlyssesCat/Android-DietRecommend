package com.example.helloworld.Home;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.helloworld.COM.MyApp;
import com.example.helloworld.MainActivity;
import com.example.helloworld.R;


public class HealthReportActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApp.getAppManager().addActivity(this);

        setContentView(R.layout.activity_healthreport);
    }
    public void close(View view){
        Intent intent = new Intent();
        Bundle bundle = this.getIntent().getExtras();
        intent.setClass(HealthReportActivity.this,MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
