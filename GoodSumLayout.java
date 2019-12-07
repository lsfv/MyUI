package com.linson.android.myui.UILIB;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.linson.android.myui.R;


//考虑到2大原则：开闭，分治中的分治原则，最好把问题，拆开处理。最后组合的原则。因此采用group来完成这个功能。
//view动画虽然简单，但是可控制属性少。而且还需要清楚效果间的组合。所以干脆多用属性动画或值动画。除非真的很简单。
public class GoodSumLayout extends ConstraintLayout
{
    public GoodSumLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        LayoutInflater.from(context).inflate( R.layout.cui_good, this,true);
        mMyControls=new MyControls();//cut it into 'onCreate'
    }

    public void onClick(int num)
    {
        //数字动画。
        //大拇指动画。
        //拇指动画。
        numberAnimotion();
        handAnimotion();
        handPlus();
    }

    private void handPlus()
    {
        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(mMyControls.mImageView2, "Alpha",0f,1f);
        objectAnimator.setDuration(500);
        objectAnimator.setInterpolator(new AccelerateInterpolator());
        objectAnimator.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
                mMyControls.mImageView2.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                mMyControls.mImageView2.setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation)
            {
                mMyControls.mImageView2.setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animator animation)
            {
                mMyControls.mImageView2.setVisibility(VISIBLE);
            }
        });
    }

    private void numberAnimotion()
    {

    }

    private void handAnimotion()
    {
        PropertyValuesHolder holder_scalex=PropertyValuesHolder.ofFloat("ScaleX", 1f,1,15f);
        PropertyValuesHolder holder_scaleY=PropertyValuesHolder.ofFloat("ScaleY", 1f,1,15f);
        ObjectAnimator objectAnimator=ObjectAnimator.ofPropertyValuesHolder(mMyControls.mImageView,holder_scalex,holder_scaleY);
        objectAnimator.setDuration(500);
        objectAnimator.setInterpolator(new BounceInterpolator());
        objectAnimator.start();
    }


    //region The class of FindControls
    private MyControls mMyControls=null;
    public class MyControls
    {
        private ImageView mImageView2;
        private ImageView mImageView;

        public MyControls()
        {
            mImageView2 = (ImageView) findViewById(R.id.imageView2);
            mImageView = (ImageView) findViewById(R.id.imageView);
        }
    }
    //endregion
}