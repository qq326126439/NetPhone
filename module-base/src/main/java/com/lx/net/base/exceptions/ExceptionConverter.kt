package com.lx.net.base.exceptions

import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.lx.net.base.message.ErrorMsg
import com.lx.net.base.message.ErrorMsgEnum
import retrofit2.HttpException
import java.io.FileNotFoundException
import java.net.*
import javax.net.ssl.SSLHandshakeException
/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.base.exceptions
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022/6/16 10:13
 * @description ：异常解析类
 */
object ExceptionConverter {

    fun create(throwable: Throwable?): ErrorMsg {

        return if (throwable is AppException) {
            ErrorMsg(throwable.errorCode, throwable.message.toString(), throwable)
        } else {
            parseExceptionConverter(throwable)
        }
    }

    private fun parseExceptionConverter(throwable: Throwable?): ErrorMsg {
        val errorMsgEnum = when (throwable) {
            is HttpException -> {
                ErrorMsgEnum.HTTP_SERVER_ERROR
            }
            is UnknownHostException -> {
                ErrorMsgEnum.HTTP_UNKNOWN_HOST
            }
            is UnknownServiceException -> {
                ErrorMsgEnum.HTTP_UNKNOWN_SERVICE
            }
            is SocketTimeoutException -> {
                ErrorMsgEnum.HTTP_SOCKET_TIMEOUT
            }
            is ConnectException, is SocketException -> {
                ErrorMsgEnum.HTTP_CONNECT_ERROR
            }
            is SSLHandshakeException -> {
                ErrorMsgEnum.HTTP_SSL_HANDSHAKE
            }
            is JsonSyntaxException -> {
                ErrorMsgEnum.JSON_SYNTAX_ERROR
            }
            is JsonParseException -> {
                ErrorMsgEnum.JSON_PARSE_ERROR
            }
            is FileNotFoundException ->{
                ErrorMsgEnum.FILE_NOT_FOUND
            }
            else -> {
                ErrorMsgEnum.UNKNOWN_ERROR_MESSAGE
            }
        }
        return ErrorMsg(errorMsgEnum, throwable)
    }

}