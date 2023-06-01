package com.lx.net.widget.dialog

import android.os.Bundle
import androidx.annotation.LayoutRes
import com.lx.net.widget.dialog.ViewConvertListener

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.widget.dialog
 * @org ： 深圳赛为安全技术服务有限公司
 * @date :2022/8/29 15:11
 * @description ：
 */
class NiceDialog : BaseNiceDialog() {

    private var convertListener: ViewConvertListener<NiceDialog>? = null

    companion object {
        fun init(): NiceDialog {
            return NiceDialog()
        }
    }

    override fun intLayoutId(): Int {
        return layoutId
    }

    override fun convertView(holder: ViewHolder, dialog: BaseNiceDialog) {
        convertListener?.convertView(holder, dialog as NiceDialog)
    }

    fun layoutId(@LayoutRes layoutId: Int): NiceDialog {
        this.layoutId = layoutId
        return this
    }

    fun convertListener(convertListener: ViewConvertListener<NiceDialog>?): NiceDialog {
        this.convertListener = convertListener
        return this
    }

    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)
        if (savedState != null) {
            convertListener = savedState.getParcelable("listener")
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