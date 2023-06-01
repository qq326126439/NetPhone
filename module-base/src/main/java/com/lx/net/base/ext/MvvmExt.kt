package com.lx.net.base.ext

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.*
import com.lx.net.base.activity.BaseViewModel
import com.lx.net.base.exceptions.AppException
import com.lx.net.base.model.BaseEntity
import com.lx.net.common.toast.ToastUtils
import com.lx.net.common.utils.LogCompat.logE
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.CoroutineContext

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.base.ext
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022/6/20 18:25
 * @description ：
 */
private const val IO_TIMEOUT = "timeout"
typealias VmLiveData<T> = MutableLiveData<VmState<T>>
typealias VmData<T> = LiveData<VmState<T>>

@MainThread
inline fun <T> VmLiveData<T>.vmLiveObserver(owner: LifecycleOwner, vmResult: VmResult<T>.() -> Unit) {
    val result = VmResult<T>()
        result.vmResult()
    observes(owner = owner) {
        when (it) {
            is VmState.Loading -> {
                result.onLoading()
            }
            is VmState.Success -> {
                result.onSuccess(it.data)
                result.onComplete()
            }
            is VmState.Error -> {
                result.onError(it.error)
                result.onComplete()
            }
        }
    }
}

@MainThread
inline fun <T> VmData<T>.vmObserver(owner: LifecycleOwner, vmResult: VmResult<T>.() -> Unit) {
    val result = VmResult<T>()
    result.vmResult()
    observes(owner = owner) {
        when (it) {
            is VmState.Loading -> {
                result.onLoading()
            }
            is VmState.Success -> {
                result.onSuccess(it.data)
                result.onComplete()
            }
            is VmState.Error -> {
                result.onError(it.error)
                result.onComplete()
            }
        }
    }
}

class GlobalCoroutineExceptionHandler() :
    CoroutineExceptionHandler {
    override val key: CoroutineContext.Key<*>
        get() = CoroutineExceptionHandler

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        val msg =  exception.stackTraceToString()
        Log.e("GlobalCoroutineExceptionHandler","GlobalCoroutineExceptionHandler:${msg}")
    }
}
/**
 * net request
 * @param request request method
 * @param viewState request result
 */
fun <T> BaseViewModel.launchVmRequest(viewState: VmLiveData<T>, request: suspend () -> BaseEntity<T>) : Job{
      return viewModelScope.launch (GlobalCoroutineExceptionHandler()){
        runCatching {
            viewState.value = VmState.Loading
            request()
        }.onSuccess {
            viewState.paresVmResult(it)
        }.onFailure {
            if(it !is CancellationException)
              viewState.paresVmException(it)
        }
    }
}

/**
 * net request
 * @param request request method
 * @param vmResult request result
 */
fun <T> BaseViewModel.launchVmStateRequest(request: suspend () -> BaseEntity<T>, vmResult: VmResult<T>.() -> Unit) {
    val result = VmResult<T>().also(vmResult)
    viewModelScope.launch {
        runCatching {
            result.onLoading
            request()
        }.onSuccess {
            if (it.dataRight())
                result.onSuccess(it.getResData())
            else
                result.onError(AppException(it.getMessage(), it.getResCode()))
        }.onFailure {
            result.onError(AppException(it))
        }
    }
}

/**
 * net request
 * @param request request method
 */
fun <T> BaseViewModel.launchRequestLiveNoState(viewState: VmLiveData<T>, request: suspend () -> BaseEntity<T>){
     viewModelScope.launch {
        runCatching<BaseViewModel, BaseEntity<T>> {
            request()
        }.onSuccess {
            viewState.paresVmResult(it)
        }.onFailure {
            viewState.paresVmException(it)
        }
    }
}

/**
 * net request
 * @param request request method
 */
fun <T> BaseViewModel.launchRequestNoState(request: suspend () -> BaseEntity<T>){
     viewModelScope.launch {
        runCatching {
            request()
        }
    }
}

/**
 * 以协程形式执行
 */
fun BaseViewModel.launchBlock(block: () -> Unit) {
    viewModelScope.launch { block() }
}

/**
 * 处理返回值
 *
 * @param result 请求结果
 */
fun <T> VmLiveData<T>.paresVmResult(result: BaseEntity<T>) {
    value = if(result.dataRight()) {
        VmState.Success(result.getResData())
           }else{
        VmState.Error(
            AppException(
                result.getMessage(), result.getResCode()
            )
        )
          }
}

/**
 * 处理返回值
 *
 * @param result 请求结果
 */
fun <T> VmLiveData<T>.catchCoroutineExceptionHandler(result: BaseEntity<T>) {
    value = if(result.dataRight()) {
        VmState.Success(result.getResData())
    }else{
        VmState.Error(
            AppException(
                result.getMessage(), result.getResCode()
            )
        )
    }
}


/**
 * 异常转换异常处理
 */
fun <T> VmLiveData<T>.paresVmException(e: Throwable) {
    ("Throwable ----- $e ").logE("VmLiveData")
    e.printStackTrace()
    this.value = VmState.Error(AppException(e))
}


/**
 * view model 使用网络请求，拓展并捕获异常
 *
 * @param doAction
 * @receiver
 */
fun BaseViewModel.launchHttp(
    error: (e: AppException) -> Unit = { /*ToastUtils.show("发送请求失败，请联系管理员！")*/ },
    loading: (Boolean) -> Unit = { },
    doAction: suspend CoroutineScope.() -> Unit,
) {
    viewModelScope.launch {
        flow {
            try {
                emit(doAction())
            }catch (e:Exception){
                val appException = AppException(e)
                error(appException)
            }
        }.onStart {
            loading(true)
        }.onCompletion {
            loading(false)
        }.collect()
    }
}

fun <T> BaseEntity<T>.callback(error: () -> Unit = {}, success: (T?) -> Unit) {
    if (dataRight()) {
        success(getResData())
    } else {
        error()
        ToastUtils.show(getMessage())
    }
}

@MainThread
inline fun <T> LiveData<T>.observes(
    owner: LifecycleOwner,
    crossinline onChanged: (T) -> Unit
): Observer<T> {
    val wrappedObserver = Observer<T> { t -> onChanged.invoke(t) }
    observe(owner, wrappedObserver)
    return wrappedObserver
}