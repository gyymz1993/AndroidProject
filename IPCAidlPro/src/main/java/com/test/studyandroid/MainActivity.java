package com.test.studyandroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.test.studyandroid.abstra.ShapeDrawer;
import com.test.studyandroid.view.ClipView;
import com.test.studyandroid.view.LineView;
import com.test.studyandroid.view.LineView2;
import com.test.studyandroid.view.Rect1View;
import com.test.studyandroid.view.Rect3View;
import com.test.studyandroid.view.RlueView;
import com.test.studyandroid.view.WatchView;
import com.test.studyandroid.viewutils.AttributeTool;

import java.io.FileOutputStream;
import java.util.Timer;
import java.util.TimerTask;

/*
 * 创建人：Yangshao
 * 创建时间：2016/3/7 16:17
 * @version
 *
_ooOoo_
o8888888o
88" . "88
(| -_- |)
O\  =  /O
____/`---'\____
.'  \\|   |//  `.
/  \\||| : |||// \
/_|||||-:- |||||--\
| |\\\ -  /// |----\
|\_|  ''\---/'' |---\
\  .-\__  `-`  ___/-./
___`. .'  /--.--\  `. . __
."" '<  `.___\_<|>_/___.'  >'"".
| | :  `- \`.;`\ _ /`;.`/ - ` : | |
\  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        该就寝了
*/
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Button btn= (Button) findViewById(R.id.id_jp);
       // ImageView igv= (ImageView) findViewById(R.id.id_ig_view);
       // canvasImage(igv);
       // canvasPaint(igv);
       // canvasRoundRect(igv);
      //  canvasXinxin(igv);
       // canvasMove(igv);

        //initView();

        initDrawView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ShapeDrawer shapeDrawer=null;
        AttributeTool at=AttributeTool.getInstance();
        switch (item.getItemId()){
//            case R.id.redo:
//                break;
        }

        return true;
    }

    private void initDrawView() {
        com.test.studyandroid.view.ImageView imageView= (com.test.studyandroid.view.ImageView) findViewById(R.id.id_imageview);

    }

    private void initView() {
        final ClipView clipViwe = (ClipView) findViewById(R.id.id_cv);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                clipViwe.postInvalidate();
            }
        }, 200, 100);

        WatchView watchview= (WatchView) findViewById(R.id.id_watchview);
        watchview.run();


        RlueView rlueView= (RlueView) findViewById(R.id.id_rlueview);


        LineView lineview= (LineView) findViewById(R.id.id_lineviewe);


        LineView2 lineview2= (LineView2) findViewById(R.id.id_lineviewe2);

        Rect1View viewById = (Rect1View) findViewById(R.id.id_rect1view);


        Rect3View rect2View = (Rect3View) findViewById(R.id.id_rect3view);
    }

    private void canvasMove(ImageView igv) {
        Bitmap  bmp=Bitmap.createBitmap(500,500, Bitmap.Config.ARGB_8888);
    }

    private void canvasXinxin(ImageView igv) {
        Bitmap bmp=Bitmap.createBitmap(500,500, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bmp);
        Paint paint=new Paint();
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.YELLOW);

        Path path=new Path();
        path.moveTo(0,150);
        path.rLineTo(300, 0);
        path.rLineTo(-300,150);
        path.rLineTo(150,-300);
        path.rLineTo(150,300);
        path.close();
        canvas.drawPath(path, paint);
        igv.setImageBitmap(bmp);


    }


    public  void canvasRoundRect(ImageView imageView){
        Bitmap bmp=Bitmap.createBitmap(500,800, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bmp);

        Paint paint=new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        canvas.drawLine(0,0,180,180,paint);
        canvas.drawArc(new RectF(10,10,400,300),30,90,true,paint);

        imageView.setImageBitmap(bmp);


    }


    public  void  canvasPaint(ImageView imageView){
        Bitmap bmpBuffer=Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bmpBuffer);

        Paint paint=new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
//        canvas.drawPoint(20, 20, paint);
//
//
//        paint.setColor(Color.BLUE);8
//        canvas.drawPoints(new float[]{10, 10, 50, 50, 50, 150, 50, 200}, paint);
//        paint.setColor(Color.GREEN);
//        canvas.drawPoints(new float[]{10, 10, 50, 50, 50, 150, 50, 200}, 1, 4, paint);
        int  offSetDay=50;
        for (int  i=0;i<5;i++){
            canvas.drawLine(10,i*offSetDay,300,offSetDay*i,paint);
        }

        imageView.setImageBitmap(bmpBuffer);

    }

    public void canvasImage(ImageView imageView){
        Bitmap bmpBuffer=Bitmap.createBitmap(1000,3000, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bmpBuffer);

        //得到原图
        Bitmap bmp=BitmapFactory.decodeResource(getResources(),R.drawable.icon_ig);
        //绘制原图
        canvas.drawBitmap(bmp,0,0,null);
        //得到宽高
        int ig_width= bmp.getWidth();
        int ig_height= bmp.getHeight();


        //绘制原图形状

























        Rect rect=new Rect(0,0,ig_width,ig_height);
        Rect imageRect=new Rect(0,ig_height,ig_width*2,ig_height*2);
        canvas.drawBitmap(bmp,rect,imageRect,null);
        imageView.setImageBitmap(bmpBuffer);


    }

    /*
     * 创建人：Yangshao
     * 创建时间：2016/3/7 9:21
     * @version 截图
     *
     */
    public void screenShot(View view){

        Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT);
            View  v=getLayoutInflater().inflate(R.layout.activity_main,null);
            v.setDrawingCacheEnabled(true);
            v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            v.layout(0,0,v.getMeasuredWidth(),v.getMeasuredHeight());
            try{
                Bitmap bt=v.getDrawingCache();
                FileOutputStream fos=new FileOutputStream("/sdcard/test.png");
                bt.compress(Bitmap.CompressFormat.PNG,100,fos);
                Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT);
            }catch (Exception e){
                Toast.makeText(this,"保存失败",Toast.LENGTH_SHORT);
            }
    }

}
