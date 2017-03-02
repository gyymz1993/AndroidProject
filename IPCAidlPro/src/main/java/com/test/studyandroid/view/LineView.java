package com.test.studyandroid.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/3/14.
 */
public class LineView extends View{
    private int preX,preY;
    private int currentX,currentY;
    /*缓存区域*/
    private Canvas bitMapCanvas;
    private Bitmap bitmapBuffer;

    private Paint paint;
    public LineView(Context context) {
        this(context,null);
    }

    public LineView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
    }

    private void initPaint() {
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
    }

    /**
     *    大小改变时回调
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (bitmapBuffer==null){
            int width=getWidth();
            int height=getHeight();
            bitmapBuffer=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
            bitMapCanvas=new Canvas(bitmapBuffer);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //江bitmap绘制在view上
        canvas.drawBitmap(bitmapBuffer,0,0,null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y= (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //手指按下
                preX=x;
                preY=y;
                break;
            case MotionEvent.ACTION_MOVE:
                //移动
                currentX=x;
                currentY=y;
                bitMapCanvas.drawLine(preX, preY, currentX, currentY, paint);
                this.invalidate();
                //当前点的 坐标 换成下一个起点
                preX=currentX;
                preY=currentY;
                break;
            case MotionEvent.ACTION_UP:
                invalidate();
                break;
            default:
                break;
        }
        return true;
    }

}
