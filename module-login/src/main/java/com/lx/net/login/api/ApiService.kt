package com.lx.net.login.api

import com.lx.net.base.model.AppBaseEntity
import com.lx.net.login.model.RegisterBean
import com.lx.net.login.model.UserLoginBean
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("auth/register")
    suspend fun userRegister(@Body requestBody: Any): AppBaseEntity<RegisterBean>

    @POST("auth/sendCode")
    suspend fun sendVerifyCode(@Body requestBody: Any): AppBaseEntity<String>

    @POST("auth/appLogin")
    suspend fun userLogin(@Body requestBody: Any): AppBaseEntity<UserLoginBean>
}