package com.computer.inu.sqkakaotalk.network
import com.computer.inu.sqkakaotalk.delete.DeleteUserInfoResponse
import com.computer.inu.sqkakaotalk.get.GetFriendResponse
import com.computer.inu.sqkakaotalk.get.GetMystoryResponse
import com.computer.inu.sqkakaotalk.get.GetprofileResponse
import com.computer.inu.sqkakaotalk.post.PostEmailAuthenticateResponse
import com.computer.inu.sqkakaotalk.post.PostLoginResponse
import com.computer.inu.sqkakaotalk.post.PostSignUpResponse
import com.computer.inu.sqkakaotalk.post.PostprofileResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface SqNetworkService {
    @POST("/kacao/emailAuthenticate")  //이메일 인증
    fun posteEmailAuthenticateResponse(
        @Body() body: JsonObject
    ): Call<PostEmailAuthenticateResponse>

    @POST("/kacao/user")  //회원가입
    fun postSignUpResponse(
        @Body() body: JsonObject
    ): Call<PostSignUpResponse>
    @DELETE("/kacao/user")  //회원탈퇴
    fun deleteUserInfoResponse(
        @Header("Content-type") content_type: String,
        @Header("x-access-token") Authorization : String
    ): Call<DeleteUserInfoResponse>
    @POST("/kacao/login")  //로그인
    fun postLoginResponse(
        @Header("Content-type") content_type: String,
        @Body() body: JsonObject
    ): Call<PostLoginResponse>
    @GET("/kacao/profile")  //프로필 사진 가져오기
    fun getprofileResponse(
        @Header("Content-type") content_type: String,
        @Header("x-access-token") Authorization: String
    ): Call<GetprofileResponse>

    @POST("/kacao/profile")  //프로필 추가 및 수정
    fun postprofileResponse(
        @Header("Content-type") content_type: String,
        @Header("x-access-token") Authorization: String
    ): Call<PostprofileResponse>

    @GET("/kacao/mystory")  //프로필 추가 및 수정
    fun getMystoryResponse(
        @Header("Content-type") content_type: String,
        @Header("x-access-token") Authorization: String
    ): Call<GetMystoryResponse>
    @GET("/kacao/friend")  //프로필 추가 및 수정
    fun getFriendResponse(
        @Header("Content-type") content_type: String,
        @Header("x-access-token") Authorization: String
    ): Call<GetFriendResponse>
}
