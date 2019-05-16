package com.example.helloworld.information;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;


import com.example.helloworld.COM.MyApp;
import com.example.helloworld.COM.com;
import com.example.helloworld.R;
import com.example.helloworld.setting.AccountInforActivity;
import com.example.helloworld.setting.SettingActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InforActivity extends Activity {
    private NodeProgressView nodeProgressView;
    private CircleProgressBar f1; // 自定义的进度条
    private SeekBar s1; // 拖动条
    private CircleProgressBar f2; // 自定义的进度条
    private SeekBar s2; // 拖动条
    private CircleProgressBar f3; // 自定义的进度条
    private SeekBar s3; // 拖动条
    private int[] colors = new int[] { Color.parseColor("#27B197"), Color.parseColor("#00A6D5") };
    private TextView food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodinformation);
        MyApp.getAppManager().addActivity(this);

        food = (TextView) findViewById(R.id.food);
        f1 = (CircleProgressBar)findViewById(R.id.f1);
        f1.setProgress(20,true);

        f2 = (CircleProgressBar)findViewById(R.id.f2);
        f2.setProgress(30,true);

        f3 = (CircleProgressBar)findViewById(R.id.f3);
        f3.setProgress(40,true);

        nodeProgressView = findViewById(R.id.node_progress);
        //还需要界面标签
        System.out.println("test0");
        getInfor();
    }

    public void toSetting(View  view){
        Intent intent = new Intent();
        Bundle bundle = this.getIntent().getExtras();
        intent.setClass(InforActivity.this,SettingActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void getInfor(){
        //Bundle bundle = this.getIntent().getExtras();
        System.out.println("test1");
        //获取食品id
        //final String foodid = bundle.getString("foodid");
        final String foodid = "1";
        Thread infoRunnable = new Thread(){
            @Override
            public void run() {
                super.run();
                Connection con = null;
                try {
                    con = com.getCon();
                    String sql = "select * from food_composition where id = 1";
                    Statement st = (Statement) con.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    if (rs.next()) {
                        final int id = rs.getInt("id");
                        final String name = rs.getString("name");
                        final int calorie = rs.getInt("calorie");
                        final float water = rs.getFloat("water");
                        final float protein = rs.getFloat("protein");
                        final float fat = rs.getFloat("fat");
                        final float dietary_fiber = rs.getFloat("dietary_fiber");
                        final float carbohydrate = rs.getFloat("carbohydrate");
                        final float vitaminA = rs.getFloat("vitaminA");
                        final float vitaminB1 = rs.getFloat("vitaminB1");
                        final float vitaminB2 = rs.getFloat("vitaminB2");
                        final float vitaminC = rs.getFloat("vitaminC");
                        final float vitaminE = rs.getFloat("vitaminE");
                        final float Na = rs.getFloat("Na");
                        final float Ca = rs.getFloat("Ca");
                        final float Fe = rs.getFloat("Fe");
                        final float cholesterol = rs.getFloat("cholesterol");
                        //final int type = rs.getInt("type");

                        InforActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //修改界面
                                System.out.println(name);
                                food.setText(name);
                                int p = (int)protein;
                                int f = (int)fat;
                                int c = (int)carbohydrate;
                                f1.setProgress(p,true);
                                f2.setProgress(f,true);
                                f3.setProgress(c,true);
                            }
                        });

                    }
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };

        infoRunnable.start();
    }
}
