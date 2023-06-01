package com.lx.net.common.toast.style;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;

import androidx.annotation.NonNull;

/**
 *    author :
 *    github :
 *    time   : 2018/09/01
 *    desc   : 默认白色样式实现
 */
public class WhiteToastStyle extends BlackToastStyle {

    @Override
    protected int getTextColor(Context context) {
        return 0XBB000000;
    }

    @Override
    protected Drawable getBackgroundDrawable(@NonNull Context context) {
        GradientDrawable drawable = new GradientDrawable();
        // 设置颜色
        drawable.setColor(0XFFEAEAEA);
        // 设置圆角
        drawable.setCornerRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, context.getResources().getDisplayMetrics()));
        return drawable;
    }
}