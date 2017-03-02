package com.test.studyandroid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/3/14.
 */
public class RlueView extends View{

    private Paint paint;
    public RlueView(Context context) {
        this(context, null);
    }

    public RlueView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RlueView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(5);
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        int width=this.getWidth();
        int height=this.getHeight();

        drawPlant(canvas, width, height);
    }



    private void drawPlant(Canvas canvas, int width, int height) {
        paint.setAntiAlias(true);  //平滑
        RectF rectF=new RectF(10,10,width,height/10);
        canvas.drawRoundRect(rectF, 10, 15, paint);  //x半径  y半径
        //计算圆盘  直径  取短的
        int  len=Math.min(width,height);
        for (int i=0;i<130;i++){
            if (i%10==0){
                paint.setColor(Color.RED);
                paint.setStrokeWidth(3);
                canvas.drawLine(10,10,0,len,paint);
            }else {
                paint.setColor(Color.GRAY);
                paint.setStrokeWidth(1);
                canvas.drawLine(10,10,0,len,paint);
            }
            //以Rr
            canvas.rotate(0, len/2, len/2);
        }
        canvas.restore();
    }

}
