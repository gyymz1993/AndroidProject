package com.test.studyandroid.viewutils;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Administrator on 2016/3/15.
 */
public class BitmapBuffer {

    private Bitmap bitmap;
    private Canvas  canvas;
    static BitmapBuffer instance;
    private BitmapBuffer(int width,int height){
        init(width,height);
    }

    private void init(int width, int height) {
        bitmap=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        canvas=new Canvas(bitmap);
    }

    public static BitmapBuffer getInstance() {
        if (instance==null){
            synchronized (BitmapBuffer.class){
                if (instance==null)
                    instance=new BitmapBuffer(SystemParams.areaWidth, SystemParams.areaHeight);
            }
        }
        return instance;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    /**
     * 将当前绘图 保存在在栈中
     */
    public void pushBitmap(){
        BitmapHistory.getInstance().pushBitmap(bitmap.copy(Bitmap.Config.ARGB_8888,false));
    }

    /**
     *  撤销
     */
    public void redo(){
      BitmapHistory history= BitmapHistory.getInstance();
        if (history.isEeDo()){
            Bitmap bmp=history.reDo();
            if (bmp!=null){
                bitmap=bmp.copy(Bitmap.Config.ARGB_8888,true);
                //重新关联画布
                canvas.setBitmap(bitmap);
                //回收
                if (bmp!=null&&!bmp.isRecycled()){
                    bmp.recycle();
                    System.gc();
                    bmp=null;
                }
            }
        }
    }


}
