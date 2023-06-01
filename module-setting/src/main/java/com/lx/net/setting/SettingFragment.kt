package com.lx.net.setting


import com.alibaba.android.arouter.facade.annotation.Route
import com.lx.net.base.fragment.BaseVmFragment
import com.lx.net.router.RouterPath


@Route(path = RouterPath.settingFragment)
class SettingFragment: BaseVmFragment(R.layout.fragment_setting) {
    override fun createObserver() {

    }
}