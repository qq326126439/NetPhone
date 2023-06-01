package com.lx.net.router.applaction

import android.app.Application
import com.alibaba.android.arouter.facade.template.IProvider
/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.router.applaction
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022年6月16日12:56:23
 * @description ：模块初始化接口
 */
interface IAppInit : IProvider {

    fun initApp(application: Application)

}