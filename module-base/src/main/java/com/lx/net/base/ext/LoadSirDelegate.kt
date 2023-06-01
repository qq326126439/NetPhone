package com.lx.net.base.ext

import android.view.View
import androidx.annotation.MainThread
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.lx.net.base.page.EmptyCallback
import com.lx.net.base.page.ErrorCallback
import com.lx.net.base.page.LoadingCallback

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.base.ext
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022/6/20 18:21
 * @description ：状态页管理
 */
enum class LoadPageState {
    Loading,
    Success,
    Error,
    Empty
}

interface LoadSirDelegate {

    fun registerAttachLayout(target: Any, onReloadListener: (view: View?) -> Unit)

    fun showLoading()

    fun showSuccess()

    fun showError()

    fun showEmpty()

    fun setState(state: LoadPageState)
}


internal class LoadSirDelegateImpl : LoadSirDelegate {

    private var mLoadService: LoadService<*>? = null

    override fun registerAttachLayout(target: Any, onReloadListener: (view: View?) -> Unit) {
        mLoadService = LoadSir.getDefault().register(target) {
            onReloadListener.invoke(it)
        }
    }

    override fun showLoading() {
        mLoadService?.showCallback(LoadingCallback::class.java)
    }

    override fun showSuccess() {
        mLoadService?.showSuccess()
    }

    override fun showError() {
        mLoadService?.showCallback(ErrorCallback::class.java)
    }

    override fun showEmpty() {
        mLoadService?.showCallback(EmptyCallback::class.java)
    }

    override fun setState(state: LoadPageState) {
        when (state) {
            LoadPageState.Loading -> {
                showLoading()
            }
            LoadPageState.Success -> {
                showSuccess()
            }
            LoadPageState.Error -> {
                showError()
            }
            LoadPageState.Empty -> {
                showEmpty()
            }
        }
    }
}

/**
 *
 * Example usage
 *
 * ```
 * class MyActivity : BaseActivity(){
 *   private val loadPage by loadSirDelegate()
 * }
 * ```
 */
@MainThread
fun loadSirDelegate() = lazy<LoadSirDelegate> {
    return@lazy LoadSirDelegateImpl()
}
