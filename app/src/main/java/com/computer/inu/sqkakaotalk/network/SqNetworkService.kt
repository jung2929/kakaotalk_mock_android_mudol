package com.computer.inu.sqkakaotalk.network
import com.computer.inu.sqkakaotalk.delete.DeleteFriendInfoResponse
import com.computer.inu.sqkakaotalk.delete.DeleteUserInfoResponse
import com.computer.inu.sqkakaotalk.get.GetDeleteFriendInfoResponse
import com.computer.inu.sqkakaotalk.get.GetFriendResponse
import com.computer.inu.sqkakaotalk.get.GetMystoryResponse
import com.computer.inu.sqkakaotalk.get.GetprofileResponse
import com.computer.inu.sqkakaotalk.post.*
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

    @POST("/kacao/profile")  //프로필 변경
    fun postprofileResponse(
        @Header("Content-type") content_type: String,
        @Header("x-access-token") Authorization: String,
        @Body() body: JsonObject//"Prof_img" :"http://kaca5.com/imageP/%EA%B8%B0%EB%B3%B8_%ED%94%84%EC%82%AC.png",
                                 // Back_img" : "http://kaca5.com/imageP/%EA%B8%B0%EB%B3%B8_%EB%B0%B0%EA%B2%BD.png",
                                 //"Status" : "안녕ㅋ"
    ): Call<PostprofileResponse>

    @GET("/kacao/mystory")  //프로필 히스토리
    fun getMystoryResponse(
        @Header("Content-type") content_type: String,
        @Header("x-access-token") Authorization: String
    ): Call<GetMystoryResponse>
    @GET("/kacao/friend")  //친구목록
    fun getFriendResponse(
        @Header("Content-type") content_type: String,
        @Header("x-access-token") Authorization: String
    ): Call<GetFriendResponse>

    @POST("/kacao/friend_add")  //친구추가
    fun postAddFriendResponse(
        @Header("Content-type") content_type: String,
        @Header("x-access-token") Authorization: String,
        @Body() body: JsonObject//	"Friend_Email":"test@naver.com",
                                 //    "Tel" : "01"
    ): Call<PostAddFriendResponse>
    @DELETE("/kacao/friend_delete")  //친구차단
    fun deleteFriendInfoResponse(
        @Header("Content-type") content_type: String,
        @Header("x-access-token") Authorization : String,
        @Body() body: JsonObject  //	"Friend_Email":"test@naver.com"
    ): Call<DeleteFriendInfoResponse>
    @GET("/kacao/friend_delete")  //차단한 친구 목록
    fun getDeleteFriendInfoResponse(
        @Header("Content-type") content_type: String,
        @Header("x-access-token") Authorization : String
    ): Call<GetDeleteFriendInfoResponse>

}
