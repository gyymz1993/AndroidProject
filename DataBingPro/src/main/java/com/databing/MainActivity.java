package com.databing;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.databing.bean.User;
import com.databing.databinding.ActivityMainBinding;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       ActivityMainBinding  activityMainBinding= DataBindingUtil.setContentView(this, R.layout.activity_main);
        User user = new User("loonggg", 23);
        activityMainBinding.setUser(user);
        }

}
