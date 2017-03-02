package com.test.studyandroid.view;

import android.content.Context;
import android.graphics.Bitmap;
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
public class Rect3View extends View {



    private Paint  paint;
    private Path  path;
    private int firstX,firstY;
    private Bitmap  bitmapBuffer;
    private Canvas  bitmapCanvas;
    
    public Rect3View(Context context) {
        this(context, null);
    }

    public Rect3View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Rect3View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        
        initPaint();
    }

    private void initPaint() {
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        path=new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (bitmapBuffer==null){
            int  width=getMeasuredWidth();
            int  height=getMeasuredHeight();
            bitmapBuffer=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
            bitmapCanvas=new Canvas(bitmapBuffer);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
        canvas.drawBitmap(bitmapBuffer,0,0,null);
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
                //方向 ↗
                if (x<firstX&&y>firstY){
                    path.addRect(firstX,x,y,firstY, Path.Direction.CCW);
                }
                //方向 ↘
                if (x>firstX&&y>firstY) {
                    path.addRect(firstX,firstY,x,y, Path.Direction.CCW);
                }
                //方向 ↖
                if (x>firstX&&y<firstY){
                    path.addRect(x,y,firstX,firstY, Path.Direction.CCW);
                }
                //方向 ↙
                if (x<firstX&&y>firstY){
                    path.addRect(x,firstY,firstX,y, Path.Direction.CCW);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                bitmapCanvas.drawPath(path,paint);
                invalidate();
                break;
        }
        return true;
    }
}
