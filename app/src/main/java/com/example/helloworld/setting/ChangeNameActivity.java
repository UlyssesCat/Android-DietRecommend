package com.example.helloworld.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.COM.MyApp;
import com.example.helloworld.COM.com;
import com.example.helloworld.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ChangeNameActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changename);
        MyApp.getAppManager().addActivity(this);


    }
     public void toAccountInfor(View view){
         Intent intent = new Intent();
         Bundle bundle = this.getIntent().getExtras();
         intent.setClass(ChangeNameActivity.this,AccountInforActivity.class);
         intent.putExtras(bundle);
         startActivity(intent);
         finish();
     }

    public void changeName(final View view){

        EditText editText1 =(EditText)findViewById(R.id.newName);
        final String str1=editText1.getText().toString();//当前文本框的值

        if(str1.trim().equals("")){
            Toast.makeText(ChangeNameActivity.this,"请输出正确的账户名",Toast.LENGTH_SHORT).show();
            return;
        }

//        Bundle bundle = this.getIntent().getExtras();
//        for (String key: bundle.keySet())
//        {
//            System.out.println("Bundle Content"+"Key="+key+",content="+bundle.getString(key));
//        }//获取所有bundle信息

        Bundle bundle = this.getIntent().getExtras();
        final String userid = bundle.getString("username");//当前登陆的用户id

        Thread changeNameThread = new Thread(){
            @Override
            public void run() {
                super.run();
                Connection con = null;
                try {
                    con = com.getCon();
                    String sql = "update user set user_name = '" + str1 + "' where user_id = " + userid + ";";
                    Statement st = (Statement) con.createStatement();
                    st.execute(sql);
                    toAccountInfor(view);


                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        changeNameThread.start();
    }

}
