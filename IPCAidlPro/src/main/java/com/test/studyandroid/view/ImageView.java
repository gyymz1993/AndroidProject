package com.test.studyandroid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.test.studyandroid.abstra.ShapeDrawer;
import com.test.studyandroid.viewutils.BitmapBuffer;
import com.test.studyandroid.viewutils.SystemParams;


/**
 * Created by Administrator on 2016/3/15.
 */
public class ImageView extends View{

    private ShapeDrawer shapeDrawer;  //图形绘制器
    public ImageView(Context context) {
        this(context, null);

    }

    public ImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        shapeDrawer=new LineDrawer(this);
    }

    public ShapeDrawer getShapeDrawer() {
        return shapeDrawer;
    }

    public void setShapeDrawer(ShapeDrawer shapeDrawer) {
        this.shapeDrawer = shapeDrawer;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        SystemParams.areaWidth=this.getMeasuredWidth();
        SystemParams.areaHeight=this.getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (SystemParams.isRedo){
            //撤销
            canvas.drawBitmap(BitmapBuffer.getInstance().getBitmap(),0,0,null);
            SystemParams.isRedo=false;
        }else{
            shapeDrawer.draw(canvas);
        }
        shapeDrawer.logic();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return shapeDrawer.onTouchEvent(event);
    }
}
