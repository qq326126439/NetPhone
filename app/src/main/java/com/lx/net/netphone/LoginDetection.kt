package com.lx.net.netphone

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.lx.net.common.constant.ParamKey.EXTRA_LOGIN_CHECK
import com.lx.net.common.utils.LogCompat.logI
import com.lx.net.router.RouterPath

/**
 * @author : lxm
 * @version ：
 * @package_name ：cc.crrcgc.hse
 * @org ： 深圳赛为安全技术服务有限公司
 * @date :2022/8/29 15:11
 * @description ：
 */
@Interceptor(priority = 2, name = "登录检测")
class LoginDetection : IInterceptor{

    override fun init(context: Context?) {
        ("LoginDetection  拦截器").logI("LoginDetection")
    }

    override fun process(postcard: Postcard?, callback: InterceptorCallback?) {
        when(postcard?.path){
            RouterPath.register, RouterPath.mainMenu ->{
                if(postcard.extras.getBoolean(EXTRA_LOGIN_CHECK)){
                    ("LoginDetection 拦截器   线程名称:${Thread.currentThread().name}").logI("LoginDetection")
                    callback?.onInterrupt(Throwable("登录检测拦截"))
                }else{
                    callback?.onContinue(postcard)
                }
            }
            else ->{
                callback?.onContinue(postcard)
            }
        }
    }

}