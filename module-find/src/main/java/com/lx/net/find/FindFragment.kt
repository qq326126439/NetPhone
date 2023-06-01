package com.lx.net.find


import com.alibaba.android.arouter.facade.annotation.Route
import com.lx.net.base.fragment.BaseVmFragment
import com.lx.net.router.RouterPath


@Route(path = RouterPath.findFragment)
class FindFragment: BaseVmFragment(R.layout.fragment_find) {
    override fun createObserver() {

    }
}