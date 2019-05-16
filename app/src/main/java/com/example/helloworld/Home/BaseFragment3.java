package com.example.helloworld.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.helloworld.R;
import com.example.helloworld.crop.ChangePictureActivity;


public class BaseFragment3 extends Fragment {

private LinearLayout linearLayout1;
private LinearLayout linearLayout2;
    public static BaseFragment3 newInstance(String info) {
        Bundle args = new Bundle();
        BaseFragment3 fragment = new BaseFragment3();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base3,null);
        linearLayout1 = (LinearLayout)view.findViewById(R.id.change);
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent();
                    Bundle bundle = getActivity().getIntent().getExtras();
                    intent.setClass(getActivity(),ChangePictureActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
        }
        });
        linearLayout2 = (LinearLayout)view.findViewById(R.id.toHealthReport);
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                Bundle bundle = getActivity().getIntent().getExtras();
                intent.setClass(getActivity(),HealthReportActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
    }

}
