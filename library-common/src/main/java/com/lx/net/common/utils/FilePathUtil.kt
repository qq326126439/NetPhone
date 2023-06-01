package com.lx.net.common.utils

import android.os.Environment
import com.lx.net.common.utils.LogCompat.logI
import java.io.File

/***********************************************************************
 * <p>@description:
 * <p>@author: pengl
 * <p>@created on: 2022/8/22 0022 15:25
 * <p>@version: 1.0.0
 * <p>@modify time:2022/8/22 0022 15:25
 **********************************************************************/
object FilePathUtil {

    //视频缓存路径 /tsc.xxx.xxx/cache/video/
    fun getVideoCache(): String {
        try {
            val videoCache = "${HseAlcImpl.getApplication().getExternalFilesDir(
                Environment.DIRECTORY_DOWNLOADS)}${File.separator}cache/video/"

            val videoFileCache = File(videoCache)
            if (!videoFileCache.exists() || !videoFileCache.isDirectory) {
                videoFileCache.mkdirs()
                return videoCache
            }
            return videoCache
        } catch (e: Exception) {
            ("保利威路径创建失败  error = $e").logI("FilePathUtil")
            e.printStackTrace()
        }
        return Environment.getExternalStorageDirectory().absolutePath + File.separator + Environment.DIRECTORY_DOWNLOADS
    }

    //缓存路径 /tsc.xxx.xxx/cache
    fun getCache(): String {
        try {
            val cache: String =
                getTscRootPath() + "cache/"
            val fileCache = File(cache)
            if (!fileCache.exists() || !fileCache.isDirectory) {
                fileCache.mkdirs()
            }
            return cache
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Environment.getExternalStorageDirectory().absolutePath + File.separator + Environment.DIRECTORY_DOWNLOADS
    }

    //视频下载缓存路径 /tsc.xxx.xxx/cache/video/download/
    fun getVideoDownCache(): String {
        try {
            val videoDownCache: String =
                getTscRootPath() + "cache/video/download/"
            val videoFileDownCahce = File(videoDownCache)
            if (!videoFileDownCahce.exists() || !videoFileDownCahce.isDirectory) {
                videoFileDownCahce.mkdirs()
            }
            return videoDownCache
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Environment.getExternalStorageDirectory().absolutePath + File.separator + Environment.DIRECTORY_DOWNLOADS
    }

    // 项目数据根目录
    fun getTscRootPath(): String {
        val rootPath =
            Environment.getExternalStorageDirectory().absolutePath + File.separator + getAppStorageDir() + File.separator
        val file = File(rootPath)
        if (!file.isDirectory || !file.exists()) {
            file.mkdirs()
        }
        return rootPath
    }

    //必须是这个，不能修改，否则无法正常使用Tbs 打开文件
    fun getTbsReaderTemp(): String {
        val temp =
            Environment.getExternalStorageDirectory().absolutePath + File.separator + "TbsReaderTemp"
        val file = File(temp)
        if (!file.exists() || !file.isDirectory) {
            file.mkdirs()
        }
        return temp
    }

    private fun getAppStorageDir() = "crrc.hse.beta"
}