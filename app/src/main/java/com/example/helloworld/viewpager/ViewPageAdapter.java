package com.example.helloworld.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.List;

public class ViewPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    public ViewPageAdapter(FragmentManager fm){
        super(fm);
    }
    public ViewPageAdapter(FragmentManager fm, List<Fragment>fragments){
        super(fm);
        this.fragments=fragments;
    }
    @Override
    public int getCount(){
        return fragments.size();
    }

    @Override
    public Fragment getItem(int arg0){
        return fragments.get(arg0);
    }



}
