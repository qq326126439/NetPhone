package com.lx.net.login.api


import com.lx.net.common.constant.Constants
import com.lx.net.library_network.converter.StringConverterFactory
import com.lx.net.library_network.interceptor.LoggerInterceptor
import com.lx.net.library_network.interceptor.ParamsInterceptor
import com.lx.net.library_network.interceptor.TokenInterceptor
import com.lx.net.local.MMKVUtil
import com.lx.net.login.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitService {

    private val BASE_URL by lazy {  MMKVUtil.readString(Constants.API_BASE_URL, "http://192.168.10.45:9988") }
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
//            .addInterceptor(ParamsInterceptor())
//            .addInterceptor(TokenInterceptor())
            .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
            .build()
    }

    fun getApiService(okHttpClient: OkHttpClient = getOkHttpClient()): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL.toString())
            .addConverterFactory(StringConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(ApiService::class.java)
    }


}