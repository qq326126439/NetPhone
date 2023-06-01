package com.lx.net.base.activity

import android.os.Bundle
import com.lx.net.common.constant.Constants
import com.lx.net.db.AppDatabase
import com.lx.net.local.MMKVUtil

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.base.base
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022/6/16 15:15
 * @description ：
 */
abstract class BaseVmActivity : BaseActivity(){

    protected val userId by lazy { MMKVUtil.readLong(Constants.USER_ID) }
//    protected val curUserInfo by lazy { AppDatabase.database.getUserDao().getCurrentLoginUser() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createObserver()
    }

    /**
     * 创建观察者
     */
    abstract fun createObserver()

}