package com.test.studyandroid.viewutils;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Administrator on 2016/3/15.
 */
public class AttributeTool {

    private int color;    //y颜色
    private int  borderWidth;  //宽度
    private boolean  fill;     //是否填充满
    private Paint paint;
    private  AttributeTool(){
        reset();
    }

    private void reset() {
        this.color=Color.BLACK;
        this.borderWidth=1;
        this.fill=false;
    }

    public  Paint getPaint(){
        if (paint==null){
            paint=new Paint();
        }
        paint.setColor(color);
        paint.setStrokeWidth(borderWidth);
        paint.setAntiAlias(true);
        paint.setStyle(fill ? Paint.Style.FILL : Paint.Style.STROKE);
        paint.setTextSize(30);
        return paint;
    }

    private static AttributeTool instance;
    public static AttributeTool getInstance(){
        if (instance==null){
            synchronized (AttributeTool.class){
                if (instance==null)
                    instance=new AttributeTool();
            }
        }
        return  instance;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }
}
