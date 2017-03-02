package com.rx.lib.rx;


import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;

/**
 *
 * 创建人：Yangshao
 * 创建时间：2017/1/19 10:24
 * @version 利用RX实现EventBus
 *
 */
public class RxBus {
    private static  volatile RxBus defaultInstance;
    private final SerializedSubject mSubject;

    public RxBus() {
        mSubject=new SerializedSubject<>(PublishSubject.create());
    }

    public void post(Object o){
        mSubject.onNext(o);
    }

    public <T> Observable<T> toObservable(Class<T> type){
        return mSubject.ofType(type);
    }
}
