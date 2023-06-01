package com.lx.net.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/***********************************************************************
 * <p>@description:
 * <p>@author: pengl
 * <p>@created on: 2022/8/8 0008 18:18
 * <p>@version: 1.0.0
 * <p>@modify time:2022/8/8 0008 18:18
 **********************************************************************/
open class Converter<T> {

    @TypeConverter
    fun stringToObject(value: String?): List<T>? {
        val listType = object : TypeToken<List<T>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun objectToString(list: List<T>?): String? {
        return Gson().toJson(list)
    }

}