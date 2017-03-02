package com.test.studyandroid.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.test.studyandroid.abstra.ShapeDrawer;
import com.test.studyandroid.viewutils.AttributeTool;
import com.test.studyandroid.viewutils.BitmapBuffer;


/**
 * Created by Administrator on 2016/3/15.
 */
public class LineDrawer extends ShapeDrawer {


    private int firstx,firstY;
    private int currentX,currentY;
    public LineDrawer(View view) {
        super(view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x= (int) event.getX();
        int y= (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                firstx=x;
                firstY=y;
                break;
            case MotionEvent.ACTION_MOVE:
                currentX=x;
                currentY=y;
                drawLine(firstx,firstY,currentX,currentY);
                getView().invalidate();
                firstx=currentX;
                firstY=currentY;
                break;
            case MotionEvent.ACTION_UP:
                getView().invalidate();
                break;
            default:
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

    public void drawLine(float startX, float startY, float stopX, float stopY) {
        Paint paint= AttributeTool.getInstance().getPaint();
        Canvas canvas= BitmapBuffer.getInstance().getCanvas();
        canvas.drawLine(startX,startY,stopX,stopY,paint);
    }
}
