package com.lx.net.widget.dialog

import android.view.KeyEvent
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.lx.net.widget.R

/**
 * ********************************************************************
 * @description: 提示弹框
 * @author: pengl
 * @created on: 2022/7/28 0028 14:24
 * @version: 1.0.0
 * @modify time:2022/7/28 0028 14:24
 * ********************************************************************/
class NiceFragmentDialog : BaseDialogFragment() {
    //固定高度
    private var fixedHeight = true
    private var mFlContainer: FrameLayout? = null

    override fun onStart() {
        super.onStart()
        val window = dialog?.window
        window?.decorView?.setPadding(0, 0, 0, 0)
        val lp = window?.attributes
        lp?.width = (metrics.widthPixels * 0.7).toInt()
        if (fixedHeight) {
            lp?.height = (metrics.heightPixels * (1.1f / 5)).toInt()
        }
        window?.attributes = lp
        isCancelable = false
    }

    public override fun findView(view: View) {
        super.findView(view)
        mFlContainer = view.findViewById(R.id.fl_container)
    }

    public override fun initView() {
        super.initView()

    }

    override fun bindEvent() {

    }

    fun setFragment(fragment: Fragment){
        childFragmentManager.beginTransaction().replace(R.id.fl_container,fragment).commitAllowingStateLoss()
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

    override val contentViewId: Int = R.layout.dialog_nice_fragment

    /** 按下返回 */
    override fun onBackPressed(keyCode: Int, event: KeyEvent) {
        if (isAdded) {
            dismissAllowingStateLoss()
        }
    }
}