package com.lx.net.base.fragment

import android.annotation.SuppressLint
import android.text.method.ScrollingMovementMethod
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import com.lx.net.base.R
import com.lx.net.common.toast.ToastUtils
import com.lx.net.widget.dialog.*
import com.lx.net.widget.interfaces.IBaseDialog


/***********************************************************************
 * <p>@description: 弹框 base fragment
 * <p>@author: pengl
 * <p>@created on: 2022/7/30 0030 18:12
 * <p>@version: 1.0.0
 * <p>@modify time:2022/7/30 0030 18:12
 **********************************************************************/
abstract class BaseDiaFragment constructor(
    @LayoutRes contentLayoutId: Int = 0
)  : Fragment(contentLayoutId), IBaseDialog {

    protected val TAG = this.javaClass.simpleName
    protected val dialog by lazy { ConfirmDialog() }
    protected var confirmDialog: ConfirmDialog? = null
    protected var loadingDialog: LoadingDialog? = null
    protected var progressDialog: ProgressDialog? = null
    protected var niceDialog: NiceDialog? = null
    private var isLoadingDialog = false

    /**
     * 显示对话框
     */

    open fun showConfirmDialog(
        title: String,
        message: String,
        confirmDialogListener: ConfirmDialog.ConfirmDialogListener,
        fixedDialogHeight: Boolean
    ) {
        hideConfirmDialog()
        confirmDialog = ConfirmDialog()
        confirmDialog?.setFixedHeight(fixedDialogHeight)
        confirmDialog?.setConfirmDialogListener(object : ConfirmDialog.ConfirmDialogListener {
            override fun confirm() {
                confirmDialogListener.confirm()
                hideConfirmDialog()
            }

            override fun cancel() {
                confirmDialogListener.cancel()
            }
        })

        confirmDialog?.let {
            it.setTile(title)
            it.setMessage(message)

            if (!it.isAdded) {
                childFragmentManager.beginTransaction().add(it, tag).show(it)
                    .commitAllowingStateLoss()
            }
        }
    }

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
                childFragmentManager.beginTransaction().add(it, TAG).show(it)
                    .commitAllowingStateLoss()
            }
        }
    }

    /**
     * 显示加载对话框
     */
    open fun showConfirmDialog1(
        title: String,
        message: String,
        confirmDialogListener: ConfirmDialog.ConfirmDialogListener,
        text: Boolean
    ) {
        hideConfirmDialog()

        dialog.setConfirmDialogListener(object : ConfirmDialog.ConfirmDialogListener {
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

        dialog.let {
            it.setTile(title)
            it.setMessage(message)

            if (!it.isAdded) {
                childFragmentManager.beginTransaction().add(it, tag).show(it)
                    .commitAllowingStateLoss()
            }
        }
    }


    /**
     * 显示加载对话框
     */
    open fun showConfirmDialog(
        title: String, message: String, confirmDialogListener: ConfirmDialog.ConfirmDialogListener
    ) {
        hideConfirmDialog()

        dialog.setConfirmDialogListener(object : ConfirmDialog.ConfirmDialogListener {
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
                childFragmentManager.beginTransaction().add(it, tag).show(it)
                    .commitAllowingStateLoss()
            }
        }
    }


    /**
     * 单个按钮的对话框
     */
    open fun showSingleBtnConfirmDialog(
        title: String,
        message: String,
        btnText: String,
        confirmDialogListener: ConfirmDialog.ConfirmDialogListener?
    ) {
        hideConfirmDialog()
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
                childFragmentManager.beginTransaction().add(it, tag).show(it)
                    .commitAllowingStateLoss()
            }
        }
    }


    /**
     * 销毁弹框
     */
    open fun hideConfirmDialog() {
        confirmDialog?.let {
            if (it.isDialogShowing()) {
                it.dismissAllowingStateLoss()
            }
            confirmDialog = null
        }
    }


    /**
     * 显示吐司
     */
    override fun showToast(msg: String?) {
        ToastUtils.show(msg)
    }


    /**
     * 显示吐司
     */
    override fun showToast(resourceID: Int) {
        ToastUtils.show(resourceID)
    }

    override fun cancelToast() {
        ToastUtils.cancel()
    }


    /**
     * 显示加载中弹框
     */
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
        loadingDialog?.show(activity, childFragmentManager, javaClass.simpleName + "LoadingDialog")
        isLoadingDialog = true
    }


    /**
     * 显示加载中弹框
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
        loadingDialog?.show(activity, childFragmentManager, javaClass.simpleName + "LoadingDialog")
        isLoadingDialog = true
    }


    /**
     * 显示加载中弹框
     */
    override fun showLoadingDialog() {
        showLoadingDialog("加载中...")
    }


    /**
     * 隐藏加载中弹框
     */
    override fun hideLoadingDialog() {
        isLoadingDialog = false
        loadingDialog?.let {
            it.countDownTimer?.cancel()
            it.countDownTimer = null

            try {
                it.dismissAllowingStateLoss()
                it.removePreDialog(
                    childFragmentManager,
                    javaClass.simpleName + "LoadingDialog"
                )
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                loadingDialog = null
            }
            return
        }

        childFragmentManager.beginTransaction()
        childFragmentManager.findFragmentByTag(javaClass.simpleName + "LoadingDialog")?.let {
            if (it is LoadingDialog) {
                it.countDownTimer?.cancel()
                it.countDownTimer = null
                it.dismissAllowingStateLoss()
            }
        }
    }


    /**
     * 显示进度条弹框
     */
    override fun showProgressDialog() {
        if (progressDialog != null) return
        val transaction = childFragmentManager.beginTransaction()
        if (progressDialog == null) {
            progressDialog = ProgressDialog()
        }
        childFragmentManager.executePendingTransactions()

        if (progressDialog?.isAdded == false) {
            transaction.add(progressDialog!!, "ProgressDialog").show(progressDialog!!)
                .commitAllowingStateLoss()
        }
    }


    /**
     * 进度条弹框提示
     */
    override fun onProgressDialogHint(msg: String) {
        progressDialog?.setText(msg)
    }


    /**
     * 进度条弹框百分比
     */
    override fun onProgressDialogProgress(total: Long, current: Long) {
        progressDialog?.setProgress(current)
        progressDialog?.setProgressMax(total)
    }

    /**
     * 隐藏进度条弹框
     */
    override fun hideProgressDialog() {
        closeProgressDialog()
    }


    /**
     * 刷新完成
     */
    override fun refreshComplete() {

    }


    /**
     * 关闭进度条弹框
     */
    private fun closeProgressDialog() {
        val fragment = childFragmentManager.findFragmentByTag("ProgressDialog")
        if (fragment != null && fragment is ProgressDialog) {
            try {
                childFragmentManager.executePendingTransactions()
                if (fragment.isAdded) {
                    fragment.dismissAllowingStateLoss()
                    childFragmentManager.beginTransaction().remove(fragment)
                        .commitAllowingStateLoss()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        progressDialog?.dismissAllowingStateLoss()
        progressDialog = null
    }


    open fun showNiceLoadingDialog(title: String? = null, message: String? = null) {

        niceDialog = NiceDialog.init()
        niceDialog?.convertListener(object : ViewConvertListener<NiceDialog>() {
            @SuppressLint("SetTextI18n")
            override fun convertView(
                holder: ViewHolder,
                dialog: NiceDialog
            ) {
                val tvMessage = holder.getView<AppCompatTextView>(R.id.tv_message)
                holder.setText(R.id.tv_title,
                    title ?: "标题")
                tvMessage?.text = message?: "加载中"
                tvMessage?.movementMethod = ScrollingMovementMethod()
                holder.setGone(R.id.line_horizontal,true)
                holder.setGone(R.id.tv_cancel,true)
                holder.setGone(R.id.tv_confirm,true)
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
            ?.show("${this::class.java.simpleName}showInputOther", requireActivity().supportFragmentManager)
    }

    open fun closeNiceDialog(){
        niceDialog?.dismiss()
        niceDialog = null
    }



    /**
     * 手动按返回键取消加载进度框
     */
    open fun onLoadingDialogOnBackPressed() {}
}