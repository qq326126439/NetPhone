package com.lx.net.widget.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.lx.net.common.utils.DensityUtil
import com.lx.net.common.utils.HseAlcImpl
import com.lx.net.widget.R
import com.lx.net.widget.dialog.OverrideDialogFragment

/***********************************************************************
 * <p>@description: 弹框base
 * <p>@author: pengl
 * <p>@created on: 2022/7/28 0028 11:06
 *
 * <p>@version: 1.0.0
 * <p>@modify time:2022/7/28 0028 11:06
 **********************************************************************/
abstract class BaseDialogFragment : OverrideDialogFragment() {
    var TAG: String = BaseDialogFragment::class.java.simpleName

    protected var mActivity: Activity? = null
    protected var bundle: Bundle? = null

    protected open var state = DialogState.CREATED
    protected val metrics = DensityUtil.getDisplayMetrics(HseAlcImpl.getApplication())

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (mActivity == null ) {
            mActivity = context as Activity
        }
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        if (mActivity == null) {
            mActivity = activity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.NiceDialog)
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            it.setOnKeyListener(DialogInterface.OnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    onBackPressed(keyCode, event)
                    return@OnKeyListener true
                }
                false
            })
            it.window?.let {  win ->
                win.decorView.setPadding(0, 0, 0, 0)
                val lp = win.attributes
                lp?.width = (metrics.widthPixels * 0.8).toInt()
                win.attributes = lp
            }
        }
    }

    /**
     * 创建弹框
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = super.onCreateView(inflater, container, savedInstanceState)
        bundle = arguments
        if (view == null) {
            view = inflater.inflate(contentViewId, container, false)
            findView(view!!)
            initView()
            bindEvent()
        }
        return view
    }

    protected open val contentViewId: Int = 0

    protected open fun findView(view: View) {}
    protected open fun initView() {}
    protected open fun bindEvent() {}

    protected abstract fun onBackPressed(keyCode: Int, event: KeyEvent)//        return isAdded();

    /**
     * 是否正在显示
     *
     * @return
     */
    protected open val isShowing: Boolean
        get() = if (dialog == null) false else dialog?.isShowing == true


    /**
     * 展示弹框
     */
    open fun show(context: Context?, fm: FragmentManager?, FTag: String) {
        //判空
        if (context == null || fm == null) return
        if (context is Activity) {
            //如果Activity正在关闭或者已经销毁 直接返回
            val isRefuse =
                context.isFinishing || context.isDestroyed
            if (!isRefuse) {
                removePreDialog(fm, FTag)
                showAllowingLoss(fm, FTag)
            }
        }
    }

    /**
     * 移除之前的弹框
     */
    open fun removePreDialog(fm: FragmentManager, FTag: String) {
        fm.beginTransaction()
        val prev = fm.findFragmentByTag(FTag)
        prev?.let {
            if (it is BaseDialogFragment) {
                if (state == DialogState.CLOSING || state == DialogState.CLOSED) {
                    return
                }
                it.dismissAllowingStateLoss()
            }
        }
    }

    /**
     * 解决 Can not perform this action after onSaveInstanceState问题
     *
     */
    open fun showAllowingLoss(manager: FragmentManager?, tag: String?) {

        manager?.beginTransaction()?.let {
            it.add(this, tag)
            it.commitAllowingStateLoss()
        }
    }

    /**
     * 防止横竖屏切换时 getFragmentManager置空引起的问题：
     * Attempt to invoke virtual method 'android.app.FragmentTransaction
     * android.app.FragmentManager.beginTransaction()' on a null object reference
     */
    override fun dismiss() {
        if (fragmentManager == null) return
        super.dismissAllowingStateLoss()
    }


    override fun dismissAllowingStateLoss() {
        state = DialogState.CLOSING
        super.dismissAllowingStateLoss()
    }

    protected enum class DialogState {
        CREATED, OPEN, OPENING, CLOSING, CLOSED
    }

    override fun onDestroy() {
        state = DialogState.CLOSED
        super.onDestroy()
    }
}