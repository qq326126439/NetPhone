package com.lx.net.common.utils

import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.math.BigDecimal
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.regex.Pattern
import javax.crypto.Cipher

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.common.utils
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022/8/2 09:49
 * @description ：
 */
object StringUtil {

    /**
     * 判断MAC格式
     * @param mac
     * @return
     */
    fun macValid(mac: String): Boolean {
        val check = "([A-Fa-f0-9]{2}:){5}[A-Fa-f0-9]{2}"
        val regex = Pattern.compile(check)
        val matcher = regex.matcher(mac)
        return matcher.matches()
    }

    /**
     * 判断密码格式 大于等于8位小于等于18位，字母+数字
     * @param mPwd
     * @return
     */
    fun isCorrectPwd(mPwd: String?): Boolean {
        val check = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_]{8,18}$"
        val regex = Pattern.compile(check)
        val matcher = regex.matcher(mPwd.toString())
        return matcher.matches()
    }

    //验证邮箱格式
    fun validateEmail(email: String): Boolean {
        val regex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}")
        return regex.matches(email)
    }

    //验证手机号码
    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        // 正则表达式判断字符串是否为11位的手机号
        val regex = Regex("^1\\d{10}\$")
        return regex.matches(phoneNumber)
    }

    /**
     * 保留小数点后几位
     */
    fun retainDecimal(value: Double?, pattern: Int = 1): String {
        value?.let {
            val bigDecimal = BigDecimal(it)
            return bigDecimal.setScale(pattern, BigDecimal.ROUND_HALF_UP).toString()
        }
        return "0.0"
    }

    /**
     * 公钥加密
     * @param publicKeyText 公钥
     * @param text 待加密的文本
     * @return /
     */
    fun encryptByPublicKey(publicKeyText: String, text: String): String? {
        val x509EncodedKeySpec2 = X509EncodedKeySpec(Base64.decode(publicKeyText))
        val keyFactory: KeyFactory = KeyFactory.getInstance("RSA")
        val publicKey: PublicKey = keyFactory.generatePublic(x509EncodedKeySpec2)
        val cipher: Cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        val result: ByteArray =
            doLongerCipherFinal(Cipher.ENCRYPT_MODE, cipher, text.toByteArray())!!
        return Base64.encode(result)
    }

    private fun doLongerCipherFinal(opMode: Int, cipher: Cipher, source: ByteArray): ByteArray? {
        val out = ByteArrayOutputStream()
        if (opMode == Cipher.DECRYPT_MODE) {
            out.write(cipher.doFinal(source))
        } else {
            var offset = 0
            val totalSize = source.size
            while (totalSize - offset > 0) {
                val size = Math.min(cipher.getOutputSize(0) - 11, totalSize - offset)
                out.write(cipher.doFinal(source, offset, size))
                offset += size
            }
        }
        out.close()
        return out.toByteArray()
    }
}