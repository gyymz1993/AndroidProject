package com.databing;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.databing.databinding.ContentMainBinding;

/**
 * Created by Administrator on 2017/2/20.
 */
public class ContentActivity  extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ContentMainBinding contentMainBinding=DataBindingUtil.setContentView(this,R.layout.content_main);
        contentMainBinding.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ContentActivity.this,"1111",Toast.LENGTH_LONG).show();
            }
        });
        contentMainBinding.setStr("11111");
        contentMainBinding.setArrayKey(1);
        contentMainBinding.setError(true);
        contentMainBinding.setListKey(1);
        contentMainBinding.setMapKey("222222");
    }
}
