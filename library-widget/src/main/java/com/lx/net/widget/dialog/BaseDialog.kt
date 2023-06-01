package com.lx.net.widget.dialog

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.lx.net.widget.R

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.widget.dialog
 * @org ： 深圳赛为安全技术服务有限公司
 * @date :2022/8/29 15:11
 * @description ：
 */
class BaseDialog internal constructor(builder: Builder) : AppCompatDialogFragment() {

    private var outCancel = builder.outCancel
    private var dimAmount = builder.dimAmount
    private val content = builder.content
    private var convertListener = builder.convertListener
    @LayoutRes
    private var layoutId = builder.layoutId
    @StyleRes
    private var animStyle = builder.animStyle

    private fun convertView(
        holder: ViewHolder,
        dialog: BaseDialog
    ){
         convertListener?.convertView(holder, dialog)
    }

    companion object{
        const val DIM = "dim_amount"
        const val CANCEL = "out_cancel"
        const val ANIM = "anim_style"
        const val LAYOUT = "layout_id"
        const val LISTENER = "listener"
    }

    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.ActionSheetDialogStyle)
        savedState?.let {
            dimAmount = it.getFloat(DIM)
            outCancel = it.getBoolean(CANCEL)
            animStyle = it.getInt(ANIM)
            layoutId = it.getInt(LAYOUT)
            convertListener = it.getParcelable(LISTENER)
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        setDialog()
    }

    /**
     * 设置dialog属性
     */
    private fun setDialog() {
        val height = DisplayMetrics().heightPixels
        val window: Window? = dialog?.window
        val layoutParams = window?.attributes
        layoutParams?.x = 0
        layoutParams?.y = height
        layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        //调节灰色背景透明度[0-1]，默认0.5f
        layoutParams?.dimAmount = dimAmount
        layoutParams?.gravity = Gravity.BOTTOM
        // 设置显示位置
        //设置dialog进入、退出的动画
        window?.setWindowAnimations(animStyle)
        window?.attributes = layoutParams
        isCancelable = outCancel
    }


    /**
     * 屏幕旋转等导致DialogFragment销毁后重建时保存数据
     *
     * @param outState
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putFloat(DIM, dimAmount)
        outState.putBoolean(CANCEL, outCancel)
        outState.putInt(ANIM, animStyle)
        outState.putInt(LAYOUT, layoutId)
        outState.putParcelable(LISTENER, convertListener)
    }

    class Builder {

        internal var outCancel = true //是否点击外部取消
        internal var dimAmount = 0.5f //灰度深浅
        internal var content: View? = null
        internal var convertListener: ViewConvertListener<BaseDialog>? = null
        @LayoutRes
        internal var layoutId = R.layout.dialog_basics
        @StyleRes
        internal var animStyle = R.style.ActionSheetDialogAnimation

        fun outCancel(outCancel: Boolean): Builder {
            this.outCancel = outCancel
            return this
        }

        fun animStyle(@StyleRes animStyle: Int): Builder {
            this.animStyle = animStyle
            return this
        }

        fun dimAmount(dimAmount: Float): Builder {
            this.dimAmount = dimAmount
            return this
        }

        fun contentView(content: View): Builder {
            this.content = content
            return this
        }

        fun layoutId(@LayoutRes layoutId: Int): Builder {
            this.layoutId = layoutId
            return this
        }

        fun convertListener(convertListener: ViewConvertListener<BaseDialog>?): Builder {
            this.convertListener = convertListener
            return this
        }

        fun build(): BaseDialog = BaseDialog(this)

    }

    fun show(manager: FragmentManager?, tag :String): BaseDialog {
        val ft = manager?.beginTransaction()
        val prev = manager?.findFragmentByTag(tag)
        if (prev?.isAdded == true)
            ft?.remove(this)?.commitAllowingStateLoss()
        ft?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft?.add(this, tag)
        ft?.commitAllowingStateLoss()
        return this
    }

}

