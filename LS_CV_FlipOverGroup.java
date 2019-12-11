package com.linson.android.myui.UILIB;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.linson.android.myui.R;

//动画个人目前觉得最好派生自group来做。而不是view。因为我们一般用的都是属性动画。但是立体旋转，
//目前不知道如何用view的属性来做。因为需要直面旋转，而不是斜眼看它旋转。
public class LS_CV_FlipOverGroup extends ConstraintLayout
{
    private int mImgResourceID=0;



    public LS_CV_FlipOverGroup(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.cgroup_flipover, this, true);
        mMyControls=new MyControls();//cut it into 'onCreate'

        Matrix matrix=mMyControls.mImageView4.getImageMatrix();
        //这里该如何处理这个矩阵呢？
        matrix.postConcat(LS_CV_FlipOver.getRotation(50, 0, 0, new PointF(50,150)));
        mMyControls.mImageView4.setImageMatrix(matrix);
        mMyControls.mImageView4.invalidate();
    }



    //region The class of FindControls
    private MyControls mMyControls=null;
    public class MyControls
    {
        private ImageView mImageView3;
        private ImageView mImageView4;

        public MyControls()
        {
            mImageView3 = (ImageView) findViewById(R.id.imageView3);
            mImageView4 = (ImageView) findViewById(R.id.imageView4);
        }
    }
    //endregion
}