package com.lx.net.base.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import com.lx.net.base.fragment.BaseFragment
import com.lx.net.common.constant.Constants
import com.lx.net.db.AppDatabase
import com.lx.net.local.MMKVUtil

/**
 * @author : lxm
 * @version ：
 * @package_name ：cc.crrcgc.hse.base.base
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022/6/16 15:16
 * @description ：
 */
abstract class BaseVmFragment(@LayoutRes contentLayoutId: Int = 0) : BaseFragment(contentLayoutId) {

    protected val userId by lazy { MMKVUtil.readLong(Constants.USER_ID) }
    protected val baseUrl by lazy { MMKVUtil.readString(Constants.API_BASE_URL) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createObserver()
    }

    /**
     * 创建观察者
     */
    abstract fun createObserver()

}