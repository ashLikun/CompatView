package com.ashlikun.compatview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * @author　　: 李坤
 * 创建时间: 2018/8/29 11:05
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：按照比例缩放的ImageView
 */

public class ScaleImageView extends AppCompatImageView {
    /**
     * 大小比例，按照宽度
     */
    private float ratio = 16 / 9.0f;

    /**
     * 按照宽度（0）或者高度（1）为基础
     */
    private int orientation = 0;

    public ScaleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ScaleImageView);
        ratio = ta.getFloat(R.styleable.ScaleImageView_sci_ratio, ratio);
        orientation = ta.getInt(R.styleable.ScaleImageView_sci_orientation, orientation);
        ta.recycle();
    }

    public ScaleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleImageView(Context context) {
        this(context, null, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        //根据图片比例缩放
//        if (scaleToDp > 0) {
//            Drawable drawable = getDrawable();
//            if (drawable != null) {
//                if (drawable.getIntrinsicWidth() > 2 && drawable.getIntrinsicWidth() > 2) {
//                    widthSize = dip2px((drawable.getIntrinsicWidth() / scaleToDp));
//                    heightSize = dip2px((drawable.getIntrinsicHeight() / scaleToDp));
//                }
//            }
//            setMeasuredDimension(widthSize, heightSize);
//            return;
//        }

        // 如果子类设置了精确的宽高
        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY
                && (widthSize != 0 && heightSize != 0)) {
            setMeasuredDimension(widthSize, heightSize);
            return;
        }

        if (ratio > 0) {
            //宽度不变
            if (orientation == 0) {
                //宽度不变
                if (getLayoutParams() != null && getLayoutParams().width > 0) {
                    heightSize = (int) (getLayoutParams().width / ratio);
                } else {
                    heightSize = (int) (widthSize / ratio);
                }
            } else {
                //高度不变
                if (getLayoutParams() != null && getLayoutParams().height > 0) {
                    widthSize = (int) (getLayoutParams().height * ratio);
                } else {
                    widthSize = (int) (heightSize * ratio);
                }
            }
            setMeasuredDimension(widthSize, heightSize);
        }
        //如果只是设置了一个值，就等比例缩放
        else if ((widthMode == MeasureSpec.EXACTLY && widthSize != 0) || (heightMode == MeasureSpec.EXACTLY && heightSize != 0)) {
            Drawable drawable = getDrawable();
            if (drawable != null) {
                if (drawable.getIntrinsicWidth() > 2 && drawable.getIntrinsicWidth() > 2) {
                    if (widthSize != 0) {
                        float s = (widthSize / (drawable.getIntrinsicWidth() * 1f));
                        heightSize = (int) (drawable.getIntrinsicHeight() * s);
                    } else if (heightSize != 0) {
                        float s = (heightSize / (drawable.getIntrinsicHeight() * 1f));
                        widthSize = (int) (drawable.getIntrinsicHeight() * s);
                    }
                }
                setMeasuredDimension(widthSize, heightSize);
                return;
            }
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public int dip2px(float dipValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);

    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
        requestLayout();
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        if (ratio == this.ratio) {
            return;
        }
        this.ratio = ratio;
        requestLayout();
    }

    public int getOrientation() {
        return orientation;
    }

    /**
     * 按照宽度（0）或者高度（1）为基础
     */
    public void setOrientationHeight() {
        if (1 == this.orientation) {
            return;
        }
        this.orientation = 1;
        requestLayout();
    }

    /**
     * 按照宽度（0）或者高度（1）为基础
     */
    public void setOrientationWidth() {
        if (0 == this.orientation) {
            return;
        }
        this.orientation = 0;
        requestLayout();
    }
}
