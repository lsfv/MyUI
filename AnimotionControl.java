package com.linson.android.myui.UILIB;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;

//value动画。start:onwindow.
public class AnimotionControl extends View
{
    private float mMovex=20;
    private ValueAnimator valueAnimator;
    private Paint mPaint;

    public AnimotionControl(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mPaint=new Paint();
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        valueAnimator=ValueAnimator.ofFloat(0,240,200);
        //valueAnimator.setInterpolator(new OvershootInterpolator());
        //valueAnimator.setEvaluator(new BackEvaluater());
        valueAnimator.setDuration(5000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                float moveX=(Float) animation.getAnimatedValue();
                mMovex=moveX;
                invalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        valueAnimator.cancel();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawCircle(mMovex, getHeight()/2, 20, mPaint);
    }



    public static class FixSpeed implements Interpolator
    {
        @Override
        public float getInterpolation(float input)
        {
            return input;
        }
    }

    public static class BackEvaluater implements TypeEvaluator<Float>
    {
        @Override
        public Float evaluate(float fraction, Float startValue, Float endValue)
        {
            if(fraction<=0.8)
            {
                return startValue+(endValue-startValue)*fraction;
            }
            else
            {
                float realfraction=0.8f-(fraction-0.8f);
                return startValue+(endValue-startValue)*realfraction;
            }
        }
    }
}