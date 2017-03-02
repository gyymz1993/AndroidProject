package study.com.demo.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2017/2/22.
 */
public class LiveService extends Service {

    public static void  toLiveSeivice(Context context){
        Intent  intent=new Intent(context,LiveService.class);
        context.startService(intent);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //屏幕关闭时启动一个1像素activity
        final SceenManager sceenManager=SceenManager.getInstance(this);
        final ScreenBroadcastListener listener=new ScreenBroadcastListener(this);
        listener.registerListener(new ScreenBroadcastListener.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                sceenManager.finishActivity();
            }

            @Override
            public void onScreenOff() {
                sceenManager.startLiveActivity();
            }
        });

        return  START_REDELIVER_INTENT;
    }
}
