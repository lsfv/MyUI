package com.linson.android.myui.UILIB;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.util.List;


//想到动画还是用图片比较好，所以用决定用图片代替文字来做动画。设定最大数为99999.最小为0.
//如果当前数字为0，直接从左到右跳进所有数字。否则从从左到右,不相同的数字原数字跳出。新跳进,
//因为很明显。这里的动画是顺序关系，可以用set来组合小动画。
//刚开始做。发现这里不适合用view。虽然是数字，其实它的变化更多，多个小动画。那么必须定位裁剪区域做区域动画。转为group吧。
//放5个图片。设置5个动画，加入到动画组。如果某个数字是不需要改动，那么单个动画时间为0.否则动画。
//恩。应该是分治法，整个动画就是替换数字。所以可以做一个替换数字的view。之后再在group中放置5个view.而不是5个imageview.
//因为播放的时间不是固定，所以播放一个，结束回调另一个开始。
public class LS_CV_NUMBER extends LS_CV_BASEView
{
    private int mCurrentNumber=0;
    private int mPercent;
    private AnimatorSet mAnimatorSet;
    private List<Animator> mObjectAnimators;

    public LS_CV_NUMBER(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        if(mCurrentNumber==0)
        {
            //直接从左到右跳进所有数字
        }
        else
        {
            //从左到右,不相同的数字原数字跳出。新跳进,
        }
    }

    public void startA()
    {

        mAnimatorSet.playSequentially(mObjectAnimators);
    }

    //region setter getter
    public int getCurrentNumber()
    {
        return mCurrentNumber;
    }

    public void setCurrentNumber(int currentNumber)
    {
        if(currentNumber>=0 && currentNumber<=99999 && currentNumber!=mCurrentNumber)
        {
            mCurrentNumber = currentNumber;
            startA();
        }
    }

    public int getPercent()
    {
        return mPercent;
    }

    public void setPercent(int percent)
    {
        mPercent = percent;
    }
    //endregion
}