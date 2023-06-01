package com.lx.net.base.activity

import android.os.Bundle
import com.lx.net.common.utils.StateBarUtil

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.base.base
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022/6/16 13:59
 * @description ：
 */
abstract class BaseActivity : BaseDiaActivity(){

    protected var curPage = 1
    protected val pageSize = 10
    protected var mOffset = 1
    protected val REFRESH_TIME_OUT = 1000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val point = Paint()
//        val cm = ColorMatrix()
//        cm.setSaturation(0F)
//        point.colorFilter = ColorMatrixColorFilter(cm)
//        window?.decorView?.setLayerType(View.LAYER_TYPE_HARDWARE,point)
        StateBarUtil.setStateBarTransparent(this)
        StateBarUtil.setImmersiveStatusBar(this,true)
        initView(savedInstanceState)
        bindEvent()
        fillData()
    }

    protected open fun initView(savedInstanceState: Bundle?) {

    }

    /**
     * 绑定控件的点击事件
     */
    protected open fun bindEvent() {

    }

    /**
     * 填充数据
     */
    protected open fun fillData() {

    }


    override fun onDestroy() {
        super.onDestroy()
//        IMMLeaks.fixFocusedViewLeak(application)
    }

}