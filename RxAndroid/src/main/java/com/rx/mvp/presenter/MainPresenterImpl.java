package com.rx.mvp.presenter;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import com.rx.lib.rx.RxBus;
import com.rx.lib.rx.SchedulersCompat;
import com.rx.lib.utils.EventConstant;
import com.rx.mvp.event.MainEvent;
import com.rx.mvp.view.MainViewImpl;

/**
 * Created by Administrator on 2017/1/19.
 */
public class MainPresenterImpl implements MainPresenter {

    MainViewImpl  mMainView;
    RxBus mRxBus;
    Subscription getAllSouvenir;
    public MainPresenterImpl(RxBus rxBus,MainViewImpl mainView) {
        mRxBus = rxBus;
        mMainView=mainView;
    }



    @Override
    public List<String> getLoadNetData() {
        List<String> mList=new ArrayList<>();
        final AVQuery<AVObject> avQuery = new AVQuery<>("TestObject");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    Log.v("TAG", "get data success!");
                    for (int i = 0; i < list.size(); i++) {
                        String name = list.get(i).getString("username");
                        //mList.add(name);
                    }
                } else {
                    Log.v("TAG", "get data fail!");
                }
            }
        });
        return mList;
    }



    @Override
    public void getLoadNetDatafoRx() {
        mMainView.showRefreshingLoading();
         getAllSouvenir = getObservable()
                .compose(SchedulersCompat.<List<String>>observeOnMainThread())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onCompleted() {
                        mMainView.hideRefreshingLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mMainView.hideRefreshingLoading();
                        mMainView.showToast("可能出了点错误哦");
                    }

                    @Override
                    public void onNext(List<String> list) {
                        for (int i = 0; i < list.size(); i++) {
                            Log.e("TAG  onNext", list.get(i).toString());
                        }
                        mRxBus.post(new MainEvent(1, list));
                    }
                });

    }

    @Override
    public void LoadingPageDataFromNet(int size, final int page) {
        mMainView.showRefreshingLoading();
        Log.e("TAG","'LoadingPageDataFromNet  size: "+size+" page:"+page);
        getAllSouvenir = getObservableForPage(size,page)
                .compose(SchedulersCompat.<List<String>>applyIoSchedulers())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onCompleted() {
                        mMainView.hideRefreshingLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mMainView.hideRefreshingLoading();
                        mMainView.showToast("可能出了点错误哦");
                    }

                    @Override
                    public void onNext(List<String> list) {
                        for (int i = 0; i < list.size(); i++) {
                            Log.e("TAG  onNext", list.get(i).toString());
                        }
                        if (page == 0) {
                            mRxBus.post(new MainEvent(EventConstant.REFRESH,list));
                        } else {
                            mRxBus.post(new MainEvent(EventConstant.LOADMORE,list));
                        }
                    }
                });

    }

    public Observable<List<String>> getObservable(){
        return  Observable.create(new OnSubscribe<List<String>>() {
            @Override
            public void call(final Subscriber<? super List<String>> subscriber) {
                final AVQuery<AVObject> avQuery = new AVQuery<>("TestObject");
                avQuery.findInBackground(new FindCallback<AVObject>() {
                    @Override
                    public void done(List<AVObject> list, AVException e) {
                        if (e == null) {
                            Log.v("TAG", "get data success!");
                            List<String> mList=new ArrayList<>();
                            for (int i = 0; i < list.size(); i++) {
                                String name = list.get(i).getString("username");
                                Log.e("TAG  done", name);
                                mList.add(name);
                            }
                            subscriber.onNext(mList);
                        } else {
                            Log.v("TAG", "get data fail!");
                            subscriber.onError(e);
                        }
                        subscriber.onCompleted();
                    }
                });
            }
        }).subscribeOn(AndroidSchedulers.mainThread());
    }



    public Observable<List<String>> getObservableForPage(final int size, final int page) {
        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(final Subscriber<? super List<String>> subscriber) {
                AVQuery<AVObject> query = AVQuery.getQuery("TestObject");
                //query.whereEqualTo(SouvenirDao.SOUVENIR_AUTHORID, authorId);
                //query.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
                query.setLimit(size);
                query.setSkip(size * page);
                //query.include(SouvenirDao.SOUVENIR_AUTHOR);
                //query.orderByDescending("createdAt");
                query.findInBackground(new FindCallback<AVObject>() {
                    @Override
                    public void done(List<AVObject> list, AVException e) {
                        if (e == null) {
                            Log.v("TAG", "get data success!");
                            List<String> mList=new ArrayList<>();
                            for (int i = 0; i < list.size(); i++) {
                                String name = list.get(i).getString("username");
                                Log.e("TAG  done", name);
                                mList.add(name);
                            }
                            subscriber.onNext(mList);
                        } else {
                            Log.v("TAG", "get data fail!");
                            subscriber.onError(e);
                        }
                        subscriber.onCompleted();

                    }
                });
            }
        }).subscribeOn(AndroidSchedulers.mainThread());
    }

    public void detachView() {
        if (getAllSouvenir != null && !getAllSouvenir.isUnsubscribed()) {
            getAllSouvenir.unsubscribe();
        }
    }
}
