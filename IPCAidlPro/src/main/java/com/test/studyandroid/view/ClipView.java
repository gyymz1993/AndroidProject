package com.test.studyandroid.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.test.studyandroid.R;



/*
 * 创建人：Yangshao
 * 创建时间：2016/3/14 9:27   
 * @version    
 *    
 */

public class ClipView extends View{
    private  int  i=0;
    private Bitmap bmpBoom;

    public ClipView(Context context) {
        this(context, null);
    }

    public ClipView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bmpBoom =BitmapFactory.decodeResource(getResources(), R.drawable.icon_ig);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //获取 位置 的 宽度和高度
        int  width=bmpBoom.getWidth();
        int  height=bmpBoom.getHeight();
        //裁剪区
        int frameWidth=width/7;
        Rect rect=new Rect(0,0,frameWidth,height);
        canvas.save();
        canvas.translate(100,100);   //平移坐标
        canvas.clipRect(rect);   //设置 裁剪区
        canvas.drawBitmap(bmpBoom,-i*frameWidth,0,null);  //播放一帧
        canvas.restore();
        i++;   //播放下一帧
        if(i==7) i=0;

    }
}
