package com.lx.net.base.model

import com.lx.net.base.message.ErrorMsgEnum
import java.io.Serializable

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.base.base
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022/6/16 10:13
 * @description ：数据泛型基类
 */
open class BaseEntity<T>(
    var errorCode: Int? = 1,
    var errorMsg: String? = ErrorMsgEnum.HTTP_ERROR_MSG_NULL.message,
    var datas: T? = null
): Serializable {


    /**
     * 数据是否正确，默认实现
     */
    open fun dataRight(): Boolean {
        return errorCode == 1
    }

    /**
     * 获取错误信息，默认实现
     */
    open fun getMessage(): String? {
        return errorMsg
    }

    /**
     * 获取状态码
     */
    open fun getResCode(): Int? {
        return errorCode
    }

    /**
     * 获取返回数据
     */
    open fun getResData(): T? {
        return datas
    }

}
