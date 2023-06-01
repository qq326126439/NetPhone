package com.lx.net.common.toast.style;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lx.net.common.R;
import com.lx.net.common.toast.config.IToastStyle;
import com.lx.net.common.utils.DensityUtil;
import com.lx.net.common.utils.HseAlcImpl;


/**
 * time   : 2018/12/06
 * desc   : 支付宝样式实现
 */
public class ToastAliPayStyle implements IToastStyle<TextView> {
    @Override
    public TextView createView(Context context) {
        TextView textView = new TextView(context);
        textView.setId(android.R.id.message);
        textView.setGravity(getTextGravity(context));
        textView.setTextColor(getTextColor(context));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getTextSize(context));
//        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,getTextSizeSp(context));
        int horizontalPadding = getHorizontalPadding(context);
        int verticalPadding = getVerticalPadding(context);

        // 适配布局反方向特性
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            textView.setPaddingRelative(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);
        } else {
            textView.setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);
        }
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Drawable background = getBackgroundDrawable(context);
        // 设置背景
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackground(background);
        } else {
            textView.setBackgroundDrawable(background);
        }

        // 设置 Z 轴阴影
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textView.setZ(getTranslationZ(context));
        }
        // 设置最大显示行数
        textView.setMaxLines(getMaxLines(context));
        setOffsetY((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, HseAlcImpl.Companion.getApplication().getResources().getDisplayMetrics()));
        return textView;
    }

    int offsetY = 0;

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    @Override
    public int getYOffset() {
        return offsetY;
    }


    protected int getTextGravity(Context context) {
        return Gravity.CENTER;
    }

    protected int getTextColor(Context context) {
        return 0XEEFFFFFF;
    }
    protected float getTextSizeSp(@NonNull Context context) {
        /*获取sp值*/
        //获取对应资源文件下的sp值
        float pxValue = context.getResources().getDimension(R.dimen.sp_14);
//        return pxValue;
        //将px值转换成sp值
        return DensityUtil.INSTANCE.px2sp(context, pxValue);
    }
    protected float getTextSize(@NonNull Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, context.getResources().getDisplayMetrics());
    }

    protected int getHorizontalPadding(@NonNull Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, context.getResources().getDisplayMetrics());
    }

    protected int getVerticalPadding(@NonNull Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, context.getResources().getDisplayMetrics());
    }

    protected Drawable getBackgroundDrawable(@NonNull Context context) {
        GradientDrawable drawable = new GradientDrawable();
        // 设置颜色
        drawable.setColor(0X88000000);
        // 设置圆角
        drawable.setCornerRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics()));
        return drawable;
    }

    protected float getTranslationZ(@NonNull Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, context.getResources().getDisplayMetrics());
    }

    protected int getMaxLines(Context context) {
        return 8;
    }
}