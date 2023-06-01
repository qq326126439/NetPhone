package com.lx.net.common.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/***********************************************************************
 * <p>@description: 7.0以上透明状态栏设置
 * <p>@author: pengl
 * <p>@created on: 2022/8/10 0010 10:40
 * <p>@version: 1.0.0
 * <p>@modify time:2022/8/10 0010 10:40
 **********************************************************************/
public class StateBarUtil {
    //隐藏状态栏
    public static void hide(Activity activity){
        if (Build.VERSION.SDK_INT >= 21) {
            activity.getWindow()
                    .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    public static void setStateBarTransparent(@NonNull Activity activity) {

        activity.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);
    }


    public final static int TYPE_MIUI = 0;
    public final static int TYPE_FLYME = 1;
    public final static int TYPE_M = 3;//6.0

    @IntDef({TYPE_MIUI,
            TYPE_FLYME,
            TYPE_M})
    @Retention(RetentionPolicy.SOURCE)
    @interface ViewType {
    }

    /**
     * 修改状态栏颜色，支持4.4以上版本
     * @param colorId 颜色
     */
    public static void setStatusBarColor(@NonNull Activity activity, int colorId) {

        Window window = activity.getWindow();
        window.setStatusBarColor(colorId);
    }

    /**
     * 设置状态栏透明
     */
    public static void setTranslucentStatus(@NonNull Activity activity) {

        // 5.0以上系统状态栏透明
        Window window = activity.getWindow();
        //清除透明状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        //设置状态栏颜色必须添加
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);//设置透明
    }

    /**
     * 设置沉浸式状态栏
     *
     * @param fontIconDark 状态栏字体和图标颜色是否为深色
     */
    public static void setImmersiveStatusBar(Activity activity,boolean fontIconDark) {
        setTranslucentStatus(activity);
        if (fontIconDark) {
            if (OSUtil.isMiui()) {
                setStatusBarFontIconDark(activity,TYPE_MIUI);
            } else if (OSUtil.isFlyme()) {
                setStatusBarFontIconDark(activity,TYPE_FLYME);
            } else {//其他情况下我们将状态栏设置为灰色，就不会看不见字体
                setStatusBarFontIconDark(activity,TYPE_M);
            }
        }
    }

    /**
     * 设置文字颜色
     */
    public static void setStatusBarFontIconDark(Activity activity,@ViewType int type) {
        switch (type) {
            case TYPE_MIUI:
                setMiuiUI(activity,true);
                break;
            case TYPE_M:
                setCommonUI(activity);
                break;
            case TYPE_FLYME:
                setFlymeUI(activity,true);
                break;
        }
    }

    //设置6.0的字体
    public static void setCommonUI(@NonNull Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    //设置Flyme的字体
    public static void setFlymeUI(Activity activity,boolean dark) {
        try {
            Window window = activity.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (dark) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //设置MIUI字体
    public static void setMiuiUI(Activity activity,boolean dark) {
        try {
            Window window = activity.getWindow();
            Class<? extends Window> clazz = activity.getWindow().getClass();
            @SuppressLint("PrivateApi") Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (dark) {    //状态栏亮色且黑色字体
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
