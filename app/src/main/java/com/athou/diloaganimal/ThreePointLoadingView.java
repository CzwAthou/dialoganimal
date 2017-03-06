/*
 * Copyright (c) 2016  athou（cai353974361@163.com）.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.athou.diloaganimal;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 加载动画
 */
public class ThreePointLoadingView extends View {

    // 画笔
    private Paint mBallPaint;
    //点的颜色
    private int fillColor = 0;

    // 圆的最小半径，最大半径
    private float mRadiusMin, mRadiusMax;
    //A,B,C圆的半径
    private float mABallRadius, mBBallRadius, mCBallRadius;
    // A圆心的x,y坐标
    private float mABallX, mABallY;
    // B圆心的x,y坐标
    private float mBBallX, mBBallY;
    // C圆心的x,y坐标
    private float mCBallX, mCBallY;

    private ValueAnimator animator;

    // A圆的起始透明度
    private int mABallAlpha = 255;
    // B圆的起始透明度
    private int mBBallAlpha = (int) (255 * 0.8);
    // C圆的起始透明度
    private int mCBallAlpha = (int) (255 * 0.6);

    //是否自动播放
    private boolean isAutoPlay = false;

    public ThreePointLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ThreePointLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ThreePointLoadingView, defStyleAttr, 0);
        isAutoPlay = a.getBoolean(R.styleable.ThreePointLoadingView_autoplay, false);
        fillColor = a.getColor(R.styleable.ThreePointLoadingView_fillcolor, Color.WHITE);
        a.recycle();

        mBallPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mBallPaint.setColor(fillColor);
        mBallPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 考虑padding值
        int mWidth = getWidth();
        int mHeight = getHeight();

        // 最大半径为高度5分之一
        mRadiusMax = mHeight * 1.0f / 8;
        mRadiusMin = mRadiusMax * 0.6f;

        // 间距为宽度20分之一
        float mSpace = mRadiusMin * 4;

        mABallRadius = mRadiusMax;
        mBBallRadius = mCBallRadius = mRadiusMin;

        // 总的长度为三个圆直径加上之间的间距
        float mTotalLength = mRadiusMin * 6 + mSpace * 2;

        // A圆心起始坐标，同时贝塞尔曲线的起始坐标也是这个
        mABallX = (mWidth - mTotalLength) / 2 + mRadiusMin;
        mABallY = mHeight / 2;

        // B圆心的起始坐标
        mBBallX = (mWidth - mTotalLength) / 2 + mRadiusMin * 3 + mSpace;
        mBBallY = mHeight / 2;

        // C圆心的起始坐标
        mCBallX = (mWidth - mTotalLength) / 2 + mRadiusMin * 5 + mSpace * 2;
        mCBallY = mHeight / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制B圆
        mBallPaint.setAlpha(mBBallAlpha);
        canvas.drawCircle(mBBallX, mBBallY, mBBallRadius, mBallPaint);

        // 绘制C圆
        mBallPaint.setAlpha(mCBallAlpha);
        canvas.drawCircle(mCBallX, mCBallY, mCBallRadius, mBallPaint);

        // 绘制A圆
        mBallPaint.setAlpha(mABallAlpha);
        canvas.drawCircle(mABallX, mABallY, mABallRadius, mBallPaint);

        if (isAutoPlay && animator == null) {
            animator = createValueAnimator();
            animator.start();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // 销毁view时取消动画，避免内存泄露
        if (animator != null){
            animator.cancel();
        }
    }

    // 开启值动画
    public void startLoading() {
        post(new Runnable() {
            @Override
            public void run() {
                reset();
            }
        });
    }

    //取消动画  并回复原来大小
    public void cancel() {
        if (animator != null) {
            animator.cancel();
        }
        mABallRadius = mRadiusMax;
        mBBallRadius = mCBallRadius = mRadiusMin;

        mABallAlpha = 255;
        mBBallAlpha = (int) (255 * 0.8);
        mCBallAlpha = (int) (255 * 0.6);

        invalidate();
    }

    private void reset() {
        if (animator != null) {
            animator.cancel();
        }

        mABallRadius = mRadiusMax;
        mBBallRadius = mCBallRadius = mRadiusMin;

        mABallAlpha = 255;
        mBBallAlpha = (int) (255 * 0.8);
        mCBallAlpha = (int) (255 * 0.6);

        animator = createValueAnimator();
        animator.start();
    }

    private ValueAnimator createValueAnimator() {
        ValueAnimator animator = ValueAnimator.ofFloat(mRadiusMin, mRadiusMax);
        // 动画无限模式
        animator.setRepeatCount(ValueAnimator.INFINITE);
        // 时长1秒
        animator.setDuration(1000);
        // 延迟0.5秒执行
        animator.setStartDelay(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float mOffsetX = (float) animation.getAnimatedValue();
                //动画执行的百分比
                float fraction = (mOffsetX - mRadiusMin) * 1f / (mRadiusMax - mRadiusMin);
                if (fraction < 0.5f) { // A->B
                    fraction *= 2;

                    mABallRadius = mRadiusMax - mRadiusMax * fraction * 0.4f;
                    mBBallRadius = mRadiusMax * 0.6f + mRadiusMax * fraction * 0.4f;
                    mCBallRadius = mRadiusMax * 0.6f;

                    mABallAlpha = (int) (255 - 255 * fraction * 0.4f);
                    mBBallAlpha = (int) (255 * 0.6f + 255 * fraction * 0.4f);
                    mCBallAlpha = (int) (255 * 0.6f);
                } else { // B->C
                    fraction -= 0.5;
                    fraction *= 2;

                    mABallRadius = mRadiusMax * 0.6f;
                    mBBallRadius = mRadiusMax - mRadiusMax * fraction * 0.4f;
                    mCBallRadius = mRadiusMax * 0.6f + mRadiusMax * fraction * 0.4f;

                    mABallAlpha = (int) (255 * 0.6f);
                    mBBallAlpha = (int) (255 - 255 * fraction * 0.4f);
                    mCBallAlpha = (int) (255 * 0.6f + 255 * fraction * 0.4f);
                }
                //强制刷新
                postInvalidate();
            }
        });
        return animator;
    }

    // 测量尺寸
    private int measureSize(int measureSpec, int defaultSize) {

        final int mode = MeasureSpec.getMode(measureSpec);
        final int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            return size;
        } else if (mode == MeasureSpec.AT_MOST) {
            return Math.min(size, defaultSize);
        }
        return size;
    }
}
