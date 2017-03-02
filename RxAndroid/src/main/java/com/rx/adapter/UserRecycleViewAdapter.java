package com.rx.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.rx.R;

/**
 * Created by Administrator on 2017/1/19.
 */
public class UserRecycleViewAdapter extends RecyclerView.Adapter<UserRecycleViewAdapter.ViewHolder> {

    private List<String> mList;
    public UserRecycleViewAdapter(@NonNull List<String> List) {
        Log.e("UserRecycleViewAdapter","UserRecycleViewAdapter");
        this.mList = List;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("UserRecycleViewAdapter","onCreateViewHolder");
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false)) ;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mList.size()!=0){
            holder.id_tv_user.setText(mList.get(position));
        }

    }


    @Override
    public int getItemCount() {
        Log.e("UserRecycleViewAdapter","getItemCount"+mList.size());
        return mList.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView id_tv_user;
        public ViewHolder(View itemView) {
            super(itemView);
            id_tv_user= (TextView) itemView.findViewById(R.id.id_tv_user);
        }
    }
}
