package study.com.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import study.com.demo.utils.SceenManager;

/**
 * Created by Administrator on 2017/2/22.
 */
public class LiveActivity extends Activity {

    private static final String  TAG=LiveActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Window window=getWindow();

        //放在左上角
        window.setGravity(Gravity.START|Gravity.TOP);
        WindowManager.LayoutParams layoutParams=window.getAttributes();
        layoutParams.width=1;
        layoutParams.height=1;
        layoutParams.x=0;
        layoutParams.y=0;
        window.setAttributes(layoutParams);
        SceenManager.getInstance(this).setActivity(this);
    }


    public static void actionToLiveActivity(Context context){
        Log.e("LiveActivity","actionToLiveActivity");
        Intent  intent=new Intent(context,LiveActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"LiveActivity onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"LiveActivity onDestory");
    }


}
