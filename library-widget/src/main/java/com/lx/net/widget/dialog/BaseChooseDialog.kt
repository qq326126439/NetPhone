package com.lx.net.widget.dialog

import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.lx.net.common.model.ChooseInfo
import com.lx.net.widget.R

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.widget.dialog
 * @org ： 深圳赛为安全技术服务有限公司
 * @date :2022/8/29 15:11
 * @description ：
 */
abstract class BaseChooseDialog : AppCompatDialogFragment() {

    var data: MutableList<ChooseInfo> = mutableListOf()
        internal set //只允许建造
    var checked: MutableList<Int> = mutableListOf()
        //选中position集合
        internal set
    var multiple = false //是否多选
        internal set
    private var outCancel = true //是否点击外部取消
    private var dimAmount = 0.5f //灰度深浅
//    private var content: View? = null
    private val DIM = "dim_amount"
    private val CANCEL = "out_cancel"
    private val ANIM = "anim_style"
    private val LAYOUT = "layout_id"

    @LayoutRes
    protected var layoutId = R.layout.dialog_choose

    @StyleRes
    protected var animStyle = R.style.ActionSheetDialogAnimation

    abstract fun intLayoutId(): Int

    abstract fun convertView(
        holder: ViewHolder,
        dialog: BaseChooseDialog
    )


    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.ActionSheetDialogStyle)
        savedState?.let {
            dimAmount = it.getFloat(DIM)
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
//        content?.run { root.findViewById<LinearLayout>(R.id.content_view).addView(this) }
        convertView(ViewHolder.create(root), this)
        return root
    }


    override fun onStart() {
        super.onStart()
        setDialog()
    }

    /**
     * 设置dialog属性
     */
    private fun setDialog() {
        val window = dialog?.window
        val layoutParams = window?.attributes
        layoutParams?.x = 0
        layoutParams?.y = 0
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
    }

    fun chooseData(data: MutableList<ChooseInfo>): BaseChooseDialog {
        this.data = data
        return this
    }

    fun checkedData(checked: MutableList<Int>): BaseChooseDialog {
        this.checked = checked
        return this
    }

    fun multiple(multiple: Boolean): BaseChooseDialog {
        this.multiple = multiple
        return this
    }

    fun outCancel(outCancel: Boolean): BaseChooseDialog {
        this.outCancel = outCancel
        return this
    }

    fun animStyle(@StyleRes animStyle: Int): BaseChooseDialog {
        this.animStyle = animStyle
        return this
    }

    fun dimAmount(dimAmount: Float): BaseChooseDialog {
        this.dimAmount = dimAmount
        return this
    }

//    fun contentView(content: View): BaseChooseDialog {
//        this.content = content
//        return this
//    }

    fun show(tag : String, manager: FragmentManager?): BaseChooseDialog {
        val ft = manager?.beginTransaction()
        val prev = manager?.findFragmentByTag(tag)
        if (prev?.isAdded == true)
            ft?.remove(this)?.commitAllowingStateLoss()
//        if (prev is BaseChooseDialog)
//            prev.dismissAllowingStateLoss()
        ft?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft?.add(this, tag)
        ft?.commitAllowingStateLoss()
        return this
    }

}

