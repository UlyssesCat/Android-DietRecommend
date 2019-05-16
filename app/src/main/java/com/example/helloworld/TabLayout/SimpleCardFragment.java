package com.example.helloworld.TabLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.COM.com;
import com.example.helloworld.R;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@SuppressLint("ValidFragment")
public class SimpleCardFragment extends Fragment {
    private static String mTitle;
    public ArrayList<FoodBean> fblist = new ArrayList<FoodBean>();
    public double Cll = 0;
    private TextView R1;
    private TextView R2;
    private TextView R3;
    private TextView R4;
    private TextView R5;
    private TextView R6;
    private TextView R7;
    private TextView R8;
    private TextView R9;
    private TextView K1;
    private TextView K2;
    private TextView K3;
    private TextView K4;
    private TextView K5;
    private TextView K6;
    private TextView K7;
    private TextView K8;
    private TextView K9;
    private TextView C1;
    private TextView C2;
    private TextView C3;
    private TextView C4;
    private TextView C5;
    private TextView C6;
    private TextView C7;
    private TextView C8;
    private TextView C9;
    private TextView TotalCal1;
    private TextView TotalCal2;
    private TextView TotalCal3;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button B1;
    private Button B2;
    private Button B3;
    private Button B4;
    private Button B5;
    private Button B6;
    private Button B7;
    private Button B8;
    private Button B9;
    public static FoodBean food1;
    public static FoodBean food2;
    public static FoodBean food3;
    public static FoodBean food4;
    public static FoodBean food5;
    public static FoodBean food6;
    public static FoodBean food7;
    public static FoodBean food8;
    public static FoodBean food9;

    public static int type =  0;

    public static SimpleCardFragment newInstance(String info) {
        mTitle = info;
        Bundle args = new Bundle();
        SimpleCardFragment fragment = new SimpleCardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_simple_card,null);
        R1 = (TextView) view.findViewById(R.id.R1);
        R2 = (TextView) view.findViewById(R.id.R2);
        R3 = (TextView) view.findViewById(R.id.R3);
        R4 = (TextView) view.findViewById(R.id.R4);
        R5 = (TextView) view.findViewById(R.id.R5);
        R6 = (TextView) view.findViewById(R.id.R6);
        R7 = (TextView) view.findViewById(R.id.R7);
        R8 = (TextView) view.findViewById(R.id.R8);
        R9 = (TextView) view.findViewById(R.id.R9);
        K1 = (TextView) view.findViewById(R.id.K1);
        K2 = (TextView) view.findViewById(R.id.K2);
        K3 = (TextView) view.findViewById(R.id.K3);
        K4 = (TextView) view.findViewById(R.id.K4);
        K5 = (TextView) view.findViewById(R.id.K5);
        K6 = (TextView) view.findViewById(R.id.K6);
        K7 = (TextView) view.findViewById(R.id.K7);
        K8 = (TextView) view.findViewById(R.id.K8);
        K9 = (TextView) view.findViewById(R.id.K9);
        C1 = (TextView) view.findViewById(R.id.C1);
        C2 = (TextView) view.findViewById(R.id.C2);
        C3 = (TextView) view.findViewById(R.id.C3);
        C4 = (TextView) view.findViewById(R.id.C4);
        C5 = (TextView) view.findViewById(R.id.C5);
        C6 = (TextView) view.findViewById(R.id.C6);
        C7 = (TextView) view.findViewById(R.id.C7);
        C8 = (TextView) view.findViewById(R.id.C8);
        C9 = (TextView) view.findViewById(R.id.C9);
        TotalCal1 = (TextView) view.findViewById(R.id.TotalCal1);
        TotalCal2 = (TextView) view.findViewById(R.id.TotalCal2);
        TotalCal3 = (TextView) view.findViewById(R.id.TotalCal3);
        button1 = (Button) view.findViewById(R.id.btn_choose1);
        button2 = (Button) view.findViewById(R.id.btn_choose2);
        button3 = (Button) view.findViewById(R.id.btn_choose3);
        B1 = (Button) view.findViewById(R.id.B1);
        B2 = (Button) view.findViewById(R.id.B2);
        B3 = (Button) view.findViewById(R.id.B3);
        B4 = (Button) view.findViewById(R.id.B4);
        B5 = (Button) view.findViewById(R.id.B5);
        B6 = (Button) view.findViewById(R.id.B6);
        B7 = (Button) view.findViewById(R.id.B7);
        B8 = (Button) view.findViewById(R.id.B8);
        B9 = (Button) view.findViewById(R.id.B9);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //diet_id
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH)+1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                int second = calendar.get(Calendar.SECOND);//时分秒
                Bundle bundle = getActivity().getIntent().getExtras();
                final String username = bundle.getString("username");//用户ID
                final int user_id = Integer.parseInt(username);
                final int diet_id = 1000*hour + 100*minute + 10 * second + 3 * user_id;//算法排除重复性
                //date
                final String date = year+"-"+month+"-"+day;
                //calorie
                String calo = TotalCal1.getText().toString().substring(0,TotalCal1.getText().toString().length()-2);
                final double calo1 = Double.parseDouble(calo);
                //user_id

                //太阳pe
                final String type = mTitle;

                Thread choose1_Thread = new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        Connection con = null;
                        try {
                            con = com.getCon();
                            String sql = "INSERT INTO diets_user (`diet_id`, `date`, `calorie`, `user_id`, `type`) VALUES ('"+diet_id+"', '"+date+"', '"+calo1+"', '"+user_id+"', '"+type+"');";
                            System.out.println(sql);
                            Statement st = (Statement) con.createStatement();
                            st.execute(sql);
                        } catch (SQLException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                };
                choose1_Thread.start();

                Thread choose2_Thread = new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        Connection con = null;
                        try {
                            con = com.getCon();
                            String sql1 = "INSERT INTO diet_details (`diet_id`, `food_id`, `quantity`) VALUES ('"+diet_id+"', '"+food1.getFoodid()+"', '"+K1.getText().toString().substring(0,K1.getText().toString().length()-1)+"');";
                            String sql2 = "INSERT INTO diet_details (`diet_id`, `food_id`, `quantity`) VALUES ('"+diet_id+"', '"+food2.getFoodid()+"', '"+K2.getText().toString().substring(0,K2.getText().toString().length()-1)+"');";
                            String sql3 = "INSERT INTO diet_details (`diet_id`, `food_id`, `quantity`) VALUES ('"+diet_id+"', '"+food3.getFoodid()+"', '"+K3.getText().toString().substring(0,K3.getText().toString().length()-1)+"');";
                            Statement st = (Statement) con.createStatement();
                            st.execute(sql1);
                            st.execute(sql2);
                            st.execute(sql3);
                        } catch (SQLException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                };
                choose2_Thread.start();
            }

        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //diet_id
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH)+1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                int second = calendar.get(Calendar.SECOND);//时分秒
                Bundle bundle = getActivity().getIntent().getExtras();
                final String username = bundle.getString("username");//用户ID
                final int user_id = Integer.parseInt(username);
                final int diet_id = 1000*hour + 100*minute + 10 * second + 3 * user_id;//算法排除重复性
                //date
                final String date = year+"-"+month+"-"+day;
                //calorie
                String calo = TotalCal2.getText().toString().substring(0,TotalCal2.getText().toString().length()-2);
                final double calo1 = Double.parseDouble(calo);
                //user_id

                //太阳pe
                final String type = mTitle;

                Thread choose1_Thread = new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        Connection con = null;
                        try {
                            con = com.getCon();
                            String sql = "INSERT INTO diets_user (`diet_id`, `date`, `calorie`, `user_id`, `type`) VALUES ('"+diet_id+"', '"+date+"', '"+calo1+"', '"+user_id+"', '"+type+"');";
                            System.out.println(sql);
                            Statement st = (Statement) con.createStatement();
                            st.execute(sql);
                        } catch (SQLException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                };
                choose1_Thread.start();

                Thread choose2_Thread = new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        Connection con = null;
                        try {
                            con = com.getCon();
                            String sql1 = "INSERT INTO diet_details (`diet_id`, `food_id`, `quantity`) VALUES ('"+diet_id+"', '"+food4.getFoodid()+"', '"+K4.getText().toString().substring(0,K4.getText().toString().length()-1)+"');";
                            String sql2 = "INSERT INTO diet_details (`diet_id`, `food_id`, `quantity`) VALUES ('"+diet_id+"', '"+food5.getFoodid()+"', '"+K5.getText().toString().substring(0,K5.getText().toString().length()-1)+"');";
                            String sql3 = "INSERT INTO diet_details (`diet_id`, `food_id`, `quantity`) VALUES ('"+diet_id+"', '"+food6.getFoodid()+"', '"+K6.getText().toString().substring(0,K6.getText().toString().length()-1)+"');";
                            Statement st = (Statement) con.createStatement();
                            st.execute(sql1);
                            st.execute(sql2);
                            st.execute(sql3);
                        } catch (SQLException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                };
                choose2_Thread.start();
            }

        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //diet_id
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH)+1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                int second = calendar.get(Calendar.SECOND);//时分秒
                Bundle bundle = getActivity().getIntent().getExtras();
                final String username = bundle.getString("username");//用户ID
                final int user_id = Integer.parseInt(username);
                final int diet_id = 1000*hour + 100*minute + 10 * second + 3 * user_id;//算法排除重复性
                //date
                final String date = year+"-"+month+"-"+day;
                //calorie
                String calo = TotalCal3.getText().toString().substring(0,TotalCal3.getText().toString().length()-2);
                final double calo1 = Double.parseDouble(calo);
                //user_id
                final String type = mTitle;

                Thread choose1_Thread = new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        Connection con = null;
                        try {
                            con = com.getCon();
                            String sql = "INSERT INTO diets_user (`diet_id`, `date`, `calorie`, `user_id`, `type`) VALUES ('"+diet_id+"', '"+date+"', '"+calo1+"', '"+user_id+"', '"+type+"');";
                            System.out.println(sql);
                            Statement st = (Statement) con.createStatement();
                            st.execute(sql);
                        } catch (SQLException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                };
                choose1_Thread.start();

                Thread choose2_Thread = new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        Connection con = null;
                        try {
                            con = com.getCon();
                            String sql1 = "INSERT INTO diet_details (`diet_id`, `food_id`, `quantity`) VALUES ('"+diet_id+"', '"+food7.getFoodid()+"', '"+K7.getText().toString().substring(0,K7.getText().toString().length()-1)+"');";
                            String sql2 = "INSERT INTO diet_details (`diet_id`, `food_id`, `quantity`) VALUES ('"+diet_id+"', '"+food8.getFoodid()+"', '"+K8.getText().toString().substring(0,K8.getText().toString().length()-1)+"');";
                            String sql3 = "INSERT INTO diet_details (`diet_id`, `food_id`, `quantity`) VALUES ('"+diet_id+"', '"+food9.getFoodid()+"', '"+K9.getText().toString().substring(0,K9.getText().toString().length()-1)+"');";
                            Statement st = (Statement) con.createStatement();
                            st.execute(sql1);
                            st.execute(sql2);
                            st.execute(sql3);
                        } catch (SQLException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                };
                choose2_Thread.start();
            }

        });


        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = food1.getType();
                Intent intent = new Intent();
                intent.setClass(getActivity(), ReplaceFoodActivity.class);
                startActivity(intent);
            }

        });
        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = food2.getType();
                Intent intent = new Intent();
                intent.setClass(getActivity(), ReplaceFoodActivity.class);
                startActivity(intent);
            }

        });
        B3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = food3.getType();
                Intent intent = new Intent();
                intent.setClass(getActivity(), ReplaceFoodActivity.class);
                startActivity(intent);
            }

        });
        B4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = food4.getType();
                Intent intent = new Intent();
                intent.setClass(getActivity(), ReplaceFoodActivity.class);
                startActivity(intent);
            }

        });
        B5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = food5.getType();
                Intent intent = new Intent();
                intent.setClass(getActivity(), ReplaceFoodActivity.class);
                startActivity(intent);
            }

        });
        B6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = food6.getType();
                Intent intent = new Intent();
                intent.setClass(getActivity(), ReplaceFoodActivity.class);
                startActivity(intent);
            }

        });
        B7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = food7.getType();
                Intent intent = new Intent();
                intent.setClass(getActivity(), ReplaceFoodActivity.class);
                startActivity(intent);
            }

        });
        B8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = food8.getType();
                Intent intent = new Intent();
                intent.setClass(getActivity(), ReplaceFoodActivity.class);
                startActivity(intent);
            }

        });
        B9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = food9.getType();
                Intent intent = new Intent();
                intent.setClass(getActivity(), ReplaceFoodActivity.class);
                startActivity(intent);
            }

        });


        try {
            getCll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return view;
    }

    public void getCll() throws InterruptedException {
        Bundle bundle = getActivity().getIntent().getExtras();
        final String username = bundle.getString("username");
        final float BMR = 0;
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
                        String b = bitrhdate.substring(0,4);
                        int bdate = Integer.parseInt(b);
                        Date nowdate = new Date();
                        Calendar cal = Calendar.getInstance();
                        int age = cal.get(Calendar.YEAR) - bdate;
                        if(sex.equals("woman")){
                            Cll = (655 + 9.6 * weight + 1.7 * height - 4.7 * age);
                        }else{
                            Cll = (66 + 13.7 * weight + 5 * height - 6.8 * age);
                        }
                    }
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }

        };
        Thread MenuRunnable = new Thread() {
            @Override
            public void run() {
                super.run();
                System.out.println("测试："+Cll);
                Connection con = null;
                try {
                    con = com.getCon();
                    System.out.println(username);
                    String sql = "select * from food_composition";
                    System.out.println("测试2连接");
                    Statement st = (Statement) con.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while (rs.next()) {
                        FoodBean fb = new FoodBean();
                        fb.setCalorie(rs.getInt("calorie"));
                        fb.setFoodid(rs.getInt("id"));
                        fb.setType(rs.getInt("type"));
                        fb.setName(rs.getString("name"));
                        fblist.add(fb);
                    }


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

        Thread tjRunnable = new Thread(){
            @Override
            public void run(){
                super.run();

                int allc = 10000;
                int id1=0;
                int id2=0;
                int id3=0;
                int num = fblist.size();
                int num1 = 0;
                int type2 = 0;
                int type3 = 0;

                while(allc > Cll || allc < (Cll * 0.75)){

                    allc = 0;
                    Random r = new Random();
                    num1 = r.nextInt(num);
                    while(fblist.get(num1).getType()!=1){
                        num1 = r.nextInt(num);
                    }
                    id1=fblist.get(num1).getFoodid();
                    allc += fblist.get(num1).getCalorie()*4;
                    type2 = r.nextInt(num);
                    while(fblist.get(type2).getType()==1){
                        type2 = r.nextInt(num);
                    }
                    id2 = fblist.get(type2).getFoodid();
                    if(fblist.get(type2).getType()==2){
                        allc += fblist.get(type2).getCalorie()*4;
                    }else if(fblist.get(type2).getType()==4 || fblist.get(type2).getType()==9 || fblist.get(type2).getType()==6){
                        allc += fblist.get(type2).getCalorie()*1.5;
                    }else if(fblist.get(type2).getType()==3 || fblist.get(type2).getType()==7){
                        allc += fblist.get(type2).getCalorie();
                    }

                    type3 = r.nextInt(num);
                    while(fblist.get(type3).getType()==1 || fblist.get(type3).getType()==fblist.get(type2).getType()){
                        type3 = r.nextInt(num);
                    }
                    id3 = fblist.get(type3).getFoodid();
                    if(fblist.get(type3).getType()==2){
                        allc += fblist.get(type3).getCalorie()*4;
                    }else if(fblist.get(type3).getType()==4 || fblist.get(type3).getType()==9 || fblist.get(type3).getType()==6){
                        allc += fblist.get(type3).getCalorie()*1.5;
                    }else if(fblist.get(type3).getType()==3 || fblist.get(type3).getType()==7) {
                        allc += fblist.get(type3).getCalorie();
                    }

                }
                food1 = fblist.get(num1);
                food2 = fblist.get(type2);
                food3 = fblist.get(type3);


                allc = 10000;
                id1=0;
                id2=0;
                id3=0;
                num = fblist.size();
                num1 = 0;
                type2 = 0;
                type3 = 0;
                while(allc > Cll || allc < (Cll * 0.75)){

                    allc = 0;
                    Random r = new Random();
                    num1 = r.nextInt(num);
                    while(fblist.get(num1).getType()!=1){
                        num1 = r.nextInt(num);
                    }
                    id1=fblist.get(num1).getFoodid();
                    allc += fblist.get(num1).getCalorie()*4;
                    type2 = r.nextInt(num);
                    while(fblist.get(type2).getType()==1){
                        type2 = r.nextInt(num);
                    }
                    id2 = fblist.get(type2).getFoodid();
                    if(fblist.get(type2).getType()==2){
                        allc += fblist.get(type2).getCalorie()*4;
                    }else if(fblist.get(type2).getType()==4 || fblist.get(type2).getType()==9 || fblist.get(type2).getType()==6){
                        allc += fblist.get(type2).getCalorie()*1.5;
                    }else if(fblist.get(type2).getType()==3 || fblist.get(type2).getType()==7){
                        allc += fblist.get(type2).getCalorie();
                    }

                    type3 = r.nextInt(num);
                    while(fblist.get(type3).getType()==1 || fblist.get(type3).getType()==fblist.get(type2).getType()){
                        type3 = r.nextInt(num);
                    }
                    id3 = fblist.get(type3).getFoodid();
                    if(fblist.get(type3).getType()==2){
                        allc += fblist.get(type3).getCalorie()*4;
                    }else if(fblist.get(type3).getType()==4 || fblist.get(type3).getType()==9 || fblist.get(type3).getType()==6){
                        allc += fblist.get(type3).getCalorie()*1.5;
                    }else if(fblist.get(type3).getType()==3 || fblist.get(type3).getType()==7) {
                        allc += fblist.get(type3).getCalorie();
                    }

                }
                food4 = fblist.get(num1);
                food5 = fblist.get(type2);
                food6 = fblist.get(type3);

                allc = 10000;
                id1=0;
                id2=0;
                id3=0;
                num = fblist.size();
                num1 = 0;
                type2 = 0;
                type3 = 0;
                while(allc > Cll || allc < (Cll * 0.75)){

                    allc = 0;
                    Random r = new Random();
                    num1 = r.nextInt(num);
                    while(fblist.get(num1).getType()!=1){
                        num1 = r.nextInt(num);
                    }
                    id1=fblist.get(num1).getFoodid();
                    allc += fblist.get(num1).getCalorie()*4;
                    type2 = r.nextInt(num);
                    while(fblist.get(type2).getType()==1){
                        type2 = r.nextInt(num);
                    }
                    id2 = fblist.get(type2).getFoodid();
                    if(fblist.get(type2).getType()==2){
                        allc += fblist.get(type2).getCalorie()*4;
                    }else if(fblist.get(type2).getType()==4 || fblist.get(type2).getType()==9 || fblist.get(type2).getType()==6){
                        allc += fblist.get(type2).getCalorie()*1.5;
                    }else if(fblist.get(type2).getType()==3 || fblist.get(type2).getType()==7){
                        allc += fblist.get(type2).getCalorie();
                    }

                    type3 = r.nextInt(num);
                    while(fblist.get(type3).getType()==1 || fblist.get(type3).getType()==fblist.get(type2).getType()){
                        type3 = r.nextInt(num);
                    }
                    id3 = fblist.get(type3).getFoodid();
                    if(fblist.get(type3).getType()==2){
                        allc += fblist.get(type3).getCalorie()*4;
                    }else if(fblist.get(type3).getType()==4 || fblist.get(type3).getType()==9 || fblist.get(type3).getType()==6){
                        allc += fblist.get(type3).getCalorie()*1.5;
                    }else if(fblist.get(type3).getType()==3 || fblist.get(type3).getType()==7) {
                        allc += fblist.get(type3).getCalorie();
                    }

                }
                food7 = fblist.get(num1);
                food8 = fblist.get(type2);
                food9 = fblist.get(type3);

                R1.setText(food1.getName());
                R2.setText(food2.getName());
                R3.setText(food3.getName());
                K1.setText("150g");
                double cal1 = food1.getCalorie()*1.5;
                double allcal = 0;
                double totalcal = 0;
                C1.setText(""+cal1+"kJ");
                totalcal += cal1;
                if(food2.getType()==2){
                    K2.setText("150");
                    allcal = 1.5 * food2.getCalorie();
                    totalcal += allcal;
                    C2.setText(""+allcal+"kJ");
                }else if(food2.getType()==4 || food2.getType()==6 || food2.getType()==9){
                    K2.setText("50g");
                    allcal = 0.5 * food2.getCalorie();
                    totalcal += allcal;
                    C2.setText(""+allcal+"kJ");
                }else if(food2.getType()==3 || food2.getType()==7){
                    K2.setText("30g");
                    allcal = 0.3 * food2.getCalorie();
                    totalcal += allcal;
                    C2.setText(""+allcal+"kJ");
                }

                if(food3.getType()==2){
                    K3.setText("150g");
                    allcal = 1.5 * food3.getCalorie();
                    totalcal += allcal;
                    C3.setText(""+allcal+"kJ");
                }else if(food3.getType()==4 || food3.getType()==6 || food3.getType()==9){
                    K3.setText("50g");
                    allcal = 0.5 * food3.getCalorie();
                    totalcal += allcal;
                    C3.setText(""+allcal+"kJ");
                }else if(food3.getType()==3 || food3.getType()==7){
                    K3.setText("30g");
                    allcal = 0.3 * food3.getCalorie();
                    totalcal += allcal;
                    C3.setText(""+allcal+"kJ");
                }
                TotalCal1.setText(totalcal+"kJ");
                totalcal =0;

                R4.setText(food4.getName());
                R5.setText(food5.getName());
                R6.setText(food6.getName());
                K4.setText("150g");
                cal1 = food4.getCalorie()*1.5;
                allcal = 0;
                totalcal += cal1;
                C4.setText(""+cal1+"kJ");
                if(food5.getType()==2){
                    K5.setText("150g");
                    allcal = 1.5 * food5.getCalorie();
                    totalcal += allcal;
                    C5.setText(""+allcal+"kJ");
                }else if(food5.getType()==4 || food5.getType()==6 || food5.getType()==9){
                    K5.setText("50g");
                    allcal = 0.5 * food5.getCalorie();
                    totalcal += allcal;
                    C5.setText(""+allcal+"kJ");
                }else if(food5.getType()==3 || food5.getType()==7){
                    K5.setText("30g");
                    allcal = 0.3 * food5.getCalorie();
                    totalcal += allcal;
                    C5.setText(""+allcal+"kJ");
                }

                if(food6.getType()==2){
                    K6.setText("150g");
                    allcal = 1.5 * food6.getCalorie();
                    totalcal += allcal;
                    C6.setText(""+allcal+"kJ");
                }else if(food6.getType()==4 || food6.getType()==6 || food6.getType()==9){
                    K6.setText("50g");
                    allcal = 0.5 * food6.getCalorie();
                    totalcal += allcal;
                    C6.setText(""+allcal+"kJ");
                }else if(food6.getType()==3 || food6.getType()==7){
                    K6.setText("30g");
                    allcal = 0.3 * food6.getCalorie();
                    totalcal += allcal;
                    C6.setText(""+allcal+"kJ");
                }
                TotalCal2.setText(totalcal+"kJ");
                totalcal = 0;

                R7.setText(food7.getName());
                R8.setText(food8.getName());
                R9.setText(food9.getName());
                K7.setText("150g");
                cal1 = food7.getCalorie()*1.5;
                totalcal += cal1;
                allcal = 0;
                C7.setText(""+cal1+"kJ");
                if(food8.getType()==2){
                    K8.setText("150g");
                    allcal = 1.5 * food8.getCalorie();
                    totalcal += allcal;
                    C8.setText(""+allcal+"kJ");
                }else if(food8.getType()==4 || food8.getType()==6 || food8.getType()==9){
                    K8.setText("50g");
                    allcal = 0.5 * food8.getCalorie();
                    totalcal += allcal;
                    C8.setText(""+allcal+"kJ");
                }else if(food8.getType()==3 || food8.getType()==7){
                    K8.setText("30g");
                    allcal = 0.3 * food8.getCalorie();
                    totalcal += allcal;
                    C8.setText(""+allcal+"kJ");
                }

                if(food9.getType()==2){
                    K9.setText("150g");
                    allcal = 1.5 * food9.getCalorie();
                    totalcal += allcal;
                    C9.setText(""+allcal+"kJ");
                }else if(food9.getType()==4 || food9.getType()==6 || food9.getType()==9){
                    K9.setText("50g");
                    allcal = 0.5 * food9.getCalorie();
                    totalcal += allcal;
                    C9.setText(""+allcal+"kJ");
                }else if(food9.getType()==3 || food9.getType()==7){
                    K9.setText("30g");
                    allcal = 0.3 * food9.getCalorie();
                    totalcal += allcal;
                    C9.setText(""+allcal+"kJ");
                }
                TotalCal3.setText(totalcal+"kJ");
                totalcal = 0;
            }
        };

        cllRunnable.start();
        cllRunnable.join();
        MenuRunnable.start();
        MenuRunnable.join();
        tjRunnable.start();
        tjRunnable.join();
    }


    public void foo(){

    }

}