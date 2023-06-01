package com.lx.net.base.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.LinearLayoutManager
import com.lx.net.base.R
import com.lx.net.common.netstate.NetBus
import com.lx.net.common.netstate.NetTool
import com.lx.net.common.netstate.NetType
import com.lx.net.common.netstate.Network
import com.lx.net.common.toast.ToastUtils
import com.lx.net.common.utils.LogCompat.logE
import com.lx.net.widget.dialog.ConfirmDialog
import com.lx.net.widget.dialog.LoadingDialog
import com.lx.net.widget.dialog.NiceDialog
import com.lx.net.widget.dialog.ProgressDialog
import com.lx.net.widget.dialog.TipsDialog
import com.lx.net.widget.dialog.ViewConvertListener
import com.lx.net.widget.dialog.ViewHolder
import com.lx.net.widget.interfaces.IBaseDialog

/**
 * ********************************************************************
 *
 * @description: 对话框base
 *
 * @author: pengl
 *
 * @created on: 2022/7/27 0028 17:24
 *
 * @version: 1.0.0
 *
 * @modify time:2022/7/28 0028 15:52
 *     ********************************************************************
 */
abstract class BaseDiaActivity : FontScaleAppCompatActivity(), IBaseDialog {

    protected var confirmDialog: ConfirmDialog? = null
    protected var tipsDialog: TipsDialog? = null
    protected var loadingDialog: LoadingDialog? = null
    protected var progressDialog: ProgressDialog? = null
    protected var niceDialog: NiceDialog? = null
    private var isLoadingDialog = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NetBus.registerObserver(this)
    }


    /** 刷新完成 */
    override fun refreshComplete() {}


    /** 销毁弹框 */
    open fun hideConfirmDialog() {
        confirmDialog?.let {
            if (it.isDialogShowing()) {
                it.dismissAllowingStateLoss()
            }
            confirmDialog = null
        }
    }

    /** 销毁提示弹框 */
    open fun hideTipsDialog() {
        tipsDialog?.let {
            if (it.isDialogShowing()) {
                it.dismissAllowingStateLoss()
            }
            tipsDialog = null
        }
    }


    /** 弹框是否存在 */
    open fun isConfirmDialogShow(): Boolean {
        confirmDialog?.let {
            return it.isDialogShowing()
        }
        return false
    }


    /** 显示 正在加载dialog */
    override fun showLoadingDialog(msg: String) {
        hideLoadingDialog()
        loadingDialog = LoadingDialog()
        loadingDialog?.startTimer(object : LoadingDialog.OnCountDownListener {
            override fun countDown() {
                loadingDialog?.isCancelable = false
            }
        })
        loadingDialog?.setText(msg)
        loadingDialog?.setOnBackCancelListener(object : LoadingDialog.OnBackCancelListener {
            override fun onBack() {
                onLoadingDialogOnBackPressed()
            }
        })
        loadingDialog?.show(this, supportFragmentManager, javaClass.simpleName + "LoadingDialog")
        isLoadingDialog = true
    }

    override fun showLoadingDialog() {
        showLoadingDialog("加载中...")
    }

    override fun showToast(resourceID: Int) {
        ToastUtils.show(resourceID)
    }

    override fun showToast(msg: String?) {
        ToastUtils.show(msg)
    }

    override fun cancelToast() {
        ToastUtils.cancel()
    }

    /**
     * 显示正在加载dialog
     *
     * @param message
     */
    override fun showLoadingDialog(message: String, cancelable: Boolean) {
        hideLoadingDialog()
        loadingDialog = LoadingDialog()
        if (cancelable) {
            loadingDialog?.startTimer(object : LoadingDialog.OnCountDownListener {
                override fun countDown() {
                    loadingDialog?.isCancelable = true
                }
            })
        }
        loadingDialog?.setText(message)
        loadingDialog?.setOnBackCancelListener(object : LoadingDialog.OnBackCancelListener {
            override fun onBack() {
                onLoadingDialogOnBackPressed()
            }
        })
        loadingDialog?.show(this, supportFragmentManager, javaClass.simpleName + "LoadingDialog")
        isLoadingDialog = true
    }

    private fun loadingDialog(message: String): LoadingDialog? {
        hideLoadingDialog()
        loadingDialog = LoadingDialog()
        return loadingDialog?.let {
            it.setText(message)
            it.show(this, supportFragmentManager, javaClass.simpleName + "LoadingDialog")
            isLoadingDialog = true
            it
        }
    }

    /** 手动按返回键取消加载进度框 */
    open fun onLoadingDialogOnBackPressed() {}

    /** 显示加载对话框 */
    open fun showConfirmDialog(
        title: String,
        message: String,
        okText: String,
        cancelText: String,
        confirmDialogListener: ConfirmDialog.ConfirmDialogListener
    ) {
        confirmDialog = ConfirmDialog()

        confirmDialog?.setConfirmDialogListener(object : ConfirmDialog.ConfirmDialogListener {
            override fun confirm() {
                confirmDialogListener.confirm()
                hideConfirmDialog()
                confirmDialog = null
            }

            override fun cancel() {
                confirmDialogListener.cancel()
                confirmDialog = null
            }
        })

        confirmDialog?.let {
            it.setTile(title)
            it.setMessage(message)
            it.setOkText(okText)
            it.setCancelText(cancelText)

            if (!it.isAdded) {
                supportFragmentManager.beginTransaction().add(it, TAG).show(it)
                    .commitAllowingStateLoss()
            }
        }
    }

    /** 显示提示对话框 */
    open fun showTipsDialog(
        message: String,
        btnText: String,
        confirmDialogListener: TipsDialog.TipsConfirmDialogListener
    ) {
        tipsDialog = TipsDialog()
        tipsDialog?.setTipsDialogListener(object : TipsDialog.TipsConfirmDialogListener {
            override fun onBtnClick() {
                confirmDialogListener.onBtnClick()
                hideTipsDialog()
                tipsDialog = null
            }
        })

        tipsDialog?.let {
            it.setMessage(message)
            it.setBtnText(btnText)
            if (!it.isAdded) {
                supportFragmentManager.beginTransaction().add(it, TAG).show(it)
                    .commitAllowingStateLoss()
            }
        }
    }


    /** 显示加载对话框 */
    open fun showConfirmDialog(title: String, message: String, confirmDialogListener: ConfirmDialog.ConfirmDialogListener) {
        hideConfirmDialog()
        confirmDialog = ConfirmDialog()
        confirmDialog?.setConfirmDialogListener(object : ConfirmDialog.ConfirmDialogListener {
            override fun confirm() {
                confirmDialogListener.confirm()
                hideConfirmDialog()
                confirmDialog = null
            }

            override fun cancel() {
                confirmDialogListener.cancel()
                confirmDialog = null
            }
        })
        confirmDialog?.let {
            it.setTile(title)
            it.setMessage(message)

            if (!it.isAdded) {
                supportFragmentManager.beginTransaction().add(it, TAG).show(it)
                    .commitAllowingStateLoss()
            }
        }
    }

    open fun showSingleBtnConfirmDialog(
        title: String,
        message: String,
        btnText: String,
        confirmDialogListener: ConfirmDialog.ConfirmDialogListener?
    ) {
        if (confirmDialog == null) {
            confirmDialog = ConfirmDialog()
        } else if (confirmDialog?.isAdded == true) {
            confirmDialog?.dismiss()
            confirmDialog = ConfirmDialog()
        }

        confirmDialog?.setConfirmDialogListener(object : ConfirmDialog.ConfirmDialogListener {
            override fun confirm() {
                confirmDialogListener?.confirm()
                hideConfirmDialog()
                confirmDialog = null
            }

            override fun cancel() {
                confirmDialogListener?.cancel()
                confirmDialog = null
            }
        })

        confirmDialog?.let {
            it.setTile(title)
            it.setMessage(message)
            it.setSingleBtn(true, btnText)

            if (!it.isAdded) {
                supportFragmentManager.beginTransaction().add(it, TAG).show(it)
                    .commitAllowingStateLoss()
            }
        }
    }

    /** 显示加载对话框 */
    open fun showConfirmDialog(
        title: String,
        message: String,
        confirmDialogListener: ConfirmDialog.ConfirmDialogListener,
        cancelVisible: Boolean
    ) {
        confirmDialog = ConfirmDialog()
        confirmDialog?.setConfirmDialogListener(object : ConfirmDialog.ConfirmDialogListener {
            override fun confirm() {
                confirmDialogListener.confirm()
                hideConfirmDialog()
                confirmDialog = null
            }

            override fun cancel() {
                confirmDialogListener.cancel()
                confirmDialog = null
            }
        })

        confirmDialog?.let {
            it.setTile(title)
            it.setMessage(message)
            it.setBtnCancelVisible(cancelVisible)

            if (!it.isAdded) {
                supportFragmentManager.beginTransaction().add(it, TAG).show(it)
                    .commitAllowingStateLoss()
            }
        }
    }

    open fun isConfirmDialogNull(): Boolean {
        return confirmDialog == null
    }

    open fun isShowLoadingDialog(): Boolean {
        return isLoadingDialog
    }

    /** 异常在加载dialog */
    override fun hideLoadingDialog() {
        isLoadingDialog = false
        loadingDialog?.let {
            it.countDownTimer?.cancel()
            it.countDownTimer = null

            try {
                it.dismissAllowingStateLoss()
                it.removePreDialog(
                    supportFragmentManager,
                    javaClass.simpleName + "LoadingDialog"
                )
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                loadingDialog = null
            }
            return
        }

        supportFragmentManager.beginTransaction()
        supportFragmentManager.findFragmentByTag(javaClass.simpleName + "LoadingDialog")?.let {
            if (it is LoadingDialog) {
                it.countDownTimer?.cancel()
                it.countDownTimer = null
                it.dismissAllowingStateLoss()
            }
        }
    }


    /** 显示保存数据的dialog */
    fun showSaveDialog(
        title: String,
        message: String,
        showContent: String,
        confirmDialogListener: ConfirmDialog.ConfirmDialogListener
    ) {

        if (confirmDialog?.isAdded == true) {
            confirmDialog?.dismiss()
        }
        confirmDialog = ConfirmDialog()
        confirmDialog?.setConfirmDialogListener(object : ConfirmDialog.ConfirmDialogListener {
            override fun confirm() {
                if (showContent.isNotEmpty()) {
                    showLoadingDialog(showContent)
                }
                confirmDialogListener.confirm()
                confirmDialog?.dismiss()
                confirmDialog = null
            }

            override fun cancel() {
                confirmDialogListener.cancel()
                confirmDialog?.dismiss()
                confirmDialog = null
            }
        })

        confirmDialog?.let {
            it.setTile(title)
            it.setMessage(message)

            if (!it.isAdded) {
                supportFragmentManager.beginTransaction().add(it, TAG).show(it)
                    .commitAllowingStateLoss()
            }
        }
    }

    /** 显示保存数据的dialog */
    protected open fun showSaveDialog(
        title: String,
        message: String,
        showContent: String,
        cancelable: Boolean,
        confirmDialogListener: ConfirmDialog.ConfirmDialogListener
    ) {
        confirmDialog = ConfirmDialog()
        confirmDialog?.setConfirmDialogListener(object : ConfirmDialog.ConfirmDialogListener {
            override fun confirm() {
                if (showContent.isNotEmpty()) {
                    showLoadingDialog(showContent, cancelable)
                }
                confirmDialogListener.confirm()
                confirmDialog?.dismiss()
                confirmDialog = null
            }

            override fun cancel() {
                confirmDialogListener.cancel()
                confirmDialog?.dismiss()
                confirmDialog = null
            }
        })

        confirmDialog?.let {
            it.setTile(title)
            it.setMessage(message)

            if (!it.isAdded) {
                supportFragmentManager.beginTransaction().add(it, TAG).show(it)
                    .commitAllowingStateLoss()
            }
        }
    }

    protected open fun showSaveDialog(
        title: String,
        message: String,
        showContent: String,
        confirmDialogListener: ConfirmDialog.IConfirmDialogListener
    ) {
        confirmDialog = ConfirmDialog()
        confirmDialog?.setConfirmDialogListener(object : ConfirmDialog.ConfirmDialogListener {
            override fun confirm() {
                if (showContent.isNotEmpty()) {
                    loadingDialog = loadingDialog(showContent)
                }
                confirmDialogListener.confirm(loadingDialog!!)
                confirmDialog?.dismiss()
                confirmDialog = null
            }

            override fun cancel() {
                confirmDialogListener.cancel()
                confirmDialog?.dismiss()
                confirmDialog = null
            }
        })

        confirmDialog?.let {
            it.setTile(title)
            it.setMessage(message)

            if (!it.isAdded) {
                supportFragmentManager.beginTransaction().add(it, TAG).show(it)
                    .commitAllowingStateLoss()
            }
        }
    }

    override fun showProgressDialog() {
        if (progressDialog != null) return
        val transaction = supportFragmentManager.beginTransaction()
        if (progressDialog == null) {
            progressDialog = ProgressDialog()
        }
        supportFragmentManager.executePendingTransactions()

        if (progressDialog?.isAdded == false) {
            transaction.add(progressDialog!!, "ProgressDialog").show(progressDialog!!)
                .commitAllowingStateLoss()
        }
    }

    /**
     * 隐藏进度对话框
     *
     * @author liu_haifang
     * @date 2015-4-10 下午1:59:53
     */
    override fun hideProgressDialog() {
        closeProgressDialog()
    }

    private fun closeProgressDialog() {
        val fragment = supportFragmentManager.findFragmentByTag("ProgressDialog")
        if (fragment != null && fragment is ProgressDialog) {
            try {
                supportFragmentManager.executePendingTransactions()
                if (fragment.isAdded) {
                    fragment.dismissAllowingStateLoss()
                    supportFragmentManager.beginTransaction().remove(fragment)
                        .commitAllowingStateLoss()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        progressDialog?.dismissAllowingStateLoss()
        progressDialog = null
    }

    /** 更新提示语 */
    override fun onProgressDialogHint(msg: String) {
        progressDialog?.setText(msg)
    }

    /** 更新进度 */
    override fun onProgressDialogProgress(total: Long, current: Long) {
        progressDialog?.setProgress(current)
        progressDialog?.setProgressMax(total)
    }


    @Network(netType = NetType.AUTO)
    open fun network(netType: NetType?) {
        when (netType) {
            NetType.WIFI -> {
                ("当前有网络 WIFi").logE(TAG)
                showNetState(true)
                if (NetTool.isWifiProxy()) {
                    showToast("当前wifi设置代理,请确保能够访问互联网")
                }
            }
            NetType.NET2G -> {
                ("当前有网络 2G").logE(TAG)
                showNetState(true)
            }
            NetType.NET3G -> {
                ("当前有网络 3G").logE(TAG)
                showNetState(true)
            }
            NetType.NET4G -> {
                ("当前有网络 4G").logE(TAG)
                showNetState(true)
            }
            NetType.NET5G -> {
                ("当前有网络 5G").logE(TAG)
                showNetState(true)
            }
            NetType.NET_UNKNOWN -> {
                ("当前有网络 网络类型未归类").logE(TAG)
                showNetState(true)
            }
            NetType.NONE -> {
                showNetState(false)
                ("当前没有网络").logE(TAG)
            }
            else -> {
                showNetState(false)
                ("当前没有网络").logE(TAG)
            }
        }
    }

    protected open fun showNetState(hasNet: Boolean) {}

    open fun showSingleInstanceConfirmDialog(
        title: String,
        message: String,
        okText: String,
        cancelText: String,
        confirmDialogListener: ConfirmDialog.ConfirmDialogListener
    ) {
        if (confirmDialog == null) {
            confirmDialog = ConfirmDialog()
        }
        confirmDialog?.setConfirmDialogListener(object : ConfirmDialog.ConfirmDialogListener {
            override fun confirm() {
                confirmDialogListener.confirm()
                confirmDialog?.dismiss()
                confirmDialog = null
            }

            override fun cancel() {
                confirmDialogListener.cancel()
                confirmDialog?.dismiss()
                confirmDialog = null
            }
        })

        confirmDialog?.let {
            it.setTile(title)
            it.setMessage(message)
            it.setOkText(okText)
            it.setCancelText(cancelText)
            if (it.isAdded) {
                supportFragmentManager.beginTransaction().remove(it).commit()
                supportFragmentManager.beginTransaction().add(it, TAG).commitAllowingStateLoss()
            }
        }
    }


    open fun showNiceLoadingDialog(title: String? = null, message: String? = null) {

        niceDialog = NiceDialog.init()
        niceDialog?.convertListener(object : ViewConvertListener<NiceDialog>() {
                @SuppressLint("SetTextI18n")
                override fun convertView(
                    holder: ViewHolder,
                    dialog: NiceDialog
                ) {
                    holder.setText(R.id.tv_title,
                        title ?: "标题")
                    holder.setText(R.id.tv_message , message?: "加载中")
                    holder.setGone(R.id.tv_cancel , true)
                    holder.setGone(R.id.tv_confirm , true)
                    holder.setOnClickListener(
                        R.id.tv_cancel
                    ) {
                        dialog.dismiss()
                    }
                    holder.setOnClickListener(
                        R.id.tv_confirm
                    ) {
                        dialog.dismiss()
                    }
                }
            })
            ?.dimAmount(dimAmount = 0.3f) //调节灰色背景透明度[0-1]，默认0.5f
            ?.margin(margin = 20)
            ?.showBottom(showBottom = false) //是否在底部显示dialog，默认false
            ?.outCancel(outCancel = true) //点击dialog外是否可取消，默认true
            ?.animStyle(animStyle = R.style.ActionSheetDialogAnimation)
            ?.show("${this::class.java.simpleName}showInputOther", supportFragmentManager)
    }

    open fun closeNiceDialog(){
         niceDialog?.dismiss()
         niceDialog = null
    }


    override fun onDestroy() {
        super.onDestroy()
        NetBus.unregisterObserver(this)
    }

}