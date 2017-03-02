package com.test.studyandroid.abstra;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import com.test.studyandroid.viewutils.BitmapBuffer;


/**
 * Created by Administrator on 2016/3/15.
 */
public abstract class ShapeDrawer {

    private View view;
    public  ShapeDrawer(View view){
        this.view=view;
    }

    public View getView(){
        return   view;
    }

    /**
     * 用于绘图     展示绘图结果
     */
    public void draw(Canvas  viewCanvas){
        //画历史结果
        Bitmap bitmap= BitmapBuffer.getInstance().getBitmap();
        viewCanvas.drawBitmap(bitmap,0,0,null);
    }

    /**
     * 事件
     * @param event
     * @return
     */
    public abstract boolean  onTouchEvent(MotionEvent event);


    /**
     * 绘图逻辑
     */
    public abstract  void logic();


}
