package com.broadcast;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2017/1/13.
 */
public class BaseActivity  extends AppCompatActivity {

    ForceOffLineReceiver mForceOffLineReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);
       // mForceOffLineReceiver=new ForceOffLineReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();
       // registerReceiver(mForceOffLineReceiver,new IntentFilter("com.broadcast.FROCE_OFFLINE"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.romoveActivity(this);
      //  unregisterReceiver(mForceOffLineReceiver);
    }


}
