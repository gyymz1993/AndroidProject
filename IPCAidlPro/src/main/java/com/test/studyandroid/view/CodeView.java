package com.test.studyandroid.view;//package com.test.studyandroid.view;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Rect;
//import android.util.AttributeSet;
//import android.util.TypedValue;
//import android.view.View;
//
//import java.util.Random;
//
//import master.modle.masterfinance.R;
//import master.modle.masterfinance.utils.LogUtils;
//
///*
// * 创建人：Yangshao
// * 创建时间：2016/3/11 11:37
// * @version    验证码View
// *
// */
//public class CodeView extends View {
//    private static final String Tag="CodeView";
//    private int count;//验证码字数个数
//    private int lineCount;//线条个数
//    private int fontSize;//字大小
//    private int color;
//    private String code;//验证码
//    private Random rnd;  //随机数
//    private Paint paint;
//
//    private static final int DEFAULT_COUNT=4;
//    private static final int DEFAULT_LINE_COUNT=50;
//    private static final int DEFAULT_FONT_SIZE=12;//sp
//    private static final int DEFAULT_COLOR= Color.BLACK;
//
//    public CodeView(Context context) {
//        this(context,null);
//    }
//
//    public CodeView(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public CodeView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        LogUtils.e("CodeView()");
//        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.codeView);
//        count=array.getInt(R.styleable.codeView_count,DEFAULT_COUNT);
//        lineCount=array.getInt(R.styleable.codeView_line_count, DEFAULT_LINE_COUNT);
//        fontSize=array.getDimensionPixelSize(R.styleable.codeView_font_size,
//                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
//                        DEFAULT_FONT_SIZE, getResources().getDisplayMetrics()));
//        color=array.getColor(R.styleable.codeView_code_color, DEFAULT_COLOR);
//        array.recycle();
//        initData();
//        initPaint();
//    }
//
//    private void  initData(){
//        rnd=new Random();
//        paint=new Paint();
//        code=getCode();
//
//    }
//
//    private void initPaint() {
//        paint.reset();
//        paint.setAntiAlias(true);
//        paint.setColor(color);
//        paint.setTextSize(fontSize);
//    }
//
//    private String getCode() {
//        String str="";
//        for(int i=0;i<count;i++){
//            str+=rnd.nextInt(10);
//        }
//        return str;
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        LogUtils.e("onDraw()");
//        int width=getMeasuredWidth();
//        int height=getMeasuredHeight();
//
//        Rect rect=new Rect(0,0,width,height);
//        //绘制外围矩形图
//        paint.setStrokeWidth(1);
//        paint.setStyle(Paint.Style.FILL_AND_STROKE);
//        Rect rect1=new Rect(rect);
//        rect1.inset(2, 2);  //缩小
//        canvas.drawRect(rect1, paint);
//        paint.setStyle(Paint.Style.FILL);
//        //绘制防干扰线
//        paint.setColor(Color.GRAY);
//        for (int i=0;i<lineCount;i++){
//            int x1=rnd.nextInt(width);
//            int y1=rnd.nextInt(height);
//            int x2=rnd.nextInt(width);
//            int y2=rnd.nextInt(height);
//            canvas.drawLine(x1,y1,x2,y2,paint);
//        }
//        paint.setColor(color);
//        //绘制文字
//        Rect textRect=getTextRect();
//        Paint.FontMetrics fontMetrics=paint.getFontMetrics();
//        int x=(width-textRect.width())/2;
//        int y=(int)(height/2+(fontMetrics.descent-fontMetrics.ascent)/2-fontMetrics.descent);
//        canvas.drawText(code,x,y,paint);
//    }
//
//    private Rect getTextRect() {
//        //根据paint设置 的绘制参数计算 文字所有点宽度
//        Rect rect=new Rect();
//        //文字所占区域大小 保存在rect中
//        paint.getTextBounds(code,0,code.length(),rect);
//        return rect;
//    }
//
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        LogUtils.e("onMeasure()");
//        Rect textRect=getTextRect();
//        int width=this.measureWidth(widthMeasureSpec, textRect);
//        int height=this.measureHeight(heightMeasureSpec,textRect);
//        setMeasuredDimension(width,height);
//    }
//
//    /**
//     *  计算 组建的高度
//     * @param heightMeasureSpec
//     * @param textRect
//     * @return
//     */
//    private int measureHeight(int heightMeasureSpec, Rect textRect) {
//        int mode= MeasureSpec.getMode(heightMeasureSpec);
//        int size= MeasureSpec.getSize(heightMeasureSpec);
//        int height=0;
//        if (mode== MeasureSpec.EXACTLY){
//            height=size;
//        }else if (mode== MeasureSpec.AT_MOST){
//            height=getPaddingTop()+textRect.height()+getPaddingBottom();
//        }
//        return height;
//    }
//
//    /**
//     *   计算组件的宽度
//     * @param widthMeasureSpec
//     * @param textRect
//     * @return
//     */
//    private int measureWidth(int widthMeasureSpec, Rect textRect) {
//        int mode= MeasureSpec.getMode(widthMeasureSpec);
//        int size= MeasureSpec.getSize(widthMeasureSpec);
//        int width=0;
//        if (mode== MeasureSpec.EXACTLY){
//            width=size;
//        }else if(mode== MeasureSpec.AT_MOST){
//            width=getPaddingLeft()+textRect.width()+getPaddingRight();
//        }
//        return width;
//    }
//
//
//    public int  getCount(){
//        return count;
//    }
//
//    public void setCount(int count) {
//        this.count = count;
//        code = getCode();
//        requestLayout();  //重新绘制布局大小
//    }
//
//    public  void  setLintCount(int  lineCount){
//        this.lineCount=lineCount;
//        invalidate();//重绘
//    }
//
//    public int getFontSize(){
//        return fontSize;
//    }
//
//    public void setFontSize(){
//        this.fontSize=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, fontSize,
//                getResources().getDisplayMetrics());
//        initPaint();
//        requestLayout();//重新调整布局大小
//    }
//
//    public int getColor(){
//       return count;
//    }
//
//    public  void setColor(int color){
//        this.count=color;
//        initPaint();
//        invalidate();//重绘
//    }
//
//    /*
//     * 创建人：Yangshao
//     * 创建时间：2016/3/11 12:21
//     * @version    刷新 验证码
//     *
//     */
//    public void  refresh(){
//        code=getCode();
//        invalidate();
//    }
//
//    public String theCode(){
//        return code;
//    }
//}
//
