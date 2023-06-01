package com.lx.net.base.ext

import android.app.Application
import android.os.Process
import com.lx.net.common.utils.LogCompat.logE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.base.ext
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022/7/4 16:09
 * @description ：
 */

class InitTaskRunner constructor(private val builder: Builder) : CoroutineScope by MainScope() {

    companion object{
        private val TAG = InitTaskRunner::class.java.simpleName
    }

    class Builder constructor(application: Application) {

        val mTasks: MutableList<InitTask> = mutableListOf()
        val mApplication = application

        fun addTask(task : InitTask)= apply {
            mTasks += task
        }

        fun build() : InitTaskRunner {
            return InitTaskRunner(this)
        }

    }

    fun run() {
        val isMainProcess = isMainProcess()
        val syncTasks: MutableList<InitTask> = mutableListOf()
        val asyncTasks: MutableList<InitTask> = mutableListOf()
        for (task in builder.mTasks) {
            if (!isMainProcess && task.onlyMainProcess()) {
                continue
            }
            if (task.sync()) {
                syncTasks.add(task)
            } else {
                asyncTasks.add(task)
            }
        }
        runSync(syncTasks)
        runAsync(asyncTasks)
    }

    private fun runSync(tasks: MutableList<InitTask>) {
        tasks.sortBy { it.level() }
        for (task in tasks) {
            try {
                launch {
                    task.init(builder.mApplication)
                    (" InitTaskRunner runSync ${task.asyncTaskName()} ").logE(TAG)
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    private fun runAsync(tasks: MutableList<InitTask>) {
        tasks.sortBy { it.level() }
        for (task in tasks) {
            try {
               val result =  async {
                    task.init(builder.mApplication)
                }
                (" InitTaskRunner runAsync ${task.asyncTaskName()} result --> $result").logE(TAG)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    private fun isMainProcess(): Boolean {
        return builder.mApplication.packageName == getCurrentProcessName()
    }

    private fun getCurrentProcessName(): String? {
        return try {
            val file = File("/proc/" + Process.myPid() + "/" + "cmdline")
            val mBufferedReader = BufferedReader(FileReader(file))
            val processName = mBufferedReader.readLine().trim { it <= ' ' }
            mBufferedReader.close()
            processName
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}

abstract class AsyncInitTask : InitTask {

    companion object{
        val TAG = this::class.java.simpleName
    }

    override fun sync(): Boolean {
        return false
    }

    override fun level(): Int {
        return 0
    }

    override fun onlyMainProcess(): Boolean {
        return true
    }

    override fun asyncTaskName(): String {
        return TAG
    }

}

abstract class SyncInitTask : InitTask {

    companion object{
      val TAG = this::class.java.simpleName
    }

    override fun sync(): Boolean {
        return true
    }

    override fun level(): Int {
        return 0
    }

    override fun onlyMainProcess(): Boolean {
        return true
    }

    override fun asyncTaskName(): String {
        return TAG
    }

}

interface InitTask {

    fun sync(): Boolean
    fun asyncTaskName(): String
    fun level(): Int
    fun onlyMainProcess(): Boolean
    fun init(application: Application)

}