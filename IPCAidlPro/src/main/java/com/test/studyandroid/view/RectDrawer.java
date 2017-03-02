package com.test.studyandroid.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.test.studyandroid.abstra.ShapeDrawer;
import com.test.studyandroid.viewutils.AttributeTool;
import com.test.studyandroid.viewutils.BitmapBuffer;


/*
 * 创建人：Yangshao
 * 创建时间：2016/3/15 12:18
 * @version  绘制矩形
 *
 */
public class RectDrawer extends ShapeDrawer {

    private  float  firstX,firstY;
    private float currentX,currentY;
    public RectDrawer(View view) {
        super(view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x=event.getX();
        float y=event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                firstX=x;
                firstY=y;
                break;
            case MotionEvent.ACTION_MOVE:
                currentX=x;
                currentY=y;
                getView().invalidate();
                break;
            case MotionEvent.ACTION_UP:
                Canvas canvas= BitmapBuffer.getInstance().getCanvas();
                drawShape(canvas,firstX,firstY,currentX,currentY);
                getView().invalidate();
                //保存到撤销栈中
                BitmapBuffer.getInstance().pushBitmap();
                break;
        }
        return true;
    }

    @Override
    public void logic() {

    }

    @Override
    public void draw(Canvas viewCanvas) {
        super.draw(viewCanvas);
    }

    /**
     * 画当前的 形状  drawRect 左上右下
     */
    protected void drawShape(Canvas canvas,float firstX
    ,float firstY,float currentX,float currentY){
        Paint paint= AttributeTool.getInstance().getPaint();
        //右下  ↘
        if (currentX>firstX&&currentY>firstY){
            canvas.drawRect(firstX,firstY,currentX,currentY,paint);
        }
        //左上 ↖
        if (currentX<firstX&&currentY<firstY){
            canvas.drawRect(currentX,currentY,firstX,firstY,paint);
        }
        //左下  ↙
        if (currentX<firstX&&currentY>firstY){
            canvas.drawRect(currentX,firstY,firstX,currentY,paint);
        }
        //右上 ↗
        if (currentX>firstX&&currentY<firstY){
            canvas.drawRect(firstX,currentY,currentX,firstY,paint);
        }

    }
}
