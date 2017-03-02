package com.rx.mvp.view;

/**
 * Created by Administrator on 2017/1/19.
 */
public interface MainView {

    void showProgress(String message);

    void showProgress(String message, int progress);

    void hideProgress();

    void showToast(String msg);

    void close();

}
