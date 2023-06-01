package com.lx.net.base.ext

import android.app.Application
import com.lx.net.base.BuildConfig
import com.alibaba.android.arouter.launcher.ARouter
import com.jeremyliao.liveeventbus.LiveEventBus
import com.kingja.loadsir.core.LoadSir
import com.lx.net.base.ext.SyncInitTask
import com.lx.net.base.page.EmptyCallback
import com.lx.net.base.page.ErrorCallback
import com.lx.net.base.page.LoadingCallback
import com.lx.net.common.utils.CrashHandler
import com.lx.net.common.utils.HseAlcImpl
import com.lx.net.db.AppDatabase
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.base.ext
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022/7/4 16:38
 * @description ：
 */
class RouterInitTask : SyncInitTask() {

    override fun init(application: Application) {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(application)
    }

    override fun onlyMainProcess(): Boolean {
        return true
    }

    override fun level(): Int {
        return -3
    }

    override fun asyncTaskName(): String {
        return "ARouter"
    }

}

class LiveEventBusInitTask : SyncInitTask() {

    override fun init(application: Application) {
        LiveEventBus
            .config()
            .autoClear(true)
            .lifecycleObserverAlwaysActive(true)
    }

    override fun onlyMainProcess(): Boolean {
        return true
    }

    override fun level(): Int {
        return 1
    }

    override fun asyncTaskName(): String {
        return "LiveEventBus"
    }

}

class LoggerInitTask : SyncInitTask() {

    override fun init(application: Application) {
        val formatStrategy: FormatStrategy =
            PrettyFormatStrategy.newBuilder() // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .tag("hse")
                .showThreadInfo(true)  // 输出线程信息. 默认输出
                .methodCount(0)         // 方法栈打印的个数，默认是2
                .methodOffset(7)        // 设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
                .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }

    override fun onlyMainProcess(): Boolean {
        return true
    }

    override fun level(): Int {
        return 0
    }

    override fun asyncTaskName(): String {
        return "Logger"
    }

}

/**
 * 监听生命周期  异常捕获
 */
class MonitorAppInitTask : SyncInitTask() {

    override fun init(application: Application) {
        HseAlcImpl.bindApplication(application)
        CrashHandler.init(application)
    }

    override fun onlyMainProcess(): Boolean {
        return true
    }

    override fun level(): Int {
        return -2
    }

    override fun asyncTaskName(): String {
        return "监听生命周期  异常捕获"
    }

}

/**
 * 数据库
 */
class AppDbInitTask : SyncInitTask() {

    override fun init(application: Application) {
        AppDatabase.initDataBase(application)
    }

    override fun onlyMainProcess(): Boolean {
        return true
    }

    override fun level(): Int {
        return -1
    }

    override fun asyncTaskName(): String {
        return "room"
    }

}

class LoadSirInitTask : SyncInitTask() {

    override fun init(application: Application) {
        LoadSir.beginBuilder()
            .addCallback(ErrorCallback()) //添加各种状态页
            .addCallback(EmptyCallback())
            .addCallback(LoadingCallback())
//            .setDefaultCallback(LoadingCallback::class.java)
            .commit()
    }

    override fun onlyMainProcess(): Boolean {
        return true
    }

    override fun level(): Int {
        return 1
    }

    override fun asyncTaskName(): String {
        return "LoadSir"
    }

}

//class ToastInitTask : SyncInitTask() {
//
//    override fun init(application: Application) {
//        ToastUtils.init(application)
//    }
//
//    override fun onlyMainProcess(): Boolean {
//        return true
//    }
//
//    override fun level(): Int {
//        return -4
//    }
//
//    override fun asyncTaskName(): String {
//        return "Toast"
//    }
//
//}

class SmartRefreshInitTask : SyncInitTask() {

    override fun init(application: Application) {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { _, _ ->
            ClassicsHeader(application)
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { _, _ ->
            ClassicsFooter(application)
        }
    }

    override fun onlyMainProcess(): Boolean {
        return true
    }

    override fun level(): Int {
        return 1
    }

    override fun asyncTaskName(): String {
        return "SmartRefresh"
    }

}