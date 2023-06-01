package com.lx.net.common.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Paint
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import kotlin.math.ceil

/***********************************************************************
 * <p>@description: 像素工具类
 * <p>@author: pengl
 * <p>@created on: 2022/7/28 0028 9:04
 *
 * <p>@version: 1.0.0
 * <p>@modify time:2022/7/28 0028 9:04
 **********************************************************************/
object DensityUtil {


    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     */
    fun isPad(context: Context): Boolean {
        return ((context.resources.configuration.screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE)
    }

    fun getTextWidth(paint: Paint, str: String?): Int {
        var iRet = 0
        if (str != null && str.isNotEmpty()) {
            val len = str.length
            val widths = FloatArray(len)
            paint.getTextWidths(str, widths)
            for (j in 0 until len) {
                iRet += ceil(widths[j].toDouble()).toInt()
            }
        }
        return iRet
    }

    /**
     * px值转换成dp值
     *
     * @param pxValue px值
     * @return dp值
     */
    fun px2dp(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * px值转换成sp值
     *
     * @param pxValue px值
     * @return sp值
     */
    fun px2sp(context: Context, pxValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    fun dp2px(resources: Resources, dp: Float): Float {
        val scale = resources.displayMetrics.density
        return dp * scale + 0.5f
    }

    fun sp2px(context: Context, sp: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (sp * fontScale + 0.5f).toInt()
    }

    fun sp2px(resources: Resources, sp: Float): Float {
        val scale = resources.displayMetrics.scaledDensity
        return sp * scale
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 获取dialog宽度
     */
    fun getDialogW(aty: Context): Int {
        var dm = DisplayMetrics()
        dm = aty.resources.displayMetrics
        // int w = aty.getWindowManager().getDefaultDisplay().getWidth() - 100;
        return dm.widthPixels - 100
    }

    /**
     * 获取屏幕
     */
    fun getDisplayMetrics(context: Context): DisplayMetrics {
        return context.resources.displayMetrics
    }

    /**
     * 获取屏幕宽度
     */
    fun getScreenW(context: Context): Int {
        val dm: DisplayMetrics = context.resources.displayMetrics
        return dm.widthPixels
    }

    /**
     * 获取屏幕高度
     */
    fun getScreenH(context: Context): Int {
        val dm: DisplayMetrics = context.resources.displayMetrics
        return dm.heightPixels
    }

    private fun displayMetrics(context: Context): DisplayMetrics {
        val dm = DisplayMetrics()
        val windowManager = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(dm)
        return dm
    }

    fun getNormalWH(activity: Activity?): IntArray {
        return if (activity != null) {
            val point = Point()
            val wm = activity.windowManager
            wm.defaultDisplay.getSize(point)
            intArrayOf(point.x, point.y)
        } else {
            intArrayOf(0, 0)
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    @SuppressLint("InternalInsetResource")
    fun getStatusBarHeight(context: Context): Int {
        var statusBarHeight = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }


    fun widthPixels(context: Context): Int {
        return displayMetrics(context).widthPixels
    }

    fun heightPixels(context: Context): Int {
        return displayMetrics(context).heightPixels
    }

}