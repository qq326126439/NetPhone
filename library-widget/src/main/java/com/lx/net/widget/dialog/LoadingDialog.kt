package com.lx.net.widget.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.lx.net.widget.R
import com.lx.net.widget.circleprogress.CircleProgressBar

/***********************************************************************
 * <p>@description: 加载中弹框
 * <p>@author: pengl
 * <p>@created on: 2022/7/28 0028 21:08
 *
 * <p>@version: 1.0.0
 * <p>@modify time:2022/7/28 0028 21:08
 **********************************************************************/

class LoadingDialog : BaseDialogFragment() {

    private val INTERVAL_TIME_CANCEL = 1 * 1000

    private var progressBar: CircleProgressBar? = null
    private var dialogText: TextView? = null


    var countDownTimer: CountDownTimer? = null

    var onBackCancelListener: OnBackCancelListener? = null
    var onCountDownListener: OnCountDownListener? = null

    private lateinit var tipText: String


    override fun onStart() {
        super.onStart()
        val win = dialog?.window
        win?.decorView?.setPadding(0, 0, 0, 0)
        val lp = win?.attributes
        lp?.width = (metrics.widthPixels * 0.4).toInt()
        lp?.gravity = Gravity.CENTER
        win?.attributes = lp
        dialog?.setCanceledOnTouchOutside(false)
    }

    /**
     * 设置提示信息
     */
    fun setText(text: String) {
        tipText = text
        dialogText?.text = text
    }

    override val contentViewId = R.layout.dialog_loading

    override fun findView(view: View) {
        super.findView(view)
        state = DialogState.CREATED
        progressBar = view.findViewById(R.id.progressBar)
        dialogText = view.findViewById(R.id.tv_dialog_text)
    }

    override fun initView() {
        super.initView()
        dialogText?.text = tipText
    }

    override fun onBackPressed(keyCode: Int, event: KeyEvent) {
        if (isCancelable) {
            countDownTimer?.cancel()
            countDownTimer = null
            onBackCancelListener?.onBack()
            dismissAllowingStateLoss()
        }
    }

    @JvmName("setOnBackCancelListener1")
    fun setOnBackCancelListener(onBackCancelListener: OnBackCancelListener) {
        this.onBackCancelListener = onBackCancelListener
    }


    interface OnBackCancelListener {
        fun onBack()
    }

    interface OnCountDownListener {
        fun countDown()
    }


    /**
     * 开启定时器
     */
    fun startTimer(listener: OnCountDownListener) {
        onCountDownListener = listener

        countDownTimer?.let {
            it.cancel()
            countDownTimer = null
        }

        countDownTimer = object : CountDownTimer(INTERVAL_TIME_CANCEL.toLong(), 500) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                onCountDownListener?.countDown()
            }
        }
        countDownTimer?.start()
    }

    /**
     * 移除之前的弹框
     */
    override fun removePreDialog(fm: FragmentManager, FTag: String) {
        val prev = fm.findFragmentByTag(FTag)
        prev?.let {
            it as LoadingDialog
            it.countDownTimer?.cancel()
            it.countDownTimer = null

            if (it.state == DialogState.CLOSED || it.state == DialogState.CLOSING) {
                return
            }

            it.dismissAllowingStateLoss()
        }
        super.removePreDialog(fm, FTag)
    }


    /**
     * 创建弹框
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //更新状态
        state = DialogState.OPEN
        return super.onCreateDialog(savedInstanceState)
    }


    /**
     * 销毁弹框
     */
    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        state = DialogState.CLOSED
    }

    /**
     * 销毁弹框
     */
    override fun dismiss() {
        state = DialogState.CLOSED
        onBackCancelListener = null
        onCountDownListener = null
        if (fragmentManager == null) return
        super.dismiss()
    }

    override fun onDestroy() {
        countDownTimer?.cancel()
        countDownTimer = null
        super.onDestroy()
    }


}