package com.lx.net.base.message

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.base.message
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022/6/16 10:13
 * @description ：错误提示类
 */
data class ErrorMsg(val code: Int?, val message: String?, val cause: Throwable?) {

    constructor(errorMsgEnum: ErrorMsgEnum, cause: Throwable?) : this(
        errorMsgEnum.code,
        errorMsgEnum.message,
        cause
    )

    companion object {

        private const val EMPTY_EXCEPTION_CODE = 1
        private const val EMPTY_EXCEPTION_MSG = "错误信息为空"
        val DEFAULT_ERROR_MSG = ErrorMsg(EMPTY_EXCEPTION_CODE, EMPTY_EXCEPTION_MSG, null)

    }

}