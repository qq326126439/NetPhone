package com.lx.net.common.model

import com.google.gson.annotations.SerializedName

/**
 * 分页基础实体
 *
 * @param T
 * @property current
 * @property optimizeCountSql
 * @property orders
 * @property pages
 * @property records
 * @property searchCount
 * @property size
 * @property total
 * @constructor Create empty Page bean
 */
data class PageBean<T>(
    @SerializedName("current")
    val current: Int = 0,
    @SerializedName("optimizeCountSql")
    val optimizeCountSql: Boolean = false,
    @SerializedName("orders")
    val orders: List<Any> = emptyList(),
    @SerializedName("pages")
    val pages: Int = 0,
    @SerializedName("records")
    var records: List<T> = emptyList(),
    @SerializedName("searchCount")
    val searchCount: Boolean = false,
    @SerializedName("size")
    val size: Int = 0,
    @SerializedName("total")
    val total: Int = 0,
)
