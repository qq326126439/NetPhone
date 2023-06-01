package com.lx.net.router.provider

import com.alibaba.android.arouter.facade.template.IProvider
/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.router.provider
 * @org ： 深圳赛为安全技术服务有限公司
 * @date :2022/8/29 15:11
 * @description ：重新登录
 */
interface ReLoginProvider : IProvider {

    fun reLogin (tag : String)

}