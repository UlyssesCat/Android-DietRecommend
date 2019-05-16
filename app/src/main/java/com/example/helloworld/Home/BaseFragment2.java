package com.example.helloworld.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.helloworld.R;


public class BaseFragment2 extends Fragment {


    public static BaseFragment2 newInstance(String info) {
        Bundle args = new Bundle();
        BaseFragment2 fragment = new BaseFragment2();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base2,null);


        return view;
    }
}
