package com.rx.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.FadeInUpAnimator;
import rx.Subscription;
import rx.functions.Action1;
import com.rx.R;
import com.rx.adapter.UserListAdapter;
import com.rx.adapter.UserRecycleViewAdapter;
import com.rx.lib.rx.RxBus;
import com.rx.lib.rx.SchedulersCompat;
import com.rx.lib.utils.EventConstant;
import com.rx.mvp.event.MainEvent;
import com.rx.mvp.presenter.MainPresenterImpl;
import com.rx.mvp.view.MainViewImpl;

public class MainActivity extends AppCompatActivity implements MainViewImpl ,SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    List<String> mList = new ArrayList<>();
    MainPresenterImpl mMainPresenter;
    RxBus mRxBus;
    UserListAdapter mUserListAdapter;
    UserRecycleViewAdapter mUserRecycleViewAdapter;
    private SwipeRefreshLayout mSwipeFreshLayout;
    Subscription mSubscribe;
    int page;
    int pageCount = 5;

    LinearLayoutManager layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

    }

    public void initData() {
        //mMainPresenter.getLoadNetDatafoRx();
        mMainPresenter.LoadingPageDataFromNet(pageCount, page);
        mSubscribe = mRxBus.toObservable(MainEvent.class)
                .compose(SchedulersCompat.<MainEvent>observeOnMainThread())
                .subscribe(new Action1<MainEvent>() {
                    @Override
                    public void call(MainEvent mainEvent) {
                        for (int i = 0; i < mainEvent.getData().size(); i++) {
                            Log.e("TAG  call", mainEvent.getData().get(i).toString());
                        }
                        switch (mainEvent.getAction()){
                            case EventConstant.REFRESH:
                                mList.clear();
                                mList.addAll(mainEvent.getData());
                                break;
                            case EventConstant.LOADMORE:
                                mList.addAll(mainEvent.getData());
                                break;
                        }
                        mUserRecycleViewAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.id_listview);
        mSwipeFreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeFreshLayout);
        mSwipeFreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeFreshLayout.setOnRefreshListener(this);
        mRxBus = new RxBus();
        mMainPresenter = new MainPresenterImpl(mRxBus, this);
        mUserRecycleViewAdapter = new UserRecycleViewAdapter(mList);
        // mUserListAdapter = new UserListAdapter(mList, R.layout.user_list_item);
        layout = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layout);//这里用线性显示 类似于listview
        mRecyclerView.setItemAnimator(new FadeInUpAnimator());
        mRecyclerView.setAdapter(mUserRecycleViewAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING && layout.findLastVisibleItemPosition() + 1 == mUserRecycleViewAdapter.getItemCount()) {
                    mSwipeFreshLayout.setRefreshing(true);
                    page++;
                    mMainPresenter.LoadingPageDataFromNet(pageCount, page);
                } else {
                    Log.e("TAG", "没有数据");
                }
            }
        });
    }

    @Override
    public void showRefreshingLoading() {
        mSwipeFreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (mSwipeFreshLayout != null)
                    mSwipeFreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideRefreshingLoading() {
        if (mSwipeFreshLayout != null)
            mSwipeFreshLayout.setRefreshing(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!mSubscribe.isUnsubscribed()) {
            mSubscribe.unsubscribe();
        }
        mMainPresenter.detachView();
    }

    @Override
    public void showProgress(String message) {

    }

    @Override
    public void showProgress(String message, int progress) {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void close() {

    }

    @Override
    public void onRefresh() {
        page=0;
        mMainPresenter.LoadingPageDataFromNet(pageCount, page);
        Log.e("TAG","mMainPresenter.LoadingPageDataFromNet(pageCount, 0)");
    }
}