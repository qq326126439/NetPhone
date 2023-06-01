package com.lx.net.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.lx.net.base.activity.BaseViewModel
import com.lx.net.base.ext.VmData
import com.lx.net.base.ext.VmLiveData
import com.lx.net.base.ext.launchVmRequest
import com.lx.net.common.constant.Constants
import com.lx.net.common.utils.LogCompat.logE
import com.lx.net.common.utils.RSAUtils
import com.lx.net.login.model.RegisterBean
import com.lx.net.login.model.UserLoginBean
import com.lx.net.login.repository.LoginRepository

class LoginViewModel : BaseViewModel() {

    //测试邀请码
    private val inviteCode = 1658828787300831232

    private val _sendCode: VmLiveData<String> = MutableLiveData()
    val sendCode: VmData<String> = _sendCode

    private val _userRegister: VmLiveData<RegisterBean> = MutableLiveData()
    val userRegister: VmData<RegisterBean> = _userRegister

    private val _userLogin: VmLiveData<UserLoginBean> = MutableLiveData()
    val userLogin: VmData<UserLoginBean> = _userLogin

    private val repository by lazy { LoginRepository() }


    fun getVerifyCode(type: Int, email: String) {

        val map = mutableMapOf<String, Any>()
        map["type"] = type

        var pub_key: String = RSAUtils.encryptSafe(email, Constants.PUB_KEY)

        ("pub_key = $pub_key").logE()
        map["email"] = pub_key

        launchVmRequest(_sendCode) {

            repository.sendVerifyCode(map)
        }
    }


    //code : 邮箱验证码 不加密
    fun userRegister(email: String, inviteCode: String, code: String) {

        val map = mutableMapOf<String, Any>()
        var pubKeyEmail: String = RSAUtils.encryptSafe(email, Constants.PUB_KEY)
        map["email"] = pubKeyEmail
        map["inviteCode"] = this.inviteCode
        map["code"] = code


        launchVmRequest(_userRegister) {

            repository.userRegister(map)
        }

    }

    //用户登录
    fun userLogin(email: String, code: String) {

        val map = mutableMapOf<String, Any>()
        var pubKeyEmail: String = RSAUtils.encryptSafe(email, Constants.PUB_KEY)
        map["email"] = pubKeyEmail
        map["code"] = code

        launchVmRequest(_userLogin) {

            repository.userLogin(map)
        }

    }

}