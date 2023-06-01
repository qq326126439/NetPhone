package com.lx.net.common.utils

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.common.utils
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2021/10/13 10:34
 * @description ：单列模式 仿lazy()
 */
open class SingletonHolder<out T>(creator: () -> T) {

    private var creator: (() -> T)? = creator

    @Volatile
    private var instance: T? = null

    fun getInstance(): T {

        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!()
                instance = created
                creator = null
                created
            }
        }

    }

}