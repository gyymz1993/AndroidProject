package com.test.studyandroid;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;

import com.test.studyandroid.aidl.IcounService;


/*
 * 创建人：Yangshao
 * 创建时间：2016/3/7 16:20
 * @version
 *

                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         佛祖保佑       永无BUG
*/
public class ServiceDemoActivity extends Activity {

    private IcounService icounService;

    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, final IBinder service) {
            icounService= (IcounService) service;
            new  Thread(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        try {
                            SystemClock.sleep(1000);
                            Log.e("ymz","countService on service  connect,count is"+icounService.getCount());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }).start();
        }

        @Override

        public void onServiceDisconnected(ComponentName name) {
            icounService=null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ser_main);

        Intent intent = new Intent();
        intent.setAction("com.server.test");
        intent.setPackage("com.test.studyandroid"); //指定启动的是那个应用（lq.cn.twoapp）中的Action(b.aidl.DownLoadService)指向的服务组件
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        //this.bindService(new Intent("com.service.test"), this.serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
       // this.stopService(new Intent(this,MyService.class));
        Log.e("ymz","onDestroy");
        this.unbindService(serviceConnection);
        super.onDestroy();
    }
}
