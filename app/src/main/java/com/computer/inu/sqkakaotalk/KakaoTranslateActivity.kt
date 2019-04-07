package com.computer.inu.sqkakaotalk

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.computer.inu.sqkakaotalk.get.GetTranslationResponse
import com.computer.inu.sqkakaotalk.network.ApplicationController
import com.computer.inu.sqkakaotalk.network.NetworkService
import kotlinx.android.synthetic.main.activity_kakao_translate.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KakaoTranslateActivity : AppCompatActivity() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kakao_translate)
        btn_translation.setOnClickListener {
            getTranslation(edt_translate_before.text.toString())
        }

        iv_traslate_finish.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.stay, R.anim.sliding_down)
        }
    }
    fun getTranslation(string: String){
        var getTranslationResponse: Call<GetTranslationResponse> = networkService.getTranslationResponse("KakaoAK b7ac02e4691f063e21176f61fd65692f",string,"kr","en")
        getTranslationResponse.enqueue(object : Callback<GetTranslationResponse> {
            override fun onResponse(call: Call<GetTranslationResponse>?, response: Response<GetTranslationResponse>?) {
                Log.v("TAG", "보드 서버 통신 연결")
                if (response!!.isSuccessful) {
                    tv_translate_after.text = response.body()!!.translated_text.toString()
                }
                else{
                    Log.v("TAG", "마이페이지 서버 값 전달 실패")
                }
            }
            override fun onFailure(call: Call<GetTranslationResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " +t.toString())
            }
        })
    }
}
