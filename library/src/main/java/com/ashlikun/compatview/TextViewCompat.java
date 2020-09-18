package com.ashlikun.compatview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.text.Layout;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * 作者　　: 李坤
 * 创建时间:2017/9/2 0002　14:53
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：textView的兼容
 * 1：lineSpacingExtra   兼容
 * 2: 提供回调获取view宽度
 */

public class TextViewCompat extends AppCompatTextView {
    float mSpacingAdd;
    OnWidthChangListener widthChangListener;
    private int currentWidth = 0;
    public boolean movementMethodClick = false;

    public TextViewCompat(Context context) {
        this(context, null);
    }

    public TextViewCompat(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextViewCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.lineSpacingExtra});
        mSpacingAdd = a.getDimensionPixelSize(0, 0);
        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        a.recycle();

    }

    public void setWidthChangListener(OnWidthChangListener widthChangListener) {
        this.widthChangListener = widthChangListener;
    }

    //    @Override
//    public void setPadding(int left, int top, int right, int bottom) {
//        int compatpaddingButton = bottom;
//        if (isNeedCompat()) {
//            compatpaddingButton = (int) (bottom - mSpacingAdd);
//        }
//        super.setPadding(left, top, right, compatpaddingButton);
//    }

//    //手机厂商定制后的bug，原生的只是5.0
//    public boolean isNeedCompat() {
//        Log.e("aaaa", Build.BRAND + "   Build.MODEL=" + Build.MODEL);
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            return true;
//        } else if (Build.BRAND != null) {
//            String BRAND = Build.BRAND.toUpperCase();
//            if (BRAND.contains("VIVO") && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//                return true;
//            }
//            if (BRAND.contains("OPPO")) {
//                return true;
//            }
//            //m1 note m3 note   m1 metal  M3s  U20  MX5正常
////            if (BRAND.contains("MEIZU") && !"m1 metal".equals(Build.MODEL)) {
////                return true;
////            }
//        }
//
//        return false;
//    }

    @Override
    public void setLineSpacing(float add, float mult) {
        super.setLineSpacing(add, mult);
        mSpacingAdd = add;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight() - calculateExtraSpace());
        if (widthChangListener != null) {
            if (currentWidth != getMeasuredWidth()) {
                currentWidth = getMeasuredWidth();
                widthChangListener.onWidthChang(getMeasuredWidth(), getMeasuredHeight());
            }
        }
    }

    /**
     * 计算需要兼容的底部多余的高度
     *
     * @return
     */
    public int calculateExtraSpace() {
        int result = 0;
        int lastLineIndex = getLineCount() - 1;

        if (lastLineIndex >= 0) {
            Layout layout = getLayout();
            if (getMeasuredHeight() - getPaddingTop() - getPaddingBottom() == getLayout().getHeight()) {
                Rect mRect = new Rect();
                int baseline = getLineBounds(lastLineIndex, mRect);
                result = mRect.bottom - (baseline + layout.getPaint().getFontMetricsInt().bottom);
            }
        }
        return result;
    }

    public interface OnWidthChangListener {
        /**
         * 当TextView宽度改变的时候，就是可以获取宽度的时候
         *
         * @param width
         * @param height
         */
        void onWidthChang(int width, int height);
    }

    /**
     * 设置了ClickableSpan导致的上层View点击事件无法响应解决方案
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        MovementMethod movementMethod = getMovementMethod();
        //这里引用 另外一个库的FocusLinkMovementMethod对象   CommonUtils库
        if (movementMethod != null && movementMethod instanceof OnLongClickListener) {
            movementMethodClick = false;
            boolean result = super.onTouchEvent(event);
            movementMethodClick = ((OnLongClickListener) movementMethod).onLongClick(null);
            return result;
        } else {
            return super.onTouchEvent(event);
        }
    }

    /**
     * 设置了ClickableSpan导致的上层View点击事件无法响应解决方案
     */
    public void setMovementMethods(MovementMethod movement) {
        boolean focusable = isFocusable();
        boolean isClickable = isClickable();
        boolean isLongClickable = isLongClickable();
        super.setMovementMethod(movement);
        setFocusable(focusable);
        setClickable(isClickable);
        setLongClickable(isLongClickable);
    }

    /**
     * 设置了ClickableSpan导致的上层View点击事件无法响应解决方案
     */
    @Override
    public boolean performClick() {
        if (!movementMethodClick) {
            return super.performClick();
        }
        return false;
    }
}
