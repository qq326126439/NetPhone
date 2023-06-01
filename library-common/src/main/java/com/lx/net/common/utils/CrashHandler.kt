package com.lx.net.common.utils

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.Looper
import android.util.Log
import com.lx.net.common.utils.ActivityManager.popAllActivity
import com.lx.net.common.constant.FileDirectory
import com.lx.net.common.toast.ToastUtils
import com.lx.net.common.utils.DateUtil.Companion.getCurrentDate
import com.lx.net.common.utils.LogCompat.logD
import com.lx.net.common.utils.LogCompat.logE
import com.lx.net.common.utils.LogCompat.logI
import java.io.*
import java.util.*
import kotlin.system.exitProcess

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.common.utils
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022/7/30 15:53
 * @description ：
 */
object CrashHandler : Thread.UncaughtExceptionHandler {

    private val info: MutableMap<String, Any?> = HashMap()
    private val TAG: String = CrashHandler::class.java.simpleName
    private lateinit var mApplication: Application
    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null

    fun init(application: Application) {
         mApplication = application
         //获取系统默认的UncaughtException处理器
         mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
         //设置该CrashHandler为程序的默认处理器
         Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(thread: Thread, throwable : Throwable) {
        if (!handleException(throwable) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler?.uncaughtException(thread, throwable)
        } else {
            try {
                Thread.sleep(500)
            } catch (e: Exception) {
                ("uncaughtException error : $e").logE(TAG)
            }
            popAllActivity()
            exitProcess(0)
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     * @param throwable
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private fun handleException(throwable: Throwable?): Boolean {
        if (throwable == null) {
            return false
        }
        Thread{
            //收集设备参数信息
            collectDeviceInfo(mApplication)
            //保存日志文件
            saveCrashInfo2File(throwable)
            Looper.prepare()
            ToastUtils.show("很抱歉,程序出现异常,即将退出。")
            Log.d(TAG, "handleException: $throwable")
            Looper.loop()
        }.start()
        return true
    }

    /**
     * 收集设备参数信息
     *
     * @param context
     */
    private fun collectDeviceInfo(context: Context) {

        info["versionName"] = AppUtil.getVerName(context)
        info["versionCode"] = AppUtil.getVerCode(context).toString()
        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                info[field.name] = field[null]
                ("${field.name} +  : ${field[null]}").logD(TAG)
            } catch (e: Exception) {
                ("an error occured when collect crash info$e").logE(TAG)
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param throwable
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private fun saveCrashInfo2File(throwable: Throwable): String? {
        val sb = StringBuilder()
        for ((key, value) in info) {
            sb.append(key).append("=").append(value).append("\n")
        }
        val writer: Writer = StringWriter()
        val printWriter = PrintWriter(writer)
        throwable.printStackTrace(printWriter)
        var cause = throwable.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        val result = writer.toString()
        sb.append(result)
        var fos: FileOutputStream? = null
        try {
            val time = getCurrentDate()
            val fileName = "errorLog-$time.txt"
            val path = if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
                || !Environment.isExternalStorageRemovable()) { //外部存储可用
                mApplication.externalCacheDir?.absolutePath?:mApplication.cacheDir.path
            }else {
                mApplication.cacheDir.path//外部存储不可用
            }
            val dir = File("${path}${FileDirectory.CRASH_HANDLER_DIRECTORY}")
            if (!dir.exists()) {
                val dirResult = dir.mkdir()
                (" 创建目录 dir " + dirResult + " " + dir.path + " --- " + dir.absolutePath).logI(TAG)
            }
            //创建文件
            val file = File(dir, fileName)
            if (!file.exists()) {
                val fileResult = file.createNewFile()
                (" createNewFile file " + fileResult + " " + file.path + " --- " + file.absolutePath).logI(TAG)
            }
            fos = FileOutputStream(file)
            fos.write(sb.toString().toByteArray())
            return fileName
        } catch (e: Exception) {
            ("an error occured while writing file...$e").logE(TAG)
        }finally {
            fos?.close()
        }
        return null
    }

}