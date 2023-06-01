package com.lx.net.widget.dialog

import android.util.SparseArray
import android.view.View
import android.widget.TextView
import androidx.annotation.*

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.widget.dialog
 * @org ： 深圳赛为安全技术服务有限公司
 * @date :2022/8/29 15:11
 * @description ：
 */
class ViewHolder private constructor(view: View) {

    private var convertView: View = view
    private val views: SparseArray<View?> = SparseArray()

    @Suppress("UNCHECKED_CAST")
    fun <T : View> getView(@IdRes viewId: Int): T? {
        var view = views.get(viewId)
        if (view == null) {
            view = convertView.findViewById<T>(viewId)
            views.put(viewId, view)
            return view
        }
        return view as T?
    }

    fun getConvertView(): View {
        return convertView
    }

    fun setText(@IdRes viewId: Int, text: CharSequence?) {
        val textView = getView<TextView>(viewId)
        textView?.text = text
    }

    fun setText(@IdRes viewId: Int, @StringRes textId: Int) {
        val textView = getView<TextView>(viewId)
        textView?.setText(textId)
    }

    fun setTextColor(@IdRes viewId: Int, @ColorInt colorId: Int) {
        val textView = getView<TextView>(viewId)
        textView?.setTextColor(colorId)
    }

    fun setOnClickListener(
        @IdRes viewId: Int,
        clickListener: View.OnClickListener?
    ) {
        val view = getView<View>(viewId)
        view?.setOnClickListener(clickListener)
    }

    fun setBackgroundResource(@IdRes viewId: Int, @DrawableRes resId: Int) {
        val view = getView<View>(viewId)
        view?.setBackgroundResource(resId)
    }

    fun setBackgroundColor(@IdRes viewId: Int, @ColorInt colorId: Int) {
        val view = getView<View>(viewId)
        view?.setBackgroundColor(colorId)
    }

    fun setVisible(@IdRes viewId: Int, isVisible: Boolean): ViewHolder {
        val view = getView<View>(viewId)
        view?.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
        return this
    }

    fun setGone(@IdRes viewId: Int, isGone: Boolean): ViewHolder {
        val view = getView<View>(viewId)
        view?.visibility = if (isGone) View.GONE else View.VISIBLE
        return this
    }

    fun setEnabled(@IdRes viewId: Int, isEnabled: Boolean): ViewHolder {
        getView<View>(viewId)?.isEnabled = isEnabled
        return this
    }

    companion object {
        fun create(view: View): ViewHolder {
            return ViewHolder(view)
        }
    }

}