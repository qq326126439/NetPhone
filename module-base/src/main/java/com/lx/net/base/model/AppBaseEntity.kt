package com.lx.net.base.model

import java.io.Serializable

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.base.base
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022/6/16 10:13
 * @description ：数据泛型类
 */

class AppBaseEntity<T>(private val status: Int?,
                       private val message: String?, private val data: T?) : BaseEntity<T>(status, message, data),
    Serializable {

    override fun dataRight(): Boolean {
        return status == 1
    }

    override fun getMessage(): String? {
        return message
    }

    override fun getResCode(): Int? {
        return status
    }

    override fun getResData(): T? {
        return data
    }

}