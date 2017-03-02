package com.test.studyandroid.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.test.studyandroid.aidl.IcounService;


/**
 * Created by Administrator on 2016/3/15.
 */
public class MyService extends Service {

    private  boolean threadDisable=false;
    private  int count=2;

    private IcounService.Stub serviceBinder = new IcounService.Stub() {
        @Override
        public int getCount() throws RuntimeException {
            return count;
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return serviceBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new  Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    SystemClock.sleep(1000);
                    count++;
                    //Log.e("ymz","countService on service  connect,count is"+count);
                }

            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.threadDisable=true;
    }


}
