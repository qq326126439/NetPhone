package com.lx.net.widget.dialog

import android.text.method.ScrollingMovementMethod
import android.view.KeyEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.lx.net.widget.R

/***********************************************************************
 * <p>@description: 确定取消弹框
 * <p>@author: pengl
 * <p>@created on: 2022/7/28 0028 14:24
 *
 * <p>@version: 1.0.0
 * <p>@modify time:2022/7/28 0028 14:24
 **********************************************************************/
class ConfirmDialog : BaseDialogFragment() {

    private var dialogText: TextView? = null
    private var dialogMessage: TextView? = null
    private var btnOk: TextView? = null
    private var btnCancel: TextView? = null
//    private var divider: View? = null
    private var doubleBtnView: LinearLayout? = null
    private var singleBtn: TextView? = null

    private var titleText: String = ""
    private var message: String = ""
    private var okText: String = ""
    private var cancelText: String = ""
    private var isSingleBtn = false

    private var confirmDialogListener: ConfirmDialogListener? = null

    //固定高度
    private var fixedHeight = true

    override fun onStart() {
        super.onStart()
        val window = dialog?.window
        window?.decorView?.setPadding(0, 0, 0, 0)
        val lp = window?.attributes
        lp?.width = (metrics.widthPixels * 0.8).toInt()
        if (fixedHeight) {
            lp?.height = (metrics.heightPixels * (1.2f / 4)).toInt()
        }
        window?.attributes = lp
        isCancelable = false
    }

    public override fun findView(view: View) {
        super.findView(view)
        dialogText = view.findViewById(R.id.tv_title)
        dialogMessage = view.findViewById(R.id.tv_message)
        btnOk = view.findViewById(R.id.btn_ok) as TextView
        btnCancel = view.findViewById(R.id.btn_cancle)
        doubleBtnView = view.findViewById(R.id.doubleBtn)
        singleBtn = view.findViewById(R.id.singleBtn)
//        divider = view.findViewById(R.id.divider)
    }

    fun initData(){
        dialogText?.text = titleText
        dialogMessage?.text = message
        if (okText.isNotEmpty()){
            btnOk?.text = okText
        }
        if (cancelText.isNotEmpty()){
            btnCancel?.text = cancelText
        }

        if (isSingleBtn) {
            doubleBtnView?.visibility = View.GONE
            singleBtn?.visibility = View.VISIBLE
            singleBtn?.text = okText
        }
    }
    public override fun initView() {
        super.initView()
        dialogMessage?.movementMethod = ScrollingMovementMethod.getInstance()
        initData()
    }

    override fun bindEvent() {

        btnOk?.setOnClickListener {
            confirmDialogListener?.confirm()
            if (isShowing) {
                dismissAllowingStateLoss()
            }
        }

        btnCancel?.setOnClickListener {
            confirmDialogListener?.cancel()
            if (isShowing) {
                dismissAllowingStateLoss()
            }
        }

        singleBtn?.setOnClickListener {
            confirmDialogListener?.confirm()
            if (isShowing) {
                dismissAllowingStateLoss()
            }
        }
    }

    override val isShowing: Boolean
        get() {
            if (mActivity?.isFinishing == false && dialog?.isShowing == true) {
                return true
            }
            return false
        }

    fun isDialogShowing(): Boolean {
        return isShowing
    }

    fun setFixedHeight(fixedHeight: Boolean) {
        this.fixedHeight = fixedHeight
    }

    fun setBtnCancelVisible(visible: Boolean) {
        btnCancel?.visibility = if (visible) View.VISIBLE else View.GONE
//        divider?.visibility = if (visible) View.VISIBLE else View.GONE
    }


    override val contentViewId: Int = R.layout.dialog_confirm


    /**
     * 设置提示信息
     */
    fun setTile(text: String) {
        titleText = text
        dialogText?.text = text
    }

    /**
     * 设置弹框内容
     */
    fun setMessage(message: String) {
        this.message = message
        dialogMessage?.text = message
    }

    interface ConfirmDialogListener {
        fun confirm()
        fun cancel()
    }

    abstract class IConfirmDialogListener : ConfirmDialogListener {
        override fun confirm() {}
        override fun cancel() {}
        abstract fun confirm(loadingDialog: LoadingDialog)
    }

    /**
     * 设置确定按钮文字
     */
    fun setSingleBtn(single: Boolean, btnText: String) {
        okText = btnText
        isSingleBtn = single
        if (single) {
            doubleBtnView?.visibility = View.GONE
            singleBtn?.visibility = View.VISIBLE
            singleBtn?.text = btnText
        }
    }

    /**
     * 设置确定按钮文字
     */
    fun setOkText(text: String) {
        okText = text
        if (text.length > 4) {
            val params = btnOk?.layoutParams as LinearLayout.LayoutParams
            params.weight = 2f
            btnOk?.layoutParams = params
        }
        btnOk?.text = text
    }

    fun setCancelText(text: String) {
        cancelText = text
        btnCancel?.text = text
    }

    fun setConfirmDialogListener(confirmDialogListener: ConfirmDialogListener?) {
        this.confirmDialogListener = confirmDialogListener
    }

    /**
     * 按下返回
     */
    override fun onBackPressed(keyCode: Int, event: KeyEvent) {
        if (isAdded) {
            confirmDialogListener?.cancel()
            dismissAllowingStateLoss()
        }
    }
}