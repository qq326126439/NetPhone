package com.lx.net.base.api

import com.lx.net.base.BuildConfig
import com.lx.net.common.constant.Constants
import com.lx.net.library_network.converter.StringConverterFactory
import com.lx.net.library_network.interceptor.LoggerInterceptor
import com.lx.net.library_network.interceptor.ParamsInterceptor
import com.lx.net.library_network.interceptor.TokenInterceptor
import com.lx.net.local.MMKVUtil
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.login.api
 * @org ： 深圳赛为安全技术服务有限公司
 * @date :2022/8/29 15:11
 * @description ：
 */
object RetrofitService {

    private val BASE_URL by lazy {  MMKVUtil.readString(Constants.API_BASE_URL, "http://safedriver.cn:9010/") }
//    private val BASE_URL = "http://192.168.8.7:8088/"
    private const val TIMEOUT_CONNECTION = 10L
    private const val TIMEOUT_READ = 10L
    private const val TIMEOUT_WRITE = 10L


    private fun getOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().also {
            it.level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .addInterceptor(LoggerInterceptor(logging))
            .addInterceptor(TokenInterceptor())
            .addInterceptor(ParamsInterceptor())
            .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
            .build()
    }

    fun getApiService(okHttpClient: OkHttpClient = getOkHttpClient()): IDownloadFileService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL.toString())
//            .baseUrl("http://safedriver.cn:9091")
            .addConverterFactory(StringConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(IDownloadFileService::class.java)
    }


}