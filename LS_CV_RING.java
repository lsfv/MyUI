package com.linson.android.myui.UILIB;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class LS_CV_RING extends LS_CV_BASEView
{
    private int mDegree;
    private int mwidth=10;
    private Paint mPaint2;

    public LS_CV_RING(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        mPaint2=new Paint();
        mPaint2.setColor(Color.RED);
        mPaint2.setTextSize(25);
    }

    private void initPaint()
    {
        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mwidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        initPaint();

        RectF rectF=new RectF(0, 0, getWidth()-mwidth-5, getHeight()-mwidth-5);
        canvas.drawArc(rectF,180, mDegree,false,mPaint);

        canvas.drawText(String.format("%.2f%%",mDegree/360f), getWidth()/2, getHeight()/2, mPaint2);
    }


    public void setDegree(int degree)
    {
        mDegree = degree;
        invalidate();
    }

    public int getDegree()
    {
        return mDegree;
    }
}