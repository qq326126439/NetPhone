package com.lx.net.common.utils

import com.google.gson.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


/**
 * @author : lxm
 * @package_name ：com.lx.net.common.utils
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2021/3/30 11:53
 * @description ：JSON解析
 */
/**
 * 转换String
 */
fun Any?.toJsonString(): String {
    return Gson().toJson(this)
}

/**
 * 转换String带格式化
 */
fun Any?.toJsonFormatterString(): String {
    val gSon: Gson = GsonBuilder().setPrettyPrinting().create()
    val je: JsonElement = JsonParser.parseString(toJsonString())
    return gSon.toJson(je)
}

/**
 * json String格式化
 */
fun String?.parseString(): String {
    val gSon: Gson = GsonBuilder().setPrettyPrinting().create()
    val jsonElement = JsonParser.parseString(this)
    return gSon.toJson(jsonElement)
}

/**
 * String转换List
 */
inline fun <reified T> String?.toJsonArray(): List<T>? {
    return Gson().fromJson<List<T>>(this, ParameterizedTypeImpl(T::class.java))
}

inline fun <reified T> String.jsonToArray(): List<T> {
    val jsonArray: JsonArray = JsonParser.parseString(this).asJsonArray
    val gson = Gson()
    val list: MutableList<T> = ArrayList()
    for (jsonElement in jsonArray) {
        list += gson.fromJson(jsonElement, T::class.java)
    }
    return list
}

/**
 * 转换成对象
 */
inline fun <reified T> String.toJsonObject(): T {
    return Gson().fromJson(this, T::class.java)
}


/**
 * @author : lxm
 * @description ：泛型实例化
 */
class ParameterizedTypeImpl(private val clazz: Class<*>) : ParameterizedType {
    override fun getRawType(): Type = List::class.java
    override fun getOwnerType(): Type? = null
    override fun getActualTypeArguments(): Array<Type> = arrayOf(clazz)
}