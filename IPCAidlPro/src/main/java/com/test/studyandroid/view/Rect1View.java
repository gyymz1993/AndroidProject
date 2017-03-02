package com.test.studyandroid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/3/15.
 */
public class Rect1View extends View{

    private  int firstX,firstY;
    private Path  path;
    private Paint paint;
    public Rect1View(Context context) {
        this(context, null);
    }

    public Rect1View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Rect1View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
    }

    private void initPaint() {
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);

        path=new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x=(int)event.getX();
        int y=(int)event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.reset();
                firstX=x;
                firstY=y;
                break;
            case MotionEvent.ACTION_MOVE:
                path.reset();
                path.addRect(firstX,firstY,x,y, Path.Direction.CCW);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                invalidate();
                break;
        }
        return true;
    }
}
