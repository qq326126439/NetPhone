package com.lx.net.library_network.interceptor

import com.lx.net.local.MMKVUtil
import com.orhanobut.logger.Logger
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response

class ParamsInterceptor : Interceptor {

    private val TAG = ParamsInterceptor::class.java.simpleName

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = MMKVUtil.readString("user_token", "")
        val originalRequest = chain.request()
        return if (token?.isEmpty() == true) {
            Logger.t(TAG).i("token.isEmpty originalRequest $originalRequest")
            chain.proceed(originalRequest)
        }else {
            val updateRequest = originalRequest.newBuilder().addHeader("Authorization", token!!).build()
            Logger.t(TAG).i("originalRequest $originalRequest token $token")
            chain.proceed(updateRequest)
        }
    }

}