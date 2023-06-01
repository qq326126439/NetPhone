package com.lx.net.base.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Resources
import android.content.res.TypedArray
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lx.net.common.utils.LogCompat.logE

/***********************************************************************
 * <p>@description: 字体缩放base类
 * <p>@author: pengl
 * <p>@created on: 2022/7/27 0027 17:30
 *
 * <p>@version: 1.0.0
 * <p>@modify time:2022/7/27 0027 17:30
 **********************************************************************/
open class FontScaleAppCompatActivity : AppCompatActivity() {

    protected val TAG = this::class.java.simpleName

    override fun attachBaseContext(newBase: Context) {
        val resources = newBase.resources
        val newConfig = resources.configuration
        val displayMetrics = resources.displayMetrics
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            if (newConfig != null && newConfig.fontScale != 1f) {
                newConfig.fontScale = 1f
                displayMetrics.scaledDensity = displayMetrics.density * newConfig.fontScale
                super.attachBaseContext(newBase.createConfigurationContext(newConfig))
                (" attachBaseContext config.fontScale = ${newConfig.fontScale}").logE(TAG)
                return
            }
        }
        super.attachBaseContext(newBase)
    }

    /**
     * 重写字体缩放比例  api<=25
     *
     * @return 资源文件
     */
    override fun getResources(): Resources {
        val res = super.getResources()
        val config = res.configuration
        val displayMetrics = res.displayMetrics
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
            //禁止app字体随系统字体大小变化
            if (config != null && config.fontScale != 1f) {
                config.fontScale = 1f
                displayMetrics.scaledDensity = displayMetrics.density * config.fontScale
                res.updateConfiguration(config, displayMetrics)
                (" getResources config.fontScale = ${config.fontScale}").logE(TAG)
                return res
            }
        }
        return res
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        fixAndroid8ScreenOrientation()
        super.onCreate(savedInstanceState)
    }

    /**
     * Android 8.0 在透明主题下，修改activity方向会出现闪退
     * 这里判断如果是Android 8.0 并且是含透明主题，则将固定方向去掉
     */
    @SuppressLint("DiscouragedPrivateApi")
    private fun fixAndroid8ScreenOrientation() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating) {
            try {
                val field = Activity::class.java.getDeclaredField("mActivityInfo") ?: return
                field.isAccessible = true
                val o = field[this] as ActivityInfo
                o.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                field.isAccessible = false
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     *
     * @return 通过反射判断是否是透明页面
     */
    private val isTranslucentOrFloating: Boolean
        @SuppressLint("PrivateApi")
        get() {
            var isTranslucentOrFloating = false
            try {
                val styleClass = Class.forName("com.android.internal.R\$styleable") ?: return false
                val windowField = styleClass.getDeclaredField("Window") ?: return false
                windowField.isAccessible = true
                val styleableRes = windowField[null] as IntArray
                val ta = obtainStyledAttributes(styleableRes)
                val m = ActivityInfo::class.java.getMethod(
                    "isTranslucentOrFloating",
                    TypedArray::class.java
                )
                    ?: return false
                m.isAccessible = true
                isTranslucentOrFloating = m.invoke(null, ta) as Boolean
                m.isAccessible = false
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return isTranslucentOrFloating
        }

}