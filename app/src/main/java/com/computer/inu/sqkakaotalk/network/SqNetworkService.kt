package com.computer.inu.sqkakaotalk.network
import com.computer.inu.sqkakaotalk.delete.DeleteUserInfoResponse
import com.computer.inu.sqkakaotalk.post.PostEmailAuthenticateResponse
import com.computer.inu.sqkakaotalk.post.PostLoginResponse
import com.computer.inu.sqkakaotalk.post.PostSignUpResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST

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
}
