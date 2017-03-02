package com.rx.mvp.event;

import java.util.List;

/**
 * Created by Administrator on 2017/1/19.
 */
public class MainEvent {


    public MainEvent(int action, List<String> data) {
        mAction = action;
        mData = data;
    }

    private int mAction;
    private List<String> mData;

    public int getAction() {
        return mAction;
    }

    public void setAction(int action) {
        mAction = action;
    }

    public List<String> getData() {
        return mData;
    }

    public void setData(List<String> data) {
        mData = data;
    }
}
