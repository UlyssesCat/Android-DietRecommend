package com.example.helloworld.TabLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.helloworld.COM.MyApp;
import com.example.helloworld.COM.com;
import com.example.helloworld.MainActivity;
import com.example.helloworld.R;
import com.example.helloworld.datepicker.CustomDatePicker;
import com.example.helloworld.datepicker.DateFormatUtils;
import com.example.helloworld.login.AddSelfInforActivity2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;


public class ReplaceFoodActivity extends Activity {
    private Button B1;
    private Button B2;
    private Button B3;
    private Button B4;
    private Button B5;
    private Button B6;
    private Button B7;
    private Button B8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replacefood);
        MyApp.getAppManager().addActivity(this);
        B1 = (Button)findViewById(R.id.B1);
        B2 = (Button)findViewById(R.id.B2);
        B3 = (Button)findViewById(R.id.B3);
        B4 = (Button)findViewById(R.id.B4);
        B5 = (Button)findViewById(R.id.B5);
        B6 = (Button)findViewById(R.id.B6);
        B7 = (Button)findViewById(R.id.B7);
        B8 = (Button)findViewById(R.id.B8);


        int max=1;
        int min=1000;
        Random random = new Random();
        final int s1 = random.nextInt(min-max) + max;System.out.println(s1);
        final int s2 = random.nextInt(min-max) + max;System.out.println(s2);
        final int s3 = random.nextInt(min-max) + max;System.out.println(s3);
        final int s4 = random.nextInt(min-max) + max;System.out.println(s4);
        final int s5 = random.nextInt(min-max) + max;System.out.println(s5);
        final int s6 = random.nextInt(min-max) + max;System.out.println(s6);
        final int s7 = random.nextInt(min-max) + max;System.out.println(s7);
        final int s8 = random.nextInt(min-max) + max;System.out.println(s8);

        Thread getRunnable = new Thread() {
            @Override
            public void run() {
                Connection con = null;
                try {
                    con=com.getCon();
                    Statement st=(Statement)con.createStatement();
                    String sql = "select * from food_composition where type = "+SimpleCardFragment.type+";";
                    System.out.println(sql);
                    ResultSet rs=st.executeQuery(sql);
                    int count = 0;
                    while (rs.next()) {
                        if (count==0){
                            TextView r1 = (TextView)findViewById(R.id.R1);
                            r1.setText(rs.getString("name"));
                            TextView c1 = (TextView)findViewById(R.id.C1);
                            c1.setText(rs.getInt("calorie")+"");
                        }
                        if (count==1){
                            TextView r1 = (TextView)findViewById(R.id.R2);
                            r1.setText(rs.getString("name"));
                            TextView c1 = (TextView)findViewById(R.id.C2);
                            c1.setText(rs.getInt("calorie")+"");
                        }
                        if (count==2){
                            TextView r1 = (TextView)findViewById(R.id.R3);
                            r1.setText(rs.getString("name"));
                            TextView c1 = (TextView)findViewById(R.id.C3);
                            c1.setText(rs.getInt("calorie")+"");
                        }
                        if (count==3){
                            TextView r1 = (TextView)findViewById(R.id.R4);
                            r1.setText(rs.getString("name"));
                            TextView c1 = (TextView)findViewById(R.id.C4);
                            c1.setText(rs.getInt("calorie")+"");
                        }
                        if (count==4){
                            TextView r1 = (TextView)findViewById(R.id.R5);
                            r1.setText(rs.getString("name"));
                            TextView c1 = (TextView)findViewById(R.id.C5);
                            c1.setText(rs.getInt("calorie")+"");
                        }
                        if (count==5){
                            TextView r1 = (TextView)findViewById(R.id.R6);
                            r1.setText(rs.getString("name"));
                            TextView c1 = (TextView)findViewById(R.id.C6);
                            c1.setText(rs.getInt("calorie")+"");
                        }
                        if (count==6){
                            TextView r1 = (TextView)findViewById(R.id.R7);
                            r1.setText(rs.getString("name"));
                            TextView c1 = (TextView)findViewById(R.id.C7);
                            c1.setText(rs.getInt("calorie")+"");
                        }if (count==7){
                            TextView r1 = (TextView)findViewById(R.id.R8);
                            r1.setText(rs.getString("name"));
                            TextView c1 = (TextView)findViewById(R.id.C8);
                            c1.setText(rs.getInt("calorie")+"");
                        }
                        count++;

                    }

                    B1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent();
                            intent.setClass(ReplaceFoodActivity.class, .class);
                            startActivity(intent);
                        }

                    });
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            }
        };
        getRunnable.start();
    }



    public void toReplace(View view) {

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        intent.setClass(ReplaceFoodActivity.this, MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
