package com.lx.net.login.repository

import com.lx.net.base.model.AppBaseEntity
import com.lx.net.login.api.RetrofitService
import com.lx.net.login.model.RegisterBean
import com.lx.net.login.model.UserLoginBean

class LoginRepository {

    private val service by lazy { RetrofitService.getApiService() }

    suspend fun sendVerifyCode(map: MutableMap<String, Any>): AppBaseEntity<String> {

        return service.sendVerifyCode(map)
    }

    suspend fun userRegister(map: MutableMap<String, Any>): AppBaseEntity<RegisterBean> {

        return service.userRegister(map)
    }

    suspend fun userLogin(map: MutableMap<String, Any>): AppBaseEntity<UserLoginBean> {

        return service.userLogin(map)
    }
}