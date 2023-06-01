package com.lx.net.netphone

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.lx.net.base.ext.SyncInitTask
import com.lx.net.router.Modules
import com.lx.net.router.applaction.AppModules
import com.lx.net.router.applaction.IAppInit

/**
 * @author : lxm
 * @version ：
 * @package_name ：cc.crrcgc.hse
 * @org ： 深圳赛为安全技术服务有限公司
 * @date :2022/8/29 15:11
 * @description ：
 */
class ModuleInitTask : SyncInitTask() {

    override fun init(application: Application) {
        //val modules = application.getString(R.string.modules).split(",")
        //各模块需要初始化在这里添加以下就不每个都去写
//        val modules = mutableListOf(Modules.MSG.moduleName,Modules.WORK.moduleName)
//        modules.forEach { module ->
//            val modulePath = AppModules.getModulePath(module)
//            modulePath?.let {
//                ARouter
//                    .getInstance()
//                    .build(modulePath)
//                    .navigation()
//                    ?.run {
//                        if (this is IAppInit)
//                            this.initApp(application)
//                    }
//            }
//        }
    }

    override fun onlyMainProcess(): Boolean {
        return true
    }

    override fun level(): Int {
        return -1
    }

}