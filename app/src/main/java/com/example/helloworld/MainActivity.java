package com.example.helloworld;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import android.support.design.widget.BottomNavigationView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.helloworld.COM.MyApp;
import com.example.helloworld.information.CircleProgressBar;
import com.example.helloworld.Home.BaseFragment1;
import com.example.helloworld.Home.BaseFragment2;
import com.example.helloworld.Home.BaseFragment3;
import com.example.helloworld.Home.BottomNavigationViewHelper;
import com.example.helloworld.Home.ViewPagerAdapter;
import com.example.helloworld.fadingscroll.FadingScrollView;
import com.example.helloworld.fadingscroll.PullScrollView;
import com.example.helloworld.optionframe.OptionBottomDialog;
import com.example.helloworld.optionframe.OptionCenterDialog;
import com.example.helloworld.optionframe.OptionMaterialDialog;
import com.example.helloworld.setting.SettingActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity{

    private View layout;
    private FadingScrollView fadingScrollView;
    private PullScrollView pullScrollView;
    private ImageView imageView;
    private ViewPager viewPager;
    private MenuItem menuItem;
    private BottomNavigationView bottomNavigationView;
    private CircleProgressBar circleProgressBar; // 自定义的进度条
    private SeekBar seekbar; // 拖动条
    private int[] colors = new int[] { Color.parseColor("#27B197"), Color.parseColor("#00A6D5") };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApp.getAppManager().addActivity(this);


        final Bundle bundle = this.getIntent().getExtras();
        imageView = (ImageView) findViewById(R.id.imageview3);
        imageView.setClickable(true);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SettingActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


//        layout = (View)findViewById(R.id.nac_layout);
//        layout.setAlpha(0);
//
//        fadingScrollView = (FadingScrollView) findViewById(R.id.nac_root);
//        fadingScrollView.setFadingView(layout);
//        fadingScrollView.setFadingHeightView(findViewById(R.id.nac_image));
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.navigation_dashboard:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.navigation_notifications:
                                viewPager.setCurrentItem(2);
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //禁止ViewPager滑动
//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });

        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(BaseFragment1.newInstance("新闻"));

        adapter.addFragment(BaseFragment2.newInstance("图书"));
        adapter.addFragment(BaseFragment3.newInstance("发现"));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
    public void QQDialog(View view) {
        List<String> stringList = new ArrayList<String>();
        stringList.add("拍照");
        stringList.add("从相册选择");
        final OptionBottomDialog optionBottomDialog = new OptionBottomDialog(MainActivity.this, stringList);
        optionBottomDialog.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                optionBottomDialog.dismiss();
            }
        });
    }

    public void WXCenterDialog(View view) {
        final ArrayList<String> list = new ArrayList<>();
        list.add("标为已读");
        list.add("置顶聊天");
        list.add("删除该聊天");
        final OptionCenterDialog optionCenterDialog = new OptionCenterDialog();
        optionCenterDialog.show(MainActivity.this, list);
        optionCenterDialog.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                optionCenterDialog.dismiss();
            }
        });
    }


    public void WXDialog(View view) {
        final OptionMaterialDialog mMaterialDialog = new OptionMaterialDialog(MainActivity.this);
        mMaterialDialog
                .setTitle("这是标题")
                .setTitleTextColor(R.color.colorPrimary)
                .setTitleTextSize((float) 22.5)
                .setMessage("支持所有布局文字大小颜色等设置。")
                .setMessageTextColor(R.color.colorPrimary)
                .setMessageTextSize((float) 16.5)
                .setPositiveButtonTextColor(R.color.colorAccent)
                .setNegativeButtonTextColor(R.color.colorPrimary)
                .setPositiveButtonTextSize(15)
                .setNegativeButtonTextSize(15)
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();

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


