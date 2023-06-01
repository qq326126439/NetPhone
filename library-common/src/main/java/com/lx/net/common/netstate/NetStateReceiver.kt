package com.lx.net.common.netstate

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.lx.net.common.constant.Constants
import com.lx.net.common.netstate.NetType
import com.lx.net.common.utils.LogCompat.logE
import java.util.*


/***********************************************************************
 * <p>@description: 网络监听广播
 * <p>@author: pengl
 * <p>@created on: 2022/7/28 0028 15:47
 * <p>@version: 1.0.0
 * <p>@modify time:2022/7/28 0028 15:47
 **********************************************************************/
class NetStateReceiver : BroadcastReceiver() {

    val TAG: String = "NetStateReceiver"

    private var netType = NetType.NONE
    private var netParamsMap: MutableMap<Any, MutableList<NetParams>> = mutableMapOf()

    override fun onReceive(context: Context, intent: Intent?) {
        intent?.action?.let {
            if (Constants.ANDROID_NET_CHANNEL_ACTION == it) {
                netType = NetTool.getCurrentNetType(context)
            }
            notifyObserver(netType)
        }
    }

    private fun notifyObserver(netType: NetType) {

        if (netParamsMap.isEmpty()) return
        val observers = netParamsMap.keys
        for (observer in observers) {
            val netParams: MutableList<NetParams>? = netParamsMap[observer]
            if (!netParams.isNullOrEmpty()) {
                for (netParam in netParams) {
                    if (netParam.paramType.isAssignableFrom(netType.javaClass)) {
                        when (netParam.netType) {
                            NetType.AUTO -> invoke(netParam, observer, netType)
                            NetType.WIFI -> if (netType === NetType.WIFI || netType === NetType.NONE) {
                                invoke(netParam, observer, netType)
                            }
                            NetType.NET2G -> if (netType === NetType.NET2G || netType === NetType.NONE) {
                                invoke(netParam, observer, netType)
                            }
                            NetType.NET3G -> if (netType === NetType.NET3G || netType === NetType.NONE) {
                                invoke(netParam, observer, netType)
                            }
                            NetType.NET4G -> if (netType === NetType.NET4G || netType === NetType.NONE) {
                                invoke(netParam, observer, netType)
                            }
                            NetType.NET5G -> if (netType === NetType.NET5G || netType === NetType.NONE) {
                                invoke(netParam, observer, netType)
                            }
                            NetType.NET_UNKNOWN -> if (netType === NetType.NET_UNKNOWN || netType === NetType.NONE) {
                                invoke(netParam, observer, netType)
                            }
                            NetType.NONE -> invoke(netParam, observer, netType)
                        }
                    }
                }
            }
        }
    }

    private operator fun invoke(netParam: NetParams, observer: Any, netType: NetType) {
        try {
            netParam.method.invoke(observer, netType)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 注册观察者
     *
     * @param observer
     */
    fun registerObserver(observer: Any?) {
        if (observer == null) return
        var netParams = netParamsMap[observer]
        if (!netParams.isNullOrEmpty()) return
        netParams = findAnnotationMethod(observer)
        netParams?.let {
            netParamsMap[observer] = it
            ( " registerObserver " + observer.javaClass.name).logE(TAG)
        }
    }

    /**
     * 注解找到相关方法
     *
     * @param observer
     * @return
     */
    private fun findAnnotationMethod(observer: Any): MutableList<NetParams>? {
        try {
            val clazz: Class<*> = observer.javaClass
            val methods = clazz.methods
            if (methods.isNotEmpty()) {
                val netParams: MutableList<NetParams> = ArrayList()
                for (method in methods) {
                    val network: Network = method.getAnnotation(Network::class.java) ?: continue
                    val parameterTypes = method.parameterTypes
                    if (parameterTypes.size != 1 || parameterTypes[0] != NetType::class.java) {
                        continue
                    }
                    netParams.add(NetParams(parameterTypes[0], network.netType, method))
                }
                return netParams
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 注销观察者
     *
     * @param observer
     */
    fun unregisterObserver(observer: Any?) {
        if (observer == null) return
        val netParams: MutableList<NetParams>? = netParamsMap[observer]
        netParams?.let {
            netParamsMap.remove(observer)
            ( " unRegisterObserver " + observer.javaClass.name).logE(TAG)
        }
    }

    fun unregisterAllObserver() {
        netParamsMap.clear()
    }
}