package com.example.helloworld.setting;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.helloworld.COM.MyApp;
import com.example.helloworld.R;


public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        MyApp.getAppManager().addActivity(this);

    }
    public void toSetting(View view){
//        Intent intent = new Intent();
//        Bundle bundle = this.getIntent().getExtras();
//        intent.setClass(AboutActivity.this,SettingActivity.class);
//        intent.putExtras(bundle);
//        startActivity(intent);
        finish();
    }
}
