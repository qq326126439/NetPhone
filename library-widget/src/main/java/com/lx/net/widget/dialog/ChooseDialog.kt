package com.lx.net.widget.dialog

import android.os.Bundle
import androidx.annotation.LayoutRes
import com.lx.net.widget.dialog.BaseChooseDialog
import com.lx.net.widget.dialog.ViewConvertListener

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.widget.dialog
 * @org ： 深圳赛为安全技术服务有限公司
 * @date :2022/8/29 15:11
 * @description ：
 */
class ChooseDialog : BaseChooseDialog() {

    private var convertListener: ViewConvertListener<ChooseDialog>? = null


    companion object {

        fun init(): ChooseDialog {
            return ChooseDialog()
        }
    }


    override fun intLayoutId(): Int {
        return layoutId
    }


    fun setLayoutId(@LayoutRes layoutId: Int): ChooseDialog {
        this.layoutId = layoutId
        return this
    }

    fun setConvertListener(convertListener: ViewConvertListener<ChooseDialog>?): ChooseDialog {
        this.convertListener = convertListener
        return this
    }

    override fun convertView(holder: ViewHolder, dialog: BaseChooseDialog) {
        convertListener?.convertView(holder, dialog as ChooseDialog)
    }

    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)
        savedState?.let {
            convertListener = it.getParcelable("listener")
        }
    }

    /**
     * 保存接口
     *
     * @param outState
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("listener", convertListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        convertListener = null
    }

}