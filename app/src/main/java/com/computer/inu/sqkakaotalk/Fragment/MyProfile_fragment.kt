package com.computer.inu.sqkakaotalk.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.computer.inu.sqkakaotalk.*
import com.computer.inu.sqkakaotalk.get.GetUserInfomationResponse
import com.computer.inu.sqkakaotalk.get.GetprofileResponse
import com.computer.inu.sqkakaotalk.network.ApplicationController
import com.computer.inu.sqkakaotalk.network.NetworkService
import com.computer.inu.sqkakaotalk.network.SqNetworkService
import com.computer.inu.sqkakaotalk.post.PostLoginResponse
import com.computer.inu.sqkakaotalk.post.PostLogoutResponse
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_friend_list_fragment.*
import kotlinx.android.synthetic.main.activity_my_profile_fragment.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MyProfile_fragment : Fragment() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    val SqnetworkService: SqNetworkService by lazy {
        ApplicationController.instance.SqnetworkService
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val homeFragmentView: View = inflater!!.inflate(R.layout.activity_my_profile_fragment, container, false)
        return homeFragmentView

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getMyprofilePost()
        getMyProfile()
        tv_myprofile_banFriend.setOnClickListener {
           startActivity<BanFriendActivity>()
        }
        iv_myprofile_friendshop.setOnClickListener {
         startActivity<EmoticonShopActivity>()
        }
  /*      if (SharedPreferenceController.getIMAGE(ctx).isNotEmpty()){
            val decodedString = Base64.decode(SharedPreferenceController.getIMAGE(ctx), Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            iv_main_mypicture.setImageBitmap(decodedByte)
        }*/ //내부 DB에 저장된 사진을 입히는 과정
 if(SharedPreferenceController.getKaKaOAuthorization(ctx).isNotEmpty()){
        getkakaoUserInfoPost()}
        lr_profile_detail_mypge_allview.visibility=View.GONE
        iv_main_mypage.setOnClickListener {
            lr_profile_allview.visibility=View.GONE
            lr_profile_detail_mypge_allview.visibility=View.VISIBLE
        }
        iv_mypage_backbutton.setOnClickListener {
            lr_profile_detail_mypge_allview.visibility=View.GONE
            lr_profile_allview.visibility=View.VISIBLE
        }
        tv_myprofile_logout.setOnClickListener {

            if(SharedPreferenceController.getKaKaOAuthorization(ctx).isNotEmpty())
            { PostkakaoLogoutResponse()
             SharedPreferenceController.clearKaKaoSPC(ctx)
                SharedPreferenceController.clearAutoLogin(ctx)
                toast("카카오 로그아웃")
            }else if(SharedPreferenceController.getSQAuthorization(ctx).isNotEmpty()){
                SharedPreferenceController.clearSQSPC(ctx)
                SharedPreferenceController.clearAutoLogin(ctx)
                SharedPreferenceController.clearEmail(ctx)
                SharedPreferenceController.clearPW(ctx)
                toast("SQ 로그아웃")
                activity!!.finish()
            }
        }
        tv_myprofile_pay.setOnClickListener {
            startActivity<PayActivity>()
        }

    }
    fun getkakaoUserInfoPost(){
        var getUserInfomationResponse: Call<GetUserInfomationResponse> = networkService.getUserInfomationResponse("Bearer "+SharedPreferenceController.getKaKaOAuthorization(ctx))
        getUserInfomationResponse.enqueue(object : Callback<GetUserInfomationResponse> {
            override fun onResponse(call: Call<GetUserInfomationResponse>?, response: Response<GetUserInfomationResponse>?) {
                Log.v("TAG", "통신 연결")
                if (response!!.isSuccessful) {
                    tv_main_myname.text = response.body()!!.properties.nickname
                    tv_mypage_name.text= response.body()!!.properties.nickname
                        Glide.with(ctx).load(response.body()!!.properties.profile_image.toString()).into(iv_main_mypicture)
                    Glide.with(ctx).load(response.body()!!.properties.profile_image.toString()).into(iv_mypage_mypicture)

                }
                else{
                    Log.v("TAG", "카카오통신 실패")
                }
            }
            override fun onFailure(call: Call<GetUserInfomationResponse>?, t: Throwable?) {
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

                        tv_main_myname.setText(response.body()!!.result[0].Name.toString())
                        tv_main_myemail.setText(response.body()!!.result[0].Email.toString())
                        tv_mypage_name.setText(response.body()!!.result[0].Name.toString())
                        tv_mypage_email.setText(response.body()!!.result[0].Email.toString())


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

                        Glide.with(ctx).load(response.body()!!.result.Prof_img.toString()).into(iv_main_mypicture)
                        Glide.with(ctx).load(response.body()!!.result.Prof_img.toString()).into(iv_mypage_mypicture)
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


    fun PostkakaoLogoutResponse(){
        var postLogoutResponse: Call<PostLogoutResponse> = networkService.postLogoutResponse("Bearer "+SharedPreferenceController.getKaKaOAuthorization(ctx))
        postLogoutResponse.enqueue(object : Callback<PostLogoutResponse> {
            override fun onResponse(call: Call<PostLogoutResponse>?, response: Response<PostLogoutResponse>?) {
                Log.v("TAG", "카카오 로그아웃 통신 ")
                if (response!!.isSuccessful) {
                    SharedPreferenceController.clearKaKaoSPC(ctx)
                    SharedPreferenceController.clearAutoLogin(ctx)
                    toast("로그아웃 id="+response!!.body()!!.id.toString())

                    activity!!.finish()
                }
                else{
                    Log.v("TAG", "카카오 로그아웃 실패")
                }
            }
            override fun onFailure(call: Call<PostLogoutResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " +t.toString())
            }
        })
    }

}
