package com.lx.net.base.page

import android.content.Context
import android.view.View
import com.kingja.loadsir.callback.Callback
import com.lx.net.base.R

class LoadingCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_status_loading
    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return true
    }

}
