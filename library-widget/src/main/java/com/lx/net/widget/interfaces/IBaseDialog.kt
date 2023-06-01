package com.lx.net.widget.interfaces

/***********************************************************************
 * <p>@description:BaseActivity 对话框，弹框,进度条
 * <p>@author: pengl
 * <p>@created on: 2022/7/28 0028 14:32
 * <p>@version: 1.0.0
 * <p>@modify time:2022/7/28 0028 14:32
 **********************************************************************/
interface IBaseDialog {

    fun showToast(msg: String?)
    fun showToast(resourceID : Int)
    fun cancelToast()

    fun showLoadingDialog(msg: String)
    fun showLoadingDialog(message: String, cancelable: Boolean)

    fun showLoadingDialog()
    fun hideLoadingDialog()

    fun showProgressDialog()
    fun onProgressDialogHint(msg: String)
    fun onProgressDialogProgress(total: Long, current: Long)
    fun hideProgressDialog()

    fun refreshComplete()

}