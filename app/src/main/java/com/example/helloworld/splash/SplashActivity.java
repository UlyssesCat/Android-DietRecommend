package com.example.helloworld.splash;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.helloworld.COM.MyApp;
import com.example.helloworld.MainActivity;
import com.example.helloworld.R;
import com.example.helloworld.login.LoginActivity;

public class SplashActivity extends Activity {
    private static final long DELAY_TIME=3000L;
    private boolean checked = false;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        MyApp.getAppManager().addActivity(this);

        button = findViewById(R.id.btn1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked = true;
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!checked) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        },DELAY_TIME);
    }
}
