package com.lx.net.router

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.router
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022年6月16日12:56:23
 * @description ：
 */
enum class Modules(val moduleName: String,val moduleInitPath: String) {

    LOGIN("module-login", RouterPath.loginInitPath),

}