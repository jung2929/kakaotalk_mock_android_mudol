package com.computer.inu.sqkakaotalk.network

import com.computer.inu.sqkakaotalk.get.GetTranslationResponse
import com.computer.inu.sqkakaotalk.get.GetUserInfomationResponse
import com.computer.inu.sqkakaotalk.post.PostLogoutResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface NetworkService {

    @GET("/v2/user/me")
    fun getUserInfomationResponse(
        @Header("Authorization") Authorization: String
    ): Call<GetUserInfomationResponse>

    @POST("/v1/user/logout")
    fun postLogoutResponse(
        @Header("Authorization") Authorization: String
    ): Call<PostLogoutResponse>

    @GET("/v1/translation/translate")
    fun getTranslationResponse(
        @Header("Authorization") Authorization: String,
        @Query("query") query: String,
        @Query("src_lang") src_lang: String,
        @Query("target_lang") target_lang: String
    ): Call<GetTranslationResponse>

}