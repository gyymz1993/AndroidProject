package com.test.studyandroid.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import com.test.studyandroid.viewutils.AttributeTool;


/**
 * Created by Administrator on 2016/3/15.
 */
public class OvalDraw extends RectDrawer {
    public OvalDraw(View view) {
        super(view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public void logic() {

    }

    @Override
    public void draw(Canvas viewCanvas) {
        super.draw(viewCanvas);
    }

    @Override
    protected void drawShape(Canvas canvas, float firstX, float firstY, float currentX, float currentY) {
        Paint paint= AttributeTool.getInstance().getPaint();
        if (currentX>firstX&&currentY>firstY){
            canvas.drawOval(new RectF(firstX,firstY,currentX,currentY),paint);
        }
        //左上 ↖
        if (currentX<firstX&&currentY<firstY){
            canvas.drawOval(new RectF(currentX,currentY,firstX,firstY), paint);
        }
        //左下  ↙
        if (currentX<firstX&&currentY>firstY){
            canvas.drawOval(new RectF(currentX,firstY,firstX,currentY), paint);
        }
        //右上 ↗
        if (currentX>firstX&&currentY<firstY){
            canvas.drawOval(new RectF(firstX,currentY,currentX,firstY), paint);
        }
    }
}
