package com.ashlikun.compatview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.WindowManager;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/6/11 0011　下午 3:55
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：最大高度的ScrollView
 */
public class MaxHeightScrollView extends NestedScrollView {
    /**
     * 优先级低
     * 是屏幕高度的多少
     */
    private float mMaxRatio = 0;
    /**
     * 优先级高
     * 最大高度
     */
    private float mMaxHeight = 0;

    public MaxHeightScrollView(@NonNull Context context) {
        this(context, null);
    }

    public MaxHeightScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaxHeightScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MaxHeightScrollView);
        mMaxRatio = a.getFloat(R.styleable.MaxHeightScrollView_mhsv_heightRatio, 0);
        mMaxHeight = a.getDimension(R.styleable.MaxHeightScrollView_mhsv_maxHeight, 0);
        a.recycle();
        init();
    }


    private void init() {
        if (mMaxHeight <= 0) {
            mMaxHeight = mMaxRatio * (float) getScreenHeight(getContext());
        } else {
            mMaxHeight = Math.min(mMaxHeight, mMaxRatio * (float) getScreenHeight(getContext()));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //如果没有设置高度
        if (mMaxHeight <= 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (heightMode == MeasureSpec.EXACTLY) {
            heightSize = heightSize <= mMaxHeight ? heightSize
                    : (int) mMaxHeight;
        }

        if (heightMode == MeasureSpec.UNSPECIFIED) {
            heightSize = heightSize <= mMaxHeight ? heightSize
                    : (int) mMaxHeight;
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = heightSize <= mMaxHeight ? heightSize
                    : (int) mMaxHeight;
        }
        int maxHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize,
                heightMode);
        super.onMeasure(widthMeasureSpec, maxHeightMeasureSpec);
    }

    public void setMaxRatio(float mMaxRatio) {
        this.mMaxRatio = mMaxRatio;
        init();
        requestLayout();
    }

    public void setMaxHeight(float mMaxHeight) {
        this.mMaxHeight = mMaxHeight;
        init();
        requestLayout();
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     */
    private int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }
}
