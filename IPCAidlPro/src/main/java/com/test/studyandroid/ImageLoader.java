package com.test.studyandroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.LruCache;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by Administrator on 2016/3/8.
 */
public class ImageLoader {
    /**
     * 图片缓存的核心对象
     */
    private LruCache <String,Bitmap>mLurCache;
    /**
     * 线程池
     */
    private ExecutorService mThreadPool;

    //默认线程池个数
    private  static  int DEFAULT_THREAD_COUNT;

    /**
     *
     */
    private static Type mType=Type.LIFO;

    public Runnable getTask() {
        if (mType==Type.FIF0){
            return mTaskQueue.removeFirst();
        }else if (mType==Type.LIFO){
            return mTaskQueue.removeLast();
        }
        return null;
    }

    /**
     * 调度方式
     */
    private  enum Type{
        FIF0,LIFO
    }

    /**
     * 任务列表
     */
    private LinkedList<Runnable>  mTaskQueue;

    /**
     * 后台轮询线程
     */
    private  Thread  mPoolThread;
    //轮询 线程消息
    private Handler mPoolThreadHandle;
    private static ImageLoader  imageLoader;
    //信号量  同步方法  控制变量 先后
    private Semaphore mSemaphorePoolThreadHandler=new Semaphore(0);
    //控制 线程池    出入 队列
    private Semaphore mSemaphoreQueue;
    private  Handler mUIhandler;
    private ImageLoader(int threadCount,Type type){
        init(threadCount,type);
    }

    /**
     * 初始化 线程数量 和 默认 方式
     * @param threadCount
     * @param type
     */
    private void init(int threadCount, Type type) {
        //后台轮训线程
        mPoolThread=new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                mPoolThreadHandle=new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        mThreadPool.execute(getTask());
                        try {
                            mSemaphoreQueue.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                //释放 信号量  如果不为空
                mSemaphorePoolThreadHandler.release();
                Looper.loop();
            }
        };
        mPoolThread.start();

        int maxMemory= (int) Runtime.getRuntime().maxMemory();
        int cacheMemory=maxMemory/8;
        mLurCache=new LruCache<String,Bitmap>(cacheMemory){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight();
            }
        };

        mThreadPool= Executors.newFixedThreadPool(threadCount);
        mTaskQueue=new LinkedList<>();
        mType=type;
        mSemaphoreQueue=new Semaphore(threadCount);
    }

    public static ImageLoader getInstace(int defaThreadCount,Type type){
        if (imageLoader==null){
            synchronized (ImageLoader.class){
                if (imageLoader==null){
                    imageLoader=new ImageLoader(defaThreadCount,type);
                }
            }
        }
        return imageLoader;
    }

    public void refreashBitMap(Bitmap bm,String path,ImageView imageView){
        ImageHolder imageHolder=new ImageHolder();
        imageHolder.mbitmap=bm;
        imageHolder.path=path;
        imageHolder.mImageView=imageView;
        Message message=Message.obtain();
        message.obj=imageHolder;
        mUIhandler.sendMessage(message);
    }


    /**
     * 根据路径为 ImageView 设置图片
     * @param path
     */
    public void loadImage(final String path, final ImageView imageView){
        imageView.setTag(path);
        if (mUIhandler==null){
            mUIhandler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    //获得 图片 回调
                    ImageHolder holder= (ImageHolder)msg.obj;
                    ImageView igv=holder.mImageView;
                    String ph=holder.path;
                    Bitmap  bm=holder.mbitmap;
                    if (igv.getTag().equals(ph)){
                        igv.setImageBitmap(bm);
                    }

                }
            };
        }
        Bitmap bm=getBitmapFromLurCache(path);
        if (bm!=null){
            refreashBitMap(bm,path,imageView);
        }else{
            addTasks(new Runnable(){
                @Override
                public void run() {
                    //加载 图片    图片压缩
                    //获得图片需要显示大小
                    ImageSize  imageSize=getImageViewSize(imageView);
                    //压缩 图片
                    Bitmap bitmap = decodeSampledBitmapFromPath(path, imageSize);
                    //将图片加入缓存
                    addBitmapTolruCache(path,bitmap);
                    //回调
                    refreashBitMap(bitmap, path, imageView);
                    mSemaphoreQueue.release();
                }
            });
        }
    }

    /**
     *  将图片 加入缓存
     * @param path
     * @param bitmap
     */
    private void addBitmapTolruCache(String path, Bitmap bitmap) {
        if (getBitmapFromLurCache(path)==null){
            if (bitmap!=null){
                mLurCache.put(path,bitmap);
            }
        }
    }


    private Bitmap decodeSampledBitmapFromPath(String path, ImageSize imageSize) {
        //得到 图片宽高
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeFile(path,options);


        options.inSampleSize=caculateSampleSize(options,imageSize.width,imageSize.height);
        //加载到内存中
        options.inJustDecodeBounds=false;
        Bitmap  bitmap=BitmapFactory.decodeFile(path,options);
        return bitmap;
    }

    private int caculateSampleSize(BitmapFactory.Options options, int rewidth, int reheight) {
        int width=options.outWidth;
        int heigth=options.outHeight;
        int  inSampleSize=1;
        if (width>rewidth||heigth>reheight){
            int widthRadio=Math.round(width*1.0f/rewidth);
            int heightRadio=Math.round(heigth*1.0f/reheight);
            inSampleSize=Math.max(widthRadio,heightRadio);
        }
        return  inSampleSize;
    }


    /**
     * 根据Imageview获取适当的宽高
     * @param imageView
     * @return
     */
    private ImageSize getImageViewSize(ImageView imageView) {
        DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
        ImageSize imageSize=new ImageSize();
        ViewGroup.LayoutParams lp = imageView.getLayoutParams();
        int  width = (lp.width== ViewGroup.LayoutParams.WRAP_CONTENT)?0:imageView.getWidth();
        if (width<=0){
            width=lp.width; //获取imageview在 layout中声明的 宽度
        }
        if (width<=0){
           // width=imageView.getMaxWidth();
            width=getObjctFileValue("mMinWidth",imageView);
        }
        if (width<=0){
            width = displayMetrics.widthPixels;
        }

        int  height = (lp.width== ViewGroup.LayoutParams.WRAP_CONTENT)?0:imageView.getWidth();
        if (height<=0){
            height=lp.width; //获取imageview在 layout中声明的 宽度
        }
        if (height<=0){
           // height=imageView.getMaxWidth();
            width=getObjctFileValue("mMinHeight",imageView);
        }
        if (height<=0){
            height = displayMetrics.heightPixels;
        }
        imageSize.width=width;
        imageSize.height=height;
        return imageSize;
    }

    /**
     * 通过反射 方法获取对象的属性值
     */
    private static int getObjctFileValue(String fileName,Object obj){
        int  value=0;
        Field file= null;
        try {
            file = ImageView.class.getDeclaredField(fileName);
            file.setAccessible(true);
            int fieldValue=file.getInt(obj);
            if (fieldValue>0&&fieldValue<Integer.MAX_VALUE){
                value=fieldValue;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

    public  class ImageSize{
        int width;
        int height;
    }

    private synchronized void addTasks(Runnable runnable) {
        mTaskQueue.add(runnable);
        try {
            if (mPoolThreadHandle==null)
            mSemaphorePoolThreadHandler.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mPoolThreadHandle.sendEmptyMessage(0x100);
    }

    /**
     *
     * @param path
     * @return  根据路径获取缓存图片
     */
    private Bitmap getBitmapFromLurCache(String path) {
        return mLurCache.get(path);
    }

    private  class  ImageHolder{
        Bitmap mbitmap;
        ImageView  mImageView;
        String path;
    }
}
