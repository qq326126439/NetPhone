package com.lx.net.contact


import com.alibaba.android.arouter.facade.annotation.Route
import com.lx.net.base.fragment.BaseVmFragment
import com.lx.net.router.RouterPath
import retrofit2.http.Path

@Route(path = RouterPath.contactFragment)
class ContactFragment: BaseVmFragment(R.layout.fragment_contact) {
    override fun createObserver() {
    }
}