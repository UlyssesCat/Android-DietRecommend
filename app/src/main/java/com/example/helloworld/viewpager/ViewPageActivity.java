package com.example.helloworld.viewpager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.example.helloworld.COM.MyApp;
import com.example.helloworld.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPageActivity extends FragmentActivity {
    private ViewPager viewPager;
    private LinearLayout IIIndicator;
    private PagerAdapter adapter;
    private List<Fragment> fragments = new ArrayList<Fragment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        MyApp.getAppManager().addActivity(this);

        viewPager=(ViewPager)findViewById(R.id.viewPager);
        IIIndicator=(LinearLayout)findViewById(R.id.II_indicator);

        for(int i=0;i<3;i++)
        {
            ContentFragment fragment = new ContentFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("index",i);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        adapter = new ViewPageAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int index, float arg1, int arg2) {
                for(int i=0;i<fragments.size();i++){
                    IIIndicator.getChildAt(i).setBackgroundResource(index==i?R.drawable.dot_focus:R.drawable.dot_normal);
                }
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        initIndicater();
    }
    private void initIndicater()
    {   int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10f,getResources().getDisplayMetrics());
        LinearLayout.LayoutParams Ip = new LinearLayout.LayoutParams(width,width);
        Ip.topMargin=65*width;
        Ip.bottomMargin=5*width;
        Ip.rightMargin=2*width;
        for(int i=0;i<fragments.size();i++){
            View view = new View(this);
            view.setId(i);
            view.setBackgroundResource(i==0?R.drawable.dot_focus:R.drawable.dot_normal);
            view.setLayoutParams(Ip);
            IIIndicator.addView(view,i);
    }
    }

}
