package com.example.helloworld.setting;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.helloworld.COM.MyApp;
import com.example.helloworld.MainActivity;
import com.example.helloworld.R;
import com.example.helloworld.crop.ChangePictureActivity;
import com.example.helloworld.information.InforActivity;
import com.example.helloworld.login.LoginActivity;
import com.example.helloworld.login.SharedPreferencesUtils;
import com.example.helloworld.optionframe.OptionMaterialDialog;

public class SettingActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        MyApp.getAppManager().addActivity(this);


    }

    /**
     * 返回登录界面，不消除用户和密码
     */
    public void toLogin(View view) {
        //不用自动登录就可
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        //创建记住密码和自动登录是默认不选,密码为空
        helper.putValues(new SharedPreferencesUtils.ContentValue("autoLogin", false));
        startActivity(new Intent(SettingActivity.this, LoginActivity.class));

    }

    /**
     * 返回登录界面，消除用密码
     */
    public void toLogin2(View view) {
        //置空密码即可
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        //创建记住密码和自动登录是默认不选,密码为空
        helper.putValues(
                new SharedPreferencesUtils.ContentValue("remenberPassword", false),
                new SharedPreferencesUtils.ContentValue("autoLogin", false),
                new SharedPreferencesUtils.ContentValue("password", ""));
        startActivity(new Intent(SettingActivity.this, LoginActivity.class));
    }

    public void toAbout(View view) {
        Intent intent = new Intent();
        Bundle bundle = this.getIntent().getExtras();
        intent.setClass(SettingActivity.this, AboutActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void toAccountInfor(View view) {
        Intent intent = new Intent();
        Bundle bundle = this.getIntent().getExtras();
        intent.setClass(SettingActivity.this,AccountInforActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void toSelfInfor(View view) {
        Intent intent = new Intent();
        Bundle bundle = this.getIntent().getExtras();
        intent.setClass(SettingActivity.this,SelfInforActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void toFood(View view) {
        Intent intent = new Intent();
        Bundle bundle = this.getIntent().getExtras();
        intent.setClass(SettingActivity.this, InforActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void toProtocol(View view) {
        Intent intent = new Intent();
        Bundle bundle = this.getIntent().getExtras();
        intent.setClass(SettingActivity.this,ProtocolActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void toChangePicture(View view){
        Intent intent = new Intent();
        Bundle bundle = this.getIntent().getExtras();
        intent.setClass(SettingActivity.this,ChangePictureActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void LogoutDialog(View view) {
        final OptionMaterialDialog mMaterialDialog = new OptionMaterialDialog(SettingActivity.this);
        mMaterialDialog
//                .setTitle("这是标题")
//                .setTitleTextColor(R.color.colorPrimary)
//                .setTitleTextSize((float) 22.5)
                .setMessage("你确定要退出登陆吗？")
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
                        SharedPreferencesUtils helper = new SharedPreferencesUtils(SettingActivity.this, "setting");
                        //创建记住密码和自动登录是默认不选,密码为空
                        helper.putValues(
                                new SharedPreferencesUtils.ContentValue("remenberPassword", false),
                                new SharedPreferencesUtils.ContentValue("autoLogin", false),
                                new SharedPreferencesUtils.ContentValue("password", ""));
                        MyApp.getAppManager().endApp();
                        startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                        finish();
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
    public void toMain(View view){
        Intent intent = new Intent();
        Bundle bundle = this.getIntent().getExtras();
        intent.setClass(SettingActivity.this,MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
