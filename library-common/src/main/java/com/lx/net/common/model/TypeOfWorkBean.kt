// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json     = Json { allowStructuredMapKeys = true }
// val response = json.parse(Response.serializer(), jsonString)

package com.lx.net.common.model

import com.google.gson.annotations.SerializedName


data class TypeOfWorkBean (
    val current: Long,

    val orders: List<String>,
    val pages: Long,
    val records: List<TypeOfWorkRecord>,
    val searchCount: Boolean,
    val size: Long,
    val total: Long
)

data class TypeOfWorkRecord (
    /**
     * 启用/禁用
     */
    val disabled: Boolean,

    /**
     * id
     */
    val id: Long,

    /**
     * 岗位名称
     */
    val name: String,

    /**
     * 排序
     */
    val orderNo: Long,

    /**
     * 所属机构id
     */
    @SerializedName("organId")
    val organID: Long,

    /**
     * 机构名称
     */
    val organName: String,

    /**
     * 备注
     */
    val remarks: String
)