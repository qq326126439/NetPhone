package com.lx.net.common.utils

import android.graphics.Bitmap
import android.os.Environment
import com.lx.net.common.utils.LogCompat.logI
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.channels.FileChannel
import java.text.DecimalFormat
import java.util.regex.Pattern

/**
 * @author : lxm
 * @version ： @package_name ：com.lx.net.common.utils
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022/9/3 15:53
 * @description ：
 */
object FileUtil {

    private val TAG: String = FileUtil::class.java.simpleName

    fun saveCapturePicture(fileDir: String, fileName: String, bitmap: Bitmap): Boolean {
        if (fileDir.isEmpty()) {
            return false
        }
        val dir = File(fileDir)
        if (!dir.exists()) {
            val dirResult = dir.mkdir()
            " 创建目录 dir " + dirResult + " " + dir.path + " --- " + dir.absolutePath.logI(TAG)
        }
        //创建文件
        val file = File(dir, fileName)
        if (!file.exists()) {
            val fileResult = file.createNewFile()
            " createNewFile file " + fileResult + " " + file.path + " --- " + file.absolutePath.logI(
                TAG
            )
        }
        var out: FileOutputStream? = null
        try {
            // 保存原图
            out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            out?.close()
        }
        return false
    }

    fun bitmap2File(file: File, bitmap: Bitmap): Boolean {
        if (!file.isFile) {
            return false
        }
        val fis = FileOutputStream(file)
        var buff: BufferedOutputStream? = null
        try {
            buff = BufferedOutputStream(fis, 1024)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, buff)
            buff.flush()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            buff?.close()
        }
        return false
    }

    fun getFileSize(file: File): Long {
        val fis: FileInputStream?
        val channel: FileChannel?
        if (file.isFile && file.exists()) {
            fis = FileInputStream(file)
            channel = fis.channel
            return channel.size()
        }
        return 0
    }

    fun getReadableFileSize(file: File): String {
        val size = getFileSize(file)
        return covertFileSize(size)
    }

    fun covertFileSize(size: Long): String {
        val dec = DecimalFormat("###.#")
        val kilo = " KB"
        val mega = " MB"
        val giga = " GB"
        var fileSize = 0f
        var suffix = kilo
        if (size > 1024) {
            fileSize = (size / 1024).toFloat()
            if (fileSize > 1024) {
                fileSize /= 1024
                if (fileSize > 1024) {
                    fileSize /= 1024
                    suffix = giga
                } else {
                    suffix = mega
                }
            }
        }
        return dec.format(fileSize.toDouble()) + suffix
    }

    fun isImage(fileName: String): Boolean {
        val reg = "(jpg|png|tiff|webp|heif|gif|bmp|jpeg)"
        val pattern = Pattern.compile(reg)
        return pattern.matcher(fileName).find()
    }

    fun isVideo(fileName: String): Boolean {
        val reg = "(mp4|flv|avi|mov|rm|rmvb|wmv|hevc|mkv|mpg|mpeg|mpe|3gp|mp3|m4v|dat|vob|MKV)"
        val pattern = Pattern.compile(reg)
        return pattern.matcher(fileName).find()
    }



    fun getDownloadDir(): String =
        HseAlcImpl.getApplication().getExternalFilesDir(
            Environment.DIRECTORY_DOWNLOADS)?.absolutePath.toString()


    /**
     * 复制单个文件
     *
     * @param oldPath  String 原文件路径+文件名 如：data/user/0/com.test/files/abc.txt
     * @param newPath String 复制后路径+文件名 如：data/user/0/com.test/cache/abc.txt
     * @return `true` if and only if the file was copied;
     * `false` otherwise
     */
    fun copyFile(oldPath: String, newPath: String): Boolean {
        return try {
            val oldFile = File(oldPath)
            if (!oldFile.exists()) {
                ("copyFile:  oldFile not exist.").logI(TAG)
                return false
            } else if (!oldFile.isFile) {
                ("copyFile:  oldFile not file.").logI(TAG)
                return false
            } else if (!oldFile.canRead()) {
                ("copyFile:  oldFile cannot read.").logI(TAG)
                return false
            }

            /* 如果不需要打log，可以使用下面的语句
                  if (!oldFile.exists() || !oldFile.isFile() || !oldFile.canRead()) {
                      return false;
                  }
                  */
            val fileInputStream = FileInputStream(oldPath)
            val fileOutputStream = FileOutputStream(newPath)
            val buffer = ByteArray(1024)
            var byteRead: Int
            while (-1 != fileInputStream.read(buffer).also { byteRead = it }) {
                fileOutputStream.write(buffer, 0, byteRead)
            }
            fileInputStream.close()
            fileOutputStream.flush()
            fileOutputStream.close()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

}