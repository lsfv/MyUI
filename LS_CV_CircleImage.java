package com.linson.android.myui.UILIB;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.linson.android.myui.R;

import app.lslibrary.androidHelper.LSCustomViewHelper;

//做一个最简单的圆头像，把图像的审核工作留给用户。
//先把宽高较小的。作为直径。把图片拉到直径大小正方形。并作为画笔的shader.
//把图层的起点移动到圆的起点(shader不可移动，所以移动图层)。画圆。结束。
public class LS_CV_CircleImage extends View
{
    private static int mDefaultSize=100;

    private int mRadius=0;
    private int mImgResourceID=0;
    private Paint mPaint;

    public LS_CV_CircleImage(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        TypedArray array=context.obtainStyledAttributes(attrs, app.lslibrary.R.styleable.LSCircleImage);
        mRadius=(int)array.getDimension(app.lslibrary.R.styleable.LSCircleImage_CircleImage_radius, Integer.MAX_VALUE);
        mImgResourceID=array.getResourceId(app.lslibrary.R.styleable.LSCircleImage_CircleImage_img,0 );
        mPaint=new Paint();
        array.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LSCustomViewHelper.SuggestMeasure suggestMeasure= LSCustomViewHelper.getCommonMeasure(widthMeasureSpec, heightMeasureSpec, mDefaultSize, mDefaultSize, LSCustomViewHelper.Enum_MeasureType.rate);
        setMeasuredDimension(suggestMeasure.width, suggestMeasure.height);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas)
    {
        if(mImgResourceID!=0)
        {
            initPaint(mPaint);//会多次执行此函数，所以最好开始重设。

            int Width=getWidth();
            int Height=getHeight();

            int tempRadius=Math.min(Width/2, Height/2);
            tempRadius=Math.min(tempRadius, mRadius);
            mRadius=tempRadius;

            int centerX=Width/2;
            int centerY=Height/2;

            @SuppressLint("DrawAllocation") Bitmap bitmap_origetation=BitmapFactory.decodeResource(getResources(), mImgResourceID);
            @SuppressLint("DrawAllocation") Bitmap bitmap_scale=Bitmap.createScaledBitmap(bitmap_origetation, mRadius*2, mRadius*2, false);
            mPaint.setShader(new BitmapShader(bitmap_scale, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            canvas.save();
            canvas.translate(centerX-mRadius, centerY-mRadius);
            canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
            canvas.restore();
        }
        else
        {
            super.onDraw(canvas);
        }
    }

    private void initPaint(Paint paint)
    {
        paint.reset();
    }


    public void setImage(int id)
    {
        if(id!=0 && id!=mImgResourceID)
        {
            mImgResourceID=id;
            invalidate();
        }
    }

    public int getImage()
    {
        return mImgResourceID;
    }
}