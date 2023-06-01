package com.lx.net.base.page

import android.content.Context
import android.view.View
import android.widget.TextView
import com.kingja.loadsir.callback.Callback
import com.lx.net.base.R

class EmptyCallback(var tip: String? = null) : Callback() {
    override fun onCreateView(): Int {
        return R.layout.layout_status_empty
    }

    override fun onViewCreate(context: Context?, view: View?) {
        view?.let {
            val tvEmptyTip = it.findViewById<TextView>(R.id.tv_empty_tip)
            tvEmptyTip.text = tip?: context?.getString(R.string.str_none_data)
        }
        super.onViewCreate(context, view)
    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return false
    }

}
