package com.lx.net.library_network.converter

import com.lx.net.library_network.ext.jsonToRequestBody
import okhttp3.RequestBody
import retrofit2.Converter

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.network.converter
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022/6/20 17:07
 * @description ：
 */
class StringRequestConverter : Converter<String, RequestBody> {

    override fun convert(value: String): RequestBody =
        value.jsonToRequestBody()

}