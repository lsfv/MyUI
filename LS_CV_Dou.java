package com.linson.android.myui.UILIB;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.linson.android.myui.R;

//立体旋转+图层本身的平面旋转。

public class LS_CV_Dou extends LS_CV_BASEView
{
    private Bitmap mBitmap;
    private ObjectAnimator mObjectAnimator;
    private int mDegree;
    private int mMyRotation;
    public LS_CV_Dou(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        mDefaultSizeH=300;
        mDefaultSizeH=300;
        mBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.lemon);

        PropertyValuesHolder pvh_degree=PropertyValuesHolder.ofInt("MyRotation",-30,30);
        PropertyValuesHolder pvh_mRectw=PropertyValuesHolder.ofInt("Degree",0,30,30,30,0,-30,-30,-30,0);
        mObjectAnimator=ObjectAnimator.ofPropertyValuesHolder(this, pvh_degree,pvh_mRectw);
        mObjectAnimator.setDuration(5000);
    }

    public void startAnimotion()
    {
        mObjectAnimator.start();
    }


    //
    @Override
    protected void onDraw(Canvas canvas)
    {
        mBitmap=Bitmap.createScaledBitmap(mBitmap, getWidth(), getHeight(), false);
        Bitmap bitTop=Bitmap.createBitmap(mBitmap,0,0,getWidth(),getHeight()/2);
        Bitmap bitBottom=Bitmap.createBitmap(mBitmap,0,getHeight()/2,getWidth(),getHeight()/2);




        canvas.save();
        canvas.rotate(mMyRotation,getWidth()/2,getHeight()/2);
        canvas.drawBitmap(bitBottom, 0, getHeight()/2, mPaint);
        canvas.rotate(-mMyRotation,getWidth()/2,getHeight()/2);
        canvas.restore();

        canvas.save();
        canvas.rotate(mMyRotation,getWidth()/2,getHeight()/2);
        Matrix matrix=getRotation(mMyRotation, 0, mDegree, 0, new PointF(getWidth()/2,getHeight()/2));
        canvas.concat(matrix);
        canvas.drawBitmap(bitTop, 0, 0, mPaint);
        canvas.rotate(-mMyRotation,getWidth()/2,getHeight()/2);
        canvas.restore();


        canvas.restore();
    }



    public int getDegree()
    {
        return mDegree;
    }

    public void setDegree(int degree)
    {
        mDegree = degree;
        invalidate();
    }

    public int getMyRotation()
    {
        return mMyRotation;
    }

    public void setMyRotation(int W)
    {
        mMyRotation = W;
    }


    public static Matrix getRotation(int rotation, int degreex, int degreey, int degreeZ, PointF CanvasTransferBy)
    {
        Matrix matrix=new Matrix();
        Camera camera=new Camera();
        camera.rotateX(degreex);
        camera.rotateY(degreey);
        camera.rotateZ(degreeZ);
        camera.getMatrix(matrix);
        matrix.preTranslate(-CanvasTransferBy.x, -CanvasTransferBy.y);
        matrix.postTranslate(CanvasTransferBy.x, CanvasTransferBy.y);
        return matrix;
    }


}