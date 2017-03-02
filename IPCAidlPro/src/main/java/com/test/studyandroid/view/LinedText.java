package com.test.studyandroid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * �Զ�����º��ߵ��ı���ͼ
 */
public  class LinedText extends TextView {
  private Rect mRect;
  private Paint mPaint;

  public LinedText(Context context, AttributeSet attrs) {
      super(context, attrs);
      mRect = new Rect();
      mPaint = new Paint();
      mPaint.setStyle(Paint.Style.STROKE);
      mPaint.setColor(0x800000FF);
  }
  
  @Override
  protected void onDraw(Canvas canvas) {
      int count = getLineCount();
      Rect r = mRect;
      Paint paint = mPaint;

      for (int i = 0; i < count; i++) {
          int baseline = getLineBounds(i, r);
          if(i%2==1){
            canvas.drawLine(r.left, baseline + 1, r.right, baseline + 1, paint);
          }
      }

      super.onDraw(canvas);
  }
}