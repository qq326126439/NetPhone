package com.lx.net.common.utils

import com.lx.net.common.constant.Constants.NULL_CHARACTER
import java.security.MessageDigest

/**
 * @author : lxm
 * @package_name ：com.lx.net.common.utils
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2021/4/8 19:22
 * @description ：Md5工具
 */
object Md5Util {

    private val TAG = Md5Util::class.java.simpleName

    fun encode(encode: String): String {
        try {
            val instance: MessageDigest = MessageDigest.getInstance("MD5")//获取md5加密对象
            val digest: ByteArray = instance.digest(encode.toByteArray())//对字符串加密，返回字节数组
            val sb = StringBuffer()
            for (b in digest) {
                val i: Int = b.toInt() and 0xff//获取低八位有效值
                var hexString = Integer.toHexString(i)//将整数转化为16进制
                if (hexString.length < 2) {
                    hexString = "0$hexString"//如果是一位的话，补0
                }
                sb.append(hexString)
            }
            return sb.toString()

        } catch (e: Exception) {
            e.printStackTrace()
            return NULL_CHARACTER
        }
    }

    private fun getMd5(oldStr: String): String? {
        val hexDigits = charArrayOf(
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'
        )
        return try { // 参数oldStr表示要加密的字符串
            // 转换成字节流
            val oldBytes = oldStr.toByteArray()
            // 得到对象
            val md = MessageDigest.getInstance("MD5")
            // 初始化
            md.update(oldBytes)
            // 运行加密算法
            val newBytes = md.digest()
            // 构造长度为2倍的字符串
            val newStr = CharArray(32)
            // 循环进行处理
            for (i in 0..15) {
                val tmp = newBytes[i]
                newStr[2 * i] = hexDigits[tmp.toInt() ushr 4 and 0xf]
                newStr[2 * i + 1] = hexDigits[tmp.toInt() and 0xf]
            }
            String(newStr)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}

