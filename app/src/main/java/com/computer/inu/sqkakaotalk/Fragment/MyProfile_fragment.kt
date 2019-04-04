package com.computer.inu.sqkakaotalk.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.computer.inu.sqkakaotalk.LoginActivity
import com.computer.inu.sqkakaotalk.network.ApplicationController
import com.computer.inu.sqkakaotalk.network.NetworkService
import com.computer.inu.sqkakaotalk.PayActivity
import com.computer.inu.sqkakaotalk.R
import com.computer.inu.sqkakaotalk.SharedPreferenceController
import com.computer.inu.sqkakaotalk.get.GetUserInfomationResponse
import kotlinx.android.synthetic.main.activity_my_profile_fragment.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyProfile_fragment : Fragment() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val homeFragmentView: View = inflater!!.inflate(R.layout.activity_my_profile_fragment, container, false)
        return homeFragmentView

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

  /*      if (SharedPreferenceController.getIMAGE(ctx).isNotEmpty()){
            val decodedString = Base64.decode(SharedPreferenceController.getIMAGE(ctx), Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            iv_main_mypicture.setImageBitmap(decodedByte)
        }*/ //내부 DB에 저장된 사진을 입히는 과정

        getUserInfoPost()

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
            SharedPreferenceController.AutoclearSPC(ctx)
            toast("로그아웃")
            startActivity<LoginActivity>()
        }
        tv_myprofile_pay.setOnClickListener {
            startActivity<PayActivity>()
        }

    }
    fun getUserInfoPost(){
        var getUserInfomationResponse: Call<GetUserInfomationResponse> = networkService.getUserInfomationResponse("Bearer CHuNK4GoYmpm_h1I8eN902ccDedS4vn9t8vKXwopyWAAAAFp5sb3HA")
        getUserInfomationResponse.enqueue(object : Callback<GetUserInfomationResponse> {
            override fun onResponse(call: Call<GetUserInfomationResponse>?, response: Response<GetUserInfomationResponse>?) {
                Log.v("TAG", "보드 서버 통신 연결")
                if (response!!.isSuccessful) {
                    tv_main_myname.text = response.body()!!.properties.nickname

                 /*   if (response.body()!!.data.image != null) {
                        Glide.with(context).load(response.body()!!.data.image).into(mypage_background_img)
                        image = response.body()!!.data.image!!
                    }*/
                toast(response.body()!!.properties.nickname.toString())
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
