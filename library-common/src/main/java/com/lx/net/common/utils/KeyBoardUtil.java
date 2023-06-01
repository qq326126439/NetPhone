package com.lx.net.common.utils;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;

import java.util.List;

/***********************************************************************
 * <p>@description:键盘工具
 * <p>@author: lanMing
 * <p>@created on: 2021/6/25
 * <p>@version: 1.0.0
 * <p>@modify time:
 **********************************************************************/
public class KeyBoardUtil {
    /**
     * 呼出键盘
     *
     * @param view
     */
    public static void showKeyboard(@NonNull View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            view.requestFocus();
            imm.showSoftInput(view, 0);
        }
    }
    /**
     * 隐藏软键盘(可用于Activity，Fragment) viewList 中需要放的是当前界面所有触发软键盘弹出的控件。
     * 比如一个登陆界面， 有一个账号输入框和一个密码输入框， 需要隐藏键盘的时候， 就将两个输入框对象放在 viewList 中，
     * 作为参数传到 hideSoftKeyboard 方法中即可。
     */
    public static void closeKeyBoard(Context context, View view) {
        if (view == null || context == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    /**
     * 隐藏软键盘(可用于Activity，Fragment) viewList 中需要放的是当前界面所有触发软键盘弹出的控件。
     * 比如一个登陆界面， 有一个账号输入框和一个密码输入框， 需要隐藏键盘的时候， 就将两个输入框对象放在 viewList 中，
     * 作为参数传到 hideSoftKeyboard 方法中即可。
     */
    public static void closeKeyBoard(Context context, List<View> viewList) {
        if (viewList == null || context == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            for (View v : viewList) {
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 隐藏键盘
     *
     * @param view
     */
    public static void hideKeyboard(@NonNull View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void closeKeyBoard(Activity activity) {
        if (activity == null) {
            return;

        }
        InputMethodManager methodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);//关闭软件盘
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus == null) {
            return;
        }
        methodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if ((v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            // 点击的是输入框区域，保留点击EditText的事件
            return !(event.getX() > left) || !(event.getX() < right)
                    || !(event.getY() > top) || !(event.getY() < bottom);
        }
        return false;
    }

}
