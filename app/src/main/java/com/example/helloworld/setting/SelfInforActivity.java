package com.example.helloworld.setting;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.helloworld.COM.MyApp;
import com.example.helloworld.COM.com;
import com.example.helloworld.R;
import com.example.helloworld.login.LoginActivity;
import com.example.helloworld.login.SharedPreferencesUtils;
import com.example.helloworld.optionframe.OptionMaterialDialog;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SelfInforActivity extends Activity {
    private ImageView imageView;
    private CheckBox checkBox;
    private RulerView ruler_height;   //身高的view
    private RulerView ruler_weight;  //体重的view
    private TextView tv_register_info_height_value, tv_register_info_weight_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfinfor);

        checkBox = (CheckBox) findViewById(R.id.btn_register_info_sex);
        ruler_height = (RulerView) findViewById(R.id.ruler_height);
        ruler_weight = (RulerView) findViewById(R.id.ruler_weight);

        tv_register_info_height_value = (TextView) findViewById(R.id.tv_register_info_height_value);
        tv_register_info_weight_value = (TextView) findViewById(R.id.tv_register_info_weight_value);


        ruler_height.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                tv_register_info_height_value.setText(value + "");
            }
        });


        ruler_weight.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                tv_register_info_weight_value.setText(value + "");
            }
        });
        getInfor();
MyApp.getAppManager().addActivity(this);
    }

    public void toSetting(View view) {
        Intent intent = new Intent();
        Bundle bundle = this.getIntent().getExtras();
        intent.setClass(SelfInforActivity.this, SettingActivity.class);
        intent.putExtras(bundle);
        startActivity(new Intent(SelfInforActivity.this, SettingActivity.class));
        finish();
    }

    public void getInfor() {
        Bundle bundle = this.getIntent().getExtras();
        final String userid = bundle.getString("username");
        System.out.println("用户名：" + userid);
        Thread infoRunnable = new Thread() {
            @Override
            public void run() {
                super.run();
                Connection con = null;
                try {
                    System.out.println("用户名：" + userid);
                    con = com.getCon();
                    String sql = "select * from user where user_id =" + userid;
                    Statement st = (Statement) con.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    if (rs.next()) {

                        final String name = rs.getString("user_name");
                        final String date = rs.getDate("birthdate").toString();
                        final String sex = rs.getString("sex");
                        final String height = rs.getString("height");
                        final String weight = rs.getString("weight");
                        SelfInforActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ruler_height.setValue(Float.parseFloat(height), 80, 250, 1);
                                ruler_weight.setValue(Float.parseFloat(weight), 20, 200, 0.1f);
                                tv_register_info_height_value.setText(height + "");
                                tv_register_info_weight_value.setText(weight + "");
                                switch (sex) {
                                    case "man":
                                        checkBox.setChecked(false);
                                        break;
                                    case "women":
                                        checkBox.setChecked(true);
                                        break;
                                }
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
                    String current_sex = (checkBox.isChecked()) ? "woman" : "man";
                    String current_height = tv_register_info_height_value.getText().toString();
                    String current_weight = tv_register_info_weight_value.getText().toString();
                    String sql = "update user set sex='" + current_sex + "', height = '" + Float.parseFloat(current_height) + "' , weight = '" + Float.parseFloat(current_weight) + "' where user_id = " + username + ";";
                    System.out.println(sql);
                    Statement st = (Statement) con.createStatement();
                    st.executeUpdate(sql);
//                    bundle.putString("sex", current_sex);
//                    intent.putExtras(bundle);
//                    bundle.putString("height", current_height);
//                    intent.putExtras(bundle);
//                    bundle.putString("weight", current_weight);
//                    intent.putExtras(bundle);


                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        uploadRunnable.start();
    }

    public void UpdateDialog(View view) {
        final OptionMaterialDialog mMaterialDialog = new OptionMaterialDialog(SelfInforActivity.this);
        mMaterialDialog
//                .setTitle("这是标题")
//                .setTitleTextColor(R.color.colorPrimary)
//                .setTitleTextSize((float) 22.5)
                .setMessage("确认修改？")
                .setMessageTextColor(R.color.black)
                .setMessageTextSize((float) 16.5)
                .setPositiveButtonTextColor(R.color.black)
                .setNegativeButtonTextColor(R.color.black)
                .setPositiveButtonTextSize(15)
                .setNegativeButtonTextSize(15)
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                        upload();
                    }
                })
                .setNegativeButton("取消",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();
                            }
                        })
                .setCanceledOnTouchOutside(true)
                .setOnDismissListener(
                        new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
//                                Toast.makeText(MainActivity.this, "onDismiss 取消", Toast.LENGTH_SHORT).show();
                            }
                        })
                .show();
    }
}
