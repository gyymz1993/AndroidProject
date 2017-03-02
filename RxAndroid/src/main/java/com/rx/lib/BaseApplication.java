package com.rx.lib;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by Administrator on 2017/1/18.
 */
public class BaseApplication extends Application {
    private static BaseApplication mBaseApplication = null;
    private static Looper mMainThreadLooper = null;
    private static Handler mMainThreadHandler = null;
    private static int mMainThreadId;
    private static Thread mMainThread = null;

    @Override
    public void onCreate() {
        super.onCreate();

        BaseApplication.mBaseApplication = this;
        BaseApplication.mMainThreadLooper = getMainLooper();
        BaseApplication.mMainThreadHandler = new Handler();
        BaseApplication.mMainThreadId = android.os.Process.myTid();
        BaseApplication.mMainThread = Thread.currentThread();

        initParams();
//        AVObject testObject = new AVObject("TestObject");
//        testObject.put("username", "yangmingzhi");
//        testObject.put("password", "123456789");
//        testObject.saveInBackground();
//
//        AVObject user = new AVObject("_User");
//        user.put("username", "gyymz1993");
//        user.put("password","123456789"); // add another field
//        user.saveInBackground();
    }

    private void initParams() {
        /*AVOSCloud初始化*/
        AVOSCloud.initialize(this, "LneRHU0WmK4SWh5N0j4Y5oLu-gzGzoHsz", "vAejkMa9sTm2Susj27iG7vPA");
    }

    private Context getContext() {
        return this.getApplicationContext();
    }

    public static BaseApplication getApplication() {
        return mBaseApplication;
    }

    public static Looper getMainThreadLooper() {
        return mMainThreadLooper;
    }

    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    public static int getMainThreadId() {
        return mMainThreadId;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

}
