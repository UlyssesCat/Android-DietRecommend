package com.example.helloworld.Home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


import com.example.helloworld.R;
import com.example.helloworld.TabLayout.SegmentTabActivity;
import com.example.helloworld.information.CircleProgressBar;
import com.example.helloworld.COM.com;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;


public class BaseFragment1 extends Fragment {
    private CircleProgressBar weight_acount; // 自定义的进度条
    private CircleProgressBar hot_acount;
    private SeekBar seekbar; // 拖动条
    private Button button1;
    private Button button2;
    private int[] colors = new int[] { Color.parseColor("#27B197"), Color.parseColor("#00A6D5") };
    private double Cll;
    private TextView cal;

    public static BaseFragment1 newInstance(String info) {
        Bundle args = new Bundle();
        BaseFragment1 fragment = new BaseFragment1();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_base1,null);
        weight_acount = (CircleProgressBar)view.findViewById(R.id.weight_count);
        cal = (TextView)view.findViewById(R.id.cal);
        weight_acount.setProgress(30,true);
        hot_acount = (CircleProgressBar)view.findViewById(R.id.hot_count);
        button1= (Button)view.findViewById(R.id.toSuggest);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = getActivity().getIntent().getExtras();
                intent.setClass(getActivity(), SegmentTabActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        button2= (Button)view.findViewById(R.id.toReport);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = getActivity().getIntent().getExtras();
                intent.setClass(getActivity(), HealthReportActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        try {
            System.out.println("测试1");
            getCll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return view;
    }
    public void getCll() throws InterruptedException {
        Bundle bundle = getActivity().getIntent().getExtras();
        final String username = bundle.getString("username");
        final Thread cllRunnable = new Thread() {
            @Override
            public void run() {
                super.run();
                Connection con = null;
                try {
                    con = com.getCon();
                    System.out.println(username);
                    String sql = "select * from user where user_id =" + username;
                    Statement st = (Statement) con.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    if (rs.next()) {
                        final int userid = rs.getInt("user_id");
                        System.out.println(userid);
                        final String bitrhdate = rs.getDate("birthdate").toString();
                        final double weight = rs.getDouble("weight");
                        final double height = rs.getDouble("height");
                        final String sex = rs.getString("sex");
                        String b = bitrhdate.substring(0, 4);
                        int bdate = Integer.parseInt(b);
                        Date nowdate = new Date();
                        Calendar cal = Calendar.getInstance();
                        int age = cal.get(Calendar.YEAR) - bdate;
                        if (sex.equals("woman")) {
                            Cll = (655 + 9.6 * weight + 1.7 * height - 4.7 * age);
                        } else {
                            Cll = (66 + 13.7 * weight + 5 * height - 6.8 * age);
                        }
                    }
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }

        };

        Thread RestRunnable = new Thread() {
            @Override
            public void run() {
                super.run();
                Connection con = null;
                Date nowdate = new Date();
                Calendar calendar = Calendar.getInstance();
                double totalCal = 0;
                String date = calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+1+"-"+calendar.get(Calendar.DATE)+1;
                System.out.println(username+" "+date);
                try {
                    con = com.getCon();
                    String sql = "select * from diets_user where user_id =" + username +" and date ="+date;
                    Statement st = (Statement) con.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while (rs.next()) {
                        double calorie = rs.getDouble("calorie");
                        totalCal += calorie;
                    }
                    int restCal = (int)(Cll-totalCal);
                    cal.setText("你还能吃"+restCal+"千卡");
                    int bfb = (int)(restCal*100/Cll);
                    hot_acount.setProgress(bfb,false);


         /*           SegmentTabActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SimpleCardFragment fs = SimpleCardFragment.newInstance("breakfast");
                            fs.setView(food1,food2,food3);
                            mFragments.remove(0);
                            mFragments.add(fs);
                        }
                    });
*/
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }

        };



        cllRunnable.start();
        cllRunnable.join();
        RestRunnable.start();
        RestRunnable.join();
    }
}
