package com.lx.net.common.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.FileProvider
import java.io.ByteArrayOutputStream
import java.io.File
import kotlin.math.sqrt

/**
 * @author : lxm
 * @package_name ：cc.crrcgc.hse.common.ext
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2021/8/29 15:11
 * @description ： 拓展
 */

/**
 * @param radius  模糊度（0--25）
 * @param context 上下文
 * @return 模糊后的bitmap
 */
fun Bitmap.toBlur(radius: Float, context: Context): Bitmap {
    var temp = radius
    val outBitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
    val renderScript = RenderScript.create(context)
    val scriptIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
    val allIn = Allocation.createFromBitmap(renderScript, this)
    val allOut = Allocation.createFromBitmap(renderScript, outBitmap)
    if (temp > 25)
        temp = 25F
    else if (temp <= 0)
        temp = 1F
    scriptIntrinsic.setRadius(temp)
    scriptIntrinsic.setInput(allIn)
    scriptIntrinsic.forEach(allOut)
    allOut.copyTo(outBitmap)
    scriptIntrinsic.destroy()
    if (!this.isRecycled)
        this.recycle()
    return outBitmap
}

fun Bitmap.bitmapOption(size: Int): ByteArrayOutputStream {
    val out = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, 85, out)
    val zoom = sqrt((size * 1024 / out.toByteArray().size.toFloat()).toDouble()).toFloat()
    val matrix = Matrix()
    matrix.setScale(zoom, zoom)
    var result = Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    out.reset()
    result.compress(Bitmap.CompressFormat.PNG, 90, out)
    if (out.toByteArray().size > size * 1024) {
        println(out.toByteArray().size)
        matrix.setScale(0.9f, 0.9f)
        result = Bitmap.createBitmap(result, 0, 0, result.width, result.height, matrix, true)
        out.reset()
        result.compress(Bitmap.CompressFormat.PNG, 90, out)
    }
    return out
}

/**
 * @param  url url地址
 * @return
 */
@SuppressLint("QueryPermissionsNeeded")
fun Context.openBrowser(url: String) {
    val uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, uri)
        .run { addCategory(Intent.CATEGORY_DEFAULT) }
    val resolveList = packageManager.queryIntentActivities(intent, PackageManager.MATCH_ALL)
    if (resolveList.size > 0) {
        val chooser = Intent.createChooser(intent, "请选择")
        this.startActivity(chooser)
    }
}

/**
 * 让view不根据系统窗口来调整自己的布局
 */
fun Activity.setRequestApplyInsets() {
    val window = window
    //让view不根据系统窗口来调整自己的布局
    val mContentView =
        window.findViewById<View>(Window.ID_ANDROID_CONTENT) as ViewGroup
    val mChildView = mContentView.getChildAt(0)
    mChildView.fitsSystemWindows = false
    mChildView.requestApplyInsets()
}

fun Context.getUriForFile(file : File): Uri {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        FileProvider.getUriForFile(
            applicationContext,
            "$packageName.provider",
            file
        )
    } else {
        Uri.fromFile(file)
    }
}

fun Context.getFilesPath(dirName : String? = null):String{
    return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
        || !Environment.isExternalStorageRemovable()) {
        //外部存储可用
        getExternalFilesDir(dirName)?.absolutePath?:filesDir.path
    }else {
        //外部存储不可用
        filesDir.path
    }
}

fun Context.getCachePath():String{
    return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
        || !Environment.isExternalStorageRemovable()) {
        //外部存储可用
        externalCacheDir?.absolutePath?:cacheDir.path
    }else {
        //外部存储不可用
        cacheDir.path
    }
}
