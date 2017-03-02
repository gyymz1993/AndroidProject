package study.com.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import study.com.demo.processlive.KeepLiveService;
import study.com.demo.utils.SceenManager;
import study.com.demo.utils.ScreenBroadcastListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MainActivity", "MainActivity onCreate");

        Intent intent=new Intent(this,KeepLiveService.class);
        startService(intent);

    }

    public void startBraodcast() {
        final SceenManager sceenManager = SceenManager.getInstance(this);
        ScreenBroadcastListener screenBroadcastListener = new ScreenBroadcastListener(this);
        Log.e("screenBroadcastListener", screenBroadcastListener.toString() + "");
        screenBroadcastListener.registerListener(new ScreenBroadcastListener.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                Log.e("sceenManager", "sceenManager onScreenOn");
                sceenManager.finishActivity();
            }

            @Override
            public void onScreenOff() {
                sceenManager.startLiveActivity();
                Log.e("MainActivity", "LiveActivity onScreenOff");
            }
        });
    }


}
