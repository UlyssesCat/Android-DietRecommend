package com.example.helloworld.setting;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.COM.MyApp;
import com.example.helloworld.COM.com;
import com.example.helloworld.R;
import com.example.helloworld.crop.ChangePictureActivity;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.io.Console;


public class AccountInforActivity extends Activity {
    private static final String TAG_SERVICE = "";
    private ImageView imageView;
    private TextView getTime;
    private Calendar calendar;// 用来装日期的
    private DatePickerDialog dialog;
    private TextView username;
    private TextView birthday;
    private ImageView imageVi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountinfor);
        MyApp.getAppManager().addActivity(this);

        username = (TextView) findViewById(R.id.username);
        birthday = (TextView) findViewById(R.id.birthday);
        imageVi = (ImageView) findViewById(R.id.image);
        getInfor();

    }

    public void changeTime(View view) {
        Bundle bundle = this.getIntent().getExtras();
        final String userid = bundle.getString("username");//当前登陆的用户id

        getTime = (TextView) findViewById(R.id.time);
        calendar = Calendar.getInstance();
        dialog = new DatePickerDialog(AccountInforActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        getTime.setText(year + "-" + monthOfYear + "-"
                                + dayOfMonth);
                        System.out.println("new date:"+getTime.getText().toString() );

                        Thread changeDateThread = new Thread(){
                            @Override
                            public void run() {
                                super.run();
                                Connection con = null;
                                try {
                                    con = com.getCon();
                                    String sql = "update user set birthdate = '" + getTime.getText().toString() + "' where user_id = " + userid + ";";
                                    System.out.println(sql);
                                    Statement st = (Statement) con.createStatement();
                                    st.execute(sql);
                                    getInfor();///////////////

                                } catch (SQLException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        };

                        changeDateThread.start();


                    }
                }, calendar.get(Calendar.YEAR), calendar
                .get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH));
        dialog.show();




    }
    public void toSetting(View  view){
//        Intent intent = new Intent();
//        Bundle bundle = this.getIntent().getExtras();
//        intent.setClass(AccountInforActivity1.this,SettingActivity.class);
//        intent.putExtras(bundle);
//        startActivity(intent);
        finish();
    }
    public void toChangeName(View view){
        Intent intent = new Intent();
        Bundle bundle = this.getIntent().getExtras();
        intent.setClass(AccountInforActivity.this,ChangeNameActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void toChangePassword(View view){
        Intent intent = new Intent();
        Bundle bundle = this.getIntent().getExtras();
        intent.setClass(AccountInforActivity.this,ChangePasswordActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void toChangePicture(View view){
        Intent intent = new Intent();
        Bundle bundle = this.getIntent().getExtras();
        intent.setClass(AccountInforActivity.this,ChangePictureActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void getInfor(){
        Bundle bundle = this.getIntent().getExtras();
        final String userid = bundle.getString("username");
        System.out.println("用户名："+ userid);
        Thread infoRunnable = new Thread(){
            @Override
            public void run() {
                super.run();
                Connection con = null;
                try {
                    System.out.println("用户名："+ userid);
                    con = com.getCon();
                    String sql = "select * from user where user_id =" + userid;
                    Statement st = (Statement) con.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    if (rs.next()) {
                        System.out.println(rs.getString("user_name"));
                        System.out.println(rs.getDate("birthdate").toString());
                        final String name = rs.getString("user_name");
                        final String date = rs.getDate("birthdate").toString();
                        String path = rs.getString("image");
                        Bitmap bm = null;
                        if(path != null){
                            File file = new File(path);
                            if(file.exists()){
                                file.setExecutable(true);
                                file.setReadable(true);
                                file.setWritable(true);
                            }
                            checkPermission();
                            bm = BitmapFactory.decodeFile(path);
                            int check = 1;
                        }else{
                            int check = 0;
                        }
                        final Bitmap bmtest = bm;


                        AccountInforActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                username.setText(name);
                                birthday.setText(date);
                                if(bmtest == null){

                                }else{
                                    imageVi.setImageBitmap(bmtest);
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
    public void checkPermission() {
        boolean isGranted = true;
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //如果没有写sd卡权限
                isGranted = false;
            }
            if (this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
            }
            Log.i("cbs","isGranted == "+isGranted);
            if (!isGranted) {
                this.requestPermissions(
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission
                                .ACCESS_FINE_LOCATION,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        102);
            }
        }

    }

}
