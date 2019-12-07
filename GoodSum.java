package com.linson.android.myui.UILIB;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Interpolator;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.linson.android.myui.R;

//分析：1.无自定义参数。
//2.包含动画，手动触发型。那么大致思路：前端接收数字，并建立属性动画
//自定义view，属性变化，触发重绘。属性字段可以为persent。表示动画的进度。
//3.根据进度。安排动画各个元素的进展。此过程应该可以用interpolater来完成。

public class GoodSum extends View
{
    private int mProgress=100;
    private Paint mPaint;
    private Bitmap mBitmap_hand;
    private int mGoodSum=0;
    public GoodSum(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        mBitmap_hand=BitmapFactory.decodeResource(getResources(), R.drawable.hand);
        mPaint=new Paint();
        initPaint();
    }

    private void initPaint()
    {
        mPaint.reset();
        mPaint.setAntiAlias(true);
    }

    public void setProgress(int progress)
    {
        mProgress = progress;

        invalidate();
    }

    public int getProgress()
    {
        return mProgress;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        initPaint();

        LightingColorFilter lightingColorFilter=new LightingColorFilter(0x000000, getColor(mProgress));
        mPaint.setColorFilter(lightingColorFilter);
        canvas.drawBitmap(mBitmap_hand, 0, 0, mPaint);
    }

    private int getColor(int progress)
    {
        int currentColorfilter=255/100*progress;
        int currentColor=Color.rgb(250, 255-currentColorfilter, 255-currentColorfilter);
        return currentColor;
    }
}