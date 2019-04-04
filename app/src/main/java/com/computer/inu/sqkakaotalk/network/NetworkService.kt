package com.computer.inu.sqkakaotalk.network

import com.computer.inu.sqkakaotalk.get.GetUserInfomationResponse
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {

    @GET("/v2/user/me")
    fun getUserInfomationResponse(
        @Header("Authorization") Authorization: String
    ): Call<GetUserInfomationResponse>

    @GET("/v2/user/me")
    fun getCodeResponse(
        @Header("Authorization") Authorization: String
    ): Call<GetUserInfomationResponse>
}