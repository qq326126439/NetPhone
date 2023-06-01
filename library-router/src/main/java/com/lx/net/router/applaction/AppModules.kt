package com.lx.net.router.applaction

import com.lx.net.router.Modules


/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.router.applaction
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022年6月16日12:56:23
 * @description ：
 */
object AppModules {

    fun getModulePath(module: String): String? {
        return when (module) {
            Modules.LOGIN.moduleName -> Modules.LOGIN.moduleInitPath

            else -> null
        }
    }

}
