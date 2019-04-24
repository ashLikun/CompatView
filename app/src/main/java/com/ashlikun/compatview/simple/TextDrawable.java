package com.ashlikun.compatview.simple;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.ColorInt;
import android.text.TextUtils;


/**
 * 作者　　: 李坤
 * 创建时间: 2018/6/16 0016　下午 1:06
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：显示文字的GradientDrawable, 显示背景，边框，圆角，文字
 */
public class TextDrawable extends GradientDrawable {

    private String text;
    private float textSize;
    private int textColor;
    private Paint paint;
    private int paddingLeft;
    private int paddingTop;
    private int paddingRight;
    private int paddingBottom;
    private int stokeWidth;

    public TextDrawable() {
        super();
        init();
    }

    public TextDrawable(Orientation orientation, int[] colors) {
        super(orientation, colors);
        init();
    }

    private void init() {
        this.paddingLeft = dip2px(AppUtils.getApp(), 3);
        this.paddingTop = dip2px(AppUtils.getApp(), 2);
        this.paddingRight = dip2px(AppUtils.getApp(), 3);
        this.paddingBottom = dip2px(AppUtils.getApp(), 2);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        setTextSize(12);
    }


    private void setDrawableSize() {
        if (TextUtils.isEmpty(text)) {
            setSize(0, 0);
            setBounds(0, 0, 0, 0);
            return;
        }
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        int width = rect.width() + paddingLeft + paddingRight + stokeWidth * 2;
        int height = rect.height() + paddingTop + paddingBottom + stokeWidth * 2;
        setSize(width, height);
        setBounds(0, 0, width, height);
        invalidateSelf();
    }

    @Override
    public void draw(Canvas canvas) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        super.draw(canvas);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int baseline = (getIntrinsicHeight() - fontMetrics.top - fontMetrics.bottom) / 2;
        canvas.drawText(text, 0, text.length(), getIntrinsicWidth() / 2, baseline, paint);
    }

    public TextDrawable setText(String text) {
        this.text = text;
        setDrawableSize();
        return this;
    }

    public TextDrawable setTextColor(int textColor) {
        this.textColor = textColor;
        paint.setColor(this.textColor);
        setDrawableSize();
        return this;
    }

    public TextDrawable setPadding(float paddingLeftDp, float paddingTopDp, float paddingRightDp, float paddingBottomDp) {
        this.paddingLeft = dip2px(AppUtils.getApp(), paddingLeftDp);
        this.paddingTop = dip2px(AppUtils.getApp(), paddingTopDp);
        this.paddingRight = dip2px(AppUtils.getApp(), paddingRightDp);
        this.paddingBottom = dip2px(AppUtils.getApp(), paddingBottomDp);
        setDrawableSize();
        return this;
    }

    public TextDrawable setTextSize(float textSizeDp) {
        textSize = dip2px(AppUtils.getApp(), textSizeDp);
        paint.setTextSize(textSize);
        setDrawableSize();
        return this;
    }

    /**
     * 设置边框
     *
     * @param widthDp
     * @param color
     */
    public TextDrawable setStrokeNew(float widthDp, @ColorInt int color) {
        stokeWidth = dip2px(AppUtils.getApp(), widthDp);
        setStroke(stokeWidth, color);
        return this;
    }

    public TextDrawable setColorNew(int argb) {
        super.setColor(argb);
        return this;
    }

    public TextDrawable setCornerRadiusNew(float radiusDp) {
        super.setCornerRadius(dip2px(AppUtils.getApp(), radiusDp));
        return this;
    }
    /**
     * 作者　　: 李坤
     * 创建时间: 2017/6/28 11:25
     * <p>
     * 方法功能：将dip或dp值转换为px值，保证尺寸大小不变
     */

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}