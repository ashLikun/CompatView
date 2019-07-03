package com.ashlikun.compatview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * 作者　　: 李坤
 * 创建时间: 2019/3/27　18:04
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：列表带扩展的TextView
 */
public class ExpandTextView extends AppCompatTextView {
    /**
     * 是否使用展开功能
     */
    boolean mIsUseExpand = false;
    /**
     * 省略文字
     */
    String ellipsizeText = "...";
    /**
     * 占位末端的文字(汉字)个数
     */
    int endTextNumber = 0;
    /**
     * true：展开，false：收起
     */
    boolean mExpanded;
    /**
     * 状态回调
     */
    Callback mCallback;
    /**
     * 源文字内容
     */
    String mText;
    /**
     * 最多展示的行数
     */
    int maxLineCount = 3;
    /**
     * 是否开启动画
     */
    boolean isAnim = true;
    /**
     * 动画时长
     */
    int duration = 300;

    /**
     * 是否正在动画中
     */
    boolean isAnimIng = false;
    /**
     * 是否可以执行动画,内部判断用的
     */
    boolean isCanAnim = false;

    public ExpandTextView(@NonNull Context context) {
        this(context, null);
    }

    public ExpandTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.ExpandTextView);
        maxLineCount = a.getInt(R.styleable.ExpandTextView_etv_maxLineCount, maxLineCount);
        isAnim = a.getBoolean(R.styleable.ExpandTextView_etv_isAnim, isAnim);
        duration = a.getInt(R.styleable.ExpandTextView_etv_duration, duration);
        endTextNumber = a.getInt(R.styleable.ExpandTextView_etv_endTextNumber, endTextNumber);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isAnimIng) {
            return;
        }
        if (!mIsUseExpand) {
            return;
        }
        if (mText == null) {
            mText = "";
        }
        // 文字计算辅助工具
        StaticLayout sl = createStaticLayout(mText);
        // 总计行数
        int lineCount = sl.getLineCount();
        if (lineCount > maxLineCount) {
            if (mExpanded) {
                startAnima(true, mText);
                mCallback.onChang(mExpanded);
            } else {
                lineCount = maxLineCount;

                // 省略文字的宽度,和占位文字的宽度
                float dotWidth = getPaint().measureText(ellipsizeText);
                //占位文字的宽度
                if (endTextNumber > 0) {
                    char[] allZhanwei = new char[endTextNumber];
                    for (int i = 0; i < endTextNumber; i++) {
                        //全角空格
                        allZhanwei[i] = 12288;
                    }
                    dotWidth += getPaint().measureText(new String(allZhanwei));
                }
                // 找出第 showLineCount 行的文字
                int start = sl.getLineStart(lineCount - 1);
                int end = sl.getLineEnd(lineCount - 1);
                String lineText = mText.substring(start, end);

                // 将第 showLineCount 行最后的文字替换为 ellipsizeText
                int endIndex = 0;
                for (int i = lineText.length() - 1; i >= 0; i--) {
                    String str = lineText.substring(i);
                    // 找出文字宽度大于 ellipsizeText 的字符
                    if (getPaint().measureText(str) >= dotWidth) {
                        endIndex = i;
                        break;
                    }
                }
                // 新的第 showLineCount 的文字
                String newEndLineText = lineText.substring(0, endIndex) + ellipsizeText;
                // 最终显示的文字
                startAnima(true, mText.substring(0, start) + newEndLineText);
                mCallback.onChang(mExpanded);
            }
        } else {
            setText(mText);
            mCallback.onLoss();
        }
        if (!isCanAnim) {
            // 重新计算高度
            int lineHeight = 0;
            for (int i = 0; i < lineCount; i++) {
                Rect lineBound = new Rect();
                sl.getLineBounds(i, lineBound);
                lineHeight += lineBound.height();
            }
            lineHeight += getPaddingTop() + getPaddingBottom();
            setMeasuredDimension(getMeasuredWidth(), lineHeight);
        }
        isCanAnim = false;
    }

    private void startAnima(final boolean expanded, String text) {
        if (!isCanAnim) {
            setText(text);
            return;
        }
        StaticLayout sl = createStaticLayout(text);
        int oldHeight = getHeight();
        int newHeight = sl.getHeight() + getCompoundPaddingBottom() + getCompoundPaddingTop();
        //执行动画
        isAnimIng = true;
        AnimatorListenerAdapter listenerAdapter = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimIng = false;
                if (expanded) {
                    setMaxLines(Integer.MAX_VALUE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isAnimIng = false;
                if (!expanded) {
                    setText(mText);
                }
            }
        };
        if (expanded) {
            setMaxLines(maxLineCount);
            setText(mText);
            animatorToHeight(oldHeight, newHeight, listenerAdapter);
        } else {
            setMaxLines(Integer.MAX_VALUE);
            animatorToHeight(oldHeight, newHeight, listenerAdapter);
        }
    }

    public ValueAnimator animatorToHeight(int oldHeight, int newHeight, @Nullable Animator.AnimatorListener listener) {
        if (oldHeight == newHeight) {
            return null;
        }
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(oldHeight, newHeight);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                setHeight(value);
            }
        });
        if (listener != null) {
            valueAnimator.addListener(listener);
        }
        valueAnimator.start();
        return valueAnimator;
    }

    private StaticLayout createStaticLayout(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return StaticLayout.Builder.obtain(text, 0, text.length(), getPaint(), getMeasuredWidth() - getPaddingLeft() - getPaddingRight())
                    .setLineSpacing(getLineSpacingExtra(), getLineSpacingMultiplier())
                    .setIncludePad(true).build();
        } else {
            return new StaticLayout(text, getPaint(), getMeasuredWidth() - getPaddingLeft() - getPaddingRight()
                    , Layout.Alignment.ALIGN_CENTER, getLineSpacingMultiplier(), getLineSpacingExtra(), true);
        }
    }

    /**
     * 设置要显示的文字以及状态
     *
     * @param text
     * @param expanded true：展开，false：收起
     * @param callback
     */
    public void setText(String text, boolean expanded, Callback callback) {
        mIsUseExpand = true;
        mExpanded = expanded;
        mCallback = callback;
        mText = text;
        // 设置要显示的文字，这一行必须要，否则 onMeasure 宽度测量不正确
        setText(text);
    }

    public void setExpanded(boolean expanded) {
        this.mExpanded = mExpanded;
    }

    public void setCallback(Callback mCallback) {
        this.mCallback = mCallback;
    }

    public void setEllipsizeText(String ellipsizeText) {
        this.ellipsizeText = ellipsizeText;
    }

    public void setMaxLineCount(int maxLineCount) {
        this.maxLineCount = maxLineCount;
    }

    public boolean isExpanded() {
        return mExpanded;
    }

    public int getMaxLineCount() {
        return maxLineCount;
    }

    public void setUseExpand(boolean useExpand) {
        this.mIsUseExpand = useExpand;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * 展开收起状态变化
     */
    public void setChanged() {
        setChanged(!mExpanded);
    }

    /**
     * 展开收起状态变化
     *
     * @param expanded
     */
    public void setChanged(boolean expanded) {
        mExpanded = expanded;
        if (isAnim) {
            isCanAnim = true;
        }
        requestLayout();
    }

    /**
     * 设置是否使用动画
     */
    public void setAnim(boolean anim) {
        isAnim = anim;
    }

    public interface Callback {
        /**
         * 展开状态
         *
         * @param isExpanded 是否展开
         */
        void onChang(boolean isExpanded);

        /**
         * 行数小于最小行数，不满足展开或者收起条件
         */
        void onLoss();
    }


}
