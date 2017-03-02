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
 * Created by Administrator on 2016/3/14.
 */
public class LineView2 extends View{

    private int preX,preY;
    private Path path;
    private Paint  paint;

    private Bitmap bitmapBuffer;
    private Canvas bitmapCanvas;
    public LineView2(Context context) {
        this(context, null);
    }

    public LineView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
    }

    private void initPaint() {
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(5);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);

        path=new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (bitmapBuffer==null){
            int width=getMeasuredWidth();
            int height=getMeasuredHeight();
            bitmapBuffer=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
            bitmapCanvas=new Canvas(bitmapBuffer);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmapBuffer,0,0,null);
        canvas.drawPath(path,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x=(int)event.getX();
        int y=(int)event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.reset();
                preX=x;
                preY=y;
                path.moveTo(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                int contrlX=(x+preX)/2;
                int coutrlY=(y+preY)/2;
                path.quadTo(contrlX, coutrlY, x, y);
                invalidate();
                //下一次 起点
                preX=x;
                preY=y;
                break;
            case MotionEvent.ACTION_UP:
                //手指松开的绘制结果 在两个 缓存中
                bitmapCanvas.drawPath(path,paint);
                invalidate();
                break;
            default:
                break;
        }
        return true;
    }
}
