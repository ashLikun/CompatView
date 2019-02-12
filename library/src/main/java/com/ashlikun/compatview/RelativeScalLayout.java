package com.ashlikun.compatview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/6/11 0011　下午 3:55
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：按比例缩放的RelativeLayout
 */
public class RelativeScalLayout extends RelativeLayout {

    /**
     * 缩放比例
     */
    private float ratio = 0f;
    /**
     * 按照那个值为基础
     * 0:宽度
     * 1：高度
     */
    private int orientation = 0;

    public RelativeScalLayout(@NonNull Context context) {
        this(context, null);
    }

    public RelativeScalLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RelativeScalLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RelativeScalLayout);
        ratio = a.getFloat(R.styleable.RelativeScalLayout_rsl_ratio, 0);
        orientation = a.getInt(R.styleable.RelativeScalLayout_rsl_orientation, 0);
        a.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (ratio <= 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        // 如果子类设置了精确的宽高
        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY
                && (widthSize != 0 && heightSize != 0)) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        //宽度不变
        if (orientation == 0) {
            //宽度不变
            heightSize = (int) (widthSize / ratio);
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(heightSize, heightMode));
            return;
        } else {
            //高度不变
            widthSize = (int) (heightSize * ratio);
            super.onMeasure(MeasureSpec.makeMeasureSpec(widthSize, widthMode), heightMeasureSpec);
            return;
        }
    }

    /**
     * 设置比例
     *
     * @param ratio
     */
    public void setRatio(float ratio) {
        this.ratio = ratio;
        requestLayout();
    }

    /**
     * 设置方向
     *
     * @param orientation
     */
    public void setOrientation(int orientation) {
        this.orientation = orientation;
        requestLayout();
    }
}
