package com.mvpretrofitrxjava.inter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import rx.Observable;
import rx.Observer;
import com.mvpretrofitrxjava.R;
import com.mvpretrofitrxjava.inter.retrofix.RetrofixHelper;
import com.mvpretrofitrxjava.inter.rx.RxSchedulersCompat;

/**
 * Created by Administrator on 2017/2/15.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        requestMarkRetrofit();
    }

    public void requestMarkRetrofit(){
        Observable<JsonResult> observable = RetrofixHelper.getInstance(this).getRetrofixService().userLogin1("gyymz1993", "123456789", "20170808");
        observable.compose(RxSchedulersCompat.<JsonResult>applyIoSchedulers())
                .subscribe(new Observer<JsonResult>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onNext(JsonResult jsonResult) {
                if (jsonResult!=null){
                    Log.e("TAG RequestMarkRetrofit",jsonResult.toString());
                }
            }
        });

    }


}
