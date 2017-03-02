package study.com.demo.utils;

import android.app.Activity;
import android.content.Context;

import java.lang.ref.WeakReference;

import study.com.demo.LiveActivity;

/**
 * Created by Administrator on 2017/2/22.
 */
public class SceenManager {

    private Context mContext;
    private WeakReference<Activity> mActivityWeakReference;
    private static SceenManager sManager;

    public static SceenManager getInstance(Context context){
        if (sManager==null){
            synchronized (SceenManager.class){
                if (sManager==null){
                    sManager=new SceenManager(context.getApplicationContext());
                }
            }
        }
        return sManager;
    }

    private SceenManager(Context context){
        this.mContext=context;
    }

    public void setActivity(Activity activity){
        mActivityWeakReference=new WeakReference<Activity>(activity);
    }

    public void startLiveActivity(){
        LiveActivity.actionToLiveActivity(mContext);
    }

    public void finishActivity(){
        if (mActivityWeakReference!=null){
            Activity activity=mActivityWeakReference.get();
            if (activity!=null){
                activity.finish();
            }
        }
    }


}
