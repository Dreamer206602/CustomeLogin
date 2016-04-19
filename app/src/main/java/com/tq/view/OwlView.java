package com.tq.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;

import com.tq.R;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by boobooL on 2016/4/19 0019
 * Created 邮箱 ：boobooMX@163.com
 */

/**
 * 猫头鹰输入密码的时候捂住眼睛
 * 布局文件中宽=175dp，height=107dp,也可以不写默认，只是为了预览
 */
public class OwlView extends View {
     private Context mContext;
    private Bitmap bm_owl;
    private Bitmap bm_owl_arm_left;
    private Bitmap bm_owl_arm_right;
    private int bm_height;
    private int moveHeight;
    private int alpha=255;

    private int move_length=0;
    private Paint handPaintBefore;
    private Paint handPaintAfter;


    public OwlView(Context context) {
        this(context,null);
    }

    public OwlView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public OwlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        init();
    }

    private void init() {
        setBackgroundColor(Color.TRANSPARENT);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                ViewGroup.LayoutParams lp=getLayoutParams();
                if(lp==null)return;
                lp.width=dip2px(175);
                lp.height=dip2px(107);
                setLayoutParams(lp);
                setTranslationY(dip2px(9));
            }
        });

        bm_owl= BitmapFactory.decodeResource(getResources(), R.mipmap.owl_login);
        bm_owl_arm_left=BitmapFactory.decodeResource(getResources(),R.mipmap.owl_login_arm_left);
        bm_owl_arm_right=BitmapFactory.decodeResource(getResources(),R.mipmap.owl_login_arm_right);

        bm_owl=compressBitmap(bm_owl,dip2px(115),dip2px(107),false);
        bm_owl_arm_left=compressBitmap(bm_owl_arm_left,dip2px(40),dip2px(65),true);
        bm_owl_arm_right=compressBitmap(bm_owl_arm_right,dip2px(40),dip2px(65),true);

        bm_height=bm_owl_arm_left.getHeight()/3*2-dip2px(10);

        handPaintBefore=new Paint();
        handPaintBefore.setColor(Color.parseColor("#472d20"));
        handPaintBefore.setAntiAlias(true);

        handPaintAfter=new Paint();
        handPaintAfter.setAntiAlias(true);

    }
    public void close(){
        ValueAnimator alphaVa=ValueAnimator.ofInt(0,255).setDuration(300);
        alphaVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                alpha= (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        alphaVa.start();
        ValueAnimator moveVa=ValueAnimator.ofInt(dip2px(45),0).setDuration(200);
        moveVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                move_length= (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        moveVa.setStartDelay(200);
        moveVa.start();
        ValueAnimator va=ValueAnimator.ofInt(bm_height,0).setDuration(300);
        va.setInterpolator(new LinearInterpolator());
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                moveHeight= (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        va.start();
    }

    public void open(){
        final ValueAnimator alphaVa=ValueAnimator.ofInt(255,0).setDuration(300);
        alphaVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                alpha= (int) animation.getAnimatedValue();
                invalidate();

            }
        });
        alphaVa.start();
        ValueAnimator moveVa=ValueAnimator.ofInt(0,dip2px(45)).setDuration(200);
        moveVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                move_length= (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        moveVa.start();

        ValueAnimator va=ValueAnimator.ofInt(0,bm_height).setDuration(300);
        va.setInterpolator(new LinearInterpolator());
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                moveHeight= (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        va.setStartDelay(100);
        va.start();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bm_owl,new Rect(0,0,bm_owl.getWidth(),bm_owl.getHeight()),
                new Rect(dip2px(30),0,dip2px(30)+bm_owl.getWidth(),bm_owl.getHeight()),handPaintAfter);

        handPaintBefore.setAlpha(alpha);
        canvas.drawOval(new RectF(moveHeight,getHeight()-dip2px(20),move_length+dip2px(30),getHeight()),handPaintBefore);
        canvas.drawOval(new RectF(getWidth()-dip2px(30)-move_length,getHeight()-dip2px(20),getWidth()-move_length,getHeight()),handPaintBefore);

        canvas.drawBitmap(bm_owl_arm_left,//Bitmap
                new Rect(0,//left
                        0,//top
                        bm_owl_arm_left.getWidth(),//right
                        moveHeight),//bottom
                new Rect(dip2px(43),
                        getHeight()-moveHeight-dip2px(10),
                        dip2px(43)+bm_owl_arm_left.getWidth(),
                        getHeight()-dip2px(9)),
                handPaintAfter);//Paint
        //canvas.drawBitmap(Bitmap,Rect,Rect,Paint)
        canvas.drawBitmap(bm_owl_arm_right,//Bitmap
                new Rect(0,//left
                        0,//top
                        bm_owl_arm_right.getWidth(),//right
                        moveHeight),//bottom
                new Rect(getWidth()-dip2px(40)-bm_owl_arm_right.getWidth(),//left
                        getHeight()-moveHeight-dip2px(10),//top
                        getWidth()-dip2px(40),//right
                        getHeight()-dip2px(9)),//bottom
                handPaintAfter);//Paint
    }

    /**
     * 压缩图片
     * @param bitmap
     * @param reqsW
     * @param reqsH
     * @param isAdjust
     * @return
     */
    private Bitmap compressBitmap(Bitmap bitmap, int reqsW, int reqsH, boolean isAdjust) {
        if(bitmap==null||reqsW==0||reqsH==0)return  bitmap;
        if(bitmap.getWidth()>reqsW||bitmap.getHeight()>reqsH){
            //TODO 有几个方法不是太明白
            /**
             * BigDecimal用法详解
             一、简介
             Java在java.math包中提供的API类BigDecimal，用来对超过16位有效位的数进行精确的运算。双精度浮点型变量double可以处理16位有效数。在实际应用中，需要对更大或者更小的数进行运算和处理。float和double只能用来做科学计算或者是工程计算，在商业计算中要用java.math.BigDecimal。BigDecimal所创建的是对象，我们不能使用传统的+、-、*、/等算术运算符直接对其对象进行数学运算，而必须调用其相对应的方法。方法中的参数也必须是BigDecimal的对象。构造器是类的特殊方法，专门用来创建对象，特别是带有参数的对象。
             二、构造器描述
             BigDecimal(int)       创建一个具有参数所指定整数值的对象。
             BigDecimal(double) 创建一个具有参数所指定双精度值的对象。
             BigDecimal(long)    创建一个具有参数所指定长整数值的对象。
             BigDecimal(String) 创建一个具有参数所指定以字符串表示的数值的对象。

             三、方法描述
             add(BigDecimal)        BigDecimal对象中的值相加，然后返回这个对象。
             subtract(BigDecimal) BigDecimal对象中的值相减，然后返回这个对象。
             multiply(BigDecimal)  BigDecimal对象中的值相乘，然后返回这个对象。
             divide(BigDecimal)     BigDecimal对象中的值相除，然后返回这个对象。
             toString()                将BigDecimal对象的数值转换成字符串。
             doubleValue()          将BigDecimal对象中的值以双精度数返回。
             floatValue()             将BigDecimal对象中的值以单精度数返回。
             longValue()             将BigDecimal对象中的值以长整数返回。
             intValue()               将BigDecimal对象中的值以整数返回。

             四、格式化及例子
             由于NumberFormat类的format()方法可以使用BigDecimal对象作为其参数，可以利用BigDecimal对超出16位有效数字的货币值，百分值，以及一般数值进行格式化控制。
             以利用BigDecimal对货币和百分比格式化为例。首先，创建BigDecimal对象，进行BigDecimal的算术运算后，分别建立对货币和百分比格式化的引用，最后利用BigDecimal对象作为format()方法的参数，输出其格式化的货币值和百分比。
             */
            float scaleX=new BigDecimal(reqsW).divide(new BigDecimal(bitmap.getWidth()),4, RoundingMode.DOWN).floatValue();
            float scaleY=new BigDecimal(reqsH).divide(new BigDecimal(bitmap.getHeight()),4,RoundingMode.DOWN).floatValue();
            if(isAdjust){
                scaleX=scaleX<scaleY?scaleX:scaleY;
                scaleY=scaleX;
            }
            //Matrix的作用是什么
            Matrix matrix=new Matrix();
            matrix.postScale(scaleX,scaleY);

            bitmap=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        }
        return bitmap;
    }

    private int dip2px(int dpValue) {
        float scale=mContext.getResources().getDisplayMetrics().density;
         return (int)(dpValue*scale+0.5f);

    }


}
