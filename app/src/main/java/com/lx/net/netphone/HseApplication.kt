package com.lx.net.netphone

import com.alibaba.android.arouter.launcher.ARouter
import com.kingja.loadsir.core.LoadSir
import com.lx.net.base.activity.BaseApplication
import com.lx.net.base.ext.AppDbInitTask
import com.lx.net.base.ext.InitTaskRunner
import com.lx.net.base.ext.LiveEventBusInitTask
import com.lx.net.base.ext.LoggerInitTask
import com.lx.net.base.ext.MonitorAppInitTask
import com.lx.net.base.ext.SmartRefreshInitTask
import com.lx.net.base.page.EmptyCallback
import com.lx.net.base.page.ErrorCallback
import com.lx.net.base.page.LoadingCallback
import com.lx.net.common.constant.Constants
import com.lx.net.common.netstate.NetBus
import com.lx.net.common.toast.ToastUtils
import com.lx.net.common.utils.LogCompat.logI
import com.lx.net.local.MMKVUtil
import com.tencent.mmkv.MMKV

class HseApplication : BaseApplication() {

    companion object{
        private val TAG = HseApplication::class.java.simpleName
    }

    override fun onCreate() {
        super.onCreate()
        mmkvInit()
        routerInit()
        ToastUtils.init(this)
        NetBus.register(this)
        LoadSir.beginBuilder()
            .addCallback(ErrorCallback()) //添加各种状态页
            .addCallback(EmptyCallback())
            .addCallback(LoadingCallback())
//            .setDefaultCallback(LoadingCallback::class.java)
            .commit()
        val runner = InitTaskRunner
            .Builder(this@HseApplication)
//            .addTask(MMKVInitTask())
//            .addTask(RouterInitTask())
            .addTask(MonitorAppInitTask())
            .addTask(ModuleInitTask())
            .addTask(LoggerInitTask())
//            .addTask(AppDbInitTask())
            .addTask(LiveEventBusInitTask())
//            .addTask(LoadSirInitTask())
            .addTask(SmartRefreshInitTask())
        runner.build().run()
    }

    private fun mmkvInit(){
        val rootDir = MMKV.initialize(this)
        ("mmkv root: $rootDir").logI(TAG)
        MMKVUtil.write(Constants.API_BASE_URL, BuildConfig.BASE_URL)
    }

    private fun routerInit(){
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
    }

}