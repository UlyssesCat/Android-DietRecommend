package com.example.helloworld.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.helloworld.COM.MyApp;
import com.example.helloworld.COM.com;
import com.example.helloworld.R;
import com.example.helloworld.datepicker.CustomDatePicker;
import com.example.helloworld.datepicker.DateFormatUtils;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class AddSelfInforActivity1 extends Activity implements View.OnClickListener {


    private CheckBox check_sex;

    private CheckBox btn_register_info_sex;
    private TextView mTvSelectedDate;
    private CustomDatePicker mDatePicker;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addselfinfor1);
        MyApp.getAppManager().addActivity(this);

        check_sex = (CheckBox) findViewById(R.id.btn_register_info_sex);
        btn_register_info_sex = (CheckBox) findViewById(R.id.btn_register_info_sex);

        findViewById(R.id.ll_date).setOnClickListener(this);

        mTvSelectedDate = findViewById(R.id.tv_selected_date);

        editText = findViewById(R.id.Name);

        initDatePicker();

/*
        check_sex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    btn_register_info_sex.setText("woman");
                }else{
                    btn_register_info_sex.setText("man");
                }
            }
        });*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_date:
                // 日期格式为yyyy-MM-dd
                mDatePicker.show(mTvSelectedDate.getText().toString());
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatePicker.onDestroy();
    }

    private void initDatePicker() {
        long beginTimestamp = DateFormatUtils.str2Long("1990-01-01", false);
        long endTimestamp = System.currentTimeMillis();

        mTvSelectedDate.setText(DateFormatUtils.long2Str(endTimestamp, false));

        // 通过时间戳初始化日期，毫秒级别
        mDatePicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                mTvSelectedDate.setText(DateFormatUtils.long2Str(timestamp, false));
            }
        }, beginTimestamp, endTimestamp);
        // 不允许点击屏幕或物理返回键关闭
        mDatePicker.setCancelable(false);
        // 不显示时和分
        mDatePicker.setCanShowPreciseTime(false);
        // 不允许循环滚动
        mDatePicker.setScrollLoop(false);
        // 不允许滚动动画
        mDatePicker.setCanShowAnim(false);
    }

    public void upload() {
        Thread uploadRunnable = new Thread() {
            @Override
            public void run() {
                super.run();
                Connection con = null;
                try {
                    con = com.getCon();
                    Intent intent = getIntent();
                    Bundle bundle = intent.getExtras();
                    String username = bundle.getString("username");
                    String sex = (btn_register_info_sex.isChecked()) ? "woman" : "man";
                    String nickname = editText.getText().toString();
                    String date = mTvSelectedDate.getText().toString();
                    Bundle bundle1=new Bundle();
                    Intent intent1 = new Intent();
                    intent1.setClass(AddSelfInforActivity1.this,AddSelfInforActivity2.class);
                    bundle1.putString("sex",sex);
                    intent1.putExtras(bundle);
                    bundle1.putString("birthday",date);
                    intent1.putExtras(bundle);
                    String sql = "update user set sex = '" + sex + "' , user_name = '"+nickname+"' , birthdate = '"+date+"' where user_id = " + username + ";";
                    System.out.println(sql);

                    Statement st = (Statement) con.createStatement();
                    st.executeUpdate(sql);

                    startActivity(intent1);

                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        uploadRunnable.start();
    }

    public void toNext(View view) {



        upload();



    }
}
