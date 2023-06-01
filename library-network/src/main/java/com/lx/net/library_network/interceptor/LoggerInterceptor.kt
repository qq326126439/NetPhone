package com.lx.net.library_network.interceptor

import com.lx.net.library_network.ext.containMethodAnnotation
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.Multipart
import retrofit2.http.Streaming

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.network.interceptor
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022/7/12 18:00
 * @description ：文件下载不打印body 否则会卡主线程
 */
class LoggerInterceptor(private val httpLogger : HttpLoggingInterceptor) : Interceptor {

    companion object {
        private val basicLogger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        //包含[Streaming]注解，说明是文件下载
        if (request.containMethodAnnotation(Streaming::class.java)
            || request.containMethodAnnotation(Multipart::class.java)) {
            return basicLogger.intercept(chain)
        }
        return httpLogger.intercept(chain)
    }

}
