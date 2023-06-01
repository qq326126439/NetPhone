package com.lx.net.library_network.ext

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Invocation

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.network.ext
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022/7/12 17:53
 * @description ：
 */

fun String.jsonToRequestBody(contentType: MediaType = "application/json; charset=utf-8".toMediaType()) = toRequestBody(contentType)

/**
 * @param annotationClass
 * return T Retrofit定义在方法上的注解，例如[POST],[GET]
 */
inline fun <reified T : Annotation> Request.getMethodAnnotation(annotationClass: Class<T>): T? {
    return tag(Invocation::class.java)?.method()?.getAnnotation(annotationClass)
}

inline fun <reified T : Annotation> Request.containMethodAnnotation(annotationClass: Class<T>): Boolean {
    return getMethodAnnotation(annotationClass) != null
}