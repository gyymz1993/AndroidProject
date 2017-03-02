package com.databing;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


/*无需调用   直接内部调用显示图片绑定ListView*/
public class Utils {
    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        //使用Glide加载图片
        Glide.with(imageView.getContext()).load(url)
                .placeholder(R.mipmap.ic_launcher).into(imageView);
    }
}