package com.lx.net.base.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import com.lx.net.common.netstate.NetTool
import com.lx.net.common.netstate.NetType
import com.lx.net.common.netstate.Network
import com.lx.net.common.utils.LogCompat.logE

/**
 * @author : lxm
 * @version ：
 * @package_name ：cc.crrcgc.hse.base.base
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022/6/16 15:14
 * @description ：
 */
abstract class BaseFragment(@LayoutRes contentLayoutId: Int = 0 ): BaseDiaFragment(contentLayoutId){

    protected var curPage = 1
    protected val pageSize= 10
    protected var mOffset = 1
    protected val REFRESH_TIME_OUT = 1000

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view, savedInstanceState)
        bindEvent()
        fillData()
    }

    protected open fun initView(view: View, savedInstanceState: Bundle?) {

    }

    /**
     * 绑定视图控件
     */
    protected open fun bindEvent() {

    }

    /**
     * 填充数据
     */
    protected open fun fillData() {

    }

    @Network(netType = NetType.AUTO)
    open fun network(netType: NetType?) {
        when (netType) {
            NetType.WIFI -> {
                ("当前有网络 WIFi").logE(TAG)
                showNetState(true)
                if (NetTool.isWifiProxy()) {
                    showToast("当前wifi设置代理,请确保能够访问互联网")
                }
            }
            NetType.NET2G -> {
                ("当前有网络 2G").logE(TAG)
                showNetState(true)
            }
            NetType.NET3G -> {
                ("当前有网络 3G").logE(TAG)
                showNetState(true)
            }
            NetType.NET4G -> {
                ( "当前有网络 4G").logE(TAG)
                showNetState(true)
            }
            NetType.NET5G -> {
                ("当前有网络 5G").logE(TAG)
                showNetState(true)
            }
            NetType.NET_UNKNOWN -> {
                ("当前有网络 网络类型未归类").logE(TAG)
                showNetState(true)
            }
            NetType.NONE -> {
                showNetState(false)
                ( "当前没有网络").logE(TAG)
            }
            else -> {
                showNetState(false)
                ("当前没有网络").logE(TAG)
            }
        }
    }

    protected open fun showNetState(hasNet: Boolean) {}


}