package com.linson.android.myui.UILIB;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.Image;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.linson.android.myui.R;


//
public class LS_CV_MUSIC extends LS_CV_BASEView
{
    public LS_CV_MUSIC(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }
//    private Bitmap mBitmap;
//    private float mSkew=0;
//    private float mApla=1;
//    private Paint mPaint2;
//    protected int left, top, right, bottom;
//
//    public LS_CV_MUSIC(Context context, @Nullable AttributeSet attrs)
//    {
//        super(context, attrs);
//        mBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.music);
//        PropertyValuesHolder propertyValuesHolder=PropertyValuesHolder.ofFloat("Skew",0,-1,0,1,0);
//        PropertyValuesHolder propertyValuesHolder2=PropertyValuesHolder.ofFloat("Apla",0.7f,0);
//        final ObjectAnimator objectAnimator=ObjectAnimator.ofPropertyValuesHolder(this,propertyValuesHolder,propertyValuesHolder2);
//        objectAnimator.setDuration(3000);
//        objectAnimator.setInterpolator(new LinearInterpolator());
//        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
//        mPaint2=new Paint();
//
//        this.setOnClickListener(new OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                objectAnimator.start();
//            }
//        });
//    }
//
//
//
//
//    @Override
//    protected void onDraw(Canvas canvas)
//    {
//        left=getLeft();
//        right=getRight();
//        top=getTop();
//        bottom=getBottom();
//        int padding=(int)(getWidth()*0.2);
//        mBitmap=Bitmap.createScaledBitmap(mBitmap, getWidth()-padding*2, getHeight()-padding*2, false);
//
//
//        float[] pointsSrc = {left, top, right, top, left, bottom, right, bottom};
//        float[] pointsDst = {left - mSkew*padding, top + mSkew*padding, right - mSkew*padding, top-mSkew*padding, left, bottom, right, bottom};
//
//        Matrix matrix=new Matrix();
//        matrix.setPolyToPoly(pointsSrc, 0, pointsDst, 0, 4);
//
//        canvas.save();
//        canvas.concat(matrix);
//        canvas.drawBitmap(mBitmap, padding, padding, mPaint2);
//        canvas.restore();
//    }
//
//    public float getSkew()
//    {
//        return mSkew;
//    }
//
//    public void setSkew(float sk)
//    {
//        mSkew=sk;
//        invalidate();
//    }
//
//    public void setApla(float apla)
//    {
//        mApla = apla;
//    }
//
//    public float getApla()
//    {
//        return mApla;
//    }
}