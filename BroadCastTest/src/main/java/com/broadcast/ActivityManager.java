package com.broadcast;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/13.
 */
public class ActivityManager {

    public static List<Activity> mActivity=new ArrayList<>();

    public static void addActivity(Activity activity){
        mActivity.add(activity);
    }
    public static void romoveActivity(Activity activity){
        mActivity.remove(activity);
    }
    public static void finishAllActivity(){
        if (mActivity.size()!=0){
            for (Activity activity:mActivity){
                if (!activity.isFinishing()){
                    activity.finish();
                }
            }
        }
    }
}
