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
public class MaxheightScrollView extends NestedScrollView {
    private static final float DEFAULT_MAX_RATIO = 0.6f;
    private static final float DEFAULT_MAX_HEIGHT = 0f;
    /**
     * 优先级高
     */
    private float mMaxRatio = DEFAULT_MAX_RATIO;
    /**
     * 优先级低
     */
    private float mMaxHeight = DEFAULT_MAX_HEIGHT;

    public MaxheightScrollView(@NonNull Context context) {
        this(context, null);
    }

    public MaxheightScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaxheightScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MaxheightScrollView);
        mMaxRatio = a.getFloat(R.styleable.MaxheightScrollView_mhsv_HeightRatio, DEFAULT_MAX_RATIO);
        mMaxHeight = a.getDimension(R.styleable.MaxheightScrollView_mhsv_HeightDimen, DEFAULT_MAX_HEIGHT);
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
