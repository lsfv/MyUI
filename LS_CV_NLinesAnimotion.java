package com.linson.android.myui.UILIB;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import app.lslibrary.androidHelper.LSCustomViewHelper;

public class LS_CV_NLinesAnimotion extends LS_CV_BASEView
{
    private Paint mPaint;
    private Paint mPaint_lines;
    private Paint mPaint_inline;
    private float mProcess;
    ObjectAnimator valueAnimator;

    private int mLines=3;
    private List<Integer> mPercent=new ArrayList<>();
    public LS_CV_NLinesAnimotion(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        mPaint=new Paint();
        mPaint_lines=new Paint();
        mPaint_inline=new Paint();

        valueAnimator=ObjectAnimator.ofFloat(this,"Process",0,1f);
        valueAnimator.setDuration(3000);

        this.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                valueAnimator.start();
            }
        });
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        valueAnimator.start();
    }

    public void setupNGeometry(int linesCount, List<Integer> percent)
    {
        if(linesCount>=3 && percent.size()==linesCount)
        {
            mLines=linesCount;
            mPercent=percent;
            //invalidate();
        }
    }

    private void setupMpercent()
    {
        mPercent.add(40);
        mPercent.add(10);
        mPercent.add(20);
    }

    private void initPaint()
    {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setShader(new RadialGradient(getWidth()/2, getHeight()/2, getWidth()/2, 0xFF0000ff, 0xFF3399CC, Shader.TileMode.CLAMP));
    }

    private void initPaint_lines()
    {
        mPaint_lines.reset();
        mPaint_lines.setAntiAlias(true);
        mPaint_lines.setStyle(Paint.Style.STROKE);
        mPaint_lines.setStrokeWidth(5);
        mPaint_lines.setColor(Color.GREEN);
    }

    private void initPaint_inline()
    {
        mPaint_inline.reset();
        mPaint_inline.setAntiAlias(true);
        mPaint_inline.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint_inline.setColor(0x77FF0000);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        LSCustomViewHelper.SuggestMeasure suggestMeasure=LSCustomViewHelper.getCommonMeasure(widthMeasureSpec, heightMeasureSpec, 500, 500, LSCustomViewHelper.Enum_MeasureType.fixDefault);
        setMeasuredDimension(suggestMeasure.width, suggestMeasure.height);
    }



    @Override
    protected void onDraw(Canvas canvas)
    {
        initPaint_lines();//标准重设画笔流程
        initPaint_inline();
        initPaint();

        setupMpercent();

        PointF centerPoint=new PointF(getWidth()/2,getHeight()/2);


        List<PointF> outerLines=getNLinesPoints(centerPoint,getWidth()/2 , mLines);
        Path path_outer=getPath4Points(outerLines);

        List<PointF> interLines3=getNLinesPoints(centerPoint,(int)(getWidth()/2*0.75) , mLines);
        Path path_inter3=getPath4Points(interLines3);

        List<PointF> interLines2=getNLinesPoints(centerPoint,(int)(getWidth()/2*0.5) , mLines);
        Path path_inter2=getPath4Points(interLines2);

        List<PointF> interLines1=getNLinesPoints(centerPoint,(int)(getWidth()/2*0.25) , mLines);
        Path path_inter1=getPath4Points(interLines1);


        //画背景。画4个n边形。画中心射线。根据能力值求出5个能力点。5个点连接5个边。
        canvas.drawPath(path_outer, mPaint);//背景

        canvas.drawPath(path_outer, mPaint_lines);//4个n边形。
        canvas.drawPath(path_inter3, mPaint_lines);
        canvas.drawPath(path_inter2, mPaint_lines);
        canvas.drawPath(path_inter1, mPaint_lines);


        //中心射线
        for(int i=0;i<mLines;i++)
        {
            canvas.drawLine(centerPoint.x, centerPoint.y, outerLines.get(i).x, outerLines.get(i).y, mPaint_lines);
        }

        //画能力线
        List<PointF> points= new ArrayList<>();

        for(int i=0;i<mLines;i++)
        {
            points.add(getPoint4Percent(centerPoint, outerLines.get(i),(int)(mPercent.get(i)*mProcess)));
        }

        Path path1=getPath4Points(points);
        canvas.drawPath(path1, mPaint_inline);
    }



    //先求直线函数 y=ax+b。再根据能力值求x坐标值。x代入函数。求y。小学数学
    private PointF getPoint4Percent(PointF p1,PointF p2,int percent)
    {
        if(p2.x-p1.x!=0)
        {
            float a = (p2.y - p1.y) / (p2.x - p1.x);
            float b = p1.y - a * p1.x;
            float x = p1.x + (p2.x - p1.x) * percent / 100f;
            float y = a * x + b;
            return new PointF(x, y);
        }
        else
        {
            float x = p1.x ;
            float y=p1.y + (p2.y - p1.y) * percent / 100f;
            return new PointF(p1.x, y);
        }
    }

    private Path getPath4Points(List<PointF> res)
    {
        Path path=new Path();
        if(res.size()>=3)
        {
            path.moveTo(res.get(0).x, res.get(0).y);
            for (int i = 1; i < res.size(); i++)
            {
                path.lineTo(res.get(i).x, res.get(i).y);
            }
        }
        path.close();
        return path;
    }


    // cos ，初中数学
    private List<PointF> getNLinesPoints(PointF centerPoint, int radies, int n)
    {
        List<PointF> res=new ArrayList<>();
        if(n>=3)
        {
            double arcDegree = 2 * Math.PI / n;

            res.add(new PointF(centerPoint.x, centerPoint.y - radies));
            for (int i = 0; i < n - 1; i++)
            {
                res.add(new PointF((float) (Math.sin(arcDegree * (i + 1)) * radies + centerPoint.x), (float) (-Math.cos(arcDegree * (i + 1)) * radies + centerPoint.y)));
            }
        }
        return res;
    }

    public float getProcess()
    {
        return mProcess;
    }

    public void setProcess(float process)
    {
        mProcess = process;
        invalidate();
    }
}
