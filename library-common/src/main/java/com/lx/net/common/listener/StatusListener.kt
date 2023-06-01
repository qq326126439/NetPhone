package com.lx.net.common.listener

import android.app.Activity

/**
 * @author : lxm
 * @package_name ：com.lx.net.common.listener
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2021/8/29 15:11
 * @description ： 拓展
 */
interface StatusListener {

    fun onForeground(activity: Activity?)

    fun onBackground(activity: Activity?)

}