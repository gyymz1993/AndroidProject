package com.databing;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.databing.bean.User;
import com.databing.databinding.ListviewMianBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/20.
 */
public class ListViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        ListviewMianBinding listviewMianBinding=DataBindingUtil.setContentView(this,R.layout.listview_mian);
        List<User> list=new ArrayList<>();
        for (int i=0;i<10;i++){
            User user=new User();
            user.setName("yma"+i);
            user.setAge(1+i);
            if (i%2==0){
                user.setChecked(true);
            }else {
                user.setChecked(false);
            }
            user.setPic(getImageUrl());
            list.add(user);
        }
        CommonAdapter<User> adapter = new CommonAdapter<>(this, list, R.layout.listview_item_mian, com.databing.BR.userDetail);
        listviewMianBinding.setAdapter(adapter);

    }
    public String getImageUrl() {
        return "http://cdn.meme.am/instances/60677654.jpg";
    }



}
