package com.lx.net.library_network.interceptor

import com.lx.net.library_network.ext.containMethodAnnotation
import com.alibaba.android.arouter.launcher.ARouter
import com.lx.net.router.provider.ReLoginProvider
import com.orhanobut.logger.Logger
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.http.Multipart
import retrofit2.http.Streaming
import java.lang.Exception
import java.nio.charset.Charset

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.network.interceptor
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022/6/16 10:13
 * @description ：token拦截器
 */
class TokenInterceptor : Interceptor {

    private var relogin : ReLoginProvider? = null
    private val TAG = TokenInterceptor::class.java.simpleName

    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        //包含[Streaming]注解，说明是文件下载
        if (originalRequest.containMethodAnnotation(Streaming::class.java)
            || originalRequest.containMethodAnnotation(Multipart::class.java)) {
            return chain.proceed(originalRequest)
        }
        val response = chain.proceed(originalRequest)
         if(isTokenExpired(response)){
            relogin = ARouter.getInstance().navigation(ReLoginProvider::class.java)
            relogin?.reLogin(TAG)
            Logger.t(TAG).i("TokenInterceptor $originalRequest token失效")
        }
        return response.newBuilder().body(response.body).build()
    }

    private fun isTokenExpired(response: Response): Boolean {
        // Response.body.string 只能调用一次
        val string = copyBuffer(response.body)
        return getCode(string) == "1"
    }


    private fun getCode(string: String?): String {
        return string?.let {
            try {
                if(it.isEmpty()){
                    ("body null")
                }else{
                    if (!JSONObject(string).isNull("type")){
                        JSONObject(string).getString("type")
                    }else{
                        JSONObject(string).getInt("status").toString()
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
                e.toString()
            }
        } ?: error("body null")
    }

    private fun copyBuffer(body: ResponseBody?): String? {
        val source = body?.source()
        source?.request(Long.MAX_VALUE)
        val buffer = source?.buffer
        return buffer?.clone()?.readString(Charset.defaultCharset())
    }

}