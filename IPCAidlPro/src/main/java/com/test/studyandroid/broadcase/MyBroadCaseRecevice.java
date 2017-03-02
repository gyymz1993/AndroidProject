package com.test.studyandroid.broadcase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/*
 * 创建人：Yangshao
 * 创建时间：2016/3/15 16:19
 * @version    
 *    
 */
public class MyBroadCaseRecevice  extends BroadcastReceiver {

    static String MYACTION;
    int currentLoading;
    int curLoad;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (MYACTION.equals(intent.getAction())){
            Log.e("ymz", "get the broadcast from  DownloadServer");
            currentLoading = intent.getIntExtra("CurrentLoading", 0);
            mHandler.sendMessage(mHandler.obtainMessage());
        }
    }

    Handler  mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("ymz","Current loading  :"+curLoad);
            if (curLoad<0||curLoad>100){
                Log.e("ymz","ERROR"+curLoad);
                return;
            }

        }
    };
}
