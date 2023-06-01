package com.lx.net.base.exceptions

import com.lx.net.base.message.ErrorMsg

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.base.exceptions
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022/6/16 10:13
 * @description ：基础业务异常类
 */
open class AppException : RuntimeException {

    var errorMsg: String

    var errorCode: Int? = 1

    constructor(message: String?, code: Int?) : super(message) {
        errorMsg = message ?: parseError(null).message.toString()
        errorCode = code
    }

    constructor(throwable: Throwable?) : super(throwable) {
        errorMsg = parseError(throwable).message.toString()
    }

    private fun parseError(throwable: Throwable?): ErrorMsg {
        return ExceptionConverter.create(throwable)
    }
}
