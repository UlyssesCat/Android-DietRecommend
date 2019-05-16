package com.example.helloworld.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.helloworld.COM.MyApp;
import com.example.helloworld.COM.com;
import com.example.helloworld.R;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class ChangePasswordActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        MyApp.getAppManager().addActivity(this);

    }
    public void toAccountInfor(View view) {
//        Intent intent = new Intent();
//        Bundle bundle = this.getIntent().getExtras();
//        intent.setClass(ChangePasswordActivity.this, AccountInforActivity1.class);
//        intent.putExtras(bundle);
//        startActivity(intent);
        finish();
    }


    public void changePassword(final View view){
        Bundle bundle = this.getIntent().getExtras();
        final String userid = bundle.getString("username");//当前登陆的用户id

        EditText editText1 =(EditText)findViewById(R.id.oldPassword);
        final String str1=editText1.getText().toString();//当前文本框的值

        EditText editText2 =(EditText)findViewById(R.id.newPassword1);
        final String str2=editText2.getText().toString();//当前文本框的值

        EditText editText3 =(EditText)findViewById(R.id.newPassword2);
        final String str3=editText3.getText().toString();//当前文本框的值



        if(str1.equals("")||str2.equals("")||str3.equals("")){
            Toast.makeText(ChangePasswordActivity.this,"输入不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!str1.equals(bundle.getString("password"))){
            Toast.makeText(ChangePasswordActivity.this,"原密码错误",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!str2.equals(str3)){
            Toast.makeText(ChangePasswordActivity.this,"两次输入的新密码不一致",Toast.LENGTH_SHORT).show();
            return;
        }

//        Bundle bundle = this.getIntent().getExtras();
//        for (String key: bundle.keySet())
//        {
//            System.out.println("Bundle Content"+"Key="+key+",content="+bundle.getString(key));
//        }//获取所有bundle信息


        Thread changePasswordThread = new Thread(){
            @Override
            public void run() {
                super.run();
                Connection con = null;
                try {
                    con = com.getCon();
                    String sql = "update login set password = '" + str2 + "' where id = " + userid + ";";
                    Statement st = (Statement) con.createStatement();
                    st.execute(sql);
                    toAccountInfor(view);


                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        changePasswordThread.start();
    }

}
