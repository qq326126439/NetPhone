package com.lx.net.library_network.converter

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.network.converter
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022/6/20 17:03
 * @description ：
 */
class StringConverterFactory : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        if(type == String::class.java)
            return StringResponseConverter()
        return super.responseBodyConverter(type, annotations, retrofit)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
         if(type == String::class.java)
            return StringRequestConverter()
         return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
    }

}