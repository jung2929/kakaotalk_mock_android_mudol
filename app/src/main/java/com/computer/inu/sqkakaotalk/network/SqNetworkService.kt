package com.computer.inu.sqkakaotalk.network
import com.computer.inu.sqkakaotalk.delete.DeleteFavoriteResponse
import com.computer.inu.sqkakaotalk.delete.DeleteFriendInfoResponse
import com.computer.inu.sqkakaotalk.delete.DeleteUserInfoResponse
import com.computer.inu.sqkakaotalk.get.*
import com.computer.inu.sqkakaotalk.post.*
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*
import okhttp3.RequestBody
import retrofit2.http.HTTP



interface SqNetworkService {
    @POST("/kacao/emailAuthenticate")  //이메일 인증
    fun postEmailAuthenticateResponse(
        @Body() body: JsonObject
    ): Call<PostEmailAuthenticateResponse>

    @POST("/kacao/user")  //회원가입
    fun postSignUpResponse(
        @Body() body: JsonObject
    ): Call<PostSignUpResponse>
    @DELETE("/kacao/user")  //회원탈퇴
    fun deleteUserInfoResponse(
        @Header("Content-Type") content_type: String,
        @Header("x-access-token") Authorization : String
    ): Call<DeleteUserInfoResponse>
    @POST("/kacao/login")  //로그인
    fun postLoginResponse(
        @Header("Content-Type") content_type: String,
        @Body() body: JsonObject
    ): Call<PostLoginResponse>
    @GET("/kacao/profile")  //프로필 사진 가져오기
    fun getprofileResponse(
        @Header("Content-Type") content_type: String,
        @Header("x-access-token") Authorization: String
    ): Call<GetprofileResponse>

    @POST("/kacao/profile")  //프로필 변경
    fun postprofileResponse(
        @Header("Content-Type") content_type: String,
        @Header("x-access-token") Authorization: String,
        @Body() body: JsonObject//"Prof_img" :"http://kaca5.com/imageP/%EA%B8%B0%EB%B3%B8_%ED%94%84%EC%82%AC.png",
                                 // Back_img" : "http://kaca5.com/imageP/%EA%B8%B0%EB%B3%B8_%EB%B0%B0%EA%B2%BD.png",
                                 //"Status" : "안녕ㅋ"
    ): Call<PostprofileResponse>

    @GET("/kacao/mystory")  //프로필 히스토리
    fun getMystoryResponse(
        @Header("Content-Type") content_type: String,
        @Header("x-access-token") Authorization: String
    ): Call<GetMystoryResponse>
    @GET("/kacao/friend")  //친구목록
    fun getFriendResponse(
        @Header("Content-Type") content_type: String,
        @Header("x-access-token") Authorization: String
    ): Call<GetFriendResponse>

    @POST("/kacao/friend_add")  //친구추가
    fun postAddFriendResponse(
        @Header("Content-Type") content_type: String,
        @Header("x-access-token") Authorization: String,
        @Body() body: JsonObject//	"Friend_Email":"test@naver.com",
                                 //    "Tel" : "01"
    ): Call<PostAddFriendResponse>

    @HTTP(method = "DELETE", path = "/kacao/friend_delete", hasBody = true)
    fun deleteFriendInfoResponse(
        @Header("Content-Type") content_type: String,
        @Header("x-access-token") Authorization : String,
        @Body `object`: JsonObject
    ):  Call<DeleteFriendInfoResponse>


    @PATCH("/kacao/friend_delete")   //차단해제
    fun RecoverFriendInfoResponse(
        @Header("Content-Type") content_type: String,
        @Header("x-access-token") Authorization : String,
        @Body() body: JsonObject
    ):  Call<DeleteFriendInfoResponse>

    @GET("/kacao/friend_delete")  //차단한 친구 목록
    fun getDeleteFriendInfoResponse(
        @Header("Content-Type") content_type: String,
        @Header("x-access-token") Authorization : String
    ): Call<GetDeleteFriendInfoResponse>
    @GET("/kacao/friend/{Name}")  //친구검색
    fun getSearchFriendInfoResponse(
        @Header("Content-type") content_type: String,
        @Header("x-access-token") Authorization : String,
        @Path("Name") Name: String
    ): Call<GetSearchFriendInfoResponse>
    @POST("/kacao/favorites")  //즐겨찾기 추가
    fun postFavoriteResponse(
        @Header("Content-Type") content_type: String,
        @Header("x-access-token") Authorization : String,
        @Body() body: JsonObject
    ): Call<PostFavoriteResponse>
    @GET("/kacao/favorites")  //즐겨찾기 가져오기
    fun getFavoriteResponse(
        @Header("Content-Type") content_type: String,
        @Header("x-access-token") Authorization : String
    ): Call<GetFavoriteResponse>

    @HTTP(method = "DELETE", path = "/kacao/favorites", hasBody = true)
    fun deleteFavoriteResponse(
        @Header("Content-Type") content_type: String,
        @Header("x-access-token") Authorization : String,
        @Body `object`: JsonObject
    ): Call<DeleteFavoriteResponse>

    @GET("/kacao/emoticon")  //이모티콘 (전체)
    fun getEmoticonResponse(
        @Header("Content-Type") content_type: String,
        @Header("x-access-token") Authorization : String
    ): Call<GetEmoticonResponse>

    @POST("/kacao/chat")  //채팅봇
    fun postChatResponse(
        @Header("Content-Type") content_type: String,
        @Header("x-access-token") Authorization: String,
        @Body() body: JsonObject//	"Name":"맹구",
                                //  "Text" : "01"
    ): Call<PostChatResponse>
}
