package com.databing;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
* Created by lulu on 2016/12/9.
* 通用的ListView的Adapter (单布局)
*/
public class CommonAdapter<T> extends BaseAdapter {
    private Context mContext;
    private List<T> mList;
    private int mLayoutId;
    private int mVariableId;

    public CommonAdapter(Context context, List<T> list, int layoutId, int variableId) {
        mContext = context;
        mList = list;
        mLayoutId = layoutId;
        mVariableId = variableId;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewDataBinding binding = null;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), mLayoutId, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        binding.setVariable(mVariableId, mList.get(position));
        return binding.getRoot();
    }
}