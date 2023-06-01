package com.lx.net.message


import com.alibaba.android.arouter.facade.annotation.Route
import com.lx.net.base.fragment.BaseVmFragment
import com.lx.net.router.RouterPath

@Route(path = RouterPath.messageFragment)
class MessageFragment: BaseVmFragment(R.layout.fragment_message) {
    override fun createObserver() {

    }
}