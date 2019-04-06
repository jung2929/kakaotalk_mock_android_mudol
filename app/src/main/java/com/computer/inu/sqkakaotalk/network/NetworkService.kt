package com.computer.inu.sqkakaotalk.network

import com.computer.inu.sqkakaotalk.get.GetUserInfomationResponse
import com.computer.inu.sqkakaotalk.post.PostLogoutResponse
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {

    @GET("/v2/user/me")
    fun getUserInfomationResponse(
        @Header("Authorization") Authorization: String
    ): Call<GetUserInfomationResponse>

    @POST("/v1/user/logout")
    fun postLogoutResponse(
        @Header("Authorization") Authorization: String
    ): Call<PostLogoutResponse>
}