package study.com.demo.processlive;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import study.com.demo.R;

/**
 * 一个前台服务    发送一个空的Notification   图标不会显示
 *
 * service中启动一个InnerService 两个服务同时startForeground 且
 *
 * 绑定同样的ID    Stop掉InnerService这样通知栏图标及被移除
 *
 * Created by Administrator on 2017/2/22.
 */
public class KeepLiveService  extends Service{

    public static final int NOTIFICATION_ID=0x11;

    public KeepLiveService(){

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw  new UnsupportedOperationException("not yet  implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //API在18以下  直接发送notification冰将其置前台
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.JELLY_BEAN_MR2){
            startForeground(NOTIFICATION_ID,new Notification());
        }else {
            //18以上  发送notifity置前台启动 innerService
            Notification.Builder  builder=new Notification.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            startService(new Intent(this,InnerService.class));
        }
    }

    public class InnerService extends Service{

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onCreate() {
            super.onCreate();
            //发送KeepLiveService中ID相同的notification  然后取消自己的 前台显示
            Notification.Builder builder=new Notification.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            startForeground(NOTIFICATION_ID,builder.build());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    stopForeground(true);
                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    manager.cancel(NOTIFICATION_ID);
                    stopSelf();
                }
            },100);
        }
    }


}
