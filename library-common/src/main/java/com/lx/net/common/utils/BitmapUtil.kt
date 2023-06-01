package com.lx.net.common.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapFactory.Options
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream


/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.common.utils
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022/9/3 15:53
 * @description ：
 */
object BitmapUtil {

    private val TAG: String = BitmapUtil::class.java.simpleName

    fun bitmapToBytes(bm: Bitmap): ByteArray {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos)
        return baos.toByteArray()
    }

    fun bytesToBimap(b: ByteArray): Bitmap? {
        return if (b.isNotEmpty()) {
            BitmapFactory.decodeByteArray(b, 0, b.size)
        } else null
    }

    /**
     * 直接载入图片
     *
     * @param path
     * @return
     */
    fun getBitmap(path: String): Bitmap? {
        return BitmapFactory.decodeFile(path)
    }


    /**
     * 按宽高压缩载入图片
     *
     * @param path
     * @param width
     * @param height
     * @return
     */
    fun getBitmap(path: String?, width: Int, height: Int): Bitmap? {
        val op = Options()
        op.inJustDecodeBounds = true
        val xScale: Int = op.outWidth / width
        val yScale: Int = op.outHeight / height
        op.inSampleSize = if (xScale > yScale) xScale else yScale
        op.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(path, op)
    }


    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    fun compressImage(image: Bitmap?): Bitmap? {
        val baos = ByteArrayOutputStream()
        image?.compress(Bitmap.CompressFormat.JPEG, 100, baos) // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        var options = 90
        while (baos.toByteArray().size / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset() // 重置baos即清空baos
            image?.compress(
                Bitmap.CompressFormat.JPEG,
                options,
                baos
            ) // 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10 // 每次都减少10
        }
        val isBm = ByteArrayInputStream(baos.toByteArray()) // 把压缩后的数据baos存放到ByteArrayInputStream中
        val bitmap = BitmapFactory.decodeStream(isBm, null, null) // 把ByteArrayInputStream数据生成图片
        try {
            baos.close()
            isBm.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }

    fun getZoomImage(image: Bitmap, desireW: Int, desireH: Int): Bitmap? {
        if (desireH < 0 || desireW < 0) {
            return image
        }
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        // 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
        if (baos.toByteArray().size / 1024 > 1024) {
            baos.reset() // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 80, baos) // 这里压缩50%，把压缩后的数据存放到baos中
        }
        val newOpts = Options()
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true
        val w = newOpts.outWidth
        val h = newOpts.outHeight
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        // float hh = 800f;// 这里设置高度为800f
        // float ww = 480f;// 这里设置宽度为480f
        val hh = desireH.toFloat()
        val ww = desireW.toFloat()
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        var be = 1 // be=1表示不缩放
        if (w > h && w > ww) { // 如果宽度大的话根据宽度固定大小缩放
            be = (newOpts.outWidth / ww).toInt()
        } else if (w < h && h > hh) { // 如果高度高的话根据高度固定大小缩放
            be = (newOpts.outHeight / hh).toInt()
        }
        if (be <= 0) be = 1
        newOpts.inSampleSize = be // 设置缩放比例
        // newOpts.inPreferredConfig = Config.RGB_565;//降低图片从ARGB888到RGB565
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        newOpts.inJustDecodeBounds = false
        val isBm = ByteArrayInputStream(baos.toByteArray())
        val bitmap = BitmapFactory.decodeStream(isBm, null, newOpts)
        try {
            isBm.close()
            baos.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return compressImage(bitmap) // 压缩好比例大小后再进行质量压缩
    }

}