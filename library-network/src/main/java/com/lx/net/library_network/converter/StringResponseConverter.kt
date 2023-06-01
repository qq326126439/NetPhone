package com.lx.net.library_network.converter

import okhttp3.ResponseBody
import retrofit2.Converter

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.network.converter
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022/6/20 17:06
 * @description ：
 */
class StringResponseConverter: Converter<ResponseBody, String> {

    override fun convert(value: ResponseBody): String {
        return value.string()
    }

}