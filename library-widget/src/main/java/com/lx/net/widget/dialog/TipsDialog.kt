package com.lx.net.widget.dialog

import android.graphics.drawable.Drawable
import android.text.method.ScrollingMovementMethod
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.lx.net.widget.R

/**
 * ********************************************************************
 * @description: 提示弹框
 * @author: pengl
 * @created on: 2022/7/28 0028 14:24
 * @version: 1.0.0
 * @modify time:2022/7/28 0028 14:24
 * ********************************************************************/
class TipsDialog : BaseDialogFragment() {

    private var mIcon: ImageView? = null
    private var tipsContent: TextView? = null
    private var mButton: Button? = null

    private var confirmDialogListener: TipsConfirmDialogListener? = null
    private var message: String = ""
    private var btnText: String = ""


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
        mIcon = view.findViewById(R.id.mIcon)
        tipsContent = view.findViewById(R.id.tv_message)
        mButton = view.findViewById(R.id.mButton)
    }

    public override fun initView() {
        super.initView()
        tipsContent?.movementMethod = ScrollingMovementMethod.getInstance()
        initData()
    }

    fun initData() {
        tipsContent?.text = message
        mButton?.text = btnText
    }

    override fun bindEvent() {

        mButton?.setOnClickListener {
            confirmDialogListener?.onBtnClick()
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


    override val contentViewId: Int = R.layout.dialog_tips


    //设置图片
    fun setIcon(id: Drawable) {
        mIcon?.setImageDrawable(id)
    }

    /** 设置弹框内容 */
    fun setMessage(message: String) {
        this.message = message
        tipsContent?.text = message
    }

    /** 设置按钮文字 */

    fun setBtnText(text: String) {
        btnText = text
        mButton?.text = text
    }

    interface TipsConfirmDialogListener {
        fun onBtnClick()
    }


    fun setTipsDialogListener(confirmDialogListener: TipsConfirmDialogListener?) {
        this.confirmDialogListener = confirmDialogListener
    }

    /** 按下返回 */
    override fun onBackPressed(keyCode: Int, event: KeyEvent) {
        if (isAdded) {
            dismissAllowingStateLoss()
        }
    }
}