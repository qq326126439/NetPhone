package com.lx.net.login.provider

import android.content.Context
import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.lx.net.common.constant.ParamKey
import com.lx.net.common.utils.HseAlcImpl
import com.lx.net.router.ProviderPath
import com.lx.net.router.RouterPath
import com.lx.net.router.provider.ReLoginProvider

@Route(path = ProviderPath.relogin, name = "重新登录")
class ReLoginImpl : ReLoginProvider {

    override fun init(context: Context?) {

    }

    override fun reLogin(tag: String) {
//        ToastUtils.show("令牌失效，重新登录")
        ARouter.getInstance()
            .build(RouterPath.register)
            .withInt(ParamKey.EXTRA_LOGIN_OR_REGISTER,0)
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .navigation(HseAlcImpl.getApplication())
    }

}