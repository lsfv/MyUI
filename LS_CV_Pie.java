package com.linson.android.myui.UILIB;

import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.ArgbEvaluator;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import app.lslibrary.androidHelper.LSCustomViewHelper;
import app.lslibrary.androidHelper.LSLog;

public class LS_CV_Pie extends View
{
    private List<PieItem> mPieItems;
    private Paint mPaint;
    private Paint mPainttext;
    public LS_CV_Pie(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        mPaint=new Paint();
        mPainttext=new Paint();
    }

    private void initPaint()
    {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    private void initmPainttext()
    {
        mPainttext.reset();
        mPainttext.setAntiAlias(true);
        mPainttext.setColor(Color.BLACK);
        mPainttext.setTextSize(26);
    }

    public void setupPie(List<PieItem> items)
    {
        mPieItems = items;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        LSCustomViewHelper.SuggestMeasure suggestMeasure=LSCustomViewHelper.getCommonMeasure(widthMeasureSpec, heightMeasureSpec, 800, 500, LSCustomViewHelper.Enum_MeasureType.fixDefault);
        setMeasuredDimension(suggestMeasure.width, suggestMeasure.height);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        initPaint();//标准重设画笔。虽然可以放到构造函数，因为没有变化过。但是还是放这里。
        initmPainttext();

        if(mPieItems==null)
        {
            mPieItems=new ArrayList<>();
            PieItem pieItem=new PieItem(360,"NULL");
            mPieItems.add(pieItem);
        }

        //右侧留300px来写说明 。左侧画图
        //起始点为180度。得到角度。计算颜色。开始画圆，计算新开始点。得到角度。计算颜色开始画圆。重复。

        int startAngle=180;
        RectF rectF=new RectF(0, 0, getWidth()-300, getHeight());
        for(int i=0;i<mPieItems.size();i++)
        {
            if(mPieItems.get(i).color==0x000000)
            {
                int color=evaluate((float) i/mPieItems.size(), 0xffff0000, 0xff11aaaa);
                mPieItems.get(i).color=color;
            }
            mPaint.setColor(mPieItems.get(i).color);
            canvas.drawArc(rectF, startAngle, mPieItems.get(i).angle, true, mPaint);
            startAngle+=mPieItems.get(i).angle;
        }

        //start:width-300+10 ;0.   每次高度+60px
        int startTextX=getWidth()-300+10;
        startTextX=startTextX>0?startTextX:0;
        int startTextY=10;

        for(int i=0;i<mPieItems.size();i++)
        {
            mPaint.setColor(mPieItems.get(i).color);
            canvas.drawRect(startTextX, startTextY, startTextX+50, startTextY+30, mPaint);
            canvas.drawText(String.format("%s:  %.2f%%",mPieItems.get(i).itemName, mPieItems.get(i).angle/360f*100),startTextX+60 ,startTextY+28 , mPainttext);
            startTextY+=60;
        }
    }

    //从ArgbEvaluator 这个类中，直接copy过来。
    public int evaluate(float fraction, int startValue, int endValue) {
        int startInt = (Integer)startValue;
        float startA = (float)(startInt >> 24 & 255) / 255.0F;
        float startR = (float)(startInt >> 16 & 255) / 255.0F;
        float startG = (float)(startInt >> 8 & 255) / 255.0F;
        float startB = (float)(startInt & 255) / 255.0F;
        int endInt = (Integer)endValue;
        float endA = (float)(endInt >> 24 & 255) / 255.0F;
        float endR = (float)(endInt >> 16 & 255) / 255.0F;
        float endG = (float)(endInt >> 8 & 255) / 255.0F;
        float endB = (float)(endInt & 255) / 255.0F;
        startR = (float)Math.pow((double)startR, 2.2D);
        startG = (float)Math.pow((double)startG, 2.2D);
        startB = (float)Math.pow((double)startB, 2.2D);
        endR = (float)Math.pow((double)endR, 2.2D);
        endG = (float)Math.pow((double)endG, 2.2D);
        endB = (float)Math.pow((double)endB, 2.2D);
        float a = startA + fraction * (endA - startA);
        float r = startR + fraction * (endR - startR);
        float g = startG + fraction * (endG - startG);
        float b = startB + fraction * (endB - startB);
        a *= 255.0F;
        r = (float)Math.pow((double)r, 0.45454545454545453D) * 255.0F;
        g = (float)Math.pow((double)g, 0.45454545454545453D) * 255.0F;
        b = (float)Math.pow((double)b, 0.45454545454545453D) * 255.0F;
        return Math.round(a) << 24 | Math.round(r) << 16 | Math.round(g) << 8 | Math.round(b);
    }

    //item
    public static class PieItem
    {
        int angle;
        String itemName;
        int color;
        public PieItem(int _angle,String _name,int _color)
        {
            angle=_angle;
            itemName=_name;
            color=_color;
        }

        public PieItem(int _angle,String _name)
        {
            angle=_angle;
            itemName=_name;
            color=0x00000000;
        }
    }
}