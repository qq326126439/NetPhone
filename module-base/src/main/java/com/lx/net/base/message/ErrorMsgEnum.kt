package com.lx.net.base.message

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.base.message
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022/6/16 10:13
 * @description ：错误枚举类
 */
enum class ErrorMsgEnum(val code: Int, val message: String) {

    HTTP_SERVER_ERROR(10001, "服务器未知异常"),
    HTTP_REQUEST_PARAM_ERROR(10002, "请求参数缺失或无效"),
    HTTP_INVALID_AUTH(10003, "认证信息无效或已过期"),
    HTTP_ACCESS_DENIED(10004, "无权限操作"),
    HTTP_REQUEST_INVALID(10005, "错误的请求"),
    HTTP_UNKNOWN_HOST(10005, "未找到Url服务器"),
    HTTP_UNKNOWN_SERVICE(10006, "未知服务请求"),
    HTTP_SOCKET_TIMEOUT(10007, "服务器连接超时"),
    HTTP_CONNECT_ERROR(10008, "服务器连接异常"),
    HTTP_SSL_HANDSHAKE(10009, "SSL证书异常"),
    HTTP_GET_TOKEN_ERROR(20000, "获取 IM Token 失败"),
    HTTP_SEND_CODE_OVER_FREQUENCY(20001, "发送短信请求过于频繁"),
    HTTP_SEND_CODE_FAILED(20002, "短信发送失败"),
    HTTP_SEND_CODE_INVALID_PHONE_NUMBER(20003, "手机号无效"),
    HTTP_CODE_NOT_SEND(20004, "短信验证码尚未发送"),
    HTTP_CODE_INVALID(20005, "短信验证码无效"),
    HTTP_CODE_EMPTY(20006, "验证码不能为空"),
    FILE_NOT_FOUND(30000, "文件获取失败"),
    VERSION_EXIST(40000, "版本已存在"),
    VERSION_NO_EXIST(40001, "版本不存在"),
    NO_NEW_VERSION(40002, "没有新版本"),
    JSON_PARSE_ERROR(40003, "数据解析异常"),
    JSON_SYNTAX_ERROR(40004, "数据参数不匹配"),
    HTTP_ERROR_MSG_NULL(60000, "错误信息为空"),
    UNKNOWN_ERROR_MESSAGE(50000, "未知错误")

}