package com.broadcast;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private String TAG="MainActivity";
    /**
     * 强制下线
     */
    private TextView mTvForceoffline;
    private String userName;
    /**
     * 欢迎你登录成功
     */
    private TextView mTvUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        userName = getIntent().getStringExtra(IntentKey.USER_NAME);
        initView();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState!=null){
            userName=savedInstanceState.getString(IntentKey.USER_NAME);
            Log.e(TAG,"恢复数据"+userName);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(IntentKey.USER_NAME,userName);
        Log.e(TAG,"恢复保存"+userName);
    }

    public void initView() {
        mTvForceoffline = (TextView) findViewById(R.id.tv_forceoffline);
        mTvForceoffline.setOnClickListener(this);
        mTvUserName = (TextView) findViewById(R.id.tv_userName);
        mTvUserName.setText("尊敬的："+userName+"欢迎你登录成功");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forceoffline:
                sendBroadCastforoffLine();
                break;
        }
    }

    public void sendBroadCastforoffLine() {
        Intent intent = new Intent("com.broadcast.FROCE_OFFLINE");
        sendBroadcast(intent);
    }
}


