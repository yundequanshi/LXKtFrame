package com.lx.kt.frame

import retrofit2.http.Body
import retrofit2.http.POST

interface API {

    //登录获取验证码
    @POST("login/in")
    suspend fun loginCheckCode(@Body map: Map<String, String>): LoginData
}