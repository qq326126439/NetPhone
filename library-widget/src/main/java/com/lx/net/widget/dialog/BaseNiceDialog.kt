package com.lx.net.widget.dialog

import android.content.DialogInterface.OnDismissListener
import android.content.DialogInterface.OnCancelListener
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.lx.net.common.utils.DensityUtil
import com.lx.net.common.utils.HseAlcImpl
import com.lx.net.widget.R

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.widget.dialog
 * @org ： 深圳赛为安全技术服务有限公司
 * @date :2022/8/29 15:11
 * @description ：
 */
abstract class BaseNiceDialog : AppCompatDialogFragment() {

    private val MARGIN = "margin"
    private val WIDTH = "width"
    private val HEIGHT = "height"
    private val DIM = "dim_amount"
    private val BOTTOM = "show_bottom"
    private val CANCEL = "out_cancel"
    private val ANIM = "anim_style"
    private val LAYOUT = "layout_id"
    private var content: View? = null
    private var margin = 0 //左右边距
    private var width = 0 //宽度
    private var height = 0 //高度
    private var dimAmount = 0.5f //灰度深浅
    private var showBottom = false //是否底部显示
    private var outCancel = true //是否点击外部取消
    private var onCancelListener: OnCancelListener? = null
    private var onDismissListener: OnDismissListener? = null

    @StyleRes
    private var animStyle = 0

    @LayoutRes
    protected var layoutId = R.layout.dialog_common

    abstract fun intLayoutId(): Int

    abstract fun convertView(holder: ViewHolder, dialog: BaseNiceDialog)

    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.NiceDialog)
        layoutId = intLayoutId()
        //恢复保存的数据
        savedState?.let {
            margin = it.getInt(MARGIN)
            width = it.getInt(WIDTH)
            height = it.getInt(HEIGHT)
            dimAmount = it.getFloat(DIM)
            showBottom = it.getBoolean(BOTTOM)
            outCancel = it.getBoolean(CANCEL)
            animStyle = it.getInt(ANIM)
            layoutId = it.getInt(LAYOUT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = layoutInflater.inflate(layoutId, null)
        content?.run { root.findViewById<LinearLayout>(R.id.content_view).addView(this) }
        convertView(ViewHolder.create(root), this)
        return root
    }

    override fun onStart() {
        super.onStart()
        initParams()
    }

    /**
     * 屏幕旋转等导致DialogFragment销毁后重建时保存数据
     *
     * @param outState
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(MARGIN, margin)
        outState.putInt(WIDTH, width)
        outState.putInt(HEIGHT, height)
        outState.putFloat(DIM, dimAmount)
        outState.putBoolean(BOTTOM, showBottom)
        outState.putBoolean(CANCEL, outCancel)
        outState.putInt(ANIM, animStyle)
        outState.putInt(LAYOUT, layoutId)
    }

    private fun initParams() {
        val window = dialog?.window
        window?.let {
            val layoutParams = window.attributes
            //调节灰色背景透明度[0-1]，默认0.5f
            layoutParams.dimAmount = dimAmount
            //是否在底部显示
            if (showBottom) {
                layoutParams.gravity = Gravity.BOTTOM
                if (animStyle == 0)
                    animStyle = R.style.DefaultAnimation
            }
            //设置dialog宽度
            when (width) {
                0 -> {
                    context?.resources?.displayMetrics?.widthPixels?.let {
                        layoutParams.width =
                            it - 2 * DensityUtil.dip2px(
                                HseAlcImpl.getApplication(),
                                margin.toFloat()
                            )
                    }

                }
                in Int.MIN_VALUE..0 -> {
                    layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
                }
                else -> {
                    layoutParams.width =
                        DensityUtil.dip2px(HseAlcImpl.getApplication(), width.toFloat())
                }
            }
            //设置dialog高度
            if (height == 0)
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            else
                layoutParams.height =
                    DensityUtil.dip2px(HseAlcImpl.getApplication(), height.toFloat())
            //设置dialog进入、退出的动画
            window.setWindowAnimations(animStyle)
            window.attributes = layoutParams
        }
//        this.dialog?.setOnCancelListener { onCancelListener?.onCancel(it) }
//        this.dialog?.setOnDismissListener { onDismissListener?.onDismiss(it) }
        isCancelable = outCancel
    }

    fun margin(margin: Int): BaseNiceDialog {
        this.margin = margin
        return this
    }

    fun width(width: Int): BaseNiceDialog {
        this.width = width
        return this
    }

    fun height(height: Int): BaseNiceDialog {
        this.height = height
        return this
    }

    fun contentView(content: View): BaseNiceDialog {
        this.content = content
        return this
    }

    fun dimAmount(dimAmount: Float): BaseNiceDialog {
        this.dimAmount = dimAmount
        return this
    }

    fun showBottom(showBottom: Boolean): BaseNiceDialog {
        this.showBottom = showBottom
        return this
    }

    fun onCancel(onCancelListener: OnCancelListener): BaseNiceDialog {
        this.onCancelListener = onCancelListener
        return this
    }

    fun onDismiss(onDismissListener: OnDismissListener): BaseNiceDialog {
        this.onDismissListener = onDismissListener
        return this
    }

    fun outCancel(outCancel: Boolean): BaseNiceDialog {
        this.outCancel = outCancel
        return this
    }

    fun animStyle(@StyleRes animStyle: Int): BaseNiceDialog {
        this.animStyle = animStyle
        return this
    }

    fun show(tag: String, manager: FragmentManager?): BaseNiceDialog {
        val ft = manager?.beginTransaction()
        val prev = manager?.findFragmentByTag(tag)
        if (prev?.isAdded == true)
            ft?.remove(this)
        ft?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft?.add(this, tag)
        ft?.commitAllowingStateLoss()
        return this
    }

}