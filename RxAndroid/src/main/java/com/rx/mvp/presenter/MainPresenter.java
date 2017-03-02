package com.rx.mvp.presenter;

import java.util.List;

/**
 * Created by Administrator on 2017/1/19.
 */
public interface MainPresenter {
     List<String> getLoadNetData();

     void getLoadNetDatafoRx();

    /**
     * 分页查询
    * */
     void LoadingPageDataFromNet(int size, int page);

}
