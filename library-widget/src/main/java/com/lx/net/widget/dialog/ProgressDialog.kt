package com.lx.net.widget.dialog

import android.view.KeyEvent
import com.lx.net.widget.dialog.BaseDialogFragment
import com.lx.net.widget.interfaces.IBaseProgress

/***********************************************************************
 * <p>@description: 暂时未用到
 * <p>@author: pengl
 * <p>@created on: 2022/7/28 0028 15:36
 * <p>@version: 1.0.0
 * <p>@modify time:2022/7/28 0028 15:36
 **********************************************************************/
class ProgressDialog : BaseDialogFragment(), IBaseProgress {


    override fun onBackPressed(keyCode: Int, event: KeyEvent) {

    }

    override fun setText(text: String?) {

    }

    override fun setProgressMax(max: Long) {

    }

    override fun setProgress(progress: Long) {

    }

    override fun getMax(): Long {
        return 0
    }

    override fun getProgress(): Long {
        return 0
    }

    override fun showProgressText(isShow: Boolean) {

    }

    override fun showArrow(isShow: Boolean) {

    }

}