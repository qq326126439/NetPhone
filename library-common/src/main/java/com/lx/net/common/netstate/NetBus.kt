package com.lx.net.common.netstate

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import com.lx.net.common.constant.Constants


object NetBus {


    private var context: Context? = null
    private var netStateReceiver : NetStateReceiver? = null

    /**
     * 注册
     *
     * @param application
     */
    fun register(application: Application): NetBus {
        if (netStateReceiver == null) {
            context = application
            registerNetReceiver()
        }
        return this
    }

    /**
     * 注册网络状态接收广播
     */
    private fun registerNetReceiver() {
        netStateReceiver = NetStateReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(Constants.ANDROID_NET_CHANNEL_ACTION)
        context?.registerReceiver(netStateReceiver, intentFilter)
    }

    /**
     * 取消网络状态接收广播
     */
    private fun unregisterNetReceiver() {
        if (netStateReceiver != null) {
            context?.unregisterReceiver(netStateReceiver)
        }
        netStateReceiver = null
        context = null
    }

    /**
     * 注册网络状态观察者
     *
     * @param observer
     */
    fun registerObserver(observer: Any?) {
        requireNotNull(observer) { " Observer must not null " }
        requireNotNull(netStateReceiver) { " NetStateReceiver must register " }
        netStateReceiver?.registerObserver(observer)
    }

    /**
     * 注销网络状态观察者
     *
     * @param observer
     */
    fun unregisterObserver(observer: Any?) {
        if (netStateReceiver != null && observer != null) {
            netStateReceiver?.unregisterObserver(observer)
        }
//        netStateReceiver = null
//        context = null
    }

    /**
     * 注销所有网络状观察者
     */
    fun unregisterAllObserver() {
        netStateReceiver?.let { it ->
            it.unregisterAllObserver()
            unregisterNetReceiver()
        }
        netStateReceiver = null
        context = null
    }

}