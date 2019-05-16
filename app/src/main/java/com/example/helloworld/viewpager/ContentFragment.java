package com.example.helloworld.viewpager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.helloworld.R;
import com.example.helloworld.login.LoginActivity;

public class ContentFragment extends Fragment {
    private int[] bgRes = {R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content,null);
        Button btn = view.findViewById(R.id.btn);
        RelativeLayout rl = (RelativeLayout)view.findViewById(R.id.rl);
        int index = getArguments().getInt("index");
        rl.setBackgroundResource(bgRes[index]);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        btn.setVisibility(index==2?View.VISIBLE:View.GONE);
        return view;
    }
}
