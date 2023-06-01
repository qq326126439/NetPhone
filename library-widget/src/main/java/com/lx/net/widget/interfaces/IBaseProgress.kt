package com.lx.net.widget.interfaces

/***********************************************************************
 * <p>@description:进度条功能
 * <p>@author: pengl
 * <p>@created on: 2022/7/28 0028 15:37
 * <p>@version: 1.0.0
 * <p>@modify time:2022/7/28 0028 15:37
 **********************************************************************/
interface IBaseProgress {

    /**
     * 设置提示信息
     */
    fun setText(text: String?)

    /**
     * 设置最大的值
     */
    fun setProgressMax(max: Long)


    /**
     * 设置当前进度
     */
    fun setProgress(progress: Long)


    /**
     * 获取进度的最大值
     */
    fun getMax(): Long

    /**
     * 获取progressbar
     */
    fun getProgress(): Long

    /**
     * 设置进度是否可见
     */
    fun showProgressText(isShow: Boolean)

    /**
     * 是否显示箭头
     */
    fun showArrow(isShow: Boolean)

    /**
     * 显示
     */

    /**
     * 是否添加
     */
    fun isAdded(): Boolean

    /**
     * 隐藏
     */
    fun dismiss()
}