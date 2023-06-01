package com.lx.net.common.constant

/**
 * @author : lxm
 * @version ：
 * @package_name ：cc.crrcgc.hse.common.constant
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022/7/28 18:00
 * @description ：intent 请求码
 */
object RequestCode {

    const val REQ_PERMISSION_LOCATION = "LOCATION"
    const val REQUEST_PERMISSION = 0x001
    const val REQ_PERMISSION_STORAGE = "STORAGE"
    const val REQ_CODE_PERMISSION = 1
    const val REQ_CODE_LOCATION_SETTINGS = 2
    const val REQ_CODE_STORAGE_SETTINGS = 3
    /**
     * 请求一个用户
     */
    const val USER = 1

    /**
     * 请求多个用户
     */
    const val USERS = 2
    const val org = 3
}