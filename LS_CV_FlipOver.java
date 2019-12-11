package com.linson.android.myui.UILIB;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.linson.android.myui.R;

import app.lslibrary.androidHelper.LSCustomViewHelper;


//翻页效果：图片本身不能进行立体方向的旋转，canvas也没有直接方法。但是canva有concat这个无敌全能方法。
//1.图片先转为控件大小。2.分为上下2部分。3画上部分。4.下部分画出立体坐标系的x旋转角度。
//5用摄像头取得旋转的矩阵。让canva 去concat。再画下半部分。
//具体含义只能大概给出：摄像头旋转，但是先要把画布调整好。让画布处于正确的旋转轴位置。一般是图片的中间，或中上，或中下。
//不想传递画布，因为想解耦彻底。所以matrix.preTranslate模拟canvas的提前移动。
//6.再用属性动画。可以把动画放到里面。隐藏实现。
//7.对于这种立体的旋转，比较方便还是到view里面用canvas和camera来实现。因为需要直面旋转效果。而不是斜眼看他旋转。
public class LS_CV_FlipOver extends View
{
    private static int mDefaultSize=300;

    private int mImgResourceID=0;
    private Paint mPaint;
    private int mDegree=0;

    private Bitmap mBitmap;
    private Bitmap mBitmap_scale;
    private Bitmap mBitmap_top;
    private Bitmap mBitmap_bottom;

    private ObjectAnimator mObjectAnimator;

    public LS_CV_FlipOver(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        mPaint=new Paint();
        mImgResourceID= R.drawable.lemon;
        mBitmap=BitmapFactory.decodeResource(getResources(), mImgResourceID);
        mObjectAnimator=ObjectAnimator.ofInt(this, "Degree",0,85);
        mObjectAnimator.setDuration(3000);
    }

    public void startAnimotion()
    {
        mObjectAnimator.start();;
    }

    private void setDegree(int degree)
    {
        mDegree=degree;
        invalidate();
    }
    private int getDegree()
    {
        return mDegree;
    }

//    @Override
//    protected void onAttachedToWindow()
//    {
//        super.onAttachedToWindow();
//        mObjectAnimator.start();
//    }
//
//    @Override
//    protected void onDetachedFromWindow()
//    {
//        super.onDetachedFromWindow();
//        mObjectAnimator.cancel();
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LSCustomViewHelper.SuggestMeasure suggestMeasure= LSCustomViewHelper.getCommonMeasure(widthMeasureSpec, heightMeasureSpec, mDefaultSize, mDefaultSize, LSCustomViewHelper.Enum_MeasureType.rate);
        setMeasuredDimension(suggestMeasure.width, suggestMeasure.height);

    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        initPaint();
        if(mBitmap_scale==null)
        {
            mBitmap_scale = Bitmap.createScaledBitmap(mBitmap, getWidth(), getHeight(), false);
            mBitmap_top = Bitmap.createBitmap(mBitmap_scale, 0, 0, getWidth(), getHeight() / 2);
            mBitmap_bottom = Bitmap.createBitmap(mBitmap_scale, 0, getHeight() / 2, getWidth(), getHeight()/2);
        }

        canvas.drawBitmap(mBitmap_top, 0, 0, mPaint);
        canvas.save();
        canvas.concat(getRotation(mDegree, 0, 0 ,new PointF(getWidth()/2, getHeight()/2)));
        canvas.drawBitmap(mBitmap_bottom, 0, getHeight()/2, mPaint);
        canvas.restore();
    }


    private void initPaint()
    {
        mPaint.reset();
        mPaint.setAntiAlias(true);
    }

    public static Matrix getRotation(int degreex, int degreey, int degreeZ, PointF CanvasTransferBy)
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