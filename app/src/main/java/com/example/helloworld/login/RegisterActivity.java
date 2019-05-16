package com.example.helloworld.login;
import com.example.helloworld.COM.MyApp;
import com.example.helloworld.R;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private EditText et_name;
    private EditText et_password;
    private LoadingDialog mLoadingDialog; //显示正在加载的对话框
    private Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        MyApp.getAppManager().addActivity(this);

        register = (Button)findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        initViews();
    }

    Handler myHandler=new Handler() {
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            Bundle data = new Bundle();
            data = msg.getData();

            //System.out.println("id:"+data.get("id").toString());    //输出第n行，列名为“id”的值
            Log.e("TAG", "id:" + data.get("id").toString());
            //TextView tv = (TextView) findViewById(R.id.jdbc);


            //System.out.println("content:"+data.get("content").toString());
        }
    };

    Runnable runnable=new Runnable() {
        private Connection con = null;

        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                Class.forName("com.mysql.jdbc.Driver");
                //引用代码此处需要修改，address为数据IP，Port为端口号，DBName为数据名称，UserName为数据库登录账户，Password为数据库登录密码
                con =DriverManager.getConnection("jdbc:mysql://192.168.137.1:3306/food?useSSL=false", "susheng", "ss306936");
//                        DriverManager.getConnection("jdbc:mysql://http://192.168.1.100/phpmyadmin/index.php:8086/b2b",
//                                UserName, Password);

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {
                testConnection(con);    //测试数据库连接
            } catch (java.sql.SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        public void testConnection(Connection con1) throws java.sql.SQLException {
            try {
                String sql = "select * from user";        //查询表名为“user”的所有内容
                Statement stmt = con1.createStatement();        //创建Statement
                ResultSet rs = stmt.executeQuery(sql);          //ResultSet类似Cursor

                //<code>ResultSet</code>最初指向第一行
                Bundle bundle = new Bundle();
                while (rs.next()) {
                    bundle.clear();
                    bundle.putString("id", rs.getString("user_id"));
                    //bundle.putString("content",rs.getString("content"));
                    Message msg = new Message();
                    msg.setData(bundle);
                    myHandler.sendMessage(msg);
                }

                rs.close();
                stmt.close();
            } catch (SQLException e) {

            } finally {
                if (con1 != null)
                    try {
                        con1.close();
                    } catch (SQLException e) {
                    }
            }
        }

    };
    private void register() {

        //先做一些基本的判断，比如输入的用户命为空，密码为空，网络不可用多大情况，都不需要去链接服务器了，而是直接返回提示错误
        if (getAccount().isEmpty()){
            showToast("你输入的账号为空！");
            return;
        }

        if (getPassword().isEmpty()){
            showToast("你输入的密码为空！");
            return;
        }

        showLoading();//显示加载框
        Thread registerRunnable = new Thread() {

            @Override
            public void run() {
                super.run();
                setRegisterBtnClickable(false);
                //睡眠3秒
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Connection con = null;
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    //引用代码此处需要修改，address为数据IP，Port为端口号，DBName为数据名称，UserName为数据库登录账户，Password为数据库登录密码
                    con =DriverManager.getConnection("jdbc:mysql://192.168.137.1:3306/food?useSSL=false", "susheng", "ss306936");
//                        DriverManager.getConnection("jdbc:mysql://http://192.168.1.100/phpmyadmin/index.php:8086/b2b",
//                                UserName, Password);
                    String sql="select * from user where user_id ='" + getAccount() + "'";
                    Statement st=(Statement)con.createStatement();
                    ResultSet rs=st.executeQuery(sql);
                    if (rs.next()) {
                        showToast("此账号已存在");
                        //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        String sql0="insert into user(user_id) values(\"" + getAccount() + "\");";
                        String sql1="insert into login(id, password) values(\"" + getAccount()  + "\",\"" + getPassword() + "\");";
                        Statement st0=(Statement)con.createStatement();
                        st0.executeUpdate(sql0);
                        st0.executeUpdate(sql1);

                        showToast("注册成功");

                        Intent intent = new Intent();
                        intent.setClass(RegisterActivity.this, AddSelfInforActivity1.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("username",getAccount());
                        intent.putExtras(bundle);
                        bundle.putString("password",getPassword());
                        intent.putExtras(bundle);

                        startActivity(intent);
                        finish();//关闭页面

                        //Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                        //startActivity(intent);
                    }
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                setRegisterBtnClickable(true);  //这里解放登录按钮，设置为可以点击
                hideLoading();//隐藏加载框
            }
        };
        registerRunnable.start();


    }

    private void initViews() {
        register = (Button) findViewById(R.id.register_button);
        et_name = (EditText) findViewById(R.id.name);
        et_password = (EditText) findViewById(R.id.password);
    }

    /**
     * 获取账号
     */
    public String getAccount() {
        return et_name.getText().toString().trim();//去掉空格
    }

    /**
     * 获取密码
     */
    public String getPassword() {
        return et_password.getText().toString().trim();//去掉空格
    }


    /**
     * 显示加载的进度款
     */
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this, getString(R.string.registing), false);
        }
        mLoadingDialog.show();
    }


    /**
     * 是否可以点击登录按钮
     *
     * @param clickable
     */
    public void setRegisterBtnClickable(boolean clickable) {
        register.setClickable(clickable);
    }
    /**
     * 隐藏加载的进度框
     */
    public void hideLoading() {
        if (mLoadingDialog != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLoadingDialog.hide();
                }
            });

        }
    }
    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        });

    }

}
