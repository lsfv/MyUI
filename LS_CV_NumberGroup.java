package com.linson.android.myui.UILIB;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import com.linson.android.myui.R;
import java.util.ArrayList;
import java.util.List;
import app.lslibrary.dataHelper.LSNumber;


public class LS_CV_NumberGroup extends ConstraintLayout
{
    private int mCurrentNumber=0;
    private List<Animator> mAnimators=new ArrayList<>();
    public LS_CV_NumberGroup(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.cui_number, this, true);
    }


    //依次从右到左取出数字，生成动画。并插入到动作set中。最后播放set。
    private void startAnimotion(int newnumber)
    {
        AnimatorSet animatorSet=new AnimatorSet();
        List<Integer> currentlist=LSNumber.getEachNumber(mCurrentNumber);
        List<Integer> newList=LSNumber.getEachNumber(newnumber);

        for(int i=3;i>=1;i--)
        {
            if(i>currentlist.size() && i>newList.size())//原来没有。现在没有
            {
            }
            else if(i>currentlist.size() && i<=newList.size())//原来没有。现在有
            {
                Animator animator=getNumberAnimator(i, newList.get(i-1));
                if(animator!=null)
                {
                    mAnimators.add(animator);
                }

            }
            else if(i<=currentlist.size() && i>newList.size())//原来有，现在没有
            {
                Animator animator=getNumberAnimator(i, null);
                if(animator!=null)
                {
                    mAnimators.add(animator);
                }
            }
            else if(i<=currentlist.size() && i<=newList.size())//原来有。现在有.
            {
                if(currentlist.get(i-1)!=newList.get(i-1))//并且数字不相同
                {
                    Animator animator = getNumberAnimator(i, newList.get(i - 1));
                    if (animator != null)
                    {
                        mAnimators.add(animator);
                    }
                }
            }
        }
        animatorSet.playSequentially(mAnimators);
        animatorSet.start();
    }

    private Animator getNumberAnimator(int level,Integer newOnenumber)
    {
        ImageView imageView=null;
        if(level==3)
        {
            imageView=findViewById(R.id.iv_third);
        }
        else if(level==2)
        {
            imageView=findViewById(R.id.iv_second);
        }
        else if(level==1)
        {
            imageView=findViewById(R.id.iv_first);
        }

        //替换还是直接取消
        Animator animator=null;
        if(newOnenumber==null)
        {
            imageView.setImageDrawable(null);
        }
        else
        {
            int sourceID=getResources().getIdentifier("n"+newOnenumber, "drawable", "com.linson.android.myui");
            imageView.setImageResource(sourceID);
            imageView.setTranslationY(imageView.getHeight());
            animator=ObjectAnimator.ofFloat(imageView, "TranslationY", imageView.getHeight(),0);
            animator.setDuration(1000);
        }

        return animator;
    }


    //region getter and setter
    public int getCurrentNumber()
    {
        return mCurrentNumber;
    }

    public void setCurrentNumber(int newNumber)
    {
        if(newNumber>0 && newNumber<=999 && newNumber!=mCurrentNumber)
        {
            mAnimators.clear();
            startAnimotion(newNumber);
            mCurrentNumber = newNumber;
        }
    }
    //endregion
}