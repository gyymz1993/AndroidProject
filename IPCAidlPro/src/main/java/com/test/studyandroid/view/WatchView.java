package com.test.studyandroid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/*
 * 创建人：Yangshao
 * 创建时间：2016/3/14 10:09
 * @version
 *
 */
public class WatchView extends View{
    private Calendar  calendar;
    private Paint  paint;
    public WatchView(Context context) {
        this(context, null);
    }

    public WatchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WatchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);

        calendar=Calendar.getInstance();
    }

    public void run() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                postInvalidate();
            }
        }, 0, 1000);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //获取宽高
        int width=this.getWidth();
        int height=this.getHeight();

        //计算圆盘  直径  取短的
        int  len=Math.min(width,height);
        //绘制  表盘
        drawPlate(canvas, len);
        //绘制指针
        drawPaints(canvas, len);
    }

    /**
     *  绘制  圆盘
     * @param canvas
     * @param len
     */
    private void drawPlate(Canvas canvas, int len) {
        canvas.save();
        //画圆
        int r=len/2;
        canvas.drawCircle(r,r,r,paint);
        //画刻度
        for (int i=0;i<60;i++){
            if (i % 5==0){
                //长刻度   占圆 半径的1/10
                paint.setColor(Color.RED);
                paint.setStrokeWidth(4);
                canvas.drawLine(r+9*r/10,r,len,r,paint);
            }else {
                paint.setColor(Color.GREEN);
                paint.setStrokeWidth(1);
                canvas.drawLine(r+14*r/15,r,len,r,paint);
            }
            //以Rr
            canvas.rotate(6, r, r);
        }
        canvas.restore();
    }

    /**
     *  画指针
     * @param canvas
     * @param len
     */
    private void drawPaints(Canvas canvas, int len) {
        //先获取系统时间
        calendar.setTimeInMillis(System.currentTimeMillis());
        //获取分时秒
        int  hours=calendar.get(Calendar.HOUR)%12;   //转换为12小时
        int  minutes=calendar.get(Calendar.MINUTE);
        int seconds=calendar.get(Calendar.SECOND);
        //画时针
        //角度
        int degree=360/12*hours;
        //转换为弧度
        double radians=Math.toRadians(degree);
        //计算 两个点 的坐标

        int  r=len/2;
        int startX=r;
        int startY=r;

        int endX= (int) (startX+r*0.5*Math.cos(radians));
        int endY= (int) (startY+r*0.5*Math.sin(radians));
        canvas.save();
        paint.setStrokeWidth(3);
        canvas.rotate(-90,r,r);
        //画时针
        canvas.drawLine(startX,startY,endX,endY,paint);
        canvas.restore();

        //画分针
        //画角度
        degree=360/60*minutes;
        radians=Math.toRadians(degree);
        endX= (int) (startX+r*0.6*Math.cos(radians));
        endY= (int) (startY+r*0.6*Math.sin(radians));
        canvas.save();
        //从 0-3 开始  时间就卡机
        paint.setStrokeWidth(2);
        //0 度从3点开始   所有 时间从12点开始需要旋转 90度
        canvas.rotate(-90, r, r);
        //画时针
        canvas.drawLine(startX,startY,endX,endY,paint);
        canvas.restore();

        //画秒针
        degree=360/60*seconds;
        radians=Math.toRadians(degree);
        endX= (int) (startX+r*0.8*Math.cos(radians));
        endY= (int) (startY+r*0.8*Math.sin(radians));
        canvas.save();
        paint.setStrokeWidth(1);
        //从0-3度 开始  时间12点 处开始   所以续传90度
        canvas.rotate(-90, r, r);
        //画时针
        canvas.drawLine(startX,startY,endX,endY,paint);
        //再给秒针 画个尾巴
        radians=Math.toRadians(degree-180);
        endX= (int) (startX+r*0.15*Math.cos(radians));
        endY= (int) (startY+r*0.15*Math.sin(radians));
        canvas.drawLine(startX,startY,endX,endY,paint);
        canvas.restore();
    }
}
