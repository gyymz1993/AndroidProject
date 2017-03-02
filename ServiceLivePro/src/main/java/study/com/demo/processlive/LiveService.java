package study.com.demo.processlive;

import android.annotation.TargetApi;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

/**
 * Created by Administrator on 2017/2/22.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class LiveService   extends NotificationListenerService{

    private LiveService(){

    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
    }
}
