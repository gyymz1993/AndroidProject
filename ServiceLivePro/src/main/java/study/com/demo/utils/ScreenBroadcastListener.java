package study.com.demo.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * Created by Administrator on 2017/2/22.
 */
public class ScreenBroadcastListener {
    /***
     * 屏幕关闭时启动LiveActivity  开屏是关闭
     *
     * 监听系统锁屏广播   以接口的形式通知MainActivty启动或者关闭
     * */
    private Context mContext;
    private ScreenStateListener mStateListener;
    private ScreenBroadCastReceiver mBroadCastReceiver;


    public ScreenBroadcastListener(Context context){
        Log.e("ScreenBroadCastReceiver","start ScreenBroadcastListener");
        this.mContext=context;
        mBroadCastReceiver=new ScreenBroadCastReceiver();
    }
    public interface  ScreenStateListener{
        void onScreenOn();
        void onScreenOff();
    }

    public class ScreenBroadCastReceiver extends BroadcastReceiver{
        private String action;
        @Override
        public void onReceive(Context context, Intent intent) {
            action=intent.getAction();
            Log.e("ScreenBroadCastReceiver","start onReceive"+action);
            if (intent.ACTION_SCREEN_ON.equals(action)){
                mStateListener.onScreenOn();
                unRegisterListener();
            }else if (Intent.ACTION_SCREEN_OFF.equals(action)){
                mStateListener.onScreenOff();
            }
        }


    }

    public void registerListener(ScreenStateListener listener){
        mStateListener=listener;
        registerListener();
    }

    private  void registerListener(){
        IntentFilter filter=new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        mContext.registerReceiver(mBroadCastReceiver,filter);
    }

    private  void unRegisterListener(){
        mContext.unregisterReceiver(mBroadCastReceiver);
    }
}
