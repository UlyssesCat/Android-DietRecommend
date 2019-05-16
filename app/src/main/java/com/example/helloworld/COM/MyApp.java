package com.example.helloworld.COM;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

public class MyApp {
    private static List<Activity> activityList;
    private static MyApp appManager;

    public static MyApp getAppManager(){
        if(appManager==null){
            appManager = new MyApp();
        }
        return appManager;
    }

    public void addActivity(Activity act){
        if(activityList == null){
            activityList = new LinkedList<Activity>();
        }
        activityList.add(act);
    }
    public void endApp(){
        for(Activity activity:activityList){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
