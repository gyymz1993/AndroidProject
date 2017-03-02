package com.test.studyandroid.viewutils;

import android.graphics.Bitmap;

import java.util.Stack;

/**
 * Created by Administrator on 2016/3/15.
 */
public class BitmapHistory {

    private static BitmapHistory instance;
    private static Stack<Bitmap> stack;
    private BitmapHistory(){
        if (stack==null)
        {
            stack=new Stack<>();
        }
    }
    public static BitmapHistory getInstance() {
        if (instance==null){
            synchronized (BitmapBuffer.class){
                if (instance==null)
                    instance=new BitmapHistory();
            }
        }
        return instance;
    }

    /**
     * 将当前的历史绘图 结果压栈
     */

    public  void  pushBitmap(Bitmap  bitmap){
        int count=stack.size();
        if (count>=5){
            Bitmap bmp=stack.remove(0);
            if (!bmp.isRecycled()){
                bmp.recycle();
                System.gc();
                bmp=null;
            }
        }
        stack.push(bitmap);
    }

    /**
     * 撤销
     */
    public Bitmap  reDo(){
        Bitmap bmp=stack.pop(); //将顶部元素删除
        //回收 位图资源
        if (bmp!=null&&!bmp.isRecycled()){
            bmp.recycle();
            System.gc();
            bmp=null;
        }
        if (stack.empty())  return null;
        //返回撤消后的位图  对象
        return  stack.peek();
    }

    public boolean  isEeDo(){
        return  !stack.empty();
    }
}
