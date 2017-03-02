package com.rx.lib.rx;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建人：Yangshao
 * 创建时间：2017/1/18 17:19
 * @version
 * 用Compose来切换线程调度
 * RxjavaScheduler线程与Main线程切换
 *
 */
public class SchedulersCompat {
    public static <T> Observable.Transformer<T,T>  observeOnMainThread(){
        return  uiMainTransfromer;
    }

    public static <T> Observable.Transformer<T,T>  applyIoSchedulers(){
        return  ioTransfromer;
    }

    public static final Observable.Transformer  ioTransfromer=new Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            return  ((Observable)observable).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
    };

    public static final Observable.Transformer uiMainTransfromer=new Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            return ((Observable)observable).observeOn(AndroidSchedulers.mainThread());
        }
    };
}
