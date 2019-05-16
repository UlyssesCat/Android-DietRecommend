package com.example.helloworld.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.helloworld.COM.MyApp;
import com.example.helloworld.COM.com;
import com.example.helloworld.MainActivity;
import com.example.helloworld.R;
import com.example.helloworld.setting.RulerView;


public class AddSelfInforActivity2 extends Activity {
    private ImageView imageView;
    private RulerView ruler_height;   //身高的view
    private RulerView ruler_weight ;  //体重的view

    private TextView tv_register_info_height_value,tv_register_info_weight_value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addselfinfor2);

        MyApp.getAppManager().addActivity(this);

        ruler_height=(RulerView)findViewById(R.id.ruler_height);
        ruler_weight=(RulerView)findViewById(R.id.ruler_weight);


        tv_register_info_height_value=(TextView) findViewById(R.id.tv_register_info_height_value);
        tv_register_info_weight_value=(TextView) findViewById(R.id.tv_register_info_weight_value);

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

        ruler_height.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                tv_register_info_height_value.setText(value+"");
            }
        });


        ruler_weight.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                tv_register_info_weight_value.setText(value+"");
            }
        });

        ruler_height.setValue(165, 80, 250, 1);

        ruler_weight.setValue(55, 20, 200, 0.1f);


    }
    public void upload(){
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

                    String height = tv_register_info_height_value.getText().toString(), weight = tv_register_info_weight_value.getText().toString();
                    Intent intent1 = new Intent();
                    Bundle bundle1 = new Bundle();
                    intent1.setClass(AddSelfInforActivity2.this,MainActivity.class);
                    bundle1.putString("height",height);
                    intent1.putExtras(bundle);
                    bundle1.putString("weight",weight);
                    intent1.putExtras(bundle);
                    String sql = "update user set height = '" + Float.parseFloat(height) + "' , weight = '" + Float.parseFloat(weight) + "' where user_id = " + username + ";";
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
    public void toLast(View view){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        intent.setClass(AddSelfInforActivity2.this, AddSelfInforActivity1.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
    public void Complete(View view){
        upload();
    }
}
