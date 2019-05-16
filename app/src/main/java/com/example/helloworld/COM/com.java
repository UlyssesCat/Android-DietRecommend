package com.example.helloworld.COM;

import java.sql.Connection;
import java.sql.DriverManager;

public class com {
    public static Connection getCon()
    {
        Connection con= null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            //引用代码此处需要修改，address为数据IP，Port为端口号，DBName为数据名称，UserName为数据库登录账户，Password为数据库登录密码
            con =DriverManager.getConnection("jdbc:mysql://10.132.89.30:3306/db?useSSL=false", "root", "19981025");
//                        DriverManager.getConnection("jdbc:mysql://http://192.168.1.100/phpmyadmin/index.php:8086/b2b",
//                                UserName, Password);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return con;
    }
}
