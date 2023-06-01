package com.lx.net.base.ext

import com.lx.net.base.exceptions.AppException

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.base.ext
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022/6/20 18:21
 * @description ：
 */
class VmResult<T> {

    var onSuccess: (data: T?) -> Unit = {}
    var onError: (AppException) -> Unit = {}
    var onLoading: () -> Unit = {}
    var onComplete: () -> Unit = {}

    fun onSuccess(success: (T?) -> Unit) {
        onSuccess = success
    }

    fun onError(error: (AppException) -> Unit) {
        onError = error
    }

    fun onLoading(loading: () -> Unit) {
        onLoading = loading
    }

    fun onComplete(complete: () -> Unit) {
        onComplete = complete
    }

}

sealed class VmState<out T > {

    data class Success<out T >(val data: T?) : VmState<T>()
    data class Error(val error : AppException) : VmState<Nothing>()
    object Loading : VmState<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=${error.message}]"
            is Loading -> "Loading"
        }
    }

}