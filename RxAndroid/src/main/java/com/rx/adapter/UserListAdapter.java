package com.rx.adapter;

import java.util.List;

import com.rx.R;
import com.rx.lib.base.adapter.BaseViewHolder;
import com.rx.lib.base.adapter.CommonAdapter;

/**
 * Created by Administrator on 2017/1/19.
 */
public class UserListAdapter extends CommonAdapter<String> {

    public UserListAdapter(List<String> mDatas, int itemLayoutId) {
        super(mDatas, itemLayoutId);
    }

    @Override
    public void convert(int position, BaseViewHolder helper, String item) {
        helper.setText(R.id.id_tv_user,item);
    }
}
