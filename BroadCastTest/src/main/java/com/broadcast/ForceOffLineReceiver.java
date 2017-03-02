package com.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017/1/13.
 */
public class ForceOffLineReceiver extends BroadcastReceiver {

    private final String Tag="ForceOffLineReceiver";
    @Override
    public void onReceive(final Context context, Intent intent) {
        showDialog(context);
    }
    public  void  showDialog(final Context context){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        Log.e(Tag,context.toString());
        builder.setTitle("异地登录");
        builder.setMessage("如果不是本人操作，请尝试修改密码");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ActivityManager.finishAllActivity();
                Intent intent=new Intent(context,LoginActivity.class);
                context.startActivity(intent);
            }
        });
       final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        new  Thread(new Runnable() {
            @Override
            public void run() {
                alertDialog.show();
            }
        });

    }

}
