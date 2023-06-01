package com.lx.net.common.model

/**
 * @author : lxm
 * @package_name ：com.lx.net.common.utils
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2021/3/18 16:25
 * @description ：选择框item
 */
data class ChooseInfo(

    var name: String? = null,
    var id: Long? = null,
    var value: String? = null,
    var channel: Int? = 1,
    var check: Boolean = false
) {

    constructor(name: String?, value: String?, channel : Int? )
            : this(name, null, value, channel,false)

    constructor(name: String?, id: Long?, channel : Int? )
            : this(name, id, null, channel,false)

    constructor(name: String?, id: Long?, value: String?): this(name, id, value, null,false)

    constructor(name: String?, id: Long?) : this(name, id, null, null,false)

}