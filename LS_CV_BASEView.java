package com.linson.android.myui.UILIB;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import app.lslibrary.androidHelper.LSCustomViewHelper;

public abstract class LS_CV_BASEView extends View
{
    protected int mDefaultSizeW=300;
    protected int mDefaultSizeH=300;
    protected Paint mPaint;


    public LS_CV_BASEView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        mPaint=new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LSCustomViewHelper.SuggestMeasure suggestMeasure= LSCustomViewHelper.getCommonMeasure(widthMeasureSpec, heightMeasureSpec, mDefaultSizeW, mDefaultSizeH, LSCustomViewHelper.Enum_MeasureType.rate);
        setMeasuredDimension(suggestMeasure.width, suggestMeasure.height);
    }


}