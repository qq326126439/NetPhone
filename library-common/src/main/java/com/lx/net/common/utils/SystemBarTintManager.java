package com.lx.net.common.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

/***********************************************************************
 * <p>@description: 
 * <p>@author: pengl
 * <p>@created on: 2022/8/10 0010 11:09
 * <p>@version: 1.0.0
 * <p>@modify time:2022/8/10 0010 11:09
 **********************************************************************/
public class SystemBarTintManager {
    public static final int DEFAULT_TINT_COLOR = 0x99000000;
    private boolean mStatusBarAvailable;
    private View mStatusBarTintView;

    public SystemBarTintManager(@NonNull Activity activity) {

        Window win = activity.getWindow();
        //获取DecorView
        ViewGroup decorViewGroup = (ViewGroup) win.getDecorView();
        // 检查主题中是否有透明的状态栏
        int[] attrs = {android.R.attr.windowTranslucentStatus};
        @SuppressLint("ResourceType") TypedArray a = activity.obtainStyledAttributes(attrs);
        try {
            mStatusBarAvailable = a.getBoolean(0, false);
        } finally {
            a.recycle();
        }
        WindowManager.LayoutParams winParams = win.getAttributes();
        int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;//状态栏透明
        if ((winParams.flags & bits) != 0) {
            mStatusBarAvailable = true;
        }
        if (mStatusBarAvailable) {
            setupStatusBarView(activity, decorViewGroup);
        }
    }

    /**
     * 初始化状态栏
     *
     * @param context
     * @param decorViewGroup
     */
    private void setupStatusBarView(Activity context, @NonNull ViewGroup decorViewGroup) {
        mStatusBarTintView = new View(context);
        //设置高度为Statusbar的高度
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, DensityUtil.INSTANCE.getStatusBarHeight(context));
        params.gravity = Gravity.TOP;
        mStatusBarTintView.setLayoutParams(params);
        mStatusBarTintView.setBackgroundColor(DEFAULT_TINT_COLOR);
        //默认不显示
        mStatusBarTintView.setVisibility(View.GONE);
        //decorView添加状态栏高度的View
        decorViewGroup.addView(mStatusBarTintView);
    }

    /**
     * 显示状态栏
     */
    public void setStatusBarTintEnabled(boolean enabled) {
        if (mStatusBarAvailable) {
            mStatusBarTintView.setVisibility(enabled ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     */
    public void setStatusBarTintColor(int color) {
        if (mStatusBarAvailable) {
            mStatusBarTintView.setBackgroundColor(color);
        }
    }
}
