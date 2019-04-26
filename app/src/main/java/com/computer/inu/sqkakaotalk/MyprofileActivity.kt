package com.computer.inu.sqkakaotalk

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.util.Log
import android.view.Gravity
import com.bumptech.glide.Glide
import com.computer.inu.sqkakaotalk.get.GetUserInfomationResponse
import com.computer.inu.sqkakaotalk.get.GetprofileResponse
import com.computer.inu.sqkakaotalk.network.ApplicationController
import com.computer.inu.sqkakaotalk.network.NetworkService
import com.computer.inu.sqkakaotalk.network.SqNetworkService
import com.computer.inu.sqkakaotalk.post.PostLoginResponse
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_friend_list_fragment.*
import kotlinx.android.synthetic.main.activity_myprofile.*
import org.jetbrains.anko.ctx
import org.jetbrains.annotations.Nullable
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream



class MyprofileActivity : AppCompatActivity() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    val SqnetworkService: SqNetworkService by lazy {
        ApplicationController.instance.SqnetworkService
    }
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myprofile)

        if (SharedPreferenceController.getKaKaOAuthorization(this).isNotEmpty()) { // 카카오 로그인일때 정보받아오기
            getKAKAOUserInfoPost()
        }else if(SharedPreferenceController.getSQAuthorization(this).isNotEmpty()){
            getMyProfile()
            getMyprofilePost2()
        }

        tv_myprofile_background.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY);
        idfinish.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.stay, R.anim.sliding_down)
        }

        iv_myprofile_mypicture.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 1)
        }

        if (SharedPreferenceController.getIMAGE(this).isNotEmpty()){
            val decodedString = Base64.decode(SharedPreferenceController.getIMAGE(this), Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            iv_myprofile_mypicture.setImageBitmap(decodedByte)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    val `in` = contentResolver.openInputStream(data!!.data!!)
                    val img = BitmapFactory.decodeStream(`in`)
                    getBase64String(img)
                    `in`!!.close()
                    // 이미지 표시
                    iv_myprofile_mypicture.setImageBitmap(img)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }


      public fun getBase64String ( bitmap : Bitmap)
    {
        val byteArrayOutputStream = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imageBytes = byteArrayOutputStream.toByteArray()
       SharedPreferenceController.setIMAGE(this,Base64.encodeToString(imageBytes, Base64.NO_WRAP))

    }
    fun getMyprofilePost2(){
        var jsonObject = JSONObject()
        jsonObject.put("Email",SharedPreferenceController.getEmail(ctx))
        jsonObject.put("Pw", SharedPreferenceController.getPW(ctx))

//Gson 라이브러리의 Json Parser을 통해 객체를 Json으로!
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        var postLoginResponse: Call<PostLoginResponse> = SqnetworkService.postLoginResponse("application/json",gsonObject)
        postLoginResponse.enqueue(object : Callback<PostLoginResponse> {
            override fun onResponse(call: Call<PostLoginResponse>?, response: Response<PostLoginResponse>?) {
                Log.v("TAG", "보드 서버 통신 연결")
                if (response!!.isSuccessful) {
                    if(response.body()!!.message=="성공") {

                        tv_myprofile_myname.setText(response.body()!!.result[0].Name.toString())
                        tv_myprofile_email.setText(response.body()!!.result[0].Email.toString())

                    }
                }

            }
            override fun onFailure(call: Call<PostLoginResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " +t.toString())
            }
        })
    }
    fun getMyProfile(){
        var getProfileResponse: Call<GetprofileResponse> = SqnetworkService.getprofileResponse("application/json",SharedPreferenceController.getSQAuthorization(ctx))
        getProfileResponse.enqueue(object : Callback<GetprofileResponse> {
            override fun onResponse(call: Call<GetprofileResponse>?, response: Response<GetprofileResponse>?) {
                if (response!!.isSuccessful) {
                    if(response.body()!!.message=="성공"){
                        Glide.with(ctx).load(response.body()!!.result.Prof_img.toString()).into(iv_myprofile_mypicture)
                        Glide.with(ctx).load(response.body()!!.result.Back_img.toString()).into(tv_myprofile_background)
                    }
                }
                else{
                    Log.v("TAG", "채팅 실패")
                }
            }
            override fun onFailure(call: Call<GetprofileResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " +t.toString())
            }
        })
    }
fun getMyprofilePost(){
        var jsonObject = JSONObject()
        jsonObject.put("Email",SharedPreferenceController.getEmail(ctx))
        jsonObject.put("Pw", SharedPreferenceController.getPW(ctx))

//Gson 라이브러리의 Json Parser을 통해 객체를 Json으로!
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        var postLoginResponse: Call<PostLoginResponse> = SqnetworkService.postLoginResponse("application/json",gsonObject)
        postLoginResponse.enqueue(object : Callback<PostLoginResponse> {
            override fun onResponse(call: Call<PostLoginResponse>?, response: Response<PostLoginResponse>?) {
                Log.v("TAG", "보드 서버 통신 연결")
                if (response!!.isSuccessful) {
                    if(response.body()!!.message=="성공") {

                                tv_friend_myname.setText(response.body()!!.result[0].Name.toString())

                    }
                }

            }
            override fun onFailure(call: Call<PostLoginResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " +t.toString())
            }
        })
    }
    fun getKAKAOUserInfoPost(){
        var getUserInfomationResponse: Call<GetUserInfomationResponse> = networkService.getUserInfomationResponse("Bearer "+SharedPreferenceController.getKaKaOAuthorization(this))
        getUserInfomationResponse.enqueue(object : Callback<GetUserInfomationResponse> {
            override fun onResponse(call: Call<GetUserInfomationResponse>?, response: Response<GetUserInfomationResponse>?) {
                Log.v("TAG", "보드 서버 통신 연결")
                if (response!!.isSuccessful) {
                    tv_myprofile_myname.text = response.body()!!.properties.nickname

                    Glide.with(ctx).load(response.body()!!.properties.profile_image.toString()).into(tv_myprofile_background)
                    Glide.with(ctx).load(response.body()!!.properties.profile_image.toString()).into(iv_myprofile_mypicture)
                }
                else{
                    Log.v("TAG", "마이페이지 서버 값 전달 실패")
                }
            }
            override fun onFailure(call: Call<GetUserInfomationResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " +t.toString())
            }
        })
    }

}

